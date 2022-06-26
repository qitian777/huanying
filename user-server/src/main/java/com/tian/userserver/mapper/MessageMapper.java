package com.tian.userserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.userserver.pojo.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 消息 Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    void saveMessage(Message message);


    List<Message> getMessageList(String name);

    int updateMessageState(String from, String to);
}
