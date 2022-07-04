package com.tian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/2
 * @Version 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer03 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer03.class,args);
    }
}
