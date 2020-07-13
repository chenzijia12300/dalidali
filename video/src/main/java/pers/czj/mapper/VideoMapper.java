package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Video;

import java.util.List;

/**
 * 创建在 2020/7/11 23:40
 */
@Repository
public interface VideoMapper extends BaseMapper<Video> {

    /**
     * @author czj
     * 根据id获得视频详细信息
     * @date 2020/7/13 19:32
     * @param [id]
     * @return pers.czj.dto.VideoDetailsOutputDto
     */
    public VideoDetailsOutputDto findDetailsById(long id);

    /**
     * @author czj
     * 根据顶级频道主键获得视频信息
     * @date 2020/7/13 21:57
     * @param [id]
     * @return java.util.List<pers.czj.dto.VideoDetailsOutputDto>
     */
    public List<VideoDetailsOutputDto> listRandomByCategoryPId(long id);
}
