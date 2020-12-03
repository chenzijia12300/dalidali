package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.dto.BasicUserInfoOutputDto;
import pers.czj.entity.Follow;

import java.util.List;

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


    /**
     * @author czj
     * 获得所关注人的基本信息
     * @date 2020/8/12 16:58
     * @param []
     * @return java.util.List<pers.czj.dto.BasicUserInfoOutputDto>
     */
    public List<BasicUserInfoOutputDto> findByFollowBasicInfo(long uid);
}
