package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.service.UserInfoService;

/**
 * 创建在 2020/12/2 11:21
 */
@RestController
@RequestMapping("/user/info")
@Api(tags = "用户个人信息接口")
public class UserInfoController {

    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);


    private UserInfoService userInfoService;


    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/collect/{uid}/{pageNum}/{pageSize}")
    public CommonResult findCollectVideo(@PathVariable("uid") long uid,
                                         @PathVariable("pageNum")int pageNum, @PathVariable("pageSize")int pageSize){
        return CommonResult.success(userInfoService.findCollectVideoInfo(uid,pageNum,pageSize));
    }


    @GetMapping("/publish/{uid}/{pageNum}/{pageSize}")
    public CommonResult findPublishVideo(@PathVariable("uid")long uid,
                                         @PathVariable("pageNum")int pageNum, @PathVariable("pageSize")int pageSize){
        return CommonResult.success(userInfoService.findPublishVideoInfo(uid,pageNum,pageSize));
    }

    @GetMapping("/coin/{uid}/{pageNum}/{pageSize}")
    public CommonResult findHasCoinVideo(@PathVariable("uid")long uid,
                                         @PathVariable("pageNum")int pageNum, @PathVariable("pageSize")int pageSize){
        return CommonResult.success(userInfoService.findHasCoinVideoInfo(uid,pageNum,pageSize));
    }

    @GetMapping("/praise/{uid}/{pageNum}/{pageSize}")
    public CommonResult findPraiseVideo(@PathVariable("uid")long uid,
                                        @PathVariable("pageNum")int pageNum, @PathVariable("pageSize")int pageSize){
        return CommonResult.success(userInfoService.findPraiseCoinVideoInfo(uid,pageNum,pageSize));
    }
}
