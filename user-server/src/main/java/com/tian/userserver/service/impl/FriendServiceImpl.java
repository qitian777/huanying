package com.tian.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.config.exception.GlobalException;
import com.tian.userserver.mapper.FriendMapper;
import com.tian.userserver.pojo.Friend;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.IFriendService;
import com.tian.userserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 好友 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {
    @Autowired
    private IUserService userService;
    @Autowired
    FriendMapper friendMapper;

    /**
     * @Author QiGuang
     * @Description 添加好友
     * @Param
     */
    @Override
    public int addFriend(String username, String friendName) {
        Friend friend = getFriendByName(username, friendName);
        Long checkFriends = checkFriends(friend);
        if (checkFriends > 0) throw new GlobalException(RespBeanEnum.FRIEND_ALREADY_EXIT);
        return friendMapper.saveFriend(friend);
    }

    /**
     * @Author QiGuang
     * @Description 删除好友
     * @Param
     */
    @Override
    public int deleteFriend(String username, String friendName) {
        Friend friend = getFriendByName(username, friendName);
        Long checkFriends = checkFriends(friend);
        if (checkFriends == 0) throw new GlobalException(RespBeanEnum.NOT_FRIEND);
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.eq("f_1", friend.getF1())
                .eq("f_2", friend.getF2())
                .or()
                .eq("f_1", friend.getF2())
                .eq("f_2", friend.getF1());
        return baseMapper.delete(wrapper);
    }

    /**
     * @Author QiGuang
     * @Description 确认是否是好友
     * @Param
     */
    @Override
    public Long checkFriends(Friend friend) {
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.eq("f_1", friend.getF1())
                .eq("f_2", friend.getF2())
                .or()
                .eq("f_1", friend.getF2())
                .eq("f_2", friend.getF1());
        return baseMapper.selectCount(wrapper);
    }

    /**
     * @Author QiGuang
     * @Description 获取好友双方ID
     * @Param
     */
    @Override
    public Friend getFriendByName(String username, String friendName) {
        List<Map<String, Object>> ids = userService.getFriendID(username, friendName);
        if (ids.get(0).get("id") == null || ids.get(1).get("id") == null)
            throw new GlobalException(RespBeanEnum.FRIEND_NOT_EXIT);
        Long id1 = (Long) ids.get(0).get("id");
        Long id2 = (Long) ids.get(1).get("id");
        Friend friend = new Friend();
        friend.setF1(id1);
        friend.setF2(id2);
        return friend;
    }

    /**
     * @Author QiGuang
     * @Description 获取好友列表
     * @Param
     */
    @Override
    public List<User> getFriendList(Long id) {
        List<User> friendListA = friendMapper.getFriendListA(id);
        List<User> friendListB = friendMapper.getFriendListB(id);
        if (friendListA == null) {
            return friendListB;
        } else {
            if (friendListB != null) {
                friendListA.addAll(friendListB);
            }
            return friendListA;
        }
    }

    @Override
    public Long checkUser(String friendName, String name) {
        Long a = userService.checkUsername(friendName);
        if (a == 0) return -1L;
        Friend friend = getFriendByName(friendName, name);
        return checkFriends(friend);
    }

}
