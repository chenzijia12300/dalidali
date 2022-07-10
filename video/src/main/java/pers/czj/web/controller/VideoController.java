package pers.czj.web.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.czj.common.CommonResult;
import pers.czj.common.VideoBasicInfo;
import pers.czj.constant.HttpContentTypeEnum;
import pers.czj.constant.VideoCoverTypeEnum;
import pers.czj.constant.VideoScreenTypeEnum;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.dto.VideoHotOutputDto;
import pers.czj.dto.VideoInputDto;
import pers.czj.entity.Video;
import pers.czj.exception.CategoryException;
import pers.czj.exception.VideoException;
import pers.czj.service.CategoryService;
import pers.czj.service.PlayNumTabService;
import pers.czj.service.VideoLogService;
import pers.czj.service.VideoService;
import pers.czj.util.ImageUtils;
import pers.czj.util.StrConcatUtil;
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
import java.net.ConnectException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 创建在 2020/7/11 23:43
 */
@CrossOrigin
@RestController
@Api(tags = "视频控制器")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);

    private static final String TIMED_TASK_VALUE = "lock";

    private static final Character SEPARATOR = '/';

    private static final String CONNECTOR = "-";


    private VideoService videoService;

    private VideoLogService videoLogService;

    private CategoryService categoryService;

    private MinIOUtils minIOUtils;

    private RedisUtils redisUtils;

    private PlayNumTabService playNumTabService;

    private GetVideoDataUtils videoDataUtils;


    @Autowired
    public VideoController(VideoService videoService, VideoLogService videoLogService, CategoryService categoryService, MinIOUtils minIOUtils, RedisUtils redisUtils, PlayNumTabService playNumTabService, GetVideoDataUtils videoDataUtils) {
        this.videoService = videoService;
        this.videoLogService = videoLogService;
        this.categoryService = categoryService;
        this.minIOUtils = minIOUtils;
        this.redisUtils = redisUtils;
        this.playNumTabService = playNumTabService;
        this.videoDataUtils = videoDataUtils;
        this.strConcatUtil = new StrConcatUtil(CONNECTOR);
    }

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

    private String dir = System.getProperty("user.dir") + "/";

    private StrConcatUtil strConcatUtil;


    @PostMapping("/video")
    @ApiOperation("添加视频")
    public CommonResult addVideo(HttpSession httpSession, @RequestParam long uid, @RequestBody @Validated VideoInputDto dto) throws VideoException {
        Video video = dto.convert(uid);
        VideoBasicInfo basicInfo = (VideoBasicInfo) httpSession.getAttribute("VIDEO_INFO");
        int width = Integer.valueOf(basicInfo.getWidth());
        int height = Integer.valueOf(basicInfo.getHeight());
        BeanUtils.copyProperties(basicInfo, video);
        video.setLength(basicInfo.getDuration());
        video.setScreenType(width > height ? VideoScreenTypeEnum.LANDSCAPE : VideoScreenTypeEnum.PORTRAIT);
        //暂时废弃
//       video.setResolutionState(VideoResolutionEnum.valueOf("P_"+basicInfo.getHeight()));
        boolean flag = videoService.save(video);
        log.info("video:{}", video);
        if (!flag) {
            throw new VideoException("添加视频失败，请重试尝试");
        }
        //这里应该要有个消息队列
        log.info("用户{}上传视频{}成功!", uid, video.getUrls());
        return CommonResult.success(video.getId(), "你滴视频提交成功~，等待管理员的审核把");
    }

    @GetMapping("/video/{id}")
    @ApiOperation("获得视频的详细信息")
    public CommonResult findVideoById(@RequestParam long uid, @PathVariable("id") @Min(1) long id) throws VideoException {
        VideoDetailsOutputDto detailsOutputDto = videoService.findDetailsById(uid, id);
        log.debug("当前日期：{}", LocalDate.now());
        if (/*!redisUtils.getBit(playKey+id,userId)*/true) {
            videoService.incrPlayNum(id, 1);
            redisUtils.setBit(playKey + id, uid, true);
            redisUtils.zincrBy(LocalDate.now().toString() + "::" + detailsOutputDto.getCategoryId(), 1, String.valueOf(detailsOutputDto.getId()));
        }
        return CommonResult.success(detailsOutputDto);
    }

    @PostMapping("/video/upload")
    @ApiOperation("用户上传视频文件")
    public CommonResult uploadVideo(HttpSession httpSession, @RequestParam long userId, @RequestParam VideoCoverTypeEnum coverTypeEnum, @RequestParam("file") MultipartFile file) throws VideoException {
/*        if (httpSession.isNew()){
            httpSession.invalidate();
            throw new UserException("请重新登录！");
        }*/
        //视频存储到本地
        File temp = FileUtils.saveLocalFile(file);
        //视频服务器路径
        String url = null;
        //封面本地临时路径
        String coverLocalUrl = null;
        String compressCoverUrl = null;
        //获得视频处理后的基本信息
        VideoBasicInfo basicInfo = VideoUtils.getVideoInfo(dir, temp.getName(), coverTypeEnum);

        //上传视频文件和图片文件到文件服务器
        log.info("开始上传视频文件{}到OSS服务器", temp.getName());
        url = minIOUtils.uploadFile(temp.getName(), temp, HttpContentTypeEnum.MP4);



        /*
            如果封面为FFMPEG生成则上传
         */
        if (VideoCoverTypeEnum.STANDARD != coverTypeEnum) {
            coverLocalUrl = basicInfo.getCover();
            compressCoverUrl = basicInfo.getCompressCover();
            log.info("开始上传封面文件到OSS服务器:{}", coverLocalUrl);
            String coverWebUrl = minIOUtils.uploadFile(StrUtil.subAfter(coverLocalUrl, SEPARATOR, true), new File(coverLocalUrl), HttpContentTypeEnum.JPEG);
            basicInfo.setCover(coverWebUrl);

            if (!StrUtil.equals(coverLocalUrl, compressCoverUrl)) {
                String compressCoverWebUrl = minIOUtils.uploadFile(StrUtil.subAfter(compressCoverUrl, SEPARATOR, true), new File(compressCoverUrl), HttpContentTypeEnum.JPEG);
                basicInfo.setCompressCover(compressCoverWebUrl);
            } else {
                basicInfo.setCompressCover(coverWebUrl);
            }
        }


        basicInfo.setUrls(url);
        httpSession.setAttribute("VIDEO_INFO", basicInfo);

        //清除临时文件
        log.info("清除视频临时文件:{}", FileUtil.del(temp));
        log.info("清除封面临时文件:{}", FileUtil.del(coverLocalUrl));
        log.info("清除压缩封面临时文件:{}", FileUtil.del(compressCoverUrl));
        log.info("{}上传完毕", temp.getName());
        return CommonResult.success(url);
    }


    public List<String> uploadCover(HttpSession httpSession, @RequestParam("file") MultipartFile file) throws FileNotFoundException, VideoException {

        // 图片保存到本地
        File temp = FileUtils.saveLocalFile(file);
        // 生成压缩的而图片并保存到本地
        File compressTemp = ImageUtils.compress(temp);

        String imgWebUrl = minIOUtils.uploadFile(temp.getName(), new FileInputStream(temp), HttpContentTypeEnum.JPEG);
        String imgWebCompressUrl = minIOUtils.uploadFile(compressTemp.getName(), new FileInputStream(compressTemp), HttpContentTypeEnum.JPEG);
        VideoBasicInfo basicInfo = ((VideoBasicInfo) httpSession.getAttribute("VIDEO_INFO"));
        basicInfo.setCover(imgWebUrl);
        basicInfo.setCompressCover(imgWebCompressUrl);
        FileUtil.del(temp);
        FileUtil.del(imgWebCompressUrl);
        return ListUtil.of(imgWebCompressUrl, imgWebUrl);

    }


    @ApiOperation("获得该分类类型下的排行榜")
    @GetMapping("/video/top/{categoryStr}/{pageNum}/{pageSize}")
    public CommonResult findTopVideoByCategory(@PathVariable("categoryStr") String categoryStr,
                                               @PathVariable("pageNum")
                                               @Min(1)
                                                       int pageNum,
                                               @PathVariable("pageSize")
                                               @Max(value = 100, message = "最大数目不能超过100") int pageSize
    ) throws ConnectException {

        categoryStr = categoryStr.toUpperCase();
        String date = LocalDate.now().toString();
        String setKey = date + "::" + categoryStr;
        log.debug("setKey:{}", setKey);

        //key：redis的分类key
        String key = strConcatUtil.concat(categoryTopKey, categoryStr, pageNum, pageSize);
        Object object = redisUtils.get(key);
        if (ObjectUtils.isEmpty(object)) {
            Set<Long> set = redisUtils.zRevRange(setKey, (pageNum - 1) * pageSize, pageSize, false);
            List<VideoHotOutputDto> dtos = videoService.listHotInfoByIds(set);
            dtos.forEach(videoBasicOutputDto -> {
                long score = (videoBasicOutputDto.getPraiseNum() * 12340) + videoBasicOutputDto.getPlayNum();
                videoBasicOutputDto.setScore(score);
            });
            object = dtos;
            redisUtils.set(key, object, 10);
        }
        return CommonResult.success(object);
    }


    @ApiOperation("获得该分类类型下的排行榜(兼容旧接口，待删除)")
    @GetMapping("/video/top/{categoryStr}/{pageSize}")
    public CommonResult findTopVideoByCategoryTemp(@PathVariable("categoryStr") String categoryStr,
                                                   @PathVariable("pageSize")
                                                   @Max(value = 100, message = "最大数目不能超过100") int pageSize
    ) throws ConnectException {
        return findTopVideoByCategory(categoryStr, 1, pageSize);
    }


    /**
     * @param []
     * @return void
     * @author czj
     * 设置排行榜【定时任务】
     * @date 2020/7/17 22:17
     */
    @GetMapping("/video/setTop")
    @ApiOperation("手动调用定时任务（待删）")
    @Scheduled(cron = "0 0 0 * * ?")
    public CommonResult setTopVideoData() throws CategoryException, ConnectException {
        //集群要上锁~
        if (redisUtils.setnx(timedTaskKey, TIMED_TASK_VALUE, 10)) {
            log.info("开始启动视频排行榜定时任务");

            /**
             * 获得所有分类列表
             */
            List<CategoryOutputDto> categoryOutputDtos = categoryService.listCategory();
            List<String> pCategoryNames = new ArrayList<>();
            String date = LocalDate.now().toString();

            /**
             * 获得分类列表，将基本分类组成顶级分类
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
                log.debug("strings:{}", strings);


                if (!CollectionUtils.isEmpty(strings)) {
                    //合并集合并获得全部数据
                    redisUtils.unionZSet(strings, pCategoryName);
                }
                //删除该时段的缓存
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


    public boolean isExist(String filePath) {
        File file = new File(dirPath + filePath);
        return file.exists();
    }

}
