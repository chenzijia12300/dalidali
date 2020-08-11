package pers.czj.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.entity.Message;
import pers.czj.service.MessageService;

/**
 * 创建在 2020/8/10 21:39
 */
@RestController
@Api("消息api接口")
@RequestMapping("/api")
public class MessageApi {

    private static final Logger log = LoggerFactory.getLogger(MessageApi.class);

    private MessageService messageService;

    @Autowired
    public MessageApi(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message")
    @ApiOperation("提交消息")
    public CommonResult addMessage(@RequestBody Message message){
        boolean flag = messageService.save(message);
        return CommonResult.success();
    }

}
