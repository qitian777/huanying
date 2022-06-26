package com.tian.itemserver.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 开发日志
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-16
 */
@Getter
@Setter
@TableName("dev_log")
@ApiModel(value = "DevLog对象", description = "开发日志")
public class DevLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty("更新内容")
    private String updates;

    @ApiModelProperty("完成时间")
    private LocalDate time;

}
