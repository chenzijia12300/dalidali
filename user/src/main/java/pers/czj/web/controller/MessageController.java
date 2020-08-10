package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.constant.ActionType;
import pers.czj.entity.Message;
import pers.czj.service.MessageService;

import java.util.List;

/**
 * 创建在 2020/8/10 21:38
 */
@RestController
@Api("消息接口")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/message/list/{type}/{pageNum}/{pageSize}")
    @ApiOperation("获得消息列表")
    public CommonResult listMessageByType(@RequestParam long uid,
                                          @PathVariable(value = "type",required = false)ActionType type,
                                          @PathVariable("pageNum")int pageNum,
                                          @PathVariable("pageSize")int pageSize){
        List<Message> messages = messageService.listMessageByType(uid,type,pageNum,pageSize);
        return CommonResult.success(messages);
    }
}
