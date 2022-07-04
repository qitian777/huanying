package com.tian.itemcloudprovider02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix // 开启熔断
@EnableEurekaClient
public class ItemCloudProvider02Application {
    public static void main(String[] args) {
        SpringApplication.run(ItemCloudProvider02Application.class, args);
    }
}
