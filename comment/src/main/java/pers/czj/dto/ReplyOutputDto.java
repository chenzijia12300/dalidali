package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 创建在 2020/7/15 17:01
 */
@ApiModel("回复传输输出类")
@Data
public class ReplyOutputDto {

    @ApiModelProperty("回复主键")
    private long id;

    @ApiModelProperty("回复评论的主键")
    private long cid;

    @ApiModelProperty("回复目标的用户的主键")
    private long ruid;

    @ApiModelProperty("回复人的主键")
    private long uid;

    @ApiModelProperty("点赞数")
    private long praiseNum;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户头像")
    private String img;

    @ApiModelProperty("被回复人的用户名")
    private String rusername;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("点赞记录")
    private long flag;

}
