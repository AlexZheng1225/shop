package com.alexzheng.onlineshop.enums;

import lombok.Getter;

/**
 * @Author Alex Zheng
 * @Date created in 16:17 2020/5/7
 * @Annotation
 */
@Getter
public enum ShopStateEnum {
    CHECK(0,"审核中"),
    OFF_LINE(-1,"非法店铺"),
    SUCCESS(1,"操作成功"),
    PASS(2,"通过认证"),
    INNER_ERROR(-1001,"内部系统错误"),
    NULL_SHOPID(-1002,"ShopId为空"),
    NULL_SHOP(-1003,"shop信息为空")
    ;

    private int state;

    private String stateInfo;

    private ShopStateEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 根据传入的state返回相应的enum的值
     */
    public static ShopStateEnum stateOf(int state){
        for(ShopStateEnum stateEnum:values()){
            if(stateEnum.getState()==state){
                return stateEnum;
            }
        }
        return null;
    }


}
