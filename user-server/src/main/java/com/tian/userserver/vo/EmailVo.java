package com.tian.userserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 前端email校验
 * @Author QiGuang
 * @Date 2022/6/15
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVo {
    @javax.validation.constraints.Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
