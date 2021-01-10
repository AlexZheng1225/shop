package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 16:24 2020/4/27
 * @Annotation
 */
public interface ProductCategoryDao {

    /**
     * 通过shopId 获取商品分类信息
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量插入商品类别信息
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);

}
