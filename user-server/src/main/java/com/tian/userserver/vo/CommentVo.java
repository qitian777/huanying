package com.tian.userserver.vo;

import com.tian.serverapi.vo.PageVo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description: 评论参数类
 * @Author QiGuang
 * @Date 2022/6/17
 * @Version 1.0
 */
@Data
public class CommentVo extends PageVo {
    @NotNull(message = "ID不能为空")
    private Long itemId;
}
