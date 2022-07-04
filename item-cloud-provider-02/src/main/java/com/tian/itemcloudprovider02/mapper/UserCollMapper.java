package com.tian.itemcloudprovider02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tian.itemcloudprovider02.pojo.Items;
import com.tian.itemcloudprovider02.pojo.UserColl;
import com.tian.serverapi.vo.CloudItemVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 收藏 Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-19
 */
@Repository
public interface UserCollMapper extends BaseMapper<UserColl> {

    Page<Items> selectCollSortList(@Param("page") Page<Items> page, @Param("itemVo") CloudItemVo cloudItemVo);
}
