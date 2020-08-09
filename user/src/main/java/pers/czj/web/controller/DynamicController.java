package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.entity.Dynamic;
import pers.czj.service.DynamicService;

import java.util.List;

/**
 * 创建在 2020/8/9 22:43
 */
@RestController
@Api("个人动态接口")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    @PostMapping("/dynamic")
    @ApiOperation("添加动态")
    public CommonResult addDynamic(@RequestBody Dynamic dynamic){
        dynamicService.save(dynamic);
        //消息队列来一波
        return CommonResult.success();
    }

    @GetMapping("/dynamic/{uid}/{pageNum}/{pageSize}")
    @ApiOperation("获得动态列表")
    public CommonResult listDynamic(@PathVariable("uid") long uid,
                                    @PathVariable("pageNum") int pageNum,
                                    @PathVariable("pageSize") int pageSize){
        List<Dynamic> dynamics = dynamicService.listDynamicByPage(uid,pageNum,pageSize);
        return CommonResult.success(dynamics);
    }
}
