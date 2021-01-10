package com.alexzheng.onlineshop.service;



import com.alexzheng.onlineshop.entity.ShopCategory;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 18:47 2020/4/9
 * @Annotation
 */
public interface ShopCategoryService {

    public static final String SCLISTKEY = "shopcategorylist";

    /**
     * 根据查询条件获取ShopCategory列表
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}
