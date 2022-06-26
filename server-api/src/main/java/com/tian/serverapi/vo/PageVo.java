package com.tian.serverapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description: 分页参数
 * @Author QiGuang
 * @Date 2022/6/16
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Items前端分页参数对象", description = "分页参数")
public class PageVo {
    @ApiModelProperty("排序方式")
    @NotBlank(message = "排序方式不能为空")
    private String order;

    @ApiModelProperty("是否升序")
    @NotNull(message = "请确认排序方式")
    private Boolean isAsc;

    @ApiModelProperty("页码")
    @NotNull(message = "页码不能为空")
    @DecimalMin(value = "1",message = "页码必须大于0")
    private Integer page;

    @ApiModelProperty("页面容量")
    @NotNull(message = "页面容量不能为空")
    @DecimalMin(value = "1" ,message = "页面容量必须大于0")
    @DecimalMax(value = "50" ,message = "页面容量必须小于等于50")
    private Integer size;
}
