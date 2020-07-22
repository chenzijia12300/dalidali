package pers.czj.web.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.service.FollowService;

import javax.servlet.http.HttpSession;

/**
 * 创建在 2020/7/22 13:26
 */
@RestController

@Api("关注接口")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/dynamic_follow")
    @ApiOperation("动态处理关注")
    public CommonResult dynamicFollow(HttpSession httpSession,@RequestBody String str){
        long userId = (long) httpSession.getAttribute("USER_ID");
        followService.dynamicFollow(userId, JSON.parseObject(str).getLong("uid"));
        return CommonResult.success();
    }
}
