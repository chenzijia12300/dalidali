package pers.czj.service;

import pers.czj.entity.Dynamic;

/**
 * 创建在 2020/10/14 15:09
 */
public interface DynamicPraiseService {

    /**
     * 动态处理点赞信息
     *
     * @param userId  用户主键
     * @param dynamic 动态信息
     * @return boolean
     * @author czj
     * @date 2020/10/14 15:09
     */
    boolean handlerPraise(long userId, Dynamic dynamic);
}
