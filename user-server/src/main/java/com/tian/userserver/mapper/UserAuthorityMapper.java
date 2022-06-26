package com.tian.userserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.userserver.pojo.UserAuthority;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-14
 */
@Repository
public interface UserAuthorityMapper extends BaseMapper<UserAuthority> {

    @Select("SELECT auth_code FROM user_authority as ua\n" +
            "left join role_auth as ra on ra.auth_id = ua.id\n" +
            "where ra.role_id=#{id}")
    List<String> getAuthorityByRoleId(Long id);
}
