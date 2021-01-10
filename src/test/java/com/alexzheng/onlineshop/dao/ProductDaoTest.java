package com.alexzheng.onlineshop.dao;


import com.alexzheng.onlineshop.entity.Product;
import com.alexzheng.onlineshop.entity.ProductCategory;
import com.alexzheng.onlineshop.entity.Shop;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 15:35 2020/5/13
 * @Annotation
 */
@SpringBootTest
public class ProductDaoTest{

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void queryProductById() throws Exception {
        long productId=11L;
        Product product = productDao.queryProductById(productId);
        //测试商品详情图数量是否正确，如果测试前商品没有商品详情图则现需要添加商品详情图
        Assert.assertEquals(3,product.getProductImgList().size());
        System.out.println(product.toString());
    }

    @Test
    public void insertProduct() throws Exception {
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(12L);
        //初始化三个商品实例并添加金shopId为1的店铺里
        //同时商品类别ID为12
        Product product1 = new Product();
        product1.setProductName("1烤牛肉");
        product1.setProductDesc("1烤牛肉");
        product1.setImgAddr("测试3");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        Product product2 = new Product();
        product2.setProductName("1烤牛肉烤牛肉");
        product2.setProductDesc("1烤牛肉烤牛肉");
        product2.setImgAddr("测试4");
        product2.setPriority(1);
        product2.setEnableStatus(1);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc1);
        Product product3 = new Product();
        product3.setProductName("1牛肉丸牛肉丸");
        product3.setProductDesc("1烤牛肉丸牛肉丸");
        product3.setImgAddr("测试5");
        product3.setPriority(1);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(pc1);
        int effectedNum1 = productDao.insertProduct(product1);
        Assert.assertEquals(1,effectedNum1);
        int effectedNum2 = productDao.insertProduct(product2);
        Assert.assertEquals(1,effectedNum2);
        int effectedNum3 = productDao.insertProduct(product3);
        Assert.assertEquals(1,effectedNum3);
    }

    @Test
    public void updateProduct() {
        Product product = new Product();
        ProductCategory pc = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(1L);
        pc.setProductCategoryId(18L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("蒜泥酱");
        product.setProductDesc("蒜剁成泥");
        product.setProductId(11L);
        product.setLastEditTime(new Date());
        //修改productId为11的商品以及商品类别并校验影响的行数是否为1
        int effectedNum = productDao.updateProduct(product);
        Assert.assertEquals(1,effectedNum);
    }

    @Test
    public void queryProductList() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(18L);
        product.setProductCategory(productCategory);
        List<Product> productList = productDao.queryProductList(product,0,2);
        productList.forEach(System.out::println);
    }

    @Test
    public void queryProductCount() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(12L);
        product.setProductCategory(productCategory);
        product.setProductName("鸡");
        int result = productDao.queryProductCount(product);
        System.out.println(result);
    }

    @Test
    public void updateProductCategoryToNull() {
        long productCategoryId = 28;
        int effected = productDao.updateProductCategoryToNull(productCategoryId);
        Assert.assertEquals(1,effected);
    }
}