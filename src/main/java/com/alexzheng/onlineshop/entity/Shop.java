package com.alexzheng.onlineshop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date created in 23:42 2020/5/5
 * @Annotation
 */
@Data
public class Shop {
    //店铺ID
    private Long shopId;
    //店铺名称
    private String shopName;
    //店铺描述
    private String shopDesc;
    //店铺地址
    private String shopAddr;
    //联系方式
    private String phone;
    //店铺缩略图地址
    private String shopImg;
    //店铺权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
    //店铺状态  -1.不可用 0.审核中 1.可用
    private Integer enableStatus;
    //超级管理员给店家的建议或提醒
    private String advice;
    //区域实体类——表示店铺属于哪一块区域
    private Area area;
    //用户信息实体类——表示该店铺由谁创建
    private PersonInfo owner;
    //店铺类别实体类——表示店铺属于哪个类别
    private ShopCategory shopCategory;
}
