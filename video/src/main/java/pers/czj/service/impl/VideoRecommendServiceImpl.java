package pers.czj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pers.czj.entity.VideoRecommend;
import pers.czj.mapper.VideoRecommendMapper;
import pers.czj.service.VideoRecommendService;

import java.util.List;

/**
 * 创建在 2020/7/26 13:48
 */
@Service
public class VideoRecommendServiceImpl extends ServiceImpl<VideoRecommendMapper, VideoRecommend>implements VideoRecommendService {

    @Value("${recommend-page-size}")
    private int pageSize;

    @Override
    public List<Long> findRecommendId(int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Long> ids = baseMapper.findRecommendId();
        return ids;
    }
}
