package com.alexzheng.onlineshop.dao;


import com.alexzheng.onlineshop.entity.ProductImg;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 14:59 2020/5/13
 * @Annotation
 */
public interface ProductImgDao {

    /**
     * 查询出对应商品的所有详情图片
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgListById(long productId);

    /**
     * 删除指定目录下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

}
