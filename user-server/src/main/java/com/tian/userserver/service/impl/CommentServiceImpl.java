package com.tian.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.userserver.mapper.CommentMapper;
import com.tian.userserver.pojo.Comment;
import com.tian.userserver.pojo.CommentPo;
import com.tian.userserver.service.ICommentService;
import com.tian.userserver.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评分及评论 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * @Author QiGuang
     * @Description 添加或更新评论
     * @Param
     */
    @Override
    public int addOrUpdate(Comment comment) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.select("id")
                .eq("item_id", comment.getItemId())
                .eq("user_id", comment.getUserId());
        List<Map<String, Object>> maps = baseMapper.selectMaps(wrapper);
        if (maps.size() == 0) {
            return baseMapper.insert(comment);
        }
        comment.setId((Long) maps.get(0).get("id"));
        return baseMapper.updateById(comment);
    }

    /**
     * @Author QiGuang
     * @Description 获取评论列表
     * @Param
     */
    @Override
    public Map<String, Object> getCommentList(CommentVo commentVo) {
        System.out.println("-----------------------------");
        Page<CommentPo> page=new Page<>(commentVo.getPage(),commentVo.getSize());
        commentMapper.getCommentList(page,commentVo);
        Map<String,Object> map=new HashMap<>();
        map.put("total",page.getTotal());
        map.put("comments",page.getRecords());
        return map;
    }
}
