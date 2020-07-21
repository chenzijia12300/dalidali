package pers.czj.web.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    public CommonResult dynamicLike(HttpSession httpSession,@RequestBody String str){
        long userId = 1;httpSession.getAttribute("USER_ID");
        boolean flag = videoLogService.dynamicLike(JSON.parseObject(str).getLong("vid"),userId);
        return CommonResult.success();
    }

    @PostMapping("/video/coin")
    @ApiOperation("视频投币~")
    public CommonResult addCoin(HttpSession httpSession,@RequestBody @Validated CoinInputDto dto) throws VideoException, UserException {
        long userId = 1;httpSession.getAttribute("USER_ID");
        boolean flag = videoLogService.addCoins(dto.getVid(),userId,dto.getNum());
        return CommonResult.success();
    }

    @PostMapping("/video/collection")
    @ApiOperation("动态处理收藏")
    public CommonResult dynamicCollection(HttpSession httpSession,@RequestBody String str){
        long userId = 1;httpSession.getAttribute("USER_ID");
        boolean flag = videoLogService.dynamicCollection(JSON.parseObject(str).getLong("vid"),userId);
        return CommonResult.success();
    }


}
