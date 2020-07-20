package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import pers.czj.common.User;

/**
 * 创建在 2020/7/10 16:02
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT coin_num FROM user WHERE id = #{id}")
    public int findCoinNumById(long id);

    @Update("UPDATE user SET coin_num=coin_num+#{num} WHERE id = #{id}")
    public int incrCoinNum(long id,int num);


}
