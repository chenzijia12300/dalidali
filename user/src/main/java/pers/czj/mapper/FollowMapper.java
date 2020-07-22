package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.czj.entity.Follow;

/**
 * 创建在 2020/7/22 13:24
 */
public interface FollowMapper extends BaseMapper<Follow> {

    @Select("SELECT id FROM follow WHERE uid = #{uid} AND fuid = #{fuid}")
    public Long findFollowRelation(long uid,long fuid);

}
