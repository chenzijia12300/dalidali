package pers.czj.service;

import java.util.List;

/**
 * 创建在 2020/12/2 11:29
 */
public interface UserInfoService {

    /**
     * 获得用户所发布的视频
     *
     * @param uid,pageNum,pageSize
     * @return java.util.List
     * @author czj
     * @date 2020/12/2 11:35
     */
    public List findPublishVideoInfo(long uid, int pageNum, int pageSize);


    /**
     * 获取用户所收藏的视频
     *
     * @param [uid, pageNum, pageSize]
     * @return java.util.List
     * @author czj
     * @date 2020/12/2 11:36
     */
    public List findCollectVideoInfo(long uid, int pageNum, int pageSize);


    /**
     * 获取用户所投币的视频
     *
     * @param [uid, pageNum, pageSize]
     * @return java.util.List
     * @author czj
     * @date 2020/12/2 11:37
     */
    public List findHasCoinVideoInfo(long uid, int pageNum, int pageSize);


    /**
     * 获取用户所点赞的视频
     *
     * @param [uid]
     * @return java.util.List
     * @author czj
     * @date 2020/12/2 11:38
     */
    public List findPraiseCoinVideoInfo(long uid, int pageNum, int pageSize);
}
