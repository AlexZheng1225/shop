package com.alexzheng.onlineshop.dto;

import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.enums.ShopStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 16:13 2020/5/7
 * @Annotation
 */
@Data
public class ShopExecution {
    //结果标识
    private int state;

    //状态标识
    private String stateInfo;

    //店铺数量
    private int count;

    //操作的shop(CRUD用到)
    private Shop shop;

    //shop列表 查询shop列表时用
    private List<Shop> shopList;

    public ShopExecution() {

    }

    //店铺操作失败的构造器
    public ShopExecution(ShopStateEnum shopStateEnum) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
    }

    //店铺操作成功的构造器(单个shop)
    public ShopExecution(ShopStateEnum shopStateEnum,Shop shop) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shop = shop;
    }

    //店铺操作成功的构造器(多个shop)
    public ShopExecution(ShopStateEnum shopStateEnum,List<Shop> shopList) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shopList = shopList;
    }

}
