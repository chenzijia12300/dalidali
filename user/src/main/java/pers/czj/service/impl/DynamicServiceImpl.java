package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pers.czj.entity.Dynamic;
import pers.czj.mapper.DynamicMapper;
import pers.czj.mapper.FollowMapper;
import pers.czj.service.DynamicService;

import java.util.List;

/**
 * 创建在 2020/8/9 22:40
 */
@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper,Dynamic>implements DynamicService {


    private FollowMapper followMapper;

    @Autowired
    public DynamicServiceImpl(FollowMapper followMapper) {
        Assert.notNull(followMapper,"followMapper is not null~");
        this.followMapper = followMapper;
    }

    @Override
    public List<Dynamic> listDynamicByPage(long uid, int startPage, int pageSize) {
        List<Long> ids = followMapper.findFollowerId(uid);
        PageHelper.startPage(startPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("uid",ids);
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectList(queryWrapper);
    }
}
