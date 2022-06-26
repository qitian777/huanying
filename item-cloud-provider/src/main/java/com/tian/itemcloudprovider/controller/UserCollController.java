package com.tian.itemcloudprovider.controller;

import com.tian.itemcloudprovider.pojo.UserColl;
import com.tian.itemcloudprovider.service.IUserCollService;
import com.tian.serverapi.util.RespBean;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.serverapi.vo.CloudItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/collection")
@Api(tags = "收藏控制器")
public class UserCollController {
    @Autowired
    private IUserCollService userCollService;

    @ApiOperation(value = "收藏分类列表")
    @PostMapping("/list")
    public RespBean getList(@Valid @RequestBody CloudItemVo cloudItemVo) {
        return RespBean.success(userCollService.getCollSortList(cloudItemVo));
    }

    @ApiOperation(value = "添加收藏")
    @PostMapping("/add")
    public RespBean addColl(@Valid @RequestBody UserColl userColl) {
        if (userCollService.addColl(userColl)>0) return RespBean.success(RespBeanEnum.ADD_COLLECTION_SUCCESS);
        return RespBean.error(RespBeanEnum.ADD_COLLECTION_FAIL);
    }

    @ApiOperation(value = "删除收藏")
    @PostMapping("/delete")
    public RespBean deleteColl(@Valid @RequestBody UserColl userColl) {
        if (userCollService.deleteColl(userColl)>0) return RespBean.success(RespBeanEnum.DELETE_COLLECTION_SUCCESS);
        return RespBean.error(RespBeanEnum.DELETE_COLLECTION_FAIL);
    }

    @ApiOperation(value = "确认是否已收藏")
    @PostMapping("/check")
    public RespBean checkColl( @Valid @RequestBody UserColl userColl) {
        return RespBean.success(userCollService.checkColl(userColl));
    }
}
