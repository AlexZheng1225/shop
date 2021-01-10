package com.alexzheng.onlineshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Alex Zheng
 * @Date created in 17:31 2020/5/27
 * @Annotation
 */
@Getter
@AllArgsConstructor
public enum ProductCategoryStateEnum {
    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "操作失败"),
    NULL_SHOP(-1002, "Shop信息为空"),
    EMPETY_LIST(-1003, "请输入商品目录信息");

    private int state;
    private String stateInfo;

    /**
     * 通过state获取productCategoryEnum,从而调用ProductCategoryStateEnum的getStateInfo()获取stateInfo
     * @param index
     * @return
     */
    public static ProductCategoryStateEnum stateOf(int index){
        for(ProductCategoryStateEnum productCategoryStateEnum:values()){
            if(productCategoryStateEnum.getState() == index){
                return productCategoryStateEnum;
            }
        }
        return null;
    }

}
