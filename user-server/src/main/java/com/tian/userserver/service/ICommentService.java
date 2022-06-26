package com.tian.userserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.userserver.pojo.Comment;
import com.tian.userserver.vo.CommentVo;

import java.util.Map;

/**
 * <p>
 * 评分及评论 服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
public interface ICommentService extends IService<Comment> {

    int addOrUpdate(Comment comment);

    Map<String, Object> getCommentList(CommentVo commentVo);
}
