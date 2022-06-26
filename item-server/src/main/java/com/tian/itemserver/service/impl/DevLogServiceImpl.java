package com.tian.itemserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.itemserver.mapper.DevLogMapper;
import com.tian.itemserver.pojo.DevLog;
import com.tian.itemserver.service.IDevLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 开发日志 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Service
public class DevLogServiceImpl extends ServiceImpl<DevLogMapper, DevLog> implements IDevLogService {

    /**
     * @Author QiGuang
     * @Description 获取开发日志
     * @Param
     */
    @Override
    public List<DevLog> getDevLogList() {
        return baseMapper.selectList(null);
    }
}
