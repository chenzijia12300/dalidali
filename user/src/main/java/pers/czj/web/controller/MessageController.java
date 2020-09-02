package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.constant.ActionType;
import pers.czj.entity.Message;
import pers.czj.service.MessageService;
import pers.czj.service.UserService;

import java.util.Date;
import java.util.List;

/**
 * 创建在 2020/8/10 21:38
 */
@RestController
@Api(tags = "消息接口")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private MessageService messageService;

    private UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/message/list/{pageNum}/{pageSize}/{type}")
    @ApiOperation("获得消息列表")
    public CommonResult listMessageByType(@RequestParam long uid,
                                          @PathVariable(value = "type",required = false)ActionType type,
                                          @PathVariable("pageNum")int pageNum,
                                          @PathVariable("pageSize")int pageSize){
        List<Message> messages = messageService.listMessageByType(uid,type,pageNum,pageSize);
        log.info("用户:{}更改最后阅读时间",uid);
        userService.updateLastReadMessageTime(uid,new Date());
        return CommonResult.success(messages);
    }


    @GetMapping("/message/unread")
    public CommonResult findUnreadCount(@RequestParam long uid){
        return CommonResult.success(messageService.findUnreadCount(uid));
    }
}
