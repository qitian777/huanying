package com.tian.itemcloudprovider02.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 收藏
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-18
 */
@Getter
@Setter
@TableName("user_coll")
@ApiModel(value = "Collection对象", description = "收藏")
public class UserColl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("影视ID")
    @NotNull(message = "节目ID不能为空")
    private Long itemId;


}
