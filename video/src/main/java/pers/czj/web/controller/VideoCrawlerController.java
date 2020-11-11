package pers.czj.web.controller;

import cn.hutool.core.io.FileUtil;
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
import pers.czj.dto.UploadInputDto;
import pers.czj.dto.VideoInputDto;
import pers.czj.entity.VideoCrawlerLog;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;
import pers.czj.mapper.VideoCrawlerLogMapper;
import pers.czj.service.CrawlerSendService;
import pers.czj.service.VideoCrawlerLogService;
import pers.czj.utils.GetVideoDataUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/10/3 22:26
 *
 * ----------------------
 * 重构于 2020/10/28 20:23
 * ----------------------
 */
@RestController
@RequestMapping("/crawler/")
public class VideoCrawlerController {
    
    private static final Logger log = LoggerFactory.getLogger(VideoCrawlerController.class);

    public static final int DEFAULT_USER_ID = 1;

    @Autowired
    private GetVideoDataUtils videoDataUtils;

    @Autowired
    private VideoController videoController;

    @Autowired
    private CrawlerSendService crawlerSendService;

    @Autowired
    private VideoCrawlerLogService crawlerLogService;


    @PostMapping("/upload")
    @ApiOperation(value = "指定单个视频爬虫信息",hidden = true)
    public CommonResult uploadVideo(HttpSession httpSession, @RequestBody UploadInputDto dto) throws InterruptedException, VideoException, UserException, IOException {
        handlerVideoResource(dto.getTitle(),dto.getVideoUrl(),httpSession);
        return CommonResult.success("操作成功~");
    }


    @GetMapping("/top")
    @ApiOperation(value = "爬取哔哩哔哩排行榜信息",hidden = true)
    public CommonResult testTopData(HttpSession httpSession, @RequestParam(required = false) String url) throws InterruptedException, VideoException, UserException {
        List<Map<String,String>> maps = null;
        maps = videoDataUtils.syncGetData(url);
        String title = null;
        String videoUrl = null;
        long startTime = -1L;
        for (Map<String,String> map:maps){
            title = map.get("title");
            videoUrl = map.get("videoUrl");
            if (!crawlerLogService.exists(videoUrl)){
                if (!log.isDebugEnabled()) {
                    try {
                        startTime = System.currentTimeMillis();
                        handlerVideoResource(title, videoUrl, httpSession);
                        crawlerSendService.send(createCrawlerLog(title,videoUrl,System.currentTimeMillis()-startTime));
                    }catch (Exception e){
                        log.error("下载视频抛出异常:{}",map);
                    }
                }
            }else{
                log.info("{}已存在",videoUrl);
            }
        }
        return CommonResult.success();
    }

    public void handlerVideoResource(String title,String videoUrl,HttpSession httpSession) throws VideoException,UserException {

        /*
                 下载视频资源
         */
        Map<String,String> map = null;
        try {
            map = videoDataUtils.syncDownload(title,videoUrl);
        } catch (InterruptedException e) {
            log.error("下载视频资源:{}失败,退出系统",videoUrl);
            return;
        }
        String productUrl = map.get("productUrl");
        String coverUrl = map.get("coverUrl");
        MultipartFile multipartFile = convertMulti(productUrl);
        CommonResult commonResult = null;

        /*
            上传视频资源
         */
        try {
            commonResult = videoController.uploadVideo(httpSession,DEFAULT_USER_ID,StrUtil.isEmpty(coverUrl),multipartFile);
        } catch (IOException e) {
            log.error("上传视频资源失败:{}",multipartFile.getName());
            return;
        }

        /*
            判断封面是否为空，上传封面图
         */
        if (StrUtil.isNotEmpty(coverUrl)){
            try {
                videoController.uploadCover(httpSession,convertMulti(coverUrl));
            } catch (FileNotFoundException e) {
                log.error("上传封面：{}失败",coverUrl);
                return;
            }
        }



        //将视频相关信息，上传到服务器
        VideoInputDto dto = createDefaultDto(title,commonResult.getMessage());
        videoController.addVideo(httpSession,DEFAULT_USER_ID,dto);
        log.info("{}上传完毕",title);

        /*
            清理临时文件
         */
        FileUtil.del(productUrl);
        FileUtil.del(coverUrl);
        log.info("清理爬虫临时文件:{},{}",productUrl,coverUrl);
    }

    /*
        一些封装方法
     */

    public VideoCrawlerLog createCrawlerLog(String title,String url,long time){
        VideoCrawlerLog crawlerLog = new VideoCrawlerLog();
        crawlerLog.setUrl(url);
        crawlerLog.setTitle(title);
        crawlerLog.setTime(time);
        return crawlerLog;
    }


    private MultipartFile convertMulti(String filePath){
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(),file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),fileInputStream);
            return multipartFile;
        } catch (FileNotFoundException e) {
            log.error("{}没有发现",filePath);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("{}转换成Multi失败",filePath);
            e.printStackTrace();
        }
        return null;
    }

    private VideoInputDto createDefaultDto(String title,String url){
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
