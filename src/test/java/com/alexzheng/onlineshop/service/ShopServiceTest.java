package com.alexzheng.onlineshop.service;

import com.alexzheng.onlineshop.dto.ImageFileHolder;
import com.alexzheng.onlineshop.dto.ShopExecution;
import com.alexzheng.onlineshop.entity.Area;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.entity.ShopCategory;
import com.alexzheng.onlineshop.enums.ShopStateEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date created in 17:08 2020/4/7
 * @Annotation
 */
@SpringBootTest
public class ShopServiceTest{

    @Autowired
    private ShopService shopService;

    @Test
    public void getShopList(){
        Shop shopCondition = new Shop();
//        ShopCategory sc = new ShopCategory();
//        sc.setShopCategoryId(2L);
//        shopCondition.setShopCategory(sc);
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        ShopExecution se = shopService.getShopList(shopCondition,0,6);
        System.out.println("店铺列表数："+se.getShopList().size());
        System.out.println("店铺总数："+se.getCount());
        se.getShopList().forEach(System.out::println);
    }

    @Test
    public void modifyShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(45L);
        shop.setShopName("修改名称后的店铺");
        shop.setShopImg("E:/all/pictest/wall.jpg");
        InputStream is = new FileInputStream(shop.getShopImg());
        ShopExecution shopExecution = shopService.modifyShop(shop,new ImageFileHolder(shop.getShopImg(),is));
        Assert.assertEquals(shopExecution.getState(),ShopStateEnum.SUCCESS.getState());
    }

    @Test
    public void addShop() throws Exception {
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
        shop.setShopName("GUST");
        shop.setShopDesc("广东科技学院");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("E:/all/pictest/tianhe.jpg");
        InputStream inputStream = new FileInputStream(shopImg);
        ImageFileHolder imageFileHolder = new ImageFileHolder(shopImg.getName(),inputStream);
        ShopExecution se = shopService.addShop(shop,imageFileHolder);
        Assert.assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }

}