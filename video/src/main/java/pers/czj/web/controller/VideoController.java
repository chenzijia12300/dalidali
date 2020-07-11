package pers.czj.web.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.dto.VideoInputDto;
import pers.czj.entity.Video;
import pers.czj.service.VideoService;

/**
 * 创建在 2020/7/11 23:43
 */
@CrossOrigin
@RestController
@Api("视频控制器")
public class VideoController {

    @Autowired
    private VideoService videoService;

    public CommonResult addVideo(VideoInputDto dto){
        Video video = dto.convert();
        return null;
    }
}
