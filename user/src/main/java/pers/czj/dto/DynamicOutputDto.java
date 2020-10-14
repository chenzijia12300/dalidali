package pers.czj.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.czj.constant.DynamicType;

import java.util.Date;

/**
 * 创建在 2020/10/9 19:28
 */
@Data
public class DynamicOutputDto {

    @ApiModelProperty("动态主键")
    private long id;

    @ApiModelProperty("发送用户的主键")
    private long uid;

    @ApiModelProperty("对应事务(视频/专栏)的主键")
    private long oid;

    @ApiModelProperty("转发数")
    private long forwardNum;

    @ApiModelProperty("点赞数")
    private long praiseNum;

    @ApiModelProperty("评论数")
    private long commentNum;

    @ApiModelProperty("动态文字内容")
    private String content;

    @ApiModelProperty("图片列表")
    private String imgs;

    @ApiModelProperty("UP主名字")
    private String upName;

    @ApiModelProperty("UP主图片")
    private String upImg;

    @ApiModelProperty("动态类型")
    private DynamicType type;

    @ApiModelProperty("消息创建时间")
    private Date createTime;

    @ApiModelProperty("对应主题对象")
    private Object object;
}
