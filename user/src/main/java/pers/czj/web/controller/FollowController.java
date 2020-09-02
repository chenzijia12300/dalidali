package pers.czj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.service.FollowService;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 创建在 2020/7/22 13:26
 */
@RestController
@Api(tags = "关注接口")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/dynamic_follow")
    @ApiOperation("动态处理关注")
    public CommonResult dynamicFollow(@RequestParam long uid, @RequestBody Map<String,Long> params){
        followService.dynamicFollow(uid,params.get("followUserId"));
        return CommonResult.success();
    }


    @GetMapping("/follow/user")
    @ApiOperation("获得被关注着的基本信息")
    public CommonResult findByFollowBasicUserInfo(@RequestParam long uid){
        return CommonResult.success(followService.findByFollowBasicInfo(uid));
    }

}
