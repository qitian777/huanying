package com.tian.userserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.userserver.pojo.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 消息 服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
public interface IMessageService extends IService<Message> {

    List<Message> getMessageList(String name);

    int updateMessageState(String from,String to);

    void saveMessage(Message message);

}
