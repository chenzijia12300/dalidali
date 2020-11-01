package pers.czj.service;

/**
 * 创建在 2020/11/1 14:25
 */
public interface CommentNumService {

    /**
     * 自增评论数
     * @author czj
     * @date 2020/11/1 14:27
     * @param [pid, num]
     * @return boolean
     */
    public boolean incrCommentNum(long pid,int num);
}
