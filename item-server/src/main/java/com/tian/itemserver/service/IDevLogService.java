package com.tian.itemserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.itemserver.pojo.DevLog;

import java.util.List;

/**
 * <p>
 * 开发日志 服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
public interface IDevLogService extends IService<DevLog> {

    /**
     * @Author QiGuang
     * @Description 获取开发日志
     * @Param
     */
    List<DevLog> getDevLogList();
}
