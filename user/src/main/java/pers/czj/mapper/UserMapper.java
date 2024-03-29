package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import pers.czj.dto.BasicUserInfoOutputDto;
import pers.czj.dto.DetailsUserInfoOutputDto;
import pers.czj.entity.User;

import java.util.Date;

/**
 * 创建在 2020/7/10 16:02
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT coin_num FROM user WHERE id = #{id}")
    public int findCoinNumById(long id);


    @Select("SELECT id FROM user WHERE username = #{username}")
    public Long getIdByName(String username);

    @Update("UPDATE user SET coin_num=coin_num+#{num} WHERE id = #{id}")
    public int incrCoinNum(long id, int num);

    @Update("UPDATE user SET follow_num=follow_num+#{num} WHERE id = #{uid}")
    public int incrFollowNum(long uid, int num);

    @Update("UPDATE user SET fans_num=fans_num+#{num} WHERE id = #{uid}")
    public int incrFansNum(long uid, int num);

    @Select("SELECT read_dynamic_time FROM user WHERE id = #{uid}")
    public Date findLastReadDynamicTime(long uid);

    @Select("SELECT read_message_time FROM user WHERE id = #{uid}")
    public Date findLastReadMessageTime(long uid);

    @Select("SELECT username,img FROM user WHERE id = #{id}")
    public BasicUserInfoOutputDto findBasicUserInfoById(long id);

    @Select("SELECT id,username,email,img,follow_num,fans_num,description,grade,coin_num FROM user WHERE id = #{uid}")
    public DetailsUserInfoOutputDto findDetailsUserInfoById(long uid);


}
