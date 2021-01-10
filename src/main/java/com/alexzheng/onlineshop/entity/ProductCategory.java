package com.alexzheng.onlineshop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date created in 23:41 2020/5/5
 * @Annotation
 */
@Data
public class ProductCategory {
    //商品类别id
    private Long productCategoryId;
    //店铺ID
    private Long shopId;
    //商品类别名称
    private String productCategoryName;
    //商品类别的权重
    private Integer priority;
    //商品类别创建时间
    private Date createTime;
}
