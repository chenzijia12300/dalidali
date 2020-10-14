package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.czj.entity.VideoCrawlerLog;
import pers.czj.mapper.VideoCrawlerLogMapper;
import pers.czj.service.VideoCrawlerLogService;

/**
 * 创建在 2020/10/12 14:23
 */
@Service
public class VideoCrawlerLogServiceImpl extends ServiceImpl<VideoCrawlerLogMapper, VideoCrawlerLog> implements VideoCrawlerLogService {


    @Override
    public boolean exists(String url) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("url",url);
        Integer row = baseMapper.selectCount(wrapper);
        return row>=1?true:false;
    }
}
