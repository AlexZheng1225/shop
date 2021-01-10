package com.alexzheng.onlineshop.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author Alex Zheng
 * @Date created in 13:57 2020/4/9
 * @Annotation 主要用来解析路由并转发到相应的 html中
 */
@Controller
@RequestMapping(value = "shopadmin",method = RequestMethod.GET)
public class ShopAdminController {

    @RequestMapping("/shopoperation")
    public String shopOperation(){
    //转发到店铺操作页面
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(){
        //转发到店铺列表页面
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanage")
    public String shopManagement(){
        //转发到店铺管理页面
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement",method = RequestMethod.GET)
    public String productCategoryManagement(){
        //转发到商品类别管理页面
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation(){
        //转发到商品添加/编辑页面
        return "shop/productoperation";
    }

    @RequestMapping(value = "productmanagement")
    public String productManagement(){
        //转发到商品管理页面
        return "shop/productmanagement";
    }

}
