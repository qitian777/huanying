package com.tian.userserver.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-23
 */
@Getter
@Setter
@ToString
@ApiModel(value = "Message对象", description = "消息")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("接收者")
    private String to;

    @ApiModelProperty("发送者用户名")
    private String from;

    @ApiModelProperty("信息内容")
    private String content;

    @ApiModelProperty("发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;
//    private String createTime;

    @ApiModelProperty("系统信息：0是，1不是")
    private Integer system;

    @ApiModelProperty("信息状态：0未读，1已读")
    private Integer state;

    @ApiModelProperty("1：申请，2：同意，-1：拒绝")
    private Integer apply;



}
