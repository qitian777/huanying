package com.tian.itemcloudprovider.service;

import com.tian.itemcloudprovider.pojo.UserColl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.serverapi.vo.CloudItemVo;

import java.util.Map;

/**
 * <p>
 * 收藏 服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-19
 */
public interface IUserCollService extends IService<UserColl> {

    Map<String, Object> getCollSortList(CloudItemVo cloudItemVo);

    int addColl(UserColl userColl);

    int deleteColl(UserColl userColl);

    Long checkColl(UserColl userColl);
}
