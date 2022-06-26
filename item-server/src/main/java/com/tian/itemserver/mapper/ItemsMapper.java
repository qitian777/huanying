package com.tian.itemserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.itemserver.pojo.Items;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 影视信息 Mapper 接口
 * </p>
 *
 * @author QiGuang
 * @since 2022-06-13
 */
public interface ItemsMapper extends BaseMapper<Items> {

    /**
     * @Author QiGuang
     * @Description 时间排序
     * @Param 类型
     */
    @Select("select * from items where type like \"%\"#{type}\"%\" order by show_time desc limit 6")
    List<Items> getNewItems(String type);

    /**
     * @Author QiGuang
     * @Description B站评分排序
     * @Param 类型
     */
    @Select("select * from items where type like \"%\"#{type}\"%\" order by bi_score desc limit 10")
    List<Items> getTopItems(String type);


}
