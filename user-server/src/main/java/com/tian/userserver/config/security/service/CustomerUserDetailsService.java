package com.tian.userserver.config.security.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.config.exception.GlobalException;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.IUserAuthorityService;
import com.tian.userserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @Description: UserDetailsService实现类，获取用户用于登陆校验
 * @Author QiGuang
 * @Date 2022/6/13
 * @Version 1.0
 */
@Component
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserAuthorityService userAuthorityService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 判断用户名是否为空
        if (StringUtils.isBlank(username)) throw new GlobalException(RespBeanEnum.EMPTY_USERNAME);
        // 获取用户
        User user = userService.getUserByUsername(username);
        // 用户是否存在
        if (user==null) throw new GlobalException(RespBeanEnum.LOGIN_ERROR);

        return user;
    }
}
