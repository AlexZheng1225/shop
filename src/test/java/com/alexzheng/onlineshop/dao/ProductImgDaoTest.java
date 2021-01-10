package com.alexzheng.onlineshop.dao;


import com.alexzheng.onlineshop.entity.ProductImg;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 15:37 2020/5/14
 * @Annotation
 */
@SpringBootTest
public class ProductImgDaoTest{

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void batchInsertProductImg() throws Exception {
            ProductImg productImg1 = new ProductImg();
            productImg1.setImgAddr("pic1");
            productImg1.setImgDesc("测试图片1");
            productImg1.setPriority(1);
            productImg1.setCreateTime(new Date());
            productImg1.setProductId(6L);
            ProductImg productImg2 = new ProductImg();
            productImg2.setImgAddr("pic2");
            productImg2.setImgDesc("测试图片2");
            productImg2.setPriority(1);
            productImg2.setCreateTime(new Date());
            productImg2.setProductId(6L);
            List<ProductImg> productImgList = new ArrayList<>();
            productImgList.add(productImg1);
            productImgList.add(productImg2);
            int effectedNum = productImgDao.batchInsertProductImg(productImgList);
            Assert.assertEquals(2,effectedNum);
    }

    @Test
    public void deleteProductImgByProductId() {
        long productId = 6L;
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        Assert.assertEquals(2,effectedNum);
    }

    @Test
    public void queryProductImgList() {
        long productId = 6L;
        List<ProductImg> productImgList = productImgDao.queryProductImgListById(productId);
        productImgList.forEach(System.out::println);
    }
}