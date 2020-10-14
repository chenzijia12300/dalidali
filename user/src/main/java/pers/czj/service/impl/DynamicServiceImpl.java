package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pers.czj.dto.DynamicOutputDto;
import pers.czj.entity.Dynamic;
import pers.czj.mapper.DynamicMapper;
import pers.czj.mapper.FollowMapper;
import pers.czj.mapper.UserMapper;
import pers.czj.service.DynamicLogService;
import pers.czj.service.DynamicService;

import java.util.Date;
import java.util.List;

/**
 * 创建在 2020/8/9 22:40
 */
@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper,Dynamic>implements DynamicService {

    public static final int POSITIVE_NUM = 1;

    public static final int NEGATIVE_NUM = -1;


    private FollowMapper followMapper;

    private UserMapper userMapper;

    private DynamicLogService logService;

    @Autowired
    public DynamicServiceImpl(FollowMapper followMapper, UserMapper userMapper, DynamicLogService logService) {
        this.followMapper = followMapper;
        this.userMapper = userMapper;
        this.logService = logService;
    }

    @Override
    public List<DynamicOutputDto> listDynamicByPage(long uid, int startPage, int pageSize) {
        List<Long> ids = followMapper.findByFollowId(uid);
        PageHelper.startPage(startPage,pageSize);
        return baseMapper.listNewDynamic(ids);
    }

    @Override
    public int findUnreadCount(long uid) {
        Date lastReadTime = userMapper.findLastReadDynamicTime(uid);
        List<Long> ids = followMapper.findByFollowId(uid);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("uid",ids);
        queryWrapper.gt("create_time",lastReadTime);
        return count(queryWrapper);
    }

    @Override
    public int handlerLike(long uid, Dynamic dynamic) {
//        boolean hasPraise = logService.hasPraise(dynamic.getId(),uid);
//        int row = baseMapper.incrPraiseNum(dynamic.getId(),hasPraise?
//                NEGATIVE_NUM:POSITIVE_NUM);
        return 0;
    }
}
