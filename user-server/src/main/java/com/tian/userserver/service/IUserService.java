package com.tian.userserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.serverapi.util.RespBean;
import com.tian.userserver.pojo.User;
import com.tian.userserver.vo.LoginUser;
import com.tian.userserver.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
public interface IUserService extends IService<User> {

    Map<String, Object> doLogin(LoginUser loginUser, HttpServletRequest request);

    User getUserByUsername(String username);

    Long checkUsername(String username);

    Long checkEmail(String email);

    int getEmailCode(String email);

    int createUser(UserVo userVo,HttpServletRequest request);

    int updateUserInfo(UserVo userVo);

    int updatePicture(String newFileName, String name);

    List<Map<String, Object>> searchFriend(String username);

    List<Map<String, Object>> getFriendID(String username, String friendName);
}
