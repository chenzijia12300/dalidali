package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.constant.TableNameEnum;
import pers.czj.dto.CommonRequest;
import pers.czj.dto.Page;
import pers.czj.dto.ReplyInputDto;
import pers.czj.dto.ReplyOutputDto;
import pers.czj.entity.Reply;
import pers.czj.exception.ReplyException;
import pers.czj.service.ReplyService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 创建在 2020/7/18 14:23
 */
@CrossOrigin
@RestController
@Api("回复接口")
public class ReplyController {

    private static final Logger log = LoggerFactory.getLogger(ReplyController.class);

    @Autowired
    private ReplyService replyService;

    @PostMapping("/reply")
    @ApiOperation("添加回复接口")
    public CommonResult addReply(HttpSession httpSession,@RequestBody @Validated ReplyInputDto dto) throws ReplyException {
        Reply reply = dto.convert();
        long userId = /*(long) httpSession.getAttribute("USER_ID");*/1;
        reply.setUid(userId);
        boolean flag = replyService.save(reply);
        if (!flag){
            throw new ReplyException("添加回复失败，请重试");
        }
        return CommonResult.success();
    }

    @GetMapping("/reply/list/{tableName}/{id}/{pageNum}/{pageSize}")
    @ApiOperation("获得回复列表")
    public CommonResult listReply(HttpSession httpSession, @PathVariable("tableName") TableNameEnum nameEnum,
                                  @PathVariable("id") long id,
                                  @PathVariable("pageNum")int pageNum,
                                  @PathVariable("pageSize")int pageSize){
        long userId = 1/*(long) httpSession.getAttribute("USER_ID")*/;
        List<ReplyOutputDto> replyOutputDtoList = replyService.listReply(nameEnum,id,userId,pageNum,pageSize);
        return CommonResult.success(replyOutputDtoList);
    }
}
