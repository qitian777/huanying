package com.tian.userserver.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-14
 */
@Getter
@Setter
@TableName("user_authority")
@ApiModel(value = "UserAuthority对象", description = "")
public class UserAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String authCode;

    private Long roleId;

    private String label;

    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
