package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.czj.common.User;
import pers.czj.dto.BasicUserInfoOutputDto;
import pers.czj.dto.DetailsUserInfoOutputDto;
import pers.czj.exception.UserException;
import pers.czj.mapper.FollowMapper;
import pers.czj.mapper.UserMapper;
import pers.czj.service.UserService;

import java.util.Date;
import java.util.Objects;

/**
 * 创建在 2020/7/10 16:03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private FollowMapper followMapper;

    @Autowired
    public UserServiceImpl(FollowMapper followMapper) {
        this.followMapper = followMapper;
    }

    @Override
    public User login(String account,String password) throws UserException {
        
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(userQueryWrapper -> {
            userQueryWrapper.eq("account",account);
            userQueryWrapper.eq("password",password);
        });
        User user = baseMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)){
            throw new UserException("账户或密码错误，请重新尝试~");
        }
        return user;
    }

    @Override
    public boolean register(User user) throws UserException {
        int row = baseMapper.insert(user);
        if (row==0){
            throw new UserException("遇到未知原因，注册账户失败，请重试~");
        }
        return true;
    }

    @Override
    public int findCoinNumById(long id) {
        return baseMapper.findCoinNumById(id);
    }

    @Override
    public int incrCoinNum(long id, int num) {
        return baseMapper.incrCoinNum(id,num);
    }

    @Override
    public boolean updateLastReadDynamicTime(long uid, Date lastTime) {
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.set("read_dynamic_time",lastTime);
        wrapper.eq("id",uid);
        return update(wrapper);
    }

    @Override
    public boolean updateLastReadMessageTime(long uid, Date lastTime) {
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.set("read_message_time",lastTime);
        wrapper.eq("id",uid);
        return update(wrapper);
    }

    @Override
    public BasicUserInfoOutputDto findBasicUserInfoById(long uid) {
//        BasicUserInfoOutputDto dto = baseMapper.findBasicUserInfoById(uid);
//        followMapper.findFollowRelation();
        throw new AssertionError("方法被废弃，请选择其他方法");
    }

    @Override
    public BasicUserInfoOutputDto findBasicUserInfoById(long userId, long followUserId) {
        BasicUserInfoOutputDto dto = baseMapper.findBasicUserInfoById(userId);
        dto.setFollow(ObjectUtils.isEmpty(followMapper.findFollowRelation(userId,followUserId))?false:true);

        return dto;
    }

    @Override
    public DetailsUserInfoOutputDto findDetailsUserInfoById(long uid) throws UserException {
        DetailsUserInfoOutputDto dto =  baseMapper.findDetailsUserInfoById(uid);
        if (ObjectUtils.isEmpty(dto)){
            throw new UserException("该用户不存在");
        }
        return dto;
    }


}
