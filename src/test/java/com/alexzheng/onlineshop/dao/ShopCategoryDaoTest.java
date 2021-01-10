package com.alexzheng.onlineshop.dao;


import com.alexzheng.onlineshop.entity.ShopCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 18:33 2020/4/9
 * @Annotation
 */
@SpringBootTest
public class ShopCategoryDaoTest{

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategory() throws Exception {

//        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
        Assert.assertEquals(2,shopCategoryList.size());

//        ShopCategory testShopCategory = new ShopCategory();
//        ShopCategory parentShopCategory  = new ShopCategory();
//        parentShopCategory.setShopCategoryId(1L);
//        testShopCategory.setParent(parentShopCategory);
//        shopCategoryList = shopCategoryDao.queryShopCategory(testShopCategory);
//        Assert.assertEquals(1,shopCategoryList.size());
//        System.out.println(shopCategoryList.get(0).getShopCategoryName());

    }

}