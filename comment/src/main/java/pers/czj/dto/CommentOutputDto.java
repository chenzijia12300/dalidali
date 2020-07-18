package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 创建在 2020/7/15 16:58
 */
@Data
@ApiModel("评论传输输出类")
public class CommentOutputDto {

    @ApiModelProperty("评论主键")
    private long id;

    @ApiModelProperty("评论用户主键")
    private long uid;

    @ApiModelProperty("点赞数")
    private long praiseNum;

    @ApiModelProperty("回复数")
    private long replyNum;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户头像")
    private String img;

    @ApiModelProperty("评论时间")
    private Date createTime;

    @ApiModelProperty("回复列表")
    private List<ReplyOutputDto> replyList;

    @ApiModelProperty("点赞记录")
    private long flag;

}
