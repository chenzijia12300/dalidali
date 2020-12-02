package pers.czj.service;

import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/12/2 11:29
 */
public interface UserInfoService {

    /**
     * 获得用户所发布的视频
     * @author czj
     * @date 2020/12/2 11:35
     * @param uid,pageNum,pageSize
     * @return java.util.List
     */
    public List findPublishVideoInfo(long uid,int pageNum,int pageSize);


    /**
     * 获取用户所收藏的视频
     * @author czj
     * @date 2020/12/2 11:36
     * @param [uid, pageNum, pageSize]
     * @return java.util.List
     */
    public List findCollectVideoInfo(long uid,int pageNum,int pageSize);


    /**
     * 获取用户所投币的视频
     * @author czj
     * @date 2020/12/2 11:37
     * @param [uid, pageNum, pageSize]
     * @return java.util.List
     */
    public List findHasCoinVideoInfo(long uid, int pageNum, int pageSize);


    /**
     * 获取用户所点赞的视频
     * @author czj
     * @date 2020/12/2 11:38
     * @param [uid]
     * @return java.util.List
     */
    public List findPraiseCoinVideoInfo(long uid,int pageNum,int pageSize);
}
