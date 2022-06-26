package com.tian.itemserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.itemserver.config.exception.GlobalException;
import com.tian.itemserver.mapper.ItemsMapper;
import com.tian.itemserver.pojo.Items;
import com.tian.itemserver.service.IItemsService;
import com.tian.itemserver.vo.SearchVo;
import com.tian.serverapi.util.RespBeanEnum;
import com.tian.serverapi.vo.ItemVo;
import com.tian.serverapi.vo.PageVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 影视信息 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
@Service
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements IItemsService {

    /**
     * @Author QiGuang
     * @Description 主页
     */
    @Override
//    @Cacheable(cacheNames = "index")
    public Map<String, Object> getIndex() {
        Map<String, Object> map = new HashMap<>();
        List<Items> movies = baseMapper.getNewItems("电影");
        map.put("movies", movies);
        List<Items> dramas = baseMapper.getNewItems("电视剧");
        map.put("dramas", dramas);
        List<Items> animations = baseMapper.getNewItems("动漫");
        map.put("animations", animations);
        List<Items> topMovies = baseMapper.getTopItems("电影");
        map.put("topMovies", topMovies);
        List<Items> topDramas = baseMapper.getTopItems("电视剧");
        map.put("topDramas", topDramas);
        List<Items> topAnimations = baseMapper.getTopItems("动漫");
        map.put("topAnimations", topAnimations);
        return map;
    }

    /**
     * @Author QiGuang
     * @Description 分类
     */
    @Override
    public Map<String, Object> getSortList(ItemVo itemVo) {
        QueryWrapper<Items> wrapper = new QueryWrapper<>();
        wrapper
                .like(StringUtils.isNotBlank(itemVo.getType()), "type", itemVo.getType())
                .like(StringUtils.isNotBlank(itemVo.getArea()), "area", itemVo.getArea())
                .like(StringUtils.isNotBlank(itemVo.getStyle()), "style", itemVo.getStyle())
                .eq(itemVo.getYear() != null && itemVo.getYear() > 2009, "year(show_time)", itemVo.getYear())
                .le(itemVo.getYear() != null && itemVo.getYear() <= 2009, "year(show_time)", 2009)
                .orderBy(true, itemVo.getIsAsc(), itemVo.getOrder());
        return getRespBean(wrapper, itemVo);
    }

    /**
     * @Author QiGuang
     * @Description 详情页
     */
    @Override
    public Map<String, Object> getDetailPage(int id) {
        Items detailItem = baseMapper.selectById(id);
        // 查不到
        if (detailItem == null) throw new GlobalException(RespBeanEnum.NO_ITEMS);
        List<Items> similarItems = getSimilarItems(detailItem.getType(), detailItem.getArea(), detailItem.getStyle(),
                null, 6);
        Map<String, Object> map = new HashMap<>();
        map.put("detailItem", detailItem);
        map.put("similarItems", similarItems);
        return map;
    }

    /**
     * @Author QiGuang
     * @Description 详情页-类似推荐
     */
    @Override
    public List<Items> getSimilarItems(String type, String area, String style, List<Integer> ids, int size) {
        QueryWrapper<Items> wrapper = new QueryWrapper<>();
        wrapper
                .like(StringUtils.isBlank(type), "type", type)
                .like(StringUtils.isBlank(area), "area", area)
                .like(StringUtils.isBlank(style), "style", style)
                .notIn(ids != null, "id", ids)
                .orderBy(true, false, "show_time");
        Page<Items> page = new Page<>(1, size);
        baseMapper.selectPage(page, wrapper);
        return page.getRecords();
    }

    /**
     * @Author QiGuang
     * @Description 搜索
     */
    @Override
    public Map<String, Object> getSearchList(SearchVo searchVo) {
        QueryWrapper<Items> wrapper = new QueryWrapper<>();
        wrapper
                .like("name", searchVo.getKeyword())
                .or()
                .like("origin_name", searchVo.getKeyword())
                .or()
                .like("alias", searchVo.getKeyword())
                .orderBy(true, searchVo.getIsAsc(), searchVo.getOrder());
        return getRespBean(wrapper, searchVo);
    }

    /**
     * @Author QiGuang
     * @Description 执行查询并提取结果
     */
    private Map<String, Object> getRespBean(QueryWrapper<Items> wrapper, PageVo pageVo) {
        Page<Items> page = new Page<>(pageVo.getPage(), pageVo.getSize());
        baseMapper.selectPage(page, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("itemList", page.getRecords());
        return map;
    }
}
