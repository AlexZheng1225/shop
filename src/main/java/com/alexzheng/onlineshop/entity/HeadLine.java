package com.alexzheng.onlineshop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date created in 23:38 2020/4/5
 * @Annotation
 */
@Data
public class HeadLine {
    //ID
    private Long lineId;
    //名称
    private String lineName;
    //链接
    private String lineLink;
    //图片
    private String lineImg;
    //权重
    private Integer priority;
    //状态    0.不可用 1.可用
    private Integer enableStatus;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
}
