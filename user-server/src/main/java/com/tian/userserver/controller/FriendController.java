package com.tian.userserver.controller;

import com.tian.serverapi.util.RespBean;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.IFriendService;
import com.tian.userserver.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 * 好友 前端控制器
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/friend")
@Api(tags = "好友控制器")
public class FriendController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IFriendService friendService;

    @ApiOperation(value = "搜索好友")
    @GetMapping("/search")
    public RespBean searchFriend(String username) {
        return RespBean.success(userService.searchFriend(username));
    }

    @ApiOperation(value = "添加好友")
    @GetMapping("/add")
    public RespBean addFriend(String friendName, Principal principal) {
        System.out.println(friendName);
        System.out.println(principal.getName());
        int i = friendService.addFriend(principal.getName(), friendName);
        if (i>0) return RespBean.success(RespBeanEnum.ADD_FRIEND_SUCCESS);
        return RespBean.error(RespBeanEnum.ADD_FRIEND_FAIL);
    }

    @ApiOperation(value = "删除好友")
    @GetMapping("/delete")
    public RespBean deleteFriend(String friendName, Principal principal) {
        int i = friendService.deleteFriend(principal.getName(), friendName);
        if (i>1) return RespBean.success(RespBeanEnum.DELETE_FRIEND_SUCCESS);
        return RespBean.error(RespBeanEnum.DELETE_FRIEND_SUCCESS);
    }

    @ApiOperation(value = "获取好友列表")
    @GetMapping("/get")
    public RespBean getFriends() {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return RespBean.success(friendService.getFriendList(user.getId()));
    }

    @ApiOperation(value = "获取好友列表")
    @GetMapping("/checkUser")
    public RespBean checkUser(String friendName,Principal principal) {
        return RespBean.success(friendService.checkUser(friendName,principal.getName()));
    }
}
