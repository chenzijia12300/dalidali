package pers.czj.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.czj.dto.BasicUserInfoOutputDto;
import pers.czj.service.UserInfoService;
import pers.czj.service.UserService;
import pers.czj.utils.TempUserUtils;

import java.util.Map;

/**
 * 创建在 2020/9/6 12:08
 */
@RestController
public class UserApi {

    private UserService userService;

    private TempUserUtils tempUserUtils;

    @Autowired
    public UserApi(UserService userService, TempUserUtils tempUserUtils) {
        this.userService = userService;
        this.tempUserUtils = tempUserUtils;
    }

    @GetMapping("/user/basic/{uid}/{followerUserId}")
    public BasicUserInfoOutputDto findBasicUserInfoById(@PathVariable("uid") long uid,@PathVariable("followerUserId")long followerUserId){
        return userService.findBasicUserInfoById(uid,followerUserId);
    }

    @PostMapping("/user/create")
    public Long createUserIfNeeded(@RequestBody Map<String,String> map){
        return tempUserUtils.createUserIfNeeded(map.get("username"),map.get("img"));
    }

}
