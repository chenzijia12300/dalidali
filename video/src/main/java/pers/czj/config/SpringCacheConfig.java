package pers.czj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Callable;

/**
 * 创建在 2020/7/26 0:26
 */
public class SpringCacheConfig implements Cache {

    private static final Logger log = LoggerFactory.getLogger(SpringCacheConfig.class);

    private String name;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(Object o) {
        log.info("取数据:{}", o);
        return null;
    }

    @Override
    public <T> T get(Object o, Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    @Override
    public void put(Object o, Object o1) {
        log.info("存储数据:{}", o);
    }

    @Override
    public void evict(Object o) {

    }

    @Override
    public void clear() {

    }
}
