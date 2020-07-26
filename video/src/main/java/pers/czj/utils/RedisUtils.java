package pers.czj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 创建在 2020/3/13 15:02
 */
@Component
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    /**
     * @author czj
     * 设置键过期时间(秒)
     * expire(到期)
     * @date 2020/3/13 15:05
     * @param [key, time]
     * @return boolean
     */
    public boolean expire(String key,long time){
        if (time<0){
            return false;
        }
        redisTemplate.expire(key,time, TimeUnit.SECONDS);
        return true;
    }

    /**
     * @author czj
     * 获得键过期时间(秒)
     * @date 2020/3/13 15:08
     * @param [key]
     * @return long
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * @author czj
     * 检测该键是否存在
     * @date 2020/3/13 15:08
     * @param [key]
     * @return boolean
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * @author czj
     * 获得值
     * @date 2020/3/13 15:12
     * @param [key]
     * @return java.util.Optional<java.lang.Object>
     */
    public Object get(String key) throws ConnectException {
        Object o = redisTemplate.opsForValue().get(key);
        return o;
    }


    /**
     * @author czj
     * 设置值 如果time 小于等于0 就为无限期
     * @date 2020/3/13 15:14
     * @param [key, value, time]
     * @return boolean
     */
    public boolean set(String key,Object value,long time) throws ConnectException {
        try {
            if (time>0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else {
                redisTemplate.opsForValue().set(key,value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new ConnectException("redis连接失败！");
        }
    }
    /**
     * @author czj
     * 删除键
     * @date 2020/3/13 15:20
     * @param [key]
     * @return boolean
     */
    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

    /**
     * @author czj
     * 删除对应前缀建
     * @date 2020/7/25 23:50
     * @param [key]
     * @return java.lang.Long
     */
    @Deprecated
    public Long deleteAll(String key){
        Set keys=redisTemplate.keys(key+"*");
        return redisTemplate.delete(keys);
    }


    /**
     * @author czj
     * 获得键对应的SET集合
     * @date 2020/7/25 23:51
     * @param [key, num]
     * @return java.util.Set<java.lang.Object>
     */
    public Set<Object> getSet(String key,long num){
        return redisTemplate.opsForSet().distinctRandomMembers(key,num);
    }


    /**
     * @author czj
     * 设置SET集合
     * @date 2020/7/25 23:51
     * @param [key, object]
     * @return long
     */
    public long addSet(String key,Object object){
        return redisTemplate.opsForSet().add(key,object);
    }

    /**
     * @author czj
     * 使用ZSET数据类型存储，用于排行榜
     * @date 2020/7/16 18:10
     * @param [key, num, member]
     * @return double
     */
    public double zincrBy(String key,double num,String member){
        return redisTemplate.opsForZSet().incrementScore(key,member,num);
    }

    public Set zRevRange(String key, long start, long end,boolean needWithScores){
        if (needWithScores)
            return redisTemplate.opsForZSet().reverseRangeWithScores(key,start,end);
        else
            return redisTemplate.opsForZSet().reverseRange(key,start,end);
    }

    public void unionZSet(List<String> strings,String destKey){
        redisTemplate.opsForZSet().unionAndStore(strings.get(0),strings.subList(1,strings.size()),destKey);
    }


    public long addList(String key, Collection collection){
        return redisTemplate.opsForList().leftPushAll(key,collection);
    }


    /**
     * @author czj
     * 设置bit
     * @date 2020/7/21 15:26
     * @param [key, num, flag]
     * @return boolean
     */
    public boolean setBit(String key,long num,boolean flag){
        return redisTemplate.opsForValue().setBit(key,num,flag);
    }

    /**
     * @author czj
     * 获得bit的值
     * @date 2020/7/21 15:26
     * @param [key, num]
     * @return boolean
     */
    public boolean getBit(String key,long num){
        return redisTemplate.opsForValue().getBit(key,num);
    }


    /**
     * @author czj
     * 将对象存储进hash数据类型中
     * @date 2020/7/25 23:57
     * @param [key, params]
     * @return
     */
    public void putHash(String key, Map<String,Object> params){
        redisTemplate.opsForHash().putAll(key,params);
    }

    /**
     * @author czj
     * 将对象存储进list数据类型中
     * @date 2020/7/26 0:10
     * @param [key, collection]
     * @return void
     */
    public void pushList(String key,Collection collection){
        redisTemplate.opsForList().leftPushAll(key,collection);
    }


    /**
     * @author czj
     * 返回list对象
     * @date 2020/7/26 0:35
     * @param [key]
     * @return java.util.List<java.lang.Object>
     */
    public List<Object> getList(String key){

        return redisTemplate.opsForList().range(key,0,-1);
    }

    /**
     * @author czj
     * 返回list对象对应的下标成员
     * @date 2020/7/26 11:34
     * @param [key, id]
     * @return T
     */
    public<T> T getItemByList(String key,long id,Class<T> tClass){
        return (T)redisTemplate.opsForList().index(key,id);
    }

    /**
     * @author czj
     * 设置list对象对应的下标成员
     * @date 2020/7/26 11:35
     * @param [key, o, id]
     * @return void
     */
    public void setItemByList(String key,Object o,long id){
        redisTemplate.opsForList().set(key,id,o);
    }
}
