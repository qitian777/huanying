package com.tian.itemcloudprovider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.itemcloudprovider.config.exception.GlobalException;
import com.tian.itemcloudprovider.mapper.UserCollMapper;
import com.tian.itemcloudprovider.pojo.Items;
import com.tian.itemcloudprovider.pojo.UserColl;
import com.tian.itemcloudprovider.service.IUserCollService;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.serverapi.vo.CloudItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 收藏 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-19
 */
@Service
public class UserCollServiceImpl extends ServiceImpl<UserCollMapper, UserColl> implements IUserCollService {
    @Autowired
    private UserCollMapper userCollMapper;

    @Override
    public Map<String, Object> getCollSortList(CloudItemVo cloudItemVo) {
        cloudItemVo.setOrder("i."+cloudItemVo.getOrder());
        Page<Items> page=new Page<>(cloudItemVo.getPage(),cloudItemVo.getSize());
        userCollMapper.selectCollSortList(page,cloudItemVo);
        Map<String,Object> map=new HashMap<>();
        map.put("total",page.getTotal());
        map.put("itemList",page.getRecords());
        return map;
    }

    @Override
    public int addColl(UserColl userColl) {
        if (checkColl(userColl)>0) throw new GlobalException(RespBeanEnum.ALREADY_COLLECTED);
        return baseMapper.insert(userColl);
    }

    @Override
    public int deleteColl(UserColl userColl) {
        if (checkColl(userColl)==0) throw new GlobalException((RespBeanEnum.NOT_COLLECTED));
        QueryWrapper<UserColl> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id", userColl.getUserId())
                .eq("item_id", userColl.getItemId());
        return baseMapper.delete(wrapper);
    }

    @Override
    public Long checkColl(UserColl userColl) {
        QueryWrapper<UserColl> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id", userColl.getUserId())
                .eq("item_id", userColl.getItemId());
        return baseMapper.selectCount(wrapper);
    }
}
