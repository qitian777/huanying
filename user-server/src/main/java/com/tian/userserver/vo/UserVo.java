package com.tian.userserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Description: 用户注册及信息修改参数类
 * @Author QiGuang
 * @Date 2022/6/17
 * @Version 1.0
 */
@Data
public class UserVo {
    @ApiModelProperty("用户名")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$", message = "用户名格式错误")
    private String username;

    @ApiModelProperty("昵称")
    @Length(min = 3,max = 16,message = "昵称为长度3到16的字符串")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("新密码")
    @Pattern(regexp = "^[a-zA-Z]\\w{5,17}$", message = "密码格式错误")
    private String newPassword;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty("邮箱验证码")
    private Integer emailCode;

    @ApiModelProperty("验证码")
    @Pattern(regexp = "^[a-zA-Z0-9]{4}$", message = "验证码格式错误")
    private String captcha;
}
