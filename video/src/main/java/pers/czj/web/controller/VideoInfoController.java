package pers.czj.web.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.CoinInputDto;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;
import pers.czj.service.VideoLogService;

import javax.servlet.http.HttpSession;

/**
 * 创建在 2020/7/20 23:26
 */
@RestController
@Api("视频日记接口")
public class VideoInfoController {

    @Autowired
    private VideoLogService videoLogService;



    @PostMapping("/video/dynamic_like")
    @ApiOperation("动态处理点赞")
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "vid",value = "视频主键",paramType = "body",dataType = "Integer"
            ))
    public CommonResult dynamicLike(@RequestParam("uid") long userId, @RequestBody String str){
        boolean flag = videoLogService.dynamicLike(JSON.parseObject(str).getLong("vid"),userId);
        return CommonResult.success();
    }

    @PostMapping("/video/coin")
    @ApiOperation("视频投币~")
    public CommonResult addCoin(@RequestParam("uid") long userId,@RequestBody @Validated CoinInputDto dto) throws VideoException, UserException {
        boolean flag = videoLogService.addCoins(dto.getVid(),userId,dto.getNum());
        return CommonResult.success();
    }

    @PostMapping("/video/collection")
    @ApiOperation("动态处理收藏")
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "vid",value = "视频主键",paramType = "body",dataType = "Integer"
            ))
    public CommonResult dynamicCollection(@RequestParam("uid") long userId,@RequestBody String str){
        boolean flag = videoLogService.dynamicCollection(JSON.parseObject(str).getLong("vid"),userId);
        return CommonResult.success();
    }


}
