package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.constant.RecommendLocation;
import pers.czj.entity.VideoRecommend;

import java.util.List;

/**
 * 创建在 2020/7/26 13:48
 */
public interface VideoRecommendService extends IService<VideoRecommend> {


    List<Long> findRecommendIdByLocation(int pageNum, RecommendLocation location);
}
