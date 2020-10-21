package pers.czj.service.impl;

import cn.hutool.core.map.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pers.czj.common.CommonResult;
import pers.czj.entity.Dynamic;
import pers.czj.feign.VideoFeignClient;
import pers.czj.service.DynamicPraiseService;
import pers.czj.service.DynamicService;

/**
 * 创建在 2020/10/14 15:09
 */
@Service("VIDEO")
public class DynamicPraiseServiceImpl implements DynamicPraiseService {

    private static final Logger log = LoggerFactory.getLogger(DynamicPraiseServiceImpl.class);

    @Autowired
    private VideoFeignClient videoFeignClient;


    @Override
    public boolean handlerPraise(long userId, Dynamic dynamic) {
        log.info("本地点赞！{}",dynamic);
        CommonResult commonResult = videoFeignClient.handlerVideoLike(userId, MapUtil.of("vid",dynamic.getOid()));
        return commonResult.getCode()==200 ? true:false;
    }
}
