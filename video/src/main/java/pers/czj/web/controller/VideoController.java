package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.czj.common.CommonResult;
import pers.czj.common.User;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.dto.VideoInputDto;
import pers.czj.entity.Video;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;
import pers.czj.service.VideoService;
import pers.czj.util.VideoUtils;
import pers.czj.utils.COSUtils;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

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
    private COSUtils cosUtils;


    private String dir = System.getProperty("user.dir");


    @PostMapping("/video")
    @ApiOperation("添加视频")
    public CommonResult addVideo(HttpSession httpSession,@RequestBody @Validated VideoInputDto dto) throws VideoException {
        Video video = dto.convert();
        log.debug("sessionID:{}",httpSession.getId());
        long userId = (long) httpSession.getAttribute("USER_ID");
        Map<String,String> videoInfoMap = (Map<String, String>) httpSession.getAttribute("VIDEO_INFO");
        video.setUid(userId);
        boolean flag = videoService.save(video);
        if (!flag){
            throw new VideoException("添加视频失败，请重试尝试");
        }

        //这里应该要有个消息队列
        return CommonResult.success("你滴视频提交成功~，等待管理员的审核把");
    }

    @GetMapping("/video/{id}")
    @ApiOperation("获得视频的详细信息")
    public CommonResult findVideoById(@PathVariable("id")@Min(1) long id) throws VideoException {
        VideoDetailsOutputDto detailsOutputDto = videoService.findDetailsById(id);
        return CommonResult.success(detailsOutputDto);
    }

    @PostMapping("/video/upload")
    public CommonResult uploadVideo(HttpSession httpSession,MultipartFile file) throws VideoException, UserException {
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
        Map<String,String> map = VideoUtils.getVideoInfo(temp.getAbsolutePath());
        httpSession.setAttribute("VIDEO_INFO",map);
        String url = cosUtils.uploadFile(temp);
        return CommonResult.success(url);
    }
}
