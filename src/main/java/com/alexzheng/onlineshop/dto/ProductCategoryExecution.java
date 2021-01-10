package com.alexzheng.onlineshop.dto;

import com.alexzheng.onlineshop.entity.ProductCategory;
import com.alexzheng.onlineshop.enums.ProductCategoryStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 15:04 2020/5/28
 * @Annotation
 */
@Data
public class ProductCategoryExecution {

    private int state;

    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    //操作失败的时候使用的构造器 or 操作成功但是不需要返回数据时使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //操作成功的构造器（返回多个数据时）
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

}
