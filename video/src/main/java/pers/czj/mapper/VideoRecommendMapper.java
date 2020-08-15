package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import pers.czj.constant.RecommendLocation;
import pers.czj.entity.VideoRecommend;

import java.util.List;

/**
 * 创建在 2020/7/26 13:48
 */
public interface VideoRecommendMapper extends BaseMapper<VideoRecommend> {

    @Select("SELECT vid FROM video_recommend WHERE status=true AND location = #{location}")
    List<Long> findRecommendIdByLocation(RecommendLocation location);
}
