package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.common.VideoBasicInfo;
import pers.czj.constant.RecommendLocation;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.entity.VideoRecommend;
import pers.czj.service.VideoRecommendService;
import pers.czj.service.VideoService;

import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/7/26 13:50
 */
@CrossOrigin
@RestController
@Api("视频推荐接口")
public class VideoRecommendController {

    private static final Logger log = LoggerFactory.getLogger(VideoRecommendController.class);

    @Autowired
    private VideoRecommendService recommendService;

    @Autowired
    private VideoService videoService;

    @PostMapping("/video/recommend")
    @ApiOperation(value = "",hidden = true)
    public CommonResult addRecommend(@RequestBody VideoRecommend recommend){
        recommendService.save(recommend);
        return CommonResult.success("添加推荐成功");
    }

    @GetMapping("/video/recommend/{type}/{pageNum}")
    @ApiOperation(value = "",hidden = true)
    public CommonResult findRecommend(@PathVariable("pageNum")int pageNum, @PathVariable("type") RecommendLocation location){

        List<Long> ids = recommendService.findRecommendIdByLocation(pageNum,location);
        List<VideoBasicOutputDto> basicOutputDtos = videoService.listBasicInfoByIds(ids);
        return CommonResult.success(basicOutputDtos);
    }
}
