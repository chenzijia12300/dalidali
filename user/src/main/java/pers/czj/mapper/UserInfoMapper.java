package pers.czj.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 创建在 2020/12/2 11:22
 */
@Repository
public interface UserInfoMapper {

    @Select("SELECT id FROM video WHERE uid = #{uid} ORDER BY id DESC")
    public List<Long> findPublishVideoByUid(@Param("uid") long uid);

    @Select("SELECT vid FROM video_log WHERE uid = #{uid} AND is_collection = 1 ORDER BY id DESC")
    public List<Long> findCollectVideoByUid(@Param("uid") long uid);

    @Select("SELECT vid FROM video_log WHERE uid = #{uid} AND (coin_num = 1 or coin_num = 2) ORDER BY id DESC")
    public List<Long> findHasCoinVideoByUid(@Param("uid") long uid);

    @Select("SELECT vid FROM video_log WHERE uid = #{uid} AND is_praise = 1 ORDER BY id DESC")
    public List<Long> findPraiseCoinVideoByUid(@Param("uid") long uid);
}
