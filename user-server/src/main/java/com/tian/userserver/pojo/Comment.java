package com.tian.userserver.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 评分及评论
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Getter
@Setter
@ApiModel(value = "Comment对象", description = "评分及评论")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Null
    private Long id;

    @ApiModelProperty("影视id")
    @NotNull(message = "影视ID不能为空")
    private Long itemId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("评分")
    @NotNull(message = "评分不能为空")
    @Min(value = 0,message = "评分不能小于0")
    @Max(value = 5,message = "评分不能大于5")
    private Integer score;

    @ApiModelProperty("评价")
    @NotBlank(message = "评价不能为空")
    @Length(min = 1,max = 250,message = "评论长度不能大于250字")
    private String view;

    @ApiModelProperty("评论创建时间")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
