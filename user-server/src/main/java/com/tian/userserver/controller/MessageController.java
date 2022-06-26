package com.tian.userserver.controller;

import com.tian.serverapi.util.RespBean;
import com.tian.userserver.pojo.User;
import com.tian.userserver.service.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 * 消息 前端控制器
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/message")
@Api(tags = "离线消息")
public class MessageController {
    @Autowired
    private IMessageService messageService;

    @ApiOperation(value = "获取已保存的消息")
    @GetMapping("/get")
    public RespBean getMessage(Principal principal) {
        String name = principal.getName();
        return RespBean.success(messageService.getMessageList(name));
    }

    @ApiOperation(value = "更新消息状态")
    @GetMapping("/update")
    public RespBean updateMessageState(String from,Principal principal) {
        String to=principal.getName();
        return RespBean.success(messageService.updateMessageState(from,to));
    }

}
