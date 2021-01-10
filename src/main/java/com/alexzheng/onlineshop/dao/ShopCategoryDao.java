package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 18:05 2020/4/9
 * @Annotation
 */
public interface ShopCategoryDao {

    /**
     * 返回所有店铺类别
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
