package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.czj.entity.DynamicLog;

/**
 * 创建在 2020/10/14 16:40
 */
public interface DynamicLogMapper extends BaseMapper<DynamicLog> {

    @Select("SELECT is_praise FROM dynamic_log WHERE uid = #{uid} AND did = #{did}")
    public Boolean hasPraise(@Param("uid")long uid,@Param("did")long did);

    @Select("SELECT is_forward FROM dynamic_log WHERE uid = #{uid} AND did = #{did}")
    public Boolean hasForward(@Param("uid")long uid,@Param("did")long did);


}
