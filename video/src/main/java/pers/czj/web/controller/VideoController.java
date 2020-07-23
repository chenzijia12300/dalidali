package pers.czj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.czj.common.CommonResult;
import pers.czj.common.VideoBasicInfo;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.constant.VideoResolutionEnum;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.dto.VideoInputDto;
import pers.czj.entity.Video;
import pers.czj.entity.VideoLog;
import pers.czj.exception.CategoryException;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;
import pers.czj.service.CategoryService;
import pers.czj.service.PlayNumTabService;
import pers.czj.service.VideoLogService;
import pers.czj.service.VideoService;
import pers.czj.util.VideoUtils;
import pers.czj.utils.MinIOUtils;
import pers.czj.utils.RedisUtils;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDate;
import java.util.*;

/**
 * 创建在 2020/7/11 23:43
 */
@CrossOrigin
@RestController
@Api("视频控制器")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoLogService videoLogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MinIOUtils minIOUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PlayNumTabService playNumTabService;

    @Value("${redis.category-pre-key}")
    private String categoryTopKey;

    @Value("${redis.play-key}")
    private String playKey;

    private String dir = System.getProperty("user.dir");


    @PostMapping("/video")
    @ApiOperation("添加视频")
    public CommonResult addVideo(HttpSession httpSession,@RequestBody @Validated VideoInputDto dto) throws VideoException {
        Video video = dto.convert();
        log.debug("sessionID:{}",httpSession.getId());
        long userId = /*(long) httpSession.getAttribute("USER_ID");*/ 1;
        VideoBasicInfo basicInfo = (VideoBasicInfo) httpSession.getAttribute("VIDEO_INFO");
        video.setUid(userId);
        video.setLength(basicInfo.getDuration());
        video.setCover(basicInfo.getCover());
        video.setResolutionState(VideoResolutionEnum.valueOf("P_"+basicInfo.getHeight()));
        video.setUrls(basicInfo.getUrl());
        boolean flag = videoService.save(video);
        if (!flag){
            throw new VideoException("添加视频失败，请重试尝试");
        }
        //这里应该要有个消息队列
        return CommonResult.success("你滴视频提交成功~，等待管理员的审核把");
    }

    @GetMapping("/video/{id}")
    @ApiOperation("获得视频的详细信息")
    public CommonResult findVideoById(HttpSession httpSession,@PathVariable("id")@Min(1) long id) throws VideoException {
        long userId = /*(long) httpSession.getAttribute("USER_ID");*/ 1;
        VideoDetailsOutputDto detailsOutputDto = videoService.findDetailsById(id);
        log.info("当前日期：{}",LocalDate.now());
        if(!redisUtils.getBit(playKey+id,userId)){
            videoService.incrPlayNum(id,1);
            redisUtils.setBit(playKey+id,userId,true);
            redisUtils.zincrBy(LocalDate.now().toString()+"::"+detailsOutputDto.getCategoryId(),1, String.valueOf(detailsOutputDto.getId()));
        }
        return CommonResult.success(detailsOutputDto);
    }

    @PostMapping("/video/upload")
    public CommonResult uploadVideo(HttpSession httpSession,MultipartFile file) throws VideoException, UserException, IOException {
/*        if (httpSession.isNew()){
            httpSession.invalidate();
            throw new UserException("请重新登录！");
        }*/
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        log.info("文件名:{}\t文件后缀:{}",filename,suffix);
        String UUID = java.util.UUID.randomUUID().toString();
        log.debug("随机生成的文件名为：{}",UUID);
        File temp = new File(dir,UUID+suffix);
        try {
            file.transferTo(temp);
        } catch (IOException e) {
            log.error("存储服务器本地出现问题:{}",file.getName());
            throw new VideoException("上传视频出现错误，请重新尝试");
        }
        log.debug("文件存储路径:{}",temp.getAbsoluteFile());
        //上传视频文件到文件服务器
        String url = minIOUtils.uploadFile(temp.getName(),new FileInputStream(temp));
        log.info("dir:{},name:{}",dir,temp.getName());
        VideoBasicInfo basicInfo = VideoUtils.getVideoInfo(dir,temp.getName());
        httpSession.setAttribute("VIDEO_INFO",basicInfo);
        basicInfo.setUrl(url);
        return CommonResult.success(url);
    }

    @ApiOperation("获得该分类类型下的排行榜")
    @GetMapping("/video/top/{name}")
    public CommonResult findTopVideoByCategory(@PathVariable("name")String name) throws ConnectException {
        return CommonResult.success(redisUtils.get(categoryTopKey+name));
    }

    /**
     * @author czj
     * 设置排行榜【定时任务】
     * @date 2020/7/17 22:17
     * @param []
     * @return void
     */
    @GetMapping("/video/setTop")
    @ApiOperation("手动调用定时任务（待删）")
    public CommonResult setTopVideoData() throws CategoryException, ConnectException {
        List<CategoryOutputDto> categoryOutputDtos = categoryService.listCategory();
        List<String> pCategoryNames = new ArrayList<>();
        String date = LocalDate.now().toString();

        /*
            获得分类列表，将基本分类组成顶级分类
         */
        log.info("开始循环");
        for (CategoryOutputDto dto:categoryOutputDtos){
            List<String> strings = new ArrayList<>();
            //获得顶级频道缓存名
            String pCategoryName = date+"::"+dto.getId();
            pCategoryNames.add(pCategoryName);

            //获得二级频道缓存名
            for (CategoryOutputDto childDto:dto.getChildList()){
                long id = childDto.getId();
                strings.add(date+"::"+id);
            }
            //合并集合并获得全部数据
            redisUtils.unionZSet(strings,pCategoryName);
            Set set = redisUtils.zRevRange(pCategoryName,0,-1,false);
            if (CollectionUtils.isEmpty(set)){
                log.info("集合为空");
                return CommonResult.failed();
            }
            //将各个顶级频道前100名视频放置缓存中
            List<Long> top100List = pers.czj.utils.CollectionUtils.slicer(set,0,100);
            List<VideoBasicOutputDto> basicOutputDtos = videoService.listBasicInfoByIds(top100List);
            redisUtils.set(categoryTopKey+dto.getId(),basicOutputDtos,0);
            //待写持久化
            log.info("执行完一圈");
        }
        //合并全区总排行榜
        String key = date+"::ALL";
        redisUtils.unionZSet(pCategoryNames,key);

        //将全区前100名视频放置缓存（算冗余，不过不大嘛~）
        Set<ZSetOperations.TypedTuple> allSet = redisUtils.zRevRange(key,0,-1,true);
        List<Long> top100List = pers.czj.utils.CollectionUtils.speicalSlicer(allSet,0,100);
        List<VideoBasicOutputDto> basicOutputDtos = videoService.listBasicInfoByIds(top100List);
        redisUtils.set(categoryTopKey+"ALL",basicOutputDtos,0);
        playNumTabService.addAll(allSet);
        return CommonResult.success();
    }


    @PutMapping("/video")
    public CommonResult updateVideoPublishStatus(@RequestBody String str) throws FileNotFoundException {
        JSONObject jsonObject = JSON.parseObject(str);
        videoService.updatePublishStatus(jsonObject.getLong("id"),jsonObject.getObject("state", VideoPublishStateEnum.class));
        return CommonResult.success("发布成功！");
    }

    @GetMapping("/video/audit")
    public String findNeedAuditVideo() throws VideoException {
        return JSON.toJSONString(videoService.findNeedAuditVideo());
    }

}
