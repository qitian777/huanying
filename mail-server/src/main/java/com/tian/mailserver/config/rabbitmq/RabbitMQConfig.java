package com.tian.mailserver.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: RabbitMQ配置
 * @Author QiGuang
 * @Date 2022/6/20
 * @Version 1.0
 */
@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue emailQueue(){
        return new Queue("email-queue");
    }
}
