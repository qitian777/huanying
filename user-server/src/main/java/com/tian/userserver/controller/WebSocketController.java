package com.tian.userserver.controller;

import com.tian.userserver.pojo.Message;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: WebSocket控制器
 * @Author QiGuang
 * @Date 2022/6/23
 * @Version 1.0
 */
@RestController
public class WebSocketController {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/wbs/chat")
    public void handleMsg(Authentication authentication, Message message){
        User user = (User) authentication.getPrincipal();
        message.setFrom(user.getUsername());
        message.setState(0);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = dateFormat.format(new Date());
//        message.setCreateTime(time);
        messageService.saveMessage(message);
//        chatMsg.setFromNickName(user.getNickname());
//        System.out.println(chatMsg);
        /**
         * 发送消息
         * 1.消息接收者
         * 2.消息队列
         * 3.消息对象
         */
        simpMessagingTemplate.convertAndSendToUser(message.getTo(),"/queue/chat",message);
    }

}
