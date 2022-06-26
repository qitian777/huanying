package com.tian.userserver.controller;

import com.tian.serverapi.util.RespBean;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.userserver.pojo.Comment;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.ICommentService;
import com.tian.userserver.vo.CommentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 评分及评论 前端控制器
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @ApiOperation(value = "发表或更新评价")
    @PostMapping("/addOrUpdate")
    public RespBean addOrUpdate(@Valid @RequestBody Comment comment) {
        System.out.println("---------111111------"+comment.getView());
        System.out.println("---------111111------"+comment.getScore());
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUserId(user.getId());
        int i = commentService.addOrUpdate(comment);
        if (i>0) return RespBean.success(RespBeanEnum.COMMENT_SUCCESS);
        return RespBean.error(RespBeanEnum.COMMENT_FAIL);
    }

    @ApiOperation(value = "获取评论列表")
    @PostMapping("/get")
    public RespBean getComments(@Valid @RequestBody CommentVo commentVo) {
        return RespBean.success(commentService.getCommentList(commentVo));
    }
}
