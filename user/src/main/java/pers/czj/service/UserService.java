package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.common.User;
import pers.czj.exception.UserException;

/**
 * 创建在 2020/7/10 16:02
 */
public interface UserService extends IService<User> {

    public User login(String account,String password) throws UserException;

    public boolean register(User user) throws UserException;
}
