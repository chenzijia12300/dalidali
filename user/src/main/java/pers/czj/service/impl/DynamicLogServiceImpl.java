package pers.czj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.czj.entity.DynamicLog;
import pers.czj.mapper.DynamicLogMapper;
import pers.czj.service.DynamicLogService;

/**
 * 创建在 2020/10/14 16:41
 */
@Service
public class DynamicLogServiceImpl extends ServiceImpl<DynamicLogMapper, DynamicLog> implements DynamicLogService {


    @Override
    public boolean hasPraise(long did, long uid) {
        Boolean isPraise = baseMapper.hasPraise(uid,did);
        return ObjectUtil.isNull(isPraise)?false:isPraise.booleanValue();
    }
}
