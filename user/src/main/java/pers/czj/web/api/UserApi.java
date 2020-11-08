package pers.czj.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.dto.BasicUserInfoOutputDto;
import pers.czj.service.UserService;

/**
 * 创建在 2020/9/6 12:08
 */
@RestController
public class UserApi {

    private UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/basic/{uid}/{followerUserId}")
    public BasicUserInfoOutputDto findBasicUserInfoById(@PathVariable("uid") long uid,@PathVariable("followerUserId")long followerUserId){
        return userService.findBasicUserInfoById(uid,followerUserId);
    }
}
