package com.alexzheng.onlineshop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date created in 23:41 2020/5/5
 * @Annotation
 */
@Data
public class ProductImg {
    //商品图片id
    private Long productImgId;
    //图片地址
    private String imgAddr;
    //图片说明
    private String imgDesc;
    //图片权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //表示属于哪个商品的详情图片
    private Long productId;
}
