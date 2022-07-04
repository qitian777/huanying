package com.tian.itemcloudprovider02.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 影视信息
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-16
 */
@Getter
@Setter
@ApiModel(value = "Items对象", description = "影视信息")
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("原名")
    private String originName;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("地区")
    private String area;

    @ApiModelProperty("上映时间")
    private LocalDate showTime;

    @ApiModelProperty("上映年份")
    private Integer year;

    @ApiModelProperty("季番")
    private Integer season;

    @ApiModelProperty("风格")
    private String style;

    @ApiModelProperty("是否完结（-1：未未播，0：未完，1：完结）")
    private Integer finish;

    @ApiModelProperty("集数")
    private Integer number;

    @ApiModelProperty("主演")
    private String star;

    @ApiModelProperty("主创")
    private String staff;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("海报链接")
    private String poster;

    @ApiModelProperty("别名")
    private String alias;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("B站链接")
    private String biUrl;

    private Float biScore;


}
