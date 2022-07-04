package com.tian.itemcloudprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix // 开启熔断
@EnableEurekaClient  //服务提供者身份启动
public class ItemCloudProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemCloudProviderApplication.class, args);
    }
}
