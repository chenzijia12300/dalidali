package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.constant.ActionType;
import pers.czj.constant.TableNameEnum;
import pers.czj.dto.CommonRequest;
import pers.czj.dto.Page;
import pers.czj.dto.ReplyInputDto;
import pers.czj.dto.ReplyOutputDto;
import pers.czj.entity.Reply;
import pers.czj.exception.ReplyException;
import pers.czj.feign.MessageFeignClient;
import pers.czj.service.ReplyService;

import javax.servlet.http.HttpSession;
import java.util.List;
import static pers.czj.utils.MessageUtils.createMessage;

/**
 * 创建在 2020/7/18 14:23
 */
@CrossOrigin
@RestController
@Api(tags = "回复接口")
public class ReplyController {

    private static final Logger log = LoggerFactory.getLogger(ReplyController.class);

    private ReplyService replyService;

    private MessageFeignClient messageFeignClient;

    @Autowired
    public ReplyController(ReplyService replyService, MessageFeignClient messageFeignClient) {
        this.replyService = replyService;
        this.messageFeignClient = messageFeignClient;
    }

    @PostMapping("/reply")
    @ApiOperation("添加回复接口")
    public CommonResult addReply(@RequestParam long uid,@RequestBody @Validated ReplyInputDto dto) throws ReplyException {
        Reply reply = dto.convert();
        reply.setUid(uid);
        boolean flag = replyService.save(reply);
        if (!flag){
            throw new ReplyException("添加回复失败，请重试");
        }
        //添加消息
        messageFeignClient.addMessage(createMessage(uid,reply.getRuid(),ActionType.REPLY,reply.getContent()));
        return CommonResult.success();
    }

    @GetMapping("/reply/list/{tableName}/{id}/{pageNum}/{pageSize}")
    @ApiOperation("获得回复列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName",paramType = "path",value = "视频（VIDEO）/专栏（POST）"),
            @ApiImplicitParam(name = "id",value = "对应主键",paramType = "path"),
            @ApiImplicitParam(name = "pageNum",value = "第X页（X>=1）"),
            @ApiImplicitParam(name = "pageSize",value = "获得条数")
    })
    public CommonResult listReply(@RequestParam long uid, @PathVariable("tableName") TableNameEnum nameEnum,
                                  @PathVariable("id") long id,
                                  @PathVariable("pageNum")int pageNum,
                                  @PathVariable("pageSize")int pageSize){
        List<ReplyOutputDto> replyOutputDtoList = replyService.listReply(nameEnum,id,uid,pageNum,pageSize);
        return CommonResult.success(replyOutputDtoList);
    }
}
