package com.alexzheng.onlineshop.service;



import com.alexzheng.onlineshop.dto.ProductCategoryExecution;
import com.alexzheng.onlineshop.entity.ProductCategory;
import com.alexzheng.onlineshop.exceptions.ProductCategoryOperationExecption;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 17:02 2020/4/27
 * @Annotation
 */
public interface ProductCategoryService {

    /**
     * 查询某个店铺下所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategory(long shopId);

    /**
     * 批量添加商品类别信息
     * @param productCategoryList
     * @return
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationExecption;


    /**
     * 删除商品类别
     * 将此类别下的商品里的类别id设置为空，再删除该商品类别
     * @param productCategory
     * @param shopId
     * @return
     * @throws ProductCategoryOperationExecption
     */
    ProductCategoryExecution deleteProductCategory(long productCategory, long shopId)
            throws ProductCategoryOperationExecption;

}
