package com.tian.itemcloudprovider02.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tian.itemcloudprovider02.pojo.UserColl;
import com.tian.itemcloudprovider02.service.IUserCollService;
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

    /**
     * @Author QiGuang
     * @Description HYSTRIX失败时的回调函数
     * @Param
     */
    public RespBean findAllFallback(@Valid @RequestBody CloudItemVo cloudItemVo){
        System.out.println("------------HYSTRIX_ERROR-----------");
        return RespBean.error(RespBeanEnum.HYSTRIX_ERROR);
    }

    @ApiOperation(value = "收藏分类列表")
    // 声明一个失败回滚处理函数
    @HystrixCommand(fallbackMethod = "findAllFallback")
    @PostMapping("/list")
    public RespBean getList(@Valid @RequestBody CloudItemVo cloudItemVo) throws InterruptedException {
        System.out.println("------------02 provider-----------");
        Thread.sleep(5000);
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
