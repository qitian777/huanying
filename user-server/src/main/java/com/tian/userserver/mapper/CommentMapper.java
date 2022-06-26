package com.tian.userserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tian.userserver.pojo.Comment;
import com.tian.userserver.pojo.CommentPo;
import com.tian.userserver.vo.CommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 评分及评论 Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    Page<CommentPo> getCommentList(@Param("page") Page<CommentPo> page, @Param("commentVo")CommentVo commentVo);
}
