package com.tian.userserver.controller;

import com.tian.serverapi.vo.CloudItemVo;
import com.tian.userserver.pojo.User;
import com.tian.userserver.pojo.UserColl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * <p>
 * 收藏 前端控制器
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/userColl")
@Api(tags = "收藏，cloud消费者")
public class CollectionController {
    @Autowired
    private RestTemplate restTemplate;

//    private static final String url="http://localhost:8001/collection";
    private static final String url="http://item-cloud-provider/collection";

    @ApiOperation(value = "收藏分类列表")
    @PostMapping("/list")
    public Object getList(@Valid @RequestBody CloudItemVo cloudItemVo) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cloudItemVo.setUserId(user.getId());
        return restTemplate.postForObject(url + "/list", cloudItemVo, Object.class);
    }

    @ApiOperation(value = "添加收藏")
    @PostMapping("/add")
    public Object addColl(@Valid @RequestBody UserColl userColl) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userColl.setUserId(user.getId());
        return restTemplate.postForObject(url + "/add", userColl, Object.class);
    }

    @ApiOperation(value = "删除收藏")
    @PostMapping("/delete")
    public Object deleteColl(@Valid @RequestBody UserColl userColl) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userColl.setUserId(user.getId());
        return restTemplate.postForObject(url + "/delete", userColl, Object.class);
    }

    @ApiOperation(value = "确认是否收藏")
    @PostMapping("/check")
    public Object checkColl( @Valid @RequestBody UserColl userColl) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userColl.setUserId(user.getId());
        return restTemplate.postForObject(url + "/check", userColl, Object.class);
    }
}
