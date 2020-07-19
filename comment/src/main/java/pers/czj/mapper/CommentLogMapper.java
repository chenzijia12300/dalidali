package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import pers.czj.entity.CommentLog;

/**
 * 创建在 2020/7/19 16:57
 */
public interface CommentLogMapper extends BaseMapper<CommentLog> {

    @Select("SELECT id FROM ${tableName}_comment_log WHERE cid = #{cid} AND uid = #{uid} FOR UPDATE")
    public Long select(String tableName,long cid,long uid);

    @Insert("INSERT INTO ${tableName}_comment_log(cid,uid) VALUES(#{log.cid},#{log.uid})")
    public int addLog(String tableName,CommentLog log);

    @Delete("DELETE FROM ${tableName}_comment_log WHERE id = #{id}")
    public int deleteLog(String tableName,long id);
}
