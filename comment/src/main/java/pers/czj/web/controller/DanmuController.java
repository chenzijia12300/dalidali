package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.NumberInputDto;
import pers.czj.entity.Danmu;
import pers.czj.feign.VideoFeignClient;
import pers.czj.service.DanmuService;

import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/7/30 19:29
 */
@CrossOrigin
@RestController
@Api("弹幕接口")
public class DanmuController {

    private static final Logger log = LoggerFactory.getLogger(DanmuController.class);

    private DanmuService danmuService;

    private VideoFeignClient videoFeignClient;

    @Autowired
    public DanmuController(DanmuService danmuService, VideoFeignClient videoFeignClient) {
        this.danmuService = danmuService;
        this.videoFeignClient = videoFeignClient;
    }

    @PostMapping("/danmu")
    @ApiOperation("添加弹幕")
    public CommonResult addDanmu(@RequestParam long uid, @RequestBody Danmu danmu) {
        danmu.setUid(uid);
        danmuService.save(danmu);
        /*
            优化点：先写进消息队列，然后消息队列在写进数据库，【削峰】
         */
        videoFeignClient.incrDanmuNum(new NumberInputDto(danmu.getVid(), 1));
        return CommonResult.success("添加弹幕成功！");
    }

    @GetMapping("/danmu/{vid}/{second}")
    @ApiOperation("返回第【second】秒到【second+10】的弹幕列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vid", value = "视频主键", paramType = "path"),
            @ApiImplicitParam(name = "second", value = "秒数", paramType = "path")
    })
    public CommonResult listDanmu(@PathVariable("vid") long vid, @PathVariable("second") long second) {
        List<Danmu> danmus = danmuService.listDanmu(vid, second);
        log.debug("弹幕列表:{}", danmus);
        return CommonResult.success(danmus);
    }


    @DeleteMapping("/danmu")
    public CommonResult delDanmu(@RequestParam long uid, @RequestBody Map<String, Object> map) {
        Integer id = (Integer) map.get("did");
        danmuService.removeById(id);
        return CommonResult.success("删除弹幕成功！");
    }


}
