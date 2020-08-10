package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.common.User;
import pers.czj.exception.UserException;

import java.util.Date;

/**
 * 创建在 2020/7/10 16:02
 */
public interface UserService extends IService<User> {

    public User login(String account,String password) throws UserException;

    public boolean register(User user) throws UserException;

    /**
     * @author czj
     * 获得用户硬币数
     * @date 2020/7/20 13:55
     * @param [id]
     * @return int
     */
    public int findCoinNumById(long id);


    /**
     * @author czj
     * 更改用户硬币数
     * @date 2020/7/20 14:32
     * @param [id, num]
     * @return int
     */
    public int incrCoinNum(long id,int num);


    /**
     * @author czj
     * 更改最后阅读时间
     * @date 2020/8/10 21:24
     * @param [uid, lastTime]
     * @return boolean
     */
    public boolean updateLastReadDynamicTime(long uid, Date lastTime);
}
