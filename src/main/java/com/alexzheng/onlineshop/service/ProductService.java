package com.alexzheng.onlineshop.service;



import com.alexzheng.onlineshop.dto.ImageFileHolder;
import com.alexzheng.onlineshop.dto.ProductExecution;
import com.alexzheng.onlineshop.entity.Product;
import com.alexzheng.onlineshop.exceptions.ProductOperationException;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 16:13 2020/5/14
 * @Annotation
 */
public interface ProductService {

    /**
     *查询商品列表并分页，可输入的条件有：商品名(模糊)，商品状态，店铺I的，商品类别
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 修改商品以及图片处理
     *
     * @param product 商品信息
     * @param fileHolder 商品缩略图
     * @param productImgHolderList 商品详情图
     * @return
     */
    ProductExecution modifyProduct(Product product, ImageFileHolder fileHolder, List<ImageFileHolder> productImgHolderList);

    /**
     * 通过商品Id查询唯一的商品信息
     *
     * @param productId
     * @return
     */
    Product getProductById(Long productId);

    /**
     * 添加商品信息以及图片处理
     *
     * @param product 商品
     * @param thumbnail 缩略图
     * @param thumbnailList 详情图
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageFileHolder thumbnail, List<ImageFileHolder> thumbnailList) throws ProductOperationException;
}
