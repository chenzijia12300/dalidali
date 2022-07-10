package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.dto.BasicUserInfoOutputDto;
import pers.czj.dto.DetailsUserInfoOutputDto;
import pers.czj.entity.User;
import pers.czj.exception.UserException;

import java.util.Date;

/**
 * 创建在 2020/7/10 16:02
 */
public interface UserService extends IService<User> {

    public User login(String account, String password) throws UserException;

    public Long register(User user) throws UserException;

    public Long existsUserByName(String username);

    /**
     * @param [id]
     * @return int
     * @author czj
     * 获得用户硬币数
     * @date 2020/7/20 13:55
     */
    public int findCoinNumById(long id);


    /**
     * @param [id, num]
     * @return int
     * @author czj
     * 更改用户硬币数
     * @date 2020/7/20 14:32
     */
    public int incrCoinNum(long id, int num);


    /**
     * @param [uid, lastTime]
     * @return boolean
     * @author czj
     * 更改最后阅读时间
     * @date 2020/8/10 21:24
     */
    public boolean updateLastReadDynamicTime(long uid, Date lastTime);


    /**
     * @param [uid, lastTime]
     * @return boolean
     * @author czj
     * 更改最后阅读消息时间
     * @date 2020/8/11 15:07
     */
    public boolean updateLastReadMessageTime(long uid, Date lastTime);


    /**
     * 根据用户主键获得用户基本信息
     *
     * @param [uid]
     * @return pers.czj.dto.BasicUserInfoOutputDto
     * @author czj
     * @date 2020/9/6 12:11
     */
    @Deprecated
    public BasicUserInfoOutputDto findBasicUserInfoById(long uid);


    /**
     * 根据用户主键获得用户基本信息
     *
     * @param userId       用户主键
     * @param followUserId 可能被关注的用户主键
     * @return pers.czj.dto.BasicUserInfoOutputDto
     * @author czj
     * @date 2020/9/6 12:11
     */
    public BasicUserInfoOutputDto findBasicUserInfoById(long userId, long followUserId);


    /**
     * 根据用户主键获得用户的详细信息
     *
     * @param [uid]
     * @return pers.czj.dto.DetailsUserInfoOutputDto
     * @author czj
     * @date 2020/11/6 23:27
     */
    public DetailsUserInfoOutputDto findDetailsUserInfoById(long uid) throws UserException;
}
