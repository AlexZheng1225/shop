package com.alexzheng.onlineshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Alex Zheng
 * @Date created in 16:18 2020/5/14
 * @Annotation
 */
@Getter
@AllArgsConstructor
public enum ProductStateEnum {

    OFFLINE(-1,"非法商品"),DOWN(0,"下架"),SUCCESS(1,"成功"),
    INNER_ERROR(-1001,"操作失败"),EMPTY(-1002,"商品为空")
    ;

    private int state;
    private String stateInfo;


    /**
     * 根据传入的state返回相应的enum的值
     */
    public static ProductStateEnum stateOf(int state){
        for(ProductStateEnum stateEnum:values()){
            if(stateEnum.getState()==state){
                return stateEnum;
            }
        }
        return null;
    }
}
