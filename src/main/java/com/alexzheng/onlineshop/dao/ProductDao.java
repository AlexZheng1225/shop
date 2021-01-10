package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 14:56 2020/5/13
 * @Annotation
 */
public interface ProductDao {

    /**
     * 删除商品类别前将该类别下的商品类别ID置为空
     *
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);

    /**
     * 查询商品列表并分页，可输入的条件有：商品名(模糊)，商品状态，店铺ID，商品类别
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 查询对应商品总数
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 通过productId查询唯一的商品详情信息
     *
     * @param productId
     * @return 返回商品信息(包括详情图 )
     */
    Product queryProductById(long productId);

    /**
     * 插入商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);





}
