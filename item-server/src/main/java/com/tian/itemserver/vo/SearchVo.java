package com.tian.itemserver.vo;

import com.tian.serverapi.vo.PageVo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 搜索参数类
 * @Author QiGuang
 * @Date 2022/6/16
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Items前端搜索参数对象", description = "搜索参数")
public class SearchVo extends PageVo {
    @NotBlank(message = "搜索关键字不能为空")
    private String keyword;
}
