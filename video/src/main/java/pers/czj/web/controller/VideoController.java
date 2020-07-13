package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.common.User;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.dto.VideoInputDto;
import pers.czj.entity.Video;
import pers.czj.exception.VideoException;
import pers.czj.service.VideoService;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;

/**
 * 创建在 2020/7/11 23:43
 */
@CrossOrigin
@RestController
@Api("视频控制器")
public class VideoController {

    @Autowired
    private VideoService videoService;


    @PostMapping("/video")
    @ApiOperation("添加视频")
    public CommonResult addVideo(HttpSession httpSession,@RequestBody @Validated VideoInputDto dto) throws VideoException {
        Video video = dto.convert();
        User user = (User) httpSession.getAttribute("USER");
        video.setUid(user.getId());
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
}
