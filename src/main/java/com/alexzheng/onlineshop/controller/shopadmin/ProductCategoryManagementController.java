package com.alexzheng.onlineshop.controller.shopadmin;

import com.alexzheng.onlineshop.dto.ProductCategoryExecution;
import com.alexzheng.onlineshop.dto.Result;
import com.alexzheng.onlineshop.entity.ProductCategory;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.enums.ProductCategoryStateEnum;
import com.alexzheng.onlineshop.exceptions.ProductCategoryOperationExecption;
import com.alexzheng.onlineshop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Alex Zheng
 * @Date created in 17:10 2020/4/27
 * @Annotation
 */
@RestController
@RequestMapping("/shop")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 移除商品分类
     *
     * @param productCategoryId
     * @param request
     * @return
     */
    @PostMapping("/removeproductcategory")
    private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId == null || productCategoryId <= 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个商品类别");
        }

        try {
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            ProductCategoryExecution pce = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
            if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pce.getStateInfo());
            }
        } catch (ProductCategoryOperationExecption e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 添加商品分类
     *
     * @param productCategoryList
     * @param request
     * @return
     */
    @PostMapping("/addproductcategorys")
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                    HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
            pc.setCreateTime(new Date());
        }
        //非空判断
        if (productCategoryList == null || productCategoryList.size() <= 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商铺类别");
        }
        try {
            ProductCategoryExecution pce = productCategoryService.batchAddProductCategory(productCategoryList);
            if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pce.getStateInfo());
            }
        } catch (ProductCategoryOperationExecption e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 获取所有商品分类信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getproductcategorylist")
    public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        //在ShopManagementController中的getShopManagementInfo()方法中已经通过Session设置了currentShop
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategory(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, ps.getStateInfo(), ps.getState());
        }
    }

}
