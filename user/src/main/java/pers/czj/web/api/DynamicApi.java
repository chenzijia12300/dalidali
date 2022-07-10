package pers.czj.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.NumberInputDto;
import pers.czj.entity.Dynamic;
import pers.czj.service.DynamicService;

/**
 * 创建在 2020/8/10 11:32
 */
@RestController
@RequestMapping("/api")
@Api("动态feign接口")
public class DynamicApi {

    private DynamicService dynamicService;

    @Autowired
    public DynamicApi(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }


    @PostMapping("/dynamic")
    @ApiOperation("添加动态")
    public CommonResult addDynamic(@RequestBody Dynamic dynamic) {
        dynamicService.save(dynamic);
        //消息队列来一波
        return CommonResult.success();
    }


    @PutMapping("/dynamic")
    @ApiOperation("更改动态")
    public CommonResult updateDynamic(@RequestBody Dynamic dynamic) {
        dynamicService.updateById(dynamic);
        return CommonResult.success();
    }

    @PutMapping("/dynamic/comment")
    public CommonResult incrCommentNum(@RequestBody NumberInputDto dto) {
        dynamicService.incrCommentNum(dto.getId(), dto.getNum());
        return CommonResult.success();
    }
}
