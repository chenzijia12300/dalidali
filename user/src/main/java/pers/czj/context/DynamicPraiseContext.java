package pers.czj.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.czj.constant.DynamicType;
import pers.czj.entity.Dynamic;
import pers.czj.service.DynamicPraiseService;

import java.util.Map;

/**
 * 创建在 2020/10/14 15:21
 */
@Component
public class DynamicPraiseContext {

    private static final Logger log = LoggerFactory.getLogger(DynamicPraiseContext.class);

    private Map<String, DynamicPraiseService> map;

    @Autowired
    public DynamicPraiseContext(Map<String, DynamicPraiseService> map) {
        map.forEach(log::info);
        this.map = map;
    }

    public boolean handlerPraise(long userId,Dynamic dynamic){
        return map.get(dynamic.getType().toString()).handlerPraise(userId,dynamic);
    }
}
