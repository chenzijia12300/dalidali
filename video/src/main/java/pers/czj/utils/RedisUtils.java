package pers.czj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 创建在 2020/3/13 15:02
 */
@Component
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * @param [key, time]
     * @return boolean
     * @author czj
     * 设置键过期时间(秒)
     * expire(到期)
     * @date 2020/3/13 15:05
     */
    public boolean expire(String key, long time) {
        if (time < 0) {
            return false;
        }
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
        return true;
    }

    /**
     * @param [key]
     * @return long
     * @author czj
     * 获得键过期时间(秒)
     * @date 2020/3/13 15:08
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * @param [key]
     * @return boolean
     * @author czj
     * 检测该键是否存在
     * @date 2020/3/13 15:08
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param [key]
     * @return java.util.Optional<java.lang.Object>
     * @author czj
     * 获得值
     * @date 2020/3/13 15:12
     */
    public Object get(String key) throws ConnectException {
        Object o = redisTemplate.opsForValue().get(key);
        return o;
    }


    /**
     * @param [key, value, time]
     * @return boolean
     * @author czj
     * 设置值 如果time 小于等于0 就为无限期
     * @date 2020/3/13 15:14
     */
    public boolean set(String key, Object value, long time) throws ConnectException {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectException("redis连接失败！");
        }
    }

    /**
     * @param [key]
     * @return boolean
     * @author czj
     * 删除键
     * @date 2020/3/13 15:20
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * @param [keys]
     * @return boolean
     * @author czj
     * 批量删除
     * @date 2020/8/15 23:34
     */
    public boolean delete(Collection<String> keys) {
        keys.forEach(key -> delete(key));
        return true;
    }

    /**
     * @param [key]
     * @return java.lang.Long
     * @author czj
     * 删除对应前缀建
     * @date 2020/7/25 23:50
     */
    @Deprecated
    public Long deleteAll(String key) {
        Set keys = redisTemplate.keys(key + "*");
        return redisTemplate.delete(keys);
    }


    /**
     * @param [key, num]
     * @return java.util.Set<java.lang.Object>
     * @author czj
     * 获得键对应的SET集合
     * @date 2020/7/25 23:51
     */
    public Set<Object> getSet(String key, long num) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, num);
    }


    /**
     * @param [key, object]
     * @return long
     * @author czj
     * 设置SET集合
     * @date 2020/7/25 23:51
     */
    public long addSet(String key, Object object) {
        return redisTemplate.opsForSet().add(key, object);
    }

    /**
     * @param [key, num, member]
     * @return double
     * @author czj
     * 使用ZSET数据类型存储，用于排行榜
     * @date 2020/7/16 18:10
     */
    public double zincrBy(String key, double num, String member) {
        return redisTemplate.opsForZSet().incrementScore(key, member, num);
    }

    public Set zRevRange(String key, long start, long end, boolean needWithScores) {
        if (needWithScores)
            return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        else
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public void unionZSet(List<String> strings, String destKey) {
        boolean isAllEmpty = true;
        for (String str : strings) {
            if (isZSetNotEmpty(str)) {
                isAllEmpty = false;
                break;
            }
        }
        if (isAllEmpty) {
            log.debug("子频道集合都为空,直接跳出~");
            return;
        }
        log.debug("destKey:{}", destKey);
        log.debug("first:{}", strings.subList(0, 1).toString());
        List<String> list = strings.subList(1, strings.size());
        log.debug("subList:{}", list);
        redisTemplate.opsForZSet().unionAndStore(strings.get(0), list, destKey);
    }


    public long addList(String key, Collection collection) {
        return redisTemplate.opsForList().leftPushAll(key, collection);
    }


    /**
     * @param [key, num, flag]
     * @return boolean
     * @author czj
     * 设置bit
     * @date 2020/7/21 15:26
     */
    public boolean setBit(String key, long num, boolean flag) {
        return redisTemplate.opsForValue().setBit(key, num, flag);
    }

    /**
     * @param [key, num]
     * @return boolean
     * @author czj
     * 获得bit的值
     * @date 2020/7/21 15:26
     */
    public boolean getBit(String key, long num) {
        return redisTemplate.opsForValue().getBit(key, num);
    }


    /**
     * @param [key, params]
     * @return
     * @author czj
     * 将对象存储进hash数据类型中
     * @date 2020/7/25 23:57
     */
    public void putHash(String key, Map<String, Object> params) {
        redisTemplate.opsForHash().putAll(key, params);
    }

    /**
     * @param [key, collection]
     * @return void
     * @author czj
     * 将对象存储进list数据类型中
     * @date 2020/7/26 0:10
     */
    public void pushList(String key, Collection collection) {
        redisTemplate.opsForList().leftPushAll(key, collection);
    }


    /**
     * @param [key]
     * @return java.util.List<java.lang.Object>
     * @author czj
     * 返回list对象
     * @date 2020/7/26 0:35
     */
    public List<Object> getList(String key) {

        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * @param [key, id]
     * @return T
     * @author czj
     * 返回list对象对应的下标成员
     * @date 2020/7/26 11:34
     */
    public <T> T getItemByList(String key, long id, Class<T> tClass) {
        return (T) redisTemplate.opsForList().index(key, id);
    }

    /**
     * @param [key, o, id]
     * @return void
     * @author czj
     * 设置list对象对应的下标成员
     * @date 2020/7/26 11:35
     */
    public void setItemByList(String key, Object o, long id) {
        redisTemplate.opsForList().set(key, id, o);
    }


    /**
     * @param [key, value]
     * @return boolean
     * @author czj
     * SET if Not Exists
     * redis原子性操作，如果key不存在则返回true，否则返回false
     * @date 2020/8/15 17:52
     */
    public boolean setnx(String key, String value, long second) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, second, TimeUnit.SECONDS);
    }

    /**
     * @param [key]
     * @return boolean
     * @author czj
     * 判断集合是否有元素，有返回true，否则false
     * @date 2020/8/15 23:30
     */
    public boolean isZSetNotEmpty(String key) {
        return redisTemplate.opsForZSet().zCard(key) == 0 ? false : true;
    }
}
