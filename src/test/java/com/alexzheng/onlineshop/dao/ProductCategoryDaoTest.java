package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 16:33 2020/4/27
 * @Annotation
 */
@SpringBootTest
public class ProductCategoryDaoTest{

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void queryProductCategory() throws Exception {
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(1L);
        System.out.println(productCategoryList);
//        Assert.assertEquals(3,productCategoryList.size());
    }

    @Test
    public void batchInsertProductCategory(){
        ProductCategory pc1 = new ProductCategory();
        ProductCategory pc2 = new ProductCategory();
        pc1.setProductCategoryName("11111");
        pc1.setPriority(7);
        pc1.setCreateTime(new Date());
        pc1.setShopId(1L);
        pc2.setProductCategoryName("22222");
        pc2.setPriority(8);
        pc2.setCreateTime(new Date());
        pc2.setShopId(1L);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(pc1);
        productCategoryList.add(pc2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        if(effectedNum>0){
            System.out.println("插入成功");
        }else{
            System.out.println("插入失败");
        }
    }

    @Test
    public void deleteProductCategory(){
        long shopId = 1L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        ProductCategory productCategory = productCategoryList.get(0);
        int effected = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(),productCategory.getShopId());
        Assert.assertEquals(1,effected);
    }

}