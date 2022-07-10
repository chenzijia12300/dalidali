package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.entity.VideoLog;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;

/**
 * 创建在 2020/7/19 21:47
 */
public interface VideoLogService extends IService<VideoLog> {

    /**
     * @param [entity]
     * @return boolean
     * @author czj
     * 动态处理视频的点赞/投币/收藏等记录操作
     * @date 2020/7/19 22:42
     */
    public boolean handlerVideoLog(VideoLog entity);


    /**
     * @param [vid, uid]
     * @return boolean
     * @author czj
     * 动态处理点赞/取消赞的操作
     * @date 2020/7/20 10:46
     */
    public boolean dynamicLike(long vid, long uid);

    /**
     * @param [vid, uid, num]
     * @return boolean
     * @author czj
     * 投币~
     * @date 2020/7/20 11:08
     */
    public boolean addCoins(long vid, long uid, int num) throws VideoException, UserException;


    /**
     * @param [vid, uid]
     * @return boolean
     * @author czj
     * 动态收藏/取消收藏的操作
     * @date 2020/7/20 21:46
     */
    public boolean dynamicCollection(long vid, long uid);


    /**
     * 一键三联
     *
     * @param [vid, uid]
     * @return boolean
     * @author czj
     * @date 2020/10/6 14:14
     */
    public boolean allOperate(long vid, long uid) throws VideoException, UserException;

}
