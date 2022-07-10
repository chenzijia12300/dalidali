package pers.czj.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import pers.czj.constant.TableNameEnum;
import pers.czj.entity.Comment;

/**
 * 创建在 2020/7/15 21:14
 */
@Data
@ApiModel
public class CommentInputDto {

    private long pid;

    private long puid;

    private String content;


    /**
     * TableLogic 描述：表字段逻辑处理注解（逻辑删除
     */
    @TableLogic
    @ApiModelProperty("VIDEO(\"video\"),POST(\"post\"),DYNAMIC(\"dynamic\");")
    private TableNameEnum tableNameEnum;

    public Comment convert() {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        comment.setTableName(tableNameEnum.getName());
        return comment;
    }
}
