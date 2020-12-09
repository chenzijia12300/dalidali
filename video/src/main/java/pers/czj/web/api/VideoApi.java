package pers.czj.web.api;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.dto.NumberInputDto;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.entity.Video;
import pers.czj.exception.VideoException;
import pers.czj.feign.DynamicFeignClient;
import pers.czj.feign.UserFeignClient;
import pers.czj.service.VideoService;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/8/10 11:22
 */
@RestController
@Api("视频feign接口")
@RequestMapping("/api")
public class VideoApi {

    public static final String DYNAMIC_VIDEO_TYPE = "VIDEO";

    private VideoService videoService;

    private UserFeignClient userFeignClient;

    @Autowired
    public VideoApi(VideoService videoService, UserFeignClient userFeignClient) {
        this.videoService = videoService;
        this.userFeignClient = userFeignClient;
    }

    /*
        comment模块
     */

    @PutMapping("/video/comment")
    @ApiOperation("更改视频评论数")
    public CommonResult incrCommentNum(@RequestBody NumberInputDto dto){
        videoService.incrCommentNum(dto.getId(),dto.getNum());
        return CommonResult.success();
    }

    @PutMapping("/video/danmu")
    @ApiOperation("更改视频弹幕数")
    public CommonResult incrDanmuNum(@RequestBody NumberInputDto dto){
        videoService.incrDanmuNum(dto.getId(),dto.getNum());
        return CommonResult.success();
    }




    /*
        user模块
     */
    @GetMapping("/video/list")
    @ApiOperation("根据视频主键返回视频基本信息列表")
    public List listBasicVideoInfoByIds(@RequestParam Collection<Long> ids){
        if (CollectionUtil.isEmpty(ids)){
            return ListUtil.empty();
        }
        List<VideoBasicOutputDto> videoBasicOutputDtos = videoService.listBasicInfoByIds(ids);
        return videoBasicOutputDtos;
    }






    /*
        admin模块
     */
    @GetMapping("/video/audit")
    @ApiOperation("发现需要审核的视频")
    public String findNeedAuditVideo() throws VideoException {
        return JSON.toJSONString(videoService.findNeedAuditVideo());
    }

    @PutMapping("/video")
    @ApiOperation("发布视频并进行分辨率，雪碧图等处理(建议异步调用此接口)")
    public CommonResult updateVideoPublishStatus(@RequestBody String str) throws FileNotFoundException {
        Video video = JSON.parseObject(str, Video.class);
        videoService.updatePublishStatus(video.getId(),video.getPublishState());
        userFeignClient.addDynamic(createDynamicModel(video));
        return CommonResult.success("发布成功！");
    }

    private Map<String,Object> createDynamicModel(Video video){
        Map<String,Object> map = new HashMap<>();
        map.put("uid",video.getUid());
        map.put("oid",video.getId());
        map.put("type",DYNAMIC_VIDEO_TYPE);
        return map;
    }
}
