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
     * @author czj
     * 动态处理视频的点赞/投币/收藏等记录操作
     * @date 2020/7/19 22:42
     * @param [entity]
     * @return boolean
     */
    public boolean handlerVideoLog(VideoLog entity);


    /**
     * @author czj
     * 动态处理点赞/取消赞的操作
     * @date 2020/7/20 10:46
     * @param [vid, uid]
     * @return boolean
     */
    public boolean dynamicLike(long vid,long uid);

    /**
     * @author czj
     * 投币~
     * @date 2020/7/20 11:08
     * @param [vid, uid, num]
     * @return boolean
     */
    public boolean addCoins(long vid,long uid,int num) throws VideoException, UserException;


    /**
     * @author czj
     * 动态收藏/取消收藏的操作
     * @date 2020/7/20 21:46
     * @param [vid, uid]
     * @return boolean
     */
    public boolean dynamicCollection(long vid,long uid);


    /**
     * 一键三联
     * @author czj
     * @date 2020/10/6 14:14
     * @param [vid, uid]
     * @return boolean
     */
    public boolean allOperate(long vid,long uid) throws VideoException, UserException;

}
