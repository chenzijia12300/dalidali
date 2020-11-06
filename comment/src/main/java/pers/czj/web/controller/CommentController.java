package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import pers.czj.feign.UserFeignClient;
import pers.czj.feign.VideoFeignClient;
import pers.czj.service.CommentService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static pers.czj.utils.MessageUtils.createMessage;

/**
 * 创建在 2020/7/15 21:12
 * 重构于2020/11/06
 */
@CrossOrigin
@RestController
@Api(tags = "评论接口")
public class CommentController {

    private CommentService commentService;

    private VideoFeignClient videoFeignClient;

    private UserFeignClient userFeignClient;



    @Autowired
    public CommentController(CommentService commentService, VideoFeignClient videoFeignClient, UserFeignClient userFeignClient) {
        this.commentService = commentService;
        this.videoFeignClient = videoFeignClient;
        this.userFeignClient = userFeignClient;
    }

    @PostMapping("/comment")
    @ApiOperation("添加评论")
    public CommonResult addComment(@RequestParam long uid,@RequestBody CommentInputDto dto) throws CommentException {
        Comment comment = dto.convert();
        comment.setUid(uid);
        boolean flag = commentService.save(comment);
/*        if (!flag){
            throw new CommentException("发表评论失败，请重试~");
        }*/
        /**
         *  通过前端传值选择评论添加的模块，参数是否被修改都无所谓可以接受（参考于B站）
         */
        switch (dto.getTableNameEnum()){
            case VIDEO:
                videoFeignClient.incrCommentNum(new NumberInputDto(comment.getPid(),1));
                break;
            case DYNAMIC:
                userFeignClient.incrCommentNum(new NumberInputDto(comment.getPid(),1));
                break;
            default:
                throw new RuntimeException("评论模块暂未开发完整");
        }

        //发送消息
        userFeignClient.addMessage(createMessage(uid,dto.getPuid(),ActionType.COMMENT,dto.getContent()));
        return CommonResult.success("发表评论成功！");
    }

    @GetMapping("/comment/list/praise/{tableName}/{id}/{pageNum}/{pageSize}")
    @ApiOperation("获得热门评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName",defaultValue = "VIDEO",paramType = "path",value = "视频（VIDEO）/专栏（POST）"),
            @ApiImplicitParam(name = "id",value = "对应主键",paramType = "path"),
            @ApiImplicitParam(name = "pageNum",value = "第X页（X>=1）"),
            @ApiImplicitParam(name = "pageSize",value = "获得条数")
    })
    public CommonResult listPraiseComment(@RequestParam long uid, @PathVariable("tableName") TableNameEnum nameEnum,
                                    @PathVariable("id") long id,
                                    @PathVariable("pageNum")int pageNum,
                                    @PathVariable("pageSize")int pageSize){
        List<CommentOutputDto> dtoList = commentService.listComment(nameEnum,id,uid,pageNum,pageSize, OrderFieldEnum.PRAISE);
        return CommonResult.success(dtoList,"返回热门评论列表成功~");
    }

    @GetMapping("/comment/list/time/{tableName}/{id}/{pageNum}/{pageSize}")
    @ApiOperation("获得最新评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName",defaultValue = "VIDEO",paramType = "path",value = "视频（VIDEO）/专栏（POST）"),
            @ApiImplicitParam(name = "id",value = "对应主键",paramType = "path"),
            @ApiImplicitParam(name = "pageNum",value = "第X页（X>=1）"),
            @ApiImplicitParam(name = "pageSize",value = "获得条数")
    })
    public CommonResult listCreateTimeComment(@RequestParam long uid, @PathVariable("tableName") TableNameEnum nameEnum,
                                    @PathVariable("id") long id,
                                    @PathVariable("pageNum")int pageNum,
                                    @PathVariable("pageSize")int pageSize){
        List<CommentOutputDto> dtoList = commentService.listComment(nameEnum,id,uid,pageNum,pageSize, OrderFieldEnum.CREATE_TIME);
        return CommonResult.success(dtoList,"返回最新评论列表成功~");
    }


    @PostMapping("/comment/dynamic_like")
    @ApiOperation("操作点赞/取消赞的操作")
    public CommonResult likeComment(@RequestParam long uid,@RequestBody CommonRequest request){
        commentService.dynamicHandlerLike(request.getName(),request.getId(),uid);
        return CommonResult.success();
    }




}
