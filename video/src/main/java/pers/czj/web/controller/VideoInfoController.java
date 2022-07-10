package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.dto.CoinInputDto;
import pers.czj.dto.VideoIdDto;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;
import pers.czj.service.VideoLogService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 创建在 2020/7/20 23:26
 */
@RestController
@Api(tags = "视频信息接口")
public class VideoInfoController {

    @Autowired
    private VideoLogService videoLogService;


    @PostMapping("/video/dynamic_like")
    @ApiOperation("动态处理点赞")
    public CommonResult dynamicLike(@ApiIgnore @RequestParam("uid") long userId, @RequestBody VideoIdDto dto) {
        boolean flag = videoLogService.dynamicLike(dto.getVid(), userId);
        return CommonResult.success();
    }

    @PostMapping("/video/coin")
    @ApiOperation("视频投币~")
    public CommonResult addCoin(@RequestParam("uid") long userId, @RequestBody @Validated CoinInputDto dto) throws VideoException, UserException {
        boolean flag = videoLogService.addCoins(dto.getVid(), userId, dto.getNum());
        return CommonResult.success();
    }

    @PostMapping("/video/collection")
    @ApiOperation("动态处理收藏")
    public CommonResult dynamicCollection(@RequestParam("uid") long userId, @RequestBody VideoIdDto dto) {
        boolean flag = videoLogService.dynamicCollection(dto.getVid(), userId);
        return CommonResult.success();
    }

    @PostMapping("/video/all/operate")
    public CommonResult allOperate(@RequestParam("uid") long userId, @RequestBody VideoIdDto dto) throws VideoException, UserException {
        videoLogService.allOperate(dto.getVid(), userId);
        return CommonResult.success();
    }

}
