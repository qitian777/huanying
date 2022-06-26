package com.tian.userserver.util;

import com.tian.serverapi.pojo.EmailMsg;
import com.tian.userserver.config.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 邮箱验证码工具类
 * @Author QiGuang
 * @Date 2022/6/15
 * @Version 1.0
 */
@Component
public class EmailCodeUtil {
    @Autowired
    private RedisService redisService;

    /**
     * @Author QiGuang
     * @Description 用时间毫秒数的后六位做邮箱验证码
     * @Param
     */
    public int generatorEmailCode(){
        return (int) (System.currentTimeMillis() % 1000000);
    }

    /**
     * @Author QiGuang
     * @Description 生成邮箱验证码并存入redis
     * @Param
     */
    public EmailMsg generatorAndSaveEmailCode(String email){
        int code = generatorEmailCode();
        redisService.setInt("email_"+email,code,900L);
        return new EmailMsg(email, code);
    }

    /**
     * @Author QiGuang
     * @Description 比较邮箱验证码
     * @Param
     */
    public boolean matchesEmailCode(String email,int emailCode){
        int code = redisService.getInt("email_" + email);
        return code==emailCode;
    }
}
