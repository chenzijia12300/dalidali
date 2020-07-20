package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.entity.UserCollectionLog;

/**
 * 创建在 2020/7/20 22:38
 */
public interface UserCollectionLogService extends IService<UserCollectionLog> {

    /**
     * @author czj
     * 动态处理收藏记录
     * @date 2020/7/20 23:09
     * @param [uid, vid , isAdd]
     * @return boolean
     */
    public boolean dynamicLog(long uid,long vid,boolean isAdd);
}
