package com.tian.userserver.config.security;

import com.tian.userserver.config.security.filter.CheckTokenFilter;
import com.tian.userserver.config.security.handler.AnonymousAuthenticationHandler;
import com.tian.userserver.config.security.handler.CustomerAccessDeniedHandler;
import com.tian.userserver.config.security.service.CustomerUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Description: Security配置类
 * @Author QiGuang
 * @Date 2022/6/14
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
// 开启权限注解控制
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private CustomerUserDetailsService customerUserDetailsService;
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;
    @Resource
    private CheckTokenFilter checkTokenFilter;

    /**
     * @Author QiGuang
     * @Description 注入加密处理类
     * @Param
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @Author QiGuang
     * @Description 放行页面
     * @Param
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/captcha",
                "/devLog/**",
                "/sysUser/login",
                "/sysUser/get/**",
                "/sysUser/check/**",
                "/message/get/sys",
                "/comment/get",
                "/wbs/**",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**"
        );
    }


    /**
     * @Author QiGuang
     * @Description
     * @Param
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录前进行过滤
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //登录前进行过滤
        http.formLogin()
                // 禁用csrf防御机制
                .and().csrf().disable()
                // 不创建session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 设置需要拦截的请求
                .authorizeRequests()
                // 其余请求全部需要验证
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                // 匿名用户无权限
                .authenticationEntryPoint(anonymousAuthenticationHandler)
                // 认证用户无权限
                .accessDeniedHandler(customerAccessDeniedHandler)
                .and()
                .cors();//开启跨域配置
    }

    /**
     * @Author QiGuang
     * @Description配置认证处理器
     * @Param
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());
//    }
}

