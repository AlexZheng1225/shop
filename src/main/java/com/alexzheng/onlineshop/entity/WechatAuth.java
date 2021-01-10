package com.alexzheng.onlineshop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date created in 23:44 2020/5/5
 * @Annotation
 */
@Data
public class WechatAuth {
    //ID
    private Long wechatAuthId;
    //openID
    private String openId;
    //创建时间
    private Date createTime;
    //和用户表相关联 (用户ID)
    private PersonInfo personInfo;
}
