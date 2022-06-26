package com.tian.userserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.userserver.pojo.UserAuthority;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-14
 */
public interface IUserAuthorityService extends IService<UserAuthority> {

    /**
     * @Author QiGuang
     * @Description 获取权限字符串
     * @Param id 角色id
     */
    String getAuthorityByRoleId(Long id);

    /**
     * @Author QiGuang
     * @Description 获取权限列表
     * @Param id 角色id
     */
    List<String> getAuthorityListByRoleId(Long id);
}
