package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.entity.Danmu;
import pers.czj.service.DanmuService;

/**
 * 创建在 2020/7/30 19:29
 */
@RestController
@Api("弹幕接口")
public class DanmuController {

    private static final Logger log = LoggerFactory.getLogger(DanmuController.class);

    @Autowired
    private DanmuService danmuService;

    @PostMapping("/danmu")
    @ApiOperation("添加弹幕")
    public CommonResult addDanmu(@RequestBody Danmu danmu){
        boolean flag = danmuService.save(danmu);
        return CommonResult.success("添加弹幕成功！");
    }

    @GetMapping("/danmu/{vid}/{second}")
    @ApiOperation("返回弹幕列表")
    public CommonResult listDanmu(@PathVariable("vid") long vid,@PathVariable("second")long second){
        return CommonResult.success(danmuService.listDanmu(vid,second));
    }


}
