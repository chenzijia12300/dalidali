package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import pers.czj.entity.Comment;

/**
 * 创建在 2020/7/15 21:14
 */
@Data
@ApiModel
public class CommentInputDto {

    private long pid;

    private String content;

    public Comment convert(){
        Comment comment = new Comment();
        BeanUtils.copyProperties(this,comment);
        return comment;
    }
}
