package com.tian.userserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.userserver.pojo.Friend;
import com.tian.userserver.pojo.User;

import java.util.List;

/**
 * <p>
 * 好友 服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
public interface IFriendService extends IService<Friend> {

    int addFriend(String username, String friendName);

    int deleteFriend(String username, String friendName);

    Long checkFriends(Friend friend);

    Friend getFriendByName(String username, String friendName);

    List<User> getFriendList(Long id);

    Long checkUser(String friendName, String name);
}
