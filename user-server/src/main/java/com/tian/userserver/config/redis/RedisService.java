package com.tian.userserver.config.redis;

import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.config.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: Redis的简易封装类
 * @Author QiGuang
 * @Date 2022/6/13
 * @Version 1.0
 */
@Component
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @Author QiGuang
     * @Description 存字符串
     * @Param
     */
    public void set(String key, String value, Long timeOut) {
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
    }

    /**
     * @Author QiGuang
     * @Description 存数字
     * @Param
     */
    public void setInt(String key, int value, Long timeOut) {
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
    }

    /**
     * @Author QiGuang
     * @Description 取字符串
     * @Param
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * @Author QiGuang
     * @Description 取数字
     * @Param
     */
    public int getInt(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj==null) throw new GlobalException(RespBeanEnum.REDIS_NULL);
        return (int)obj ;
    }

    /**
     * @Author QiGuang
     * @Description 清除缓存
     * @Param
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }
}

