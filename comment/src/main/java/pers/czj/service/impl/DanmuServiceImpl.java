package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.czj.entity.Danmu;
import pers.czj.mapper.DanmuMapper;
import pers.czj.service.DanmuService;

import java.util.List;

/**
 * 创建在 2020/7/30 18:46
 */
@Service
public class DanmuServiceImpl extends ServiceImpl<DanmuMapper, Danmu> implements DanmuService {


    @Override
    public List<Danmu> listDanmu(long vid, long showSecond) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("vid", vid);
        queryWrapper.ge("show_second", showSecond);
        queryWrapper.le("show_second", showSecond + 1000 * 10);
        return baseMapper.selectList(queryWrapper);
    }
}
