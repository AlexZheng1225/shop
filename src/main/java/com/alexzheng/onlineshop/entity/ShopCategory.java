package com.alexzheng.onlineshop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date created in 23:43 2020/5/5
 * @Annotation
 */
@Data
public class ShopCategory {
    //店铺类别的ID
    private Long shopCategoryId;
    //店铺类别的名称
    private String shopCategoryName;
    //店铺类别描述
    private String shopCategoryDesc;
    //店铺类别图片的链接地址
    private String shopCategoryImg;
    //权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
    //上级ID(实体类)
    private ShopCategory parent;
}
