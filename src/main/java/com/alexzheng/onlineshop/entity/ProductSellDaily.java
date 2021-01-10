package com.alexzheng.onlineshop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/15 9:53
 * @Annotation 统计每件商品每天的具体销量
 */
@Data
public class ProductSellDaily {

    /**
     * 销量，精确到天
     */
    private Date createTime;

    /**
     * 销量
     */
    private Integer total;

    /**
     * 商品信息实体类
     */
    private Product product;

    /**
     * 店铺信息实体类
     */
    private Shop shop;

}
