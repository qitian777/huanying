package com.tian.userserver.controller;

import com.tian.serverapi.util.RespBean;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.config.redis.RedisService;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.IUserService;
import com.tian.userserver.vo.EmailVo;
import com.tian.userserver.vo.LoginUser;
import com.tian.userserver.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

/**
 * @Description: 用户登录控制器
 * @Author QiGuang
 * @Date 2022/6/15
 * @Version 1.0
 */
@RestController
@RequestMapping("/sysUser")
@Api(tags = "登录用户控制器")
public class SysUserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info/get")
    public RespBean userInfo(Principal principal) {
        String name = principal.getName();
        User user = userService.getUserByUsername(name);
        user.setPassword(null);
        return RespBean.success(user);
    }

    @ApiOperation(value = "登录并返回token")
    @PostMapping("/login")
    public RespBean login(@Valid @RequestBody LoginUser loginUser, HttpServletRequest request) {
        // 验证验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        return RespBean.success(userService.doLogin(loginUser, request));
    }

    @ApiOperation(value = "登出")
    @GetMapping("/logOut")
    public RespBean logout(Principal principal) {
        String name = principal.getName();
        redisService.del("token_" + name);
        return RespBean.success(RespBeanEnum.LOGOUT_SUCCESS);
    }

    @ApiOperation(value = "检查用户名")
    @GetMapping("/check/username")
    public RespBean checkUsername(String username) {
        return RespBean.success(userService.checkUsername(username));
    }

    @ApiOperation(value = "获取邮箱验证码")
    @PostMapping("/get/emailCode")
    public RespBean emailCode(@Valid @RequestBody EmailVo emailVo) {
        int code = userService.getEmailCode(emailVo.getEmail());
        if (code == -1) RespBean.error(RespBeanEnum.EMAIL_EXIST_ERROR, code);
        return RespBean.success(code);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/info/update")
    public RespBean changeInfo(@Valid @RequestBody UserVo userVo, Principal principal) {
        userVo.setUsername(principal.getName());
        int i = userService.updateUserInfo(userVo);
        if (i>0) return RespBean.success(RespBeanEnum.UPDATE_USER_SUCCESS);
        return RespBean.error(RespBeanEnum.UPDATE_USER_FAIL);
    }

    @ApiOperation(value = "创建用户")
    @PostMapping("/get/createUser")
    public RespBean registerUser(@Valid @RequestBody UserVo userVo,HttpServletRequest request) {
        int i = userService.createUser(userVo,request);
        if (i != 1) return RespBean.error(RespBeanEnum.CREATE_USER_FAIL);
        return RespBean.success(RespBeanEnum.CREATE_USER_SUCCESS);
    }

}
