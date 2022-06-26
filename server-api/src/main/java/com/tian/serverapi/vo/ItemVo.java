package com.tian.serverapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;

/**
 * @Description: 接收前端Items相关参数
 * @Author QiGuang
 * @Date 2022/6/16
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Items前端分类参数对象", description = "分类参数")
public class ItemVo extends PageVo{
    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("地区")
    private String area;

    @ApiModelProperty("风格")
    private String style;

    @ApiModelProperty("上映年份")
    @DecimalMin(value = "1900",message = "请正确输入年份")
    private Integer year;

}
