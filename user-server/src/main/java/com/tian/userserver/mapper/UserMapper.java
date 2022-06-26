package com.tian.userserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.userserver.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
