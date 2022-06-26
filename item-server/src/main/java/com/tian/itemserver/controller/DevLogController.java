package com.tian.itemserver.controller;

import com.tian.itemserver.pojo.DevLog;
import com.tian.itemserver.service.IDevLogService;
import com.tian.serverapi.util.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 开发日志 前端控制器
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/devLog")
@Api(tags = "开发日志")
public class DevLogController {
    @Autowired
    private IDevLogService devLogService;

    /**
     * @Author QiGuang
     * @Description 获取开发日志
     */
    @ApiOperation(value = "开发日志")
    @GetMapping("/get")
    public RespBean getDevLog(){
        List<DevLog> devLogs = devLogService.getDevLogList();
        return RespBean.success(devLogs);
    }
}
