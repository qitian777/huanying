package com.tian.userserver.config.restTemplate;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: 注入RestTemplate
 * @Author QiGuang
 * @Date 2022/6/18
 * @Version 1.0
 */
@Configuration
public class ConfigBean {
    @Bean
    @LoadBalanced //支持负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

