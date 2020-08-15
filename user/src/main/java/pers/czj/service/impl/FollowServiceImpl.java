package pers.czj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import pers.czj.dto.BasicUserInfoOutputDto;
import pers.czj.entity.Follow;
import pers.czj.mapper.FollowMapper;
import pers.czj.mapper.UserMapper;
import pers.czj.service.FollowService;

import java.util.List;

/**
 * 创建在 2020/7/22 13:25
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow>implements FollowService {

    private static final Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean dynamicFollow(long userId, long followUserId) {
        Long id = baseMapper.findFollowRelation(userId,followUserId);
        if (ObjectUtils.isEmpty(id)){
            Follow follow = new Follow(userId,followUserId);
            save(follow);
            userMapper.incrFollowNum(userId,1);
            userMapper.incrFansNum(followUserId,1);
            log.info("用户{}关注了用户{}",userId,followUserId);
        }else {
            removeById(id);
            userMapper.incrFollowNum(userId,-1);
            userMapper.incrFansNum(followUserId,-1);
            log.info("用户{}取消了对用户{}的关注",userId,followUserId);
        }
        return true;
    }

    @Override
    public List<BasicUserInfoOutputDto> findByFollowBasicInfo(long uid) {
        return findByFollowBasicInfo(uid);
    }
}
