package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.constant.TableNameEnum;
import pers.czj.dto.CommentOutputDto;
import pers.czj.entity.Comment;

import java.util.List;

/**
 * 创建在 2020/7/15 17:38
 */
public interface CommentService extends IService<Comment> {

/*    *//**
     * @author czj
     * 根据动态表名添加
     * @date 2020/7/19 11:13
     * @param [tableNameEnum, entity]
     * @return boolean
     *//*
    public boolean save(TableNameEnum tableNameEnum,Comment entity);*/

    /**
     * @author czj
     * 根据表名和对应主键查询评论列表
     * @date 2020/7/15 17:44
     * @param [nameEnum, id]
     * @return pers.czj.dto.CommentOutputDto
     */
    public List<CommentOutputDto> listComment(TableNameEnum nameEnum, long id,long userId,int pageNum, int pageSize);


    /**
     * @author czj
     * 动态处理点赞情况
     * @date 2020/7/18 23:38
     * @param [tableNameEnum, id, userId]
     * @return boolean
     */
    public boolean dynamicHandlerLike(TableNameEnum tableNameEnum,long id,long userId);
}
