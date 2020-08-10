package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.constant.ActionType;
import pers.czj.constant.OrderFieldEnum;
import pers.czj.constant.TableNameEnum;
import pers.czj.dto.*;
import pers.czj.entity.Comment;
import pers.czj.exception.CommentException;
import pers.czj.feign.MessageFeignClient;
import pers.czj.feign.VideoFeignClient;
import pers.czj.service.CommentService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static pers.czj.utils.MessageUtils.createMessage;

/**
 * 创建在 2020/7/15 21:12
 */
@CrossOrigin
@RestController
@Api("评论接口")
public class CommentController {

    private CommentService commentService;

    private VideoFeignClient videoFeignClient;

    private MessageFeignClient messageFeignClient;


    @Autowired
    public CommentController(CommentService commentService, VideoFeignClient videoFeignClient, MessageFeignClient messageFeignClient) {
        this.commentService = commentService;
        this.videoFeignClient = videoFeignClient;
        this.messageFeignClient = messageFeignClient;
    }

    @PostMapping("/comment")
    @ApiOperation("添加评论")
    public CommonResult addComment(@RequestParam long uid,@RequestBody CommentInputDto dto) throws CommentException {
        Comment comment = dto.convert();
        comment.setUid(uid);
        boolean flag = commentService.save(comment);
        if (!flag){
            throw new CommentException("发表评论失败，请重试~");
        }
        videoFeignClient.incrCommentNum(new NumberInputDto(comment.getPid(),1));
        //发送消息
        messageFeignClient.addMessage(createMessage(uid,dto.getPuid(),ActionType.COMMENT,dto.getContent()));
        return CommonResult.success("发表评论成功！");
    }

    @GetMapping("/comment/list/praise/{tableName}/{id}/{pageNum}/{pageSize}")
    @ApiOperation("获得评论列表")
    public CommonResult listPraiseComment(@RequestParam long userId, @PathVariable("tableName") TableNameEnum nameEnum,
                                    @PathVariable("id") long id,
                                    @PathVariable("pageNum")int pageNum,
                                    @PathVariable("pageSize")int pageSize){
        List<CommentOutputDto> dtoList = commentService.listComment(nameEnum,id,userId,pageNum,pageSize, OrderFieldEnum.PRAISE);
        return CommonResult.success(dtoList,"返回热门评论列表成功~");
    }

    @GetMapping("/comment/list/time/{tableName}/{id}/{pageNum}/{pageSize}")
    @ApiOperation("获得评论列表")
    public CommonResult listCreateTimeComment(@RequestParam long userId, @PathVariable("tableName") TableNameEnum nameEnum,
                                    @PathVariable("id") long id,
                                    @PathVariable("pageNum")int pageNum,
                                    @PathVariable("pageSize")int pageSize){
        List<CommentOutputDto> dtoList = commentService.listComment(nameEnum,id,userId,pageNum,pageSize, OrderFieldEnum.CREATE_TIME);
        return CommonResult.success(dtoList,"返回热门评论列表成功~");
    }


    @PostMapping("/comment/dynamic_like")
    @ApiOperation("操作点赞/取消赞的操作")
    public CommonResult likeComment(HttpSession httpSession,@RequestBody CommonRequest request){
        long userId = /*(long) httpSession.getAttribute("USER_ID");*/1;
        commentService.dynamicHandlerLike(request.getName(),request.getId(),userId);
        return CommonResult.success();
    }




}
