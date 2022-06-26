package com.tian.itemserver.controller;

import com.tian.itemserver.service.IItemsService;
import com.tian.itemserver.vo.SearchVo;
import com.tian.serverapi.util.RespBean;
import com.tian.serverapi.vo.ItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 影视信息 前端控制器
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/items")
@Api(tags = "Items获取")
public class ItemsController {
    @Autowired
    private IItemsService itemsService;

    /**
     * @Author QiGuang
     * @Description 获取主页
     */
    @ApiOperation(value = "主页")
    @GetMapping("/index")
    public RespBean getIndex(){
        return RespBean.success(itemsService.getIndex());
    }

    /**
     * @Author QiGuang
     * @Description 获取分类列表
     * @Param 分类信息
     */
    @ApiOperation(value = "list")
    @PostMapping("/list")
    public RespBean getList(@Valid @RequestBody ItemVo itemVo){
        return RespBean.success(itemsService.getSortList(itemVo));
    }

    /**
     * @Author QiGuang
     * @Description 获取详情页及推荐列表
     * @Param id
     */
    @ApiOperation(value = "详情页")
    @GetMapping("/detail")
    public RespBean getDetailPage(int id) {
        return  RespBean.success(itemsService.getDetailPage(id));
    }

    /**
     * @Author QiGuang
     * @Description
     * @Param 关键词，分页排序信息
     */
    @ApiOperation(value = "搜索")
    @PostMapping("/search")
    public RespBean searchItems(@Valid @RequestBody SearchVo searchVo) {
        return RespBean.success(itemsService.getSearchList(searchVo));
    }
}
