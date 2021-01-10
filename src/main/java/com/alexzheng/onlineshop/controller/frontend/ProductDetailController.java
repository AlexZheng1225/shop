package com.alexzheng.onlineshop.controller.frontend;

import com.alexzheng.onlineshop.entity.Product;
import com.alexzheng.onlineshop.service.ProductService;
import com.alexzheng.onlineshop.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Alex Zheng
 * @Date 2020/6/5 15:51
 * @Annotation
 */
@RestController
@RequestMapping("/frontend")
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    @GetMapping("/listproductdetail")
    private Map<String,Object> listProductDetail(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //尝试获取parentId
        long productId = HttpServletRequestUtil.getLong(request,"productId");
        if(productId!=-1L){
            //根据productId获取商品信息，包含商品详情图列表(因为left join productImg)
            Product product = productService.getProductById(productId);
            modelMap.put("product",product);
            modelMap.put("success", true);
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }
}
