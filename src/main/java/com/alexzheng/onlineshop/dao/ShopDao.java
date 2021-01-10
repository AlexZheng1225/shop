package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 10:20 2020/4/7
 * @Annotation
 */
public interface ShopDao {

    /**
     * 分页查询店铺，可输入的条件有：店铺名(模糊)，店铺状态，店铺类别，区域ID，owner
     * 前台后台都会用到
     *
     * @param shopCondition 查询条件
     * @param rowIndex 从第几行开始取
     * @param pageSize  返回的行数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * 通过shopId查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);

    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

}
