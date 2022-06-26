package com.tian.userserver.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 好友
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Getter
@Setter
@ApiModel(value = "Friend对象", description = "好友")
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long f1;

    private Long f2;


}
