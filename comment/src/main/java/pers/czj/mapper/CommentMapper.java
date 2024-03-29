package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import pers.czj.dto.CommentOutputDto;
import pers.czj.entity.Comment;

import java.util.List;

/**
 * 创建在 2020/7/15 16:38
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * @param tableName, id, userId, orderField
     * @return pers.czj.dto.CommentOutputDto
     * @author czj
     * tableName:动态表名
     * id:对应视频/专栏主键
     * @date 2020/7/15 17:06
     */
    public List<CommentOutputDto> listComment(@Param("tableName") String tableName, @Param("pId") long id, @Param("userId") long userId, @Param("orderField") String orderField);


    /**
     * @param [name, cid, num]
     * @return int
     * @author czj
     * 添加播放量
     * @date 2020/7/19 18:45
     */
    @Update("UPDATE ${name}_comment SET praise_num=praise_num+#{num} WHERE id = #{cid}")
    public int incrPraiseNum(String name, long cid, long num);

    @Update("UPDATE ${name}_comment SET reply_num=reply_num+#{num} WHERE id = #{cid}")
    public int incrReplyNum(String name, long cid, long num);
}
