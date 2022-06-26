package com.tian.userserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.userserver.mapper.MessageMapper;
import com.tian.userserver.pojo.Message;
import com.tian.userserver.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * @Author QiGuang
     * @Description 获取离线消息列表
     * @Param
     */
    @Override
    public List<Message> getMessageList(String name) {
        return messageMapper.getMessageList(name);
    }

    /**
     * @Author QiGuang
     * @Description 更新消息状态
     * @Param
     */
    @Override
    public int updateMessageState(String from, String to) {
        return messageMapper.updateMessageState(from, to);
    }


    @Override
    public void saveMessage(Message message) {
        messageMapper.saveMessage(message);
    }

}
