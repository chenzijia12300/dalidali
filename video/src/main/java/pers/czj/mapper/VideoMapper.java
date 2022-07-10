package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.dto.VideoHotOutputDto;
import pers.czj.entity.Video;

import java.util.Collection;
import java.util.List;

/**
 * 创建在 2020/7/11 23:40
 */
@Repository
public interface VideoMapper extends BaseMapper<Video> {

    public VideoDetailsOutputDto findDetailsById(long id);

    public List<VideoBasicOutputDto> listRandomByCategoryPId(long id);

    public List<VideoBasicOutputDto> listRandomAll();

    public List<VideoBasicOutputDto> listSlowRandomAll();

    public List<VideoBasicOutputDto> listBasicInfoByIds(@Param("ids") Collection<Long> ids);

    public List<VideoHotOutputDto> listHotInfoByIds(@Param("ids") Collection<Long> ids);


    /**
     * 简单SQL语句们
     */


    @Update("UPDATE video SET praise_num=praise_num+#{num} WHERE id = #{vid}")
    public int incrPraiseNum(long vid, int num);

    @Update("UPDATE video SET coin_num=coin_num+#{num} WHERE id = #{vid}")
    public int incrCoinNum(long vid, int num);

    @Update(("UPDATE video SET play_num=play_num+#{num} WHERE id = #{vid}"))
    public int incrPlayNum(long vid, int num);

    @Update("UPDATE video SET comment_num=comment_num+#{num} WHERE id = #{id}")
    public int incrCommentNum(long id, int num);

    @Update("UPDATE video SET danmu_num=danmu_num+#{num} WHERE id = #{id}")
    public int incrDanmuNum(long id, int num);

    @Select("SELECT * FROM video WHERE publish_state = #{state} ORDER BY create_time ASC LIMIT 0,1 FOR UPDATE")
    public Video findNeedAuditVideo(VideoPublishStateEnum state);

    @Update("UPDATE video SET publish_state = #{state} WHERE id = #{id}")
    public int auditing(long id, VideoPublishStateEnum state);


    /**
     * 临时方法
     */

    /**
     * 为旧数据生成压缩封面
     *
     * @return
     */
    @Select("SELECT * FROM video WHERE compress_cover IS NULL")
    public List<Video> listNoCompressCover();

}
