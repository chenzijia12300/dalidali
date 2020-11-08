package pers.czj.web.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import pers.czj.common.CommonResult;
import pers.czj.common.VideoBasicInfo;
import pers.czj.constant.HttpContentTypeEnum;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.constant.VideoResolutionEnum;
import pers.czj.constant.VideoScreenTypeEnum;
import pers.czj.dto.*;
import pers.czj.entity.Video;
import pers.czj.entity.VideoLog;
import pers.czj.exception.CategoryException;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;
import pers.czj.feign.DynamicFeignClient;
import pers.czj.feign.UserFeignClient;
import pers.czj.service.CategoryService;
import pers.czj.service.PlayNumTabService;
import pers.czj.service.VideoLogService;
import pers.czj.service.VideoService;
import pers.czj.util.VideoUtils;
import pers.czj.utils.FileUtils;
import pers.czj.utils.GetVideoDataUtils;
import pers.czj.utils.MinIOUtils;
import pers.czj.utils.RedisUtils;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
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
@Api(tags = "视频控制器")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);

    private static final String TIMED_TASK_VALUE="lock";



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

    @Autowired
    private GetVideoDataUtils videoDataUtils;

    @Value("${redis.category-pre-key}")
    private String categoryTopKey;

    @Value("${redis.play-key}")
    private String playKey;

    @Value("${redis.timed-task-key}")
    private String timedTaskKey;

    @Value("${video.default-description}")
    private String defaultDescription;

    @Value("${video.default-tags}")
    private String defaultTags;

    @Value("${video.dir-path}")
    private String dirPath;

    private String dir = System.getProperty("user.dir")+"/";


    @PostMapping("/video")
    @ApiOperation("添加视频")
    public CommonResult addVideo(HttpSession httpSession,@RequestParam long uid,@RequestBody @Validated VideoInputDto dto) throws VideoException {
        Video video = dto.convert(uid);
        VideoBasicInfo basicInfo = (VideoBasicInfo) httpSession.getAttribute("VIDEO_INFO");
        int width = Integer.valueOf(basicInfo.getWidth());
        int height = Integer.valueOf(basicInfo.getHeight());
        video.setLength(basicInfo.getDuration());
        video.setCover(basicInfo.getCover());
        video.setScreenType(width>height?VideoScreenTypeEnum.LANDSCAPE:VideoScreenTypeEnum.PORTRAIT);
        video.setWidth(width);
        video.setHeight(height);
        //暂时废弃
//       video.setResolutionState(VideoResolutionEnum.valueOf("P_"+basicInfo.getHeight()));
        video.setUrls(basicInfo.getUrl());
        log.info("video:{}",video);
        boolean flag = videoService.save(video);
        if (!flag){
            throw new VideoException("添加视频失败，请重试尝试");
        }
        //这里应该要有个消息队列
        log.info("用户{}上传视频{}成功!",uid,video.getUrls());
        return CommonResult.success("你滴视频提交成功~，等待管理员的审核把");
    }

    @GetMapping("/video/{id}")
    @ApiOperation("获得视频的详细信息")
    public CommonResult findVideoById(@RequestParam long uid,@PathVariable("id")@Min(1) long id) throws VideoException {
        VideoDetailsOutputDto detailsOutputDto = videoService.findDetailsById(uid,id);
        log.debug("当前日期：{}",LocalDate.now());
        if(/*!redisUtils.getBit(playKey+id,userId)*/true){
            videoService.incrPlayNum(id,1);
            redisUtils.setBit(playKey+id,uid,true);
            redisUtils.zincrBy(LocalDate.now().toString()+"::"+detailsOutputDto.getCategoryId(),1, String.valueOf(detailsOutputDto.getId()));
        }
        return CommonResult.success(detailsOutputDto);
    }

    @PostMapping("/video/upload")
    @ApiOperation("用户上传视频文件")
    public CommonResult uploadVideo(HttpSession httpSession,@RequestParam long userId,@RequestParam(value = "cover",required = false) boolean needCover,@RequestParam("file") MultipartFile file) throws VideoException, UserException, IOException {
/*        if (httpSession.isNew()){
            httpSession.invalidate();
            throw new UserException("请重新登录！");
        }*/
        //视频存储到本地
        File temp = FileUtils.saveLocalFile(file);

        //获得视频处理后的基本信息
        VideoBasicInfo basicInfo = VideoUtils.getVideoInfo(dir,temp.getName(),needCover);

        //上传视频文件和图片文件到文件服务器
        log.info("开始上传视频文件{}到OSS服务器",temp.getName());
        String url = minIOUtils.uploadFile(temp.getName(),new FileInputStream(temp),HttpContentTypeEnum.MP4);

        if (needCover) {
            String coverLocalUrl = basicInfo.getCover();
            log.info("开始上传封面文件到OSS服务器");
            String coverWebUrl = minIOUtils.uploadFile(coverLocalUrl.substring(coverLocalUrl.lastIndexOf("/") + 1), new FileInputStream(coverLocalUrl),HttpContentTypeEnum.JPEG);
            basicInfo.setCover(coverWebUrl);
            FileUtil.del(coverLocalUrl);
        }



        basicInfo.setUrl(url);
        httpSession.setAttribute("VIDEO_INFO",basicInfo);

        //清除临时文件
        FileUtil.del(temp);


        log.info("{}上传完毕",temp.getName());
        return CommonResult.success(url);
    }


    public CommonResult uploadCover(HttpSession httpSession,@RequestParam("file") MultipartFile file) throws FileNotFoundException, VideoException {
        File temp = FileUtils.saveLocalFile(file);
        String coverWebUrl = minIOUtils.uploadFile(temp.getName(),new FileInputStream(temp), HttpContentTypeEnum.JPEG);
        ((VideoBasicInfo) httpSession.getAttribute("VIDEO_INFO")).setCover(coverWebUrl);
        FileUtil.del(temp);
        return CommonResult.success(coverWebUrl);
    }






    @ApiOperation("获得该分类类型下的排行榜")
    @GetMapping("/video/top/{categoryId}/{pageSize}")
    public CommonResult findTopVideoByCategory(@PathVariable("categoryId")long categoryId,
                                               @PathVariable("pageSize")@Max(value = 100,message = "最大数目不能超过100") int pageSize
    ) throws ConnectException {
        String date = LocalDate.now().toString();
        String setKey = date+"::"+categoryId;
        log.debug("setKey:{}",setKey);
        Object object = redisUtils.get(categoryTopKey+categoryId+"_"+pageSize);
        if (ObjectUtils.isEmpty(object)){
            Set<Long> set = redisUtils.zRevRange(setKey, 0, pageSize, false);
            object = videoService.listBasicInfoByIds(set);
            redisUtils.set(categoryTopKey+categoryId+"_"+pageSize,object,10);
        }
        return CommonResult.success(object);
    }

    /**
     * @author czj
     * 设置排行榜【定时任务】
     * @date 2020/7/17 22:17
     * @param  []
     * @return void
     */
    @GetMapping("/video/setTop")
    @ApiOperation("手动调用定时任务（待删）")
    @Scheduled(cron = "0 0 */1 * * ?")
    public CommonResult setTopVideoData() throws CategoryException, ConnectException {
        //集群要上锁~
        if (redisUtils.setnx(timedTaskKey, TIMED_TASK_VALUE,10)) {
            log.info("开始启动视频排行榜定时任务");
            List<CategoryOutputDto> categoryOutputDtos = categoryService.listCategory();
            List<String> pCategoryNames = new ArrayList<>();
            String date = LocalDate.now().toString();
        /*
            获得分类列表，将基本分类组成顶级分类
         */
            for (CategoryOutputDto dto : categoryOutputDtos) {
                List<String> strings = new ArrayList<>();
                //获得顶级频道缓存名
                String pCategoryName = date + "::" + dto.getId();
                pCategoryNames.add(pCategoryName);

                //获得二级频道缓存名
                for (CategoryOutputDto childDto : dto.getChildList()) {
                    long id = childDto.getId();
                    String key = date + "::" + id;
                    strings.add(key);

                }
                log.debug("strings:{}",strings);
                //合并集合并获得全部数据
                if (!CollectionUtils.isEmpty(strings)) {
                    redisUtils.unionZSet(strings, pCategoryName);
                }
                redisUtils.delete(strings);
            }
            //合并全区总排行榜
            String key = date + "::ALL";
            redisUtils.unionZSet(pCategoryNames, key);

            //将全区前100名视频放置缓存（算冗余，不过不大嘛~）
            Set<ZSetOperations.TypedTuple> allSet = redisUtils.zRevRange(key, 0, -1, true);
            playNumTabService.addAll(allSet);
            //释放锁
            redisUtils.delete(timedTaskKey);
            return CommonResult.success();
        }
        return CommonResult.failed();

    }





    public boolean isExist(String filePath){
        File file = new File(dirPath+filePath);
        return file.exists();
    }

}
