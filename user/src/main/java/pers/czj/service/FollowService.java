package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.entity.Follow;

/**
 * 创建在 2020/7/22 13:24
 */
public interface FollowService extends IService<Follow> {

    /**
     * @author czj
     * 动态处理关注人
     * @date 2020/7/22 16:16
     * @param [userId, followUserId]
     * @return boolean
     */
    public boolean dynamicFollow(long userId,long followUserId);
}
