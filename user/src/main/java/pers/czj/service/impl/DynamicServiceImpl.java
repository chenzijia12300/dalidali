package pers.czj.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pers.czj.dto.DynamicOutputDto;
import pers.czj.entity.Dynamic;
import pers.czj.entity.DynamicLog;
import pers.czj.mapper.DynamicMapper;
import pers.czj.mapper.FollowMapper;
import pers.czj.mapper.UserMapper;
import pers.czj.service.DynamicLogService;
import pers.czj.service.DynamicPraiseService;
import pers.czj.service.DynamicService;

import java.util.Date;
import java.util.List;

/**
 * 创建在 2020/8/9 22:40
 */
@Service("POST")
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper,Dynamic>implements DynamicService, DynamicPraiseService {

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
    public List<DynamicOutputDto> listDynamicByPageAndType(long uid, int startPage, int pageSize,boolean isAll) {
        List<Long> ids = followMapper.findByFollowId(uid);
        PageHelper.startPage(startPage,pageSize);
        return isAll?baseMapper.listNewDynamic(uid,ids):baseMapper.listNewVideoDynamic(uid,ids);
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
    public boolean handlerPraise(long userId, Dynamic dynamic) {
        long did = dynamic.getId();
        DynamicLog dynamicLog = logService.getOne(new QueryWrapper<DynamicLog>()
                .eq("did",did)
                .eq("uid",userId));
        if (ObjectUtil.isNull(dynamicLog)){
            baseMapper.incrPraiseNum(did,POSITIVE_NUM);
            dynamicLog = new DynamicLog().setDid(did).setUid(userId).setPraise(true);
            logService.save(dynamicLog);
        }else{
            boolean isPraise = dynamicLog.isPraise();
            baseMapper.incrPraiseNum(dynamic.getId(),isPraise?NEGATIVE_NUM:POSITIVE_NUM);
            dynamicLog.setPraise(!isPraise);
            logService.updateById(dynamicLog);
        }
        return true;
    }
}
