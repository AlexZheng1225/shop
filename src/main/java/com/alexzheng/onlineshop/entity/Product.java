package com.alexzheng.onlineshop.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 23:40 2020/5/5
 * @Annotation
 */
@Data
public class Product {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 缩略图地址
     */
    private String imgAddr;

    /**
     * 原价
     */
    private String normalPrice;

    /**
     * 折扣价
     */
    private String promotionPrice;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 权重
     */
    private Integer priority;

    /**
     * 商品创建时间
     */
    private Date createTime;

    /**
     * 商品更新时间
     */
    private Date lastEditTime;

    /**
     * 商品状态 0.下架 1.在前端展示系统展示
     */
    private Integer enableStatus;

    /**
     * 商品详情图片列表（一对多/一个商品对应多张详情图)
     */
    private List<ProductImg> productImgList;

    /**
     * 商品类别/商品属于哪个商品类别的
     */
    private ProductCategory productCategory;

    /**
     * 商品是由哪个店铺发布的
     */
    private Shop shop;
}
