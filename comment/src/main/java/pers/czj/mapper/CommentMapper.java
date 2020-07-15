package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.czj.dto.CommentOutputDto;
import pers.czj.entity.Comment;

import java.util.List;

/**
 * 创建在 2020/7/15 16:38
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * @author czj
     * tableName:动态表名
     * id:对应视频/专栏主键
     * @date 2020/7/15 17:06
     * @param [tableName, id]
     * @return pers.czj.dto.CommentOutputDto
     */
    public List<CommentOutputDto> listComment(String tableName, long id);
}
