package com.tian.serverapi.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMsg {
    @ApiModelProperty("邮箱地址")
    private String email;
    @ApiModelProperty("邮箱验证码")
    private int code;
}
