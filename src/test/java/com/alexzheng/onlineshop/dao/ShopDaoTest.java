package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.Area;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.entity.ShopCategory;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 11:06 2020/4/7
 * @Annotation
 */
@SpringBootTest
public class ShopDaoTest{

    @Autowired
    private ShopDao shopDao;

    @Test
    public void queryShopListAndCount(){
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,0,6);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("Shop Count:"+count);
        System.out.println("ShopList Count:"+shopList.size());
        shopList.forEach(System.out::println);
//        ShopCategory sc = new ShopCategory();
//        sc.setShopCategoryId(2L);
//        shopCondition.setShopCategory(sc);
//        List<Shop> shopList1 = shopDao.queryShopList(shopCondition,0,2);
//        int count1 = shopDao.queryShopCount(shopCondition);
//        System.out.println("Shop Count:"+count1);
//        System.out.println("ShopList Count:"+shopList1.size());
        //System.out.println(shopList);
    }

    @Test
    public void queryByShopId(){
        Shop shop = shopDao.queryByShopId(33L);
        System.out.println(shop);
    }

    @Test
    public void insertShop() throws Exception {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory= new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setShopImg("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectNum = shopDao.insertShop(shop);
        Assert.assertEquals(1,effectNum);
    }

    @Test
    public void updateShop(){
        Shop shop = new Shop();
        shop.setShopId(48L);
        shop.setShopName("测试");
        shop.setShopDesc("13263523632");
        shop.setShopAddr("xxxxx");
        shop.setPhone("13622200000");
        shop.setShopImg("https://xxx.jpg");
        shop.setCreateTime(new Date());
        int effectNum = shopDao.updateShop(shop);
        Assert.assertEquals(1,effectNum);
    }

}