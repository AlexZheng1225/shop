package com.alexzheng.onlineshop.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Alex Zheng
 * @Date 2020/5/30 21:49
 * @Annotation
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {

    /**
     * 商城首页路由
     *
     * @return
     */
    @GetMapping("/index")
    private String index(){
        return "frontend/index";
    }

    /**
     * 商铺列表页路由
     *
     * @return
     */
    @GetMapping("/shoplist")
    private String shopList(){
        return "frontend/shoplist";
    }

    /**
     * 商铺详情页路由
     *
     * @return
     */
    @GetMapping("/shopdetail")
    private String shopDetail(){
        return "frontend/shopdetail";
    }

    /**
     * 商品详情页路由
     *
     * @return
     */
    @GetMapping("/productdetail")
    private String productDetail(){
        return "frontend/productdetail";
    }

}
