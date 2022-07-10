package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.czj.entity.UserCollectionLog;
import pers.czj.mapper.UserCollectionLogMapper;
import pers.czj.mapper.UserMapper;
import pers.czj.service.UserCollectionLogService;

/**
 * 创建在 2020/7/20 22:38
 */
@Service
public class UserCollectionLogServiceImpl extends ServiceImpl<UserCollectionLogMapper, UserCollectionLog> implements UserCollectionLogService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean dynamicLog(long uid, long vid, boolean isAdd) {
        if (isAdd) {
            UserCollectionLog log = new UserCollectionLog(uid, vid);
            save(log);
        } else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid", uid);
            queryWrapper.eq("vid", vid);
            remove(queryWrapper);
        }
        return true;
    }
}
