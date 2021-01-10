package com.alexzheng.onlineshop.service;


import com.alexzheng.onlineshop.dto.ImageFileHolder;
import com.alexzheng.onlineshop.dto.ShopExecution;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.exceptions.ShopOperationException;

/**
 * @Author Alex Zheng
 * @Date created in 16:30 2020/4/7
 * @Annotation
 */
public interface ShopService {

    /**
     * 根据shopCondition分页返回相应店铺列表数据
     * @param shopCondiction
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondiction, int pageIndex, int pageSize);


    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getShopById(long shopId);

    /**
     * 更新店铺信息 包括对图片的处理
     * @param shop
     * @param imageFileHolder
     * @return
     */
    ShopExecution modifyShop(Shop shop, ImageFileHolder imageFileHolder)throws ShopOperationException;

    /**
     * 注册店铺信息 包含图片处理
     * @param shop
     * @param imageFileHolder
     * @return
     */
    ShopExecution addShop(Shop shop, ImageFileHolder imageFileHolder)throws ShopOperationException;

}
