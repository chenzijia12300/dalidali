package pers.czj.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.entity.Dynamic;
import pers.czj.service.DynamicService;

/**
 * 创建在 2020/8/10 11:32
 */
@RestController("/api")
@Api("动态feign接口")
public class DynamicApi {

    private DynamicService dynamicService;

    @Autowired
    public DynamicApi(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }


    @PostMapping("/dynamic")
    @ApiOperation("添加动态")
    public CommonResult addDynamic(@RequestBody Dynamic dynamic){
        dynamicService.save(dynamic);
        //消息队列来一波
        return CommonResult.success();
    }
}
