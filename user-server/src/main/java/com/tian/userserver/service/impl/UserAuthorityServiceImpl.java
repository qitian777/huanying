package com.tian.userserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.userserver.mapper.UserAuthorityMapper;
import com.tian.userserver.pojo.UserAuthority;
import com.tian.userserver.service.IUserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-14
 */
@Service
public class UserAuthorityServiceImpl extends ServiceImpl<UserAuthorityMapper, UserAuthority> implements IUserAuthorityService {
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    /**
     * @Author QiGuang
     * @Description 将权限列表转为字符串
     * @Param
     */
    @Override
    public String getAuthorityByRoleId(Long id) {
        List<String> authority = getAuthorityListByRoleId(id);
        StringBuilder auth = new StringBuilder();
        for (int i = 0; i < authority.size(); i++) {
            auth.append(authority.get(i));
            if (i != authority.size() - 1) auth.append(",");
        }
        return auth.toString();
    }

    /**
     * @Author QiGuang
     * @Description 获取权限列表
     * @Param
     */
    @Override
    public List<String> getAuthorityListByRoleId(Long id) {
        return userAuthorityMapper.getAuthorityByRoleId(id);
    }
}
