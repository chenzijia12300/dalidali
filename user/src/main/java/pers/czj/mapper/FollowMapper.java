package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.czj.entity.Follow;

import java.util.List;

/**
 * 创建在 2020/7/22 13:24
 */
public interface FollowMapper extends BaseMapper<Follow> {

    @Select("SELECT id FROM follow WHERE uid = #{uid} AND fuid = #{fuid}")
    public Long findFollowRelation(long uid,long fuid);


    /**
     * @author czj
     * 获得fuid的关注者们
     * @date 2020/8/9 23:04
     * @param [fuid]
     * @return java.util.List<java.lang.Long>
     */
    @Select("SELECT uid FROM follow WHERE fuid = #{fuid}")
    public List<Long> findFollowerId(long fuid);


    /**
     * @author czj
     * 获得被uid所关注的人们
     * @date 2020/8/11 11:47
     * @param [uid]
     * @return java.util.List<java.lang.Long>
     */
    @Select("SELECT fuid FROM follow WHERE uid = #{uid}")
    public List<Long> findByFollowId(long uid);
}
