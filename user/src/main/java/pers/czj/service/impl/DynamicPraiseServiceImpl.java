package pers.czj.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pers.czj.entity.Dynamic;
import pers.czj.service.DynamicPraiseService;
import pers.czj.service.DynamicService;

/**
 * 创建在 2020/10/14 15:09
 */
@Service("VIDEO")
public class DynamicPraiseServiceImpl implements DynamicPraiseService {

    private static final Logger log = LoggerFactory.getLogger(DynamicPraiseServiceImpl.class);

    @Autowired
    private DynamicService dynamicService;


    @Override
    public boolean handlerPraise(long userId, Dynamic dynamic) {
        log.info("本地点赞！{}",dynamic);
        int row = dynamicService.handlerLike(userId,dynamic);
        return row >=1 ? true:false;
    }
}
