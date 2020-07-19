package pers.czj.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
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

    private String content;

    @TableLogic
    private TableNameEnum tableNameEnum;

    public Comment convert(){
        Comment comment = new Comment();
        BeanUtils.copyProperties(this,comment);
        comment.setTableName(tableNameEnum.getName());
        return comment;
    }
}
