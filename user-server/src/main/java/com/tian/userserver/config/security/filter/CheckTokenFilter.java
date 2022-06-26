package com.tian.userserver.config.security.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tian.userserver.config.redis.RedisService;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.IUserService;
import com.tian.userserver.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: token及验证码验证过滤器类
 * @Author QiGuang
 * @Date 2022/6/14
 * @Version 1.0
 */
@Component
public class CheckTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;


    /**
     * @Author QiGuang
     * @Description 验证令牌
     * @Param
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(tokenHeader);
        // 存在token
        if (header != null && header.startsWith(tokenHead)) {
            String authToken = header.substring(tokenHead.length());
            // 解析用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            // 用户名是否为空
            if (StringUtils.isNotBlank(username)) {
                // 获取redis中token
                String redisToken = redisService.get("token_" + username);
                // Redis中存在该用户信息
                if (StringUtils.isNotBlank(redisToken)) {
                    // token正确,但是未登录
                    if (authToken.equals(redisToken) || SecurityContextHolder.getContext().getAuthentication() == null) {
                        //登录
                        User user = userService.getUserByUsername(username);
                        //验证token是否有效,重新设置用户对象
                        if (jwtTokenUtil.validateToken(authToken, user)) {
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
