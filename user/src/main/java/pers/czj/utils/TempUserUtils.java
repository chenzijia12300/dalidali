package pers.czj.utils;

import cn.hutool.core.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.czj.entity.User;
import pers.czj.exception.UserException;
import pers.czj.service.UserService;

/**
 * 创建在 2020/12/3 17:13
 */
@Component
public class TempUserUtils {

    private static final Logger log = LoggerFactory.getLogger(TempUserUtils.class);



    private UserService userService;

    @Autowired
    public TempUserUtils(UserService userService) {
        this.userService = userService;
    }

    public Long createUserIfNeeded(String username, String img){
        long userId = userService.existsUserByName(username);
        if (userId != -1l){
            log.info("已存在该用户：{}",username);
            return userId;
        }
        User user = createRandomUser(username,img);
        try {
            return userService.register(user);
        } catch (UserException e) {
            log.info("注册临时用户:{}失败",username);
            e.printStackTrace();
        }
        return -1l;
    }

    private User createRandomUser(String username,String img){
        User user = new User();
        user.setAccount(RandomUtil.randomString(12));
        user.setPassword("123456");
        user.setUsername(username);
        user.setImg(img);
        user.setEmail("123456@qq.com");
        user.setCoinNum(100);
        user.setDescription("一个没有感情的机器人");
        return user;
    }
}
