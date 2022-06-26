package com.tian.itemserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.itemserver.pojo.Items;
import com.tian.itemserver.vo.SearchVo;
import com.tian.serverapi.vo.ItemVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 影视信息 服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
public interface IItemsService extends IService<Items> {

    Map<String,Object> getIndex();

    Map<String,Object> getSortList(ItemVo itemVo);

    Map<String,Object> getDetailPage(int id);

    List<Items> getSimilarItems(String type,String area,String style,List<Integer> ids,int size);

    Map<String,Object> getSearchList(SearchVo searchVo);

}
