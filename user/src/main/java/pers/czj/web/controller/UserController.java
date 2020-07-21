package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.LoginUserInputDto;
import pers.czj.dto.RegisterUserInputDto;
import pers.czj.common.User;
import pers.czj.exception.UserException;
import pers.czj.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * 创建在 2020/7/10 16:00
 */
@RestController
@Api("用户基本接口")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public CommonResult login(HttpSession httpSession,@RequestBody @Validated LoginUserInputDto inputDto) throws UserException {
        log.info("account:{}\tpassword:{}",inputDto.getAccount(),inputDto.getPassword());
        User user = userService.login(inputDto.getAccount(),inputDto.getPassword());
        httpSession.setAttribute("USER_ID",user.getId());
        log.debug("sessionID:{}",httpSession.getId());
        return CommonResult.success(user);
    }

    @PostMapping("/user")
    @ApiOperation("注册用户")
    public CommonResult register(@RequestBody @Validated RegisterUserInputDto inputDto) throws UserException {
        User user = inputDto.convert();
        userService.register(user);
        return CommonResult.success("注册成功！",user.getAccount());
    }

    @GetMapping("/logout")
    @ApiOperation("注销接口")
    public CommonResult logout(HttpSession session){
        if (!session.isNew())
            session.invalidate();
        return CommonResult.success("注销成功");
    }

    @GetMapping("/user/coin/{id}")
    public long findCoinNumById(@PathVariable("id") long id){
        return userService.findCoinNumById(id);
    }

    @PostMapping("/user/coin")
    public CommonResult incrCoinNumById(@RequestParam long id,@RequestParam int num){
        return CommonResult.success(userService.incrCoinNum(id,num));
    }

}
