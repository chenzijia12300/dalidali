package pers.czj.web.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.czj.common.CommonResult;
import pers.czj.constant.VideoCoverTypeEnum;
import pers.czj.dto.UploadInputDto;
import pers.czj.dto.VideoInputDto;
import pers.czj.entity.VideoCrawlerLog;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;
import pers.czj.feign.CommentFeignClient;
import pers.czj.service.CrawlerSendService;
import pers.czj.service.VideoCrawlerLogService;
import pers.czj.service.VideoInfoCrawlerService;
import pers.czj.service.VideoService;
import pers.czj.utils.GetVideoDataUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 创建在 2020/10/3 22:26
 * <p>
 * ----------------------
 * 重构于 2020/10/28 20:23
 * ----------------------
 */
@RestController
@RequestMapping("/crawler/")
public class VideoCrawlerController {

    private static final Logger log = LoggerFactory.getLogger(VideoCrawlerController.class);

    public static final int DEFAULT_USER_ID = 1;

    private GetVideoDataUtils videoDataUtils;

    private VideoController videoController;

    private CrawlerSendService crawlerSendService;

    private VideoService videoService;

    private VideoCrawlerLogService crawlerLogService;

    private VideoInfoCrawlerService videoInfoCrawlerService;

    private CommentFeignClient commentFeignClient;

    private Random random;


    @Autowired
    public VideoCrawlerController(GetVideoDataUtils videoDataUtils, VideoController videoController,
                                  CrawlerSendService crawlerSendService, VideoCrawlerLogService crawlerLogService,
                                  VideoInfoCrawlerService videoInfoCrawlerService, CommentFeignClient commentFeignClient,
                                  VideoService videoService) {
        this.videoDataUtils = videoDataUtils;
        this.videoController = videoController;
        this.crawlerSendService = crawlerSendService;
        this.crawlerLogService = crawlerLogService;
        this.videoInfoCrawlerService = videoInfoCrawlerService;
        this.commentFeignClient = commentFeignClient;
        this.videoService = videoService;
        this.random = new Random();
    }

    @PostMapping("/upload")
    @ApiOperation(value = "指定单个视频爬虫信息", hidden = true)
    public CommonResult uploadVideo(HttpSession httpSession, @RequestBody UploadInputDto dto) throws InterruptedException, VideoException, UserException, IOException {
        handlerVideoResource(dto.getTitle(), dto.getVideoUrl(), httpSession, videoInfoCrawlerService.getVideoBasicInfo(dto.getVideoUrl()));
        return CommonResult.success("操作成功~");
    }


    @GetMapping("/top")
    @ApiOperation(value = "爬取哔哩哔哩排行榜信息", hidden = true)
    public CommonResult testTopData(HttpSession httpSession, @RequestParam(required = false) String url, @RequestParam(required = false, defaultValue = "150") Integer num) {

        log.info("爬取排行榜路径:{},条数:{}", url, num);

        int nowNum = 0;
        long startTime;
        String title, videoUrl;
        List<Map<String, String>> maps = videoDataUtils.syncGetData(url);

        for (Map<String, String> map : maps) {
            title = map.get("title");
            videoUrl = map.get("videoUrl");
            log.info("title:{},videoUrl:{}", title, videoUrl);


            if (nowNum++ == num) {
                log.info("下载视频已到所需条数:{}", num);
                break;
            }

            if (crawlerLogService.exists(videoUrl)) {
                log.info("{}已存在", videoUrl);
                continue;
            }

            log.info("map:{}", videoInfoCrawlerService.getVideoBasicInfo(videoUrl));

            if (log.isDebugEnabled()) {
                log.debug("开发环境,不真实爬取文件,退出~");
                continue;
            }

            try {
                startTime = System.currentTimeMillis();
                handlerVideoResource(title, videoUrl, httpSession, videoInfoCrawlerService.getVideoBasicInfo(videoUrl));
                crawlerSendService.send(createCrawlerLog(title, videoUrl, System.currentTimeMillis() - startTime));
            } catch (Exception e) {
                log.error("下载视频抛出异常:{}\n异常:{}", map, e.getMessage());
            }

/*            try {
                log.info("休眠两秒~防止被400");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/


        }
        return CommonResult.success();
    }


    @GetMapping("/recommend/channel/{rid}/{cid}/{cpid}")
    public CommonResult crawlerChannelRecommend(HttpSession session, @PathVariable("rid") int rid,
                                                @PathVariable(value = "cid", required = false) String cid,
                                                @PathVariable(value = "cpid", required = false) String cpid
    ) {

        List<Map<String, String>> maps = videoInfoCrawlerService.getChannelRecommendVideo(rid);

        maps.forEach(map -> {
            String title = map.get("title");
            String videoUrl = map.get("videoUrl");
            Map<String, String> videoInfoMap = videoInfoCrawlerService.getVideoBasicInfo(videoUrl);

            if (ObjectUtil.isNotNull(cid)) {
                videoInfoMap.put("categoryId", cid);
            }
            if (ObjectUtil.isNotNull(cpid)) {
                videoInfoMap.put("categoryPId", cpid);
            }

            try {
                long startTime = System.currentTimeMillis();
                handlerVideoResource(title, videoUrl, session, videoInfoMap);
                crawlerSendService.send(createCrawlerLog(title, videoUrl, System.currentTimeMillis() - startTime));

            } catch (VideoException e) {
                log.info("处理视频:{}--{}出错\n{}", title, videoUrl, e);
            }
        });

        return CommonResult.success();
    }


    public void handlerVideoResource(String title, String videoUrl, HttpSession httpSession, Map<String, String> videoInfoMap) throws VideoException {


        log.info("videoInfoMap:{}", videoInfoMap);
        /*
                 下载视频资源
         */
        Map<String, String> map;
        try {
            map = videoDataUtils.syncDownload(title, videoUrl);
        } catch (InterruptedException e) {
            log.error("下载视频资源:{}失败,退出系统", videoUrl);
            return;
        }
        String productUrl = map.get("productUrl");
        String coverUrl = map.get("coverUrl");
        long userId = Long.parseLong(videoInfoMap.get("userId"));
        log.info("userId:{}", userId);

        // 生成随机数来随机生成GIF图
        VideoCoverTypeEnum type = random.nextInt(6) == 0 ? VideoCoverTypeEnum.GIF : VideoCoverTypeEnum.STANDARD;


        // 上传视频资源到OSS服务器，返回视频于服务器的URL地址
        String videoServerUrl = uploadVideoToOss(httpSession, productUrl, userId, type);

        if (StrUtil.isEmpty(videoServerUrl)) {
            log.error("上传文件到OSS服务器出现问题，提前退出");
            FileUtil.del(productUrl);
            FileUtil.del(coverUrl);
            return;
        }


        // 判断封面是否为空，上传封面图
        List<String> coverUrls = uploadCoverToOss(httpSession, coverUrl, type);


        //将视频相关信息，上传到服务器
        VideoInputDto dto = new VideoInputDto()
                .setTitle(title)
                .setDescription(videoInfoMap.get("desc"))
                .setTags(videoInfoMap.get("categoryName"))
                .setUrls(videoServerUrl)
                .setUid(userId)
                .setCategoryId(videoInfoMap.get("categoryId") == null ? 44 : Long.parseLong(videoInfoMap.get("categoryId")))
                .setCategoryPId(videoInfoMap.get("categoryPId") == null ? 11 : Long.parseLong(videoInfoMap.get("categoryPId")));
        long vid = (long) videoController.addVideo(httpSession, userId, dto).getData();
        log.info("{}上传完毕", title);

        /*
            爬取视频对应弹幕
         */
        Integer danmuSize = commentFeignClient.crawlerDanmuList(Long.parseLong(videoInfoMap.get("cid")), vid);
        videoService.incrDanmuNum(vid, danmuSize);

        /*
            清理临时文件
         */
        FileUtil.del(productUrl);
        FileUtil.del(coverUrl);
        log.info("清理爬虫临时文件:{},{}", productUrl, coverUrl);
    }

    /*
        一些封装方法
     */

    public VideoCrawlerLog createCrawlerLog(String title, String url, long time) {
        VideoCrawlerLog crawlerLog = new VideoCrawlerLog();
        crawlerLog.setUrl(url);
        crawlerLog.setTitle(title);
        crawlerLog.setTime(time);
        return crawlerLog;
    }


    private MultipartFile convertMulti(String filePath) throws FileNotFoundException {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            return multipartFile;
        } catch (IOException e) {
            log.error("{}转换成Multi失败", filePath);

        }
        return null;
    }


    private String uploadVideoToOss(HttpSession session, String localVideoUrl, long userId, VideoCoverTypeEnum typeEnum) {
        try {
            return videoController.uploadVideo(session,
                    userId,
                    typeEnum,
                    convertMulti(localVideoUrl)).getMessage();
        } catch (VideoException e) {
            log.error("上传视频资源失败:{}", localVideoUrl);
        } catch (FileNotFoundException e) {
            log.error("没有发现文件:{}", localVideoUrl);
        }
        return null;
    }


    private List<String> uploadCoverToOss(HttpSession session, String localCoverUrl, VideoCoverTypeEnum typeEnum) {
        if (typeEnum.equals(VideoCoverTypeEnum.STANDARD)) {
            try {
                return videoController.uploadCover(session, convertMulti(localCoverUrl));
            } catch (Exception e) {
                log.error("上传封面：{}失败", localCoverUrl);
            }
        }
        return ListUtil.empty();
    }


    private VideoInputDto createDefaultDto(String title, String url) {
        return VideoInputDto.createDefaultDto()
                .setTags("测试")
                .setTitle(title)
                .setUrls(url);
    }


/*    @GetMapping("/crawler/test")
    public CommonResult test(){
        VideoCrawlerLog videoCrawlerLog = new VideoCrawlerLog();
        videoCrawlerLog.setTime(System.currentTimeMillis());
        videoCrawlerLog.setTitle("123");
        videoCrawlerLog.setUrl("456");
        crawlerSendService.send(videoCrawlerLog);
        return CommonResult.success();
    }*/

}
