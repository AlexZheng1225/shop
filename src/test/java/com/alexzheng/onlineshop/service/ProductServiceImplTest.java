package com.alexzheng.onlineshop.service;

import com.alexzheng.onlineshop.dto.ImageFileHolder;
import com.alexzheng.onlineshop.dto.ProductExecution;
import com.alexzheng.onlineshop.entity.Product;
import com.alexzheng.onlineshop.entity.ProductCategory;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.enums.ProductStateEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 18:15 2020/5/14
 * @Annotation
 */
@SpringBootTest
public class ProductServiceImplTest{

    @Resource
    private ProductService productService;

    @Test
    public void addProduct() throws Exception {
        Product product = new Product();
        List<ImageFileHolder> fileHolders = new ArrayList<>();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(12L);
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setShop(shop);
        product.setPriority(5);
        product.setProductCategory(productCategory);
        product.setProductDesc("测试商品");
        product.setProductName("测试商品");
        product.setNormalPrice("20");
        product.setPromotionPrice("15");
        File thumbbailFile1 = new File("E:\\all\\pictest\\wall.jpg");
        FileInputStream fs1  = new FileInputStream(thumbbailFile1);
        ImageFileHolder fileHolder = new ImageFileHolder(thumbbailFile1.getName(),fs1);
        File thumbbailFile2 = new File("E:\\all\\pictest\\wall.jpg");
        FileInputStream fs2  = new FileInputStream(thumbbailFile2);
        ImageFileHolder fileHolder1 = new ImageFileHolder(thumbbailFile2.getName(),fs2);
        File thumbbailFile3 = new File("E:\\all\\pictest\\wall.jpg");
        FileInputStream fs3  = new FileInputStream(thumbbailFile3);
        ImageFileHolder fileHolder2 = new ImageFileHolder(thumbbailFile3.getName(),fs3);
        fileHolders.add(fileHolder);
        fileHolders.add(fileHolder1);
        fileHolders.add(fileHolder2);
        //缩略图
        File thumbbailFile4 = new File("E:\\all\\pictest\\tianhe.jpg");
        FileInputStream fs4  = new FileInputStream(thumbbailFile4);
        ImageFileHolder fileHolder4 = new ImageFileHolder(thumbbailFile1.getName(),fs4);

        ProductExecution pe = productService.addProduct(product,fileHolder4,fileHolders);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());

    }

    @Test
    public void modifyProduct() throws FileNotFoundException {
        Product product = new Product();
        List<ImageFileHolder> fileHolders = new ArrayList<>();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(12L);
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setShop(shop);
        product.setPriority(5);
        product.setProductId(19L);
        product.setProductCategory(productCategory);
        product.setProductDesc("12222222222222222222");
        product.setProductName("鸡12333333333333333333333胸肉");
        product.setNormalPrice("10");
        product.setPromotionPrice("10");
        File thumbbailFile1 = new File("E:\\all\\pictest\\wall.jpg");
        FileInputStream fs1  = new FileInputStream(thumbbailFile1);
        ImageFileHolder fileHolder = new ImageFileHolder(thumbbailFile1.getName(),fs1);
        File thumbbailFile2 = new File("E:\\all\\pictest\\wall.jpg");
        FileInputStream fs2  = new FileInputStream(thumbbailFile2);
        ImageFileHolder fileHolder1 = new ImageFileHolder(thumbbailFile2.getName(),fs2);
        File thumbbailFile3 = new File("E:\\all\\pictest\\wall.jpg");
        FileInputStream fs3  = new FileInputStream(thumbbailFile3);
        ImageFileHolder fileHolder2 = new ImageFileHolder(thumbbailFile3.getName(),fs3);
        fileHolders.add(fileHolder);
        fileHolders.add(fileHolder1);
        fileHolders.add(fileHolder2);
        //缩略图
        File thumbailFile4 = new File("E:\\all\\pictest\\tianhe.jpg");
        FileInputStream fs4  = new FileInputStream(thumbailFile4);
        ImageFileHolder fileHolder4 = new ImageFileHolder(thumbbailFile1.getName(),fs4);

        ProductExecution productExecution = productService.modifyProduct(product,fileHolder4,fileHolders);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());


    }
}