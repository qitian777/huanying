package com.tian.serverapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Description: cloud传参
 * @Author QiGuang
 * @Date 2022/6/18
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudItemVo extends ItemVo{
    @ApiModelProperty("用户ID")
    private Long userId;
}
