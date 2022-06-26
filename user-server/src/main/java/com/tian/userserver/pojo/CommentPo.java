package com.tian.userserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 评论列表结果类
 * @Author QiGuang
 * @Date 2022/6/22
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPo extends Comment{
    private String username;
    private String picture;
}
