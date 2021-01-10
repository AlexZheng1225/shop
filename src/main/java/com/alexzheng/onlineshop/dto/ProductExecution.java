package com.alexzheng.onlineshop.dto;

import com.alexzheng.onlineshop.entity.Product;
import com.alexzheng.onlineshop.enums.ProductStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 16:14 2020/5/14
 * @Annotation
 */
@Data
public class ProductExecution {
    //结果状态
    private int state;

    //状态表示
    private String stateInfo;

    //商品数量
    private int count;

    //操作的product(增删改)
    private Product product;

    //获取product列表(查询商品列表的时候用)
    private List<Product> productList;

    public ProductExecution() {
    }

    //操作失败的构造器
    public ProductExecution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //操作成功的构造器
    public ProductExecution(ProductStateEnum stateEnum,Product product) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }

    //操作成功的构造器
    public ProductExecution(ProductStateEnum stateEnum,List<Product> productList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
    }

}
