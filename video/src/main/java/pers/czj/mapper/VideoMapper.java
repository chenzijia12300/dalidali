package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Video;

import java.util.Collection;
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


    /**
     * @author czj
     * 根据主键们获得视频的基本信息
     * @date 2020/7/17 10:28
     * @param [ids]
     * @return java.util.List<pers.czj.dto.VideoDetailsOutputDto>
     */
    public List<VideoBasicOutputDto> listBasicInfoByIds(@Param("ids") Collection<Long> ids);


    @Update("UPDATE video SET praise_num=praise_num+#{num} WHERE id = #{vid}")
    public int incrPraiseNum(long vid,int num);

    @Update("UPDATE video SET coin_num=coin_num+#{num} WHERE id = #{vid}")
    public int incrCoinNum(long vid,int num);

    @Update(("UPDATE video SET play_num=play_num+#{num} WHERE id = #{vid}"))
    public int incrPlayNum(long vid,int num);
}
