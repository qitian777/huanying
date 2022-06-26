package com.tian.userserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Description: 用户登录校验
 * @Author QiGuang
 * @Date 2022/6/15
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    // 帐号以字母开头，允许5-16字节，允许字母数字下划线
    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$", message = "用户名格式错误")
    private String username;

    // 前端登录时密码回加密
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("验证码")
    @NotNull(message = "验证码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{4}$", message = "验证码格式错误")
    private String captcha;

    @ApiModelProperty("记住我")
    @NotNull(message = "请确认是否记住用户")
    private Boolean rememberMe ;
}
