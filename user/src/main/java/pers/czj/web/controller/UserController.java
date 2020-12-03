package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.connection.Connection;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.LoginUserInputDto;
import pers.czj.dto.RegisterUserInputDto;
import pers.czj.entity.User;
import pers.czj.exception.UserException;
import pers.czj.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;

/**
 * 创建在 2020/7/10 16:00
 */
@RestController
@Api(tags = "用户基本接口,web端/移动端关注这个接口就可以了")
@Validated
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private Connection connection;
//
//    private Map<Long,Channel> map = new ConcurrentHashMap<>(2);


    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public CommonResult login(HttpSession httpSession, @RequestBody @Validated LoginUserInputDto inputDto) throws UserException {
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

    @GetMapping("/user/self")
    public CommonResult getSelfUserInfo(@RequestParam long uid) throws UserException {
        return CommonResult.success(userService.findDetailsUserInfoById(uid));
    }

    @GetMapping("/logout")
    @ApiOperation("注销接口")
    public CommonResult logout(HttpSession session){
        if (!session.isNew())
            session.invalidate();
        return CommonResult.success("注销成功");
    }

    @GetMapping("/user/coin/{id}")
    @ApiOperation(value = "",hidden = true)
    public long findCoinNumById(@PathVariable("id") long id){
        return userService.findCoinNumById(id);
    }

    @PostMapping("/user/coin")
    @ApiOperation(value = "",hidden = true)
    public CommonResult incrCoinNumById(@RequestParam long id,@RequestParam int num){
        return CommonResult.success(userService.incrCoinNum(id,num));
    }


    @GetMapping("/user/details/{uid}")
    public CommonResult findUserDetails(@PathVariable("uid") @Min(1) long uid) throws UserException {
        return CommonResult.success(userService.findDetailsUserInfoById(uid));
    }




//     @PostMapping("/test/send")
//    public CommonResult testSend(@RequestBody String str){
//        rabbitTemplate.convertAndSend("dalidali-test-exchange","test",str);
//        return CommonResult.success();
//    }
//
//    @GetMapping("/test/receive")
//    public CommonResult testReceive(HttpSession session) throws IOException {
//        Channel channel = connection.createChannel(false);
//
//        GetResponse response = channel.basicGet("dalidali-test-queue",false);
//        log.info("message:{}",response);
//        long adminId = 1;*//*(long) session.getAttribute("ADMIN_ID");*//*
//        session.setAttribute("ADMIN_ID",adminId);
//        map.put(adminId,channel);
//        return CommonResult.success(response.getEnvelope().getDeliveryTag(),new String(response.getBody()));
//    }
//
//    @PostMapping("/test/ack")
//    public CommonResult testAck(HttpSession session,@RequestBody String str) throws IOException {
//        long adminId = (long) session.getAttribute("ADMIN_ID");
//        Channel channel = map.get(adminId);
//        channel.basicAck(JSON.parseObject(str).getLong("id"),false);
//        rabbitTemplate.convertAndSend("dalidali-video-exchange","video");
//        return CommonResult.success("审核成功！");
//    }


}
