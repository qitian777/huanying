package com.tian.userserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.userserver.pojo.Friend;
import com.tian.userserver.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 好友 Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Repository
public interface FriendMapper extends BaseMapper<Friend> {

    @Select("select a.id,a.username,a.nickname,a.picture \n" +
            "from user as a \n" +
            "left join friend as f on a.id=f.f_1 \n" +
            "where f.f_2=#{id}")
    List<User> getFriendListA(Long id);

    @Select("select a.id,a.username,a.nickname,a.picture \n" +
            "from user as a \n" +
            "left join friend as f on a.id=f.f_2 \n" +
            "where f.f_1=#{id}")
    List<User> getFriendListB(Long id);

    @Insert("insert into friend value (#{f1},#{f2});")
    int saveFriend(Friend friend);
}
