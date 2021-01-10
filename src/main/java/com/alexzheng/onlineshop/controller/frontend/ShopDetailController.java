package com.alexzheng.onlineshop.controller.frontend;

import com.alexzheng.onlineshop.dto.ProductExecution;
import com.alexzheng.onlineshop.entity.Product;
import com.alexzheng.onlineshop.entity.ProductCategory;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.service.ProductCategoryService;
import com.alexzheng.onlineshop.service.ProductService;
import com.alexzheng.onlineshop.service.ShopService;
import com.alexzheng.onlineshop.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Alex Zheng
 * @Date 2020/6/2 17:20
 * @Annotation
 */
@RestController
@RequestMapping("/frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺下面的商品类别列表
     *
     * @param request
     * @return
     */
    @GetMapping("/listshopdetailpageinfo")
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取从前台传过来的shopId
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1L) {
            //获取店铺信息
            shop = shopService.getShopById(shopId);
            //获取店铺下面的商品类别泪飙
            productCategoryList = productCategoryService.getProductCategory(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     *
     * 依据查询条件分页列出该店铺下面的所有商品
     *
     * @param request
     * @return
     */
    @GetMapping("/listproductsbyshop")
    private Map<String, Object> listProductByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取一页需要显示的页数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //获取店铺Id
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //空值判断
        if ((pageIndex > -1) && (pageSize > -1)) {
            //尝试获取商品类别Id
            long productCategoryId  =HttpServletRequestUtil.getLong(request,"productCategoryId");
            //尝试获取模糊查找的商品名
            String productName = HttpServletRequestUtil.getString(request,"productName");
            //组合条件查询
            Product productCondition = compactProductCondition(shopId,productName,productCategoryId);
            ProductExecution pe = productService.getProductList(productCondition,pageIndex,pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count",pe.getCount());
            modelMap.put("success",true);
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageIndex or pageSize");
        }
        return modelMap;
    }

    /**
     * 组合查询条件封装到productCondition对象中返回
     *
     * @param shopId
     * @param productName
     * @param productCategoryId
     * @return
     */
    private Product compactProductCondition(long shopId, String productName, long productCategoryId) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);

        if(productCategoryId!=-1L){
            //查询某个商品类别下的商品列表
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }

        if(productName!=null){
            //查询名字里包含productName的商品列表
            productCondition.setProductName(productName);
        }

        //只选出状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }

}
