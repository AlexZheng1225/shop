package com.alexzheng.onlineshop.controller.frontend;

import com.alexzheng.onlineshop.entity.HeadLine;
import com.alexzheng.onlineshop.entity.ShopCategory;
import com.alexzheng.onlineshop.service.HeadLineService;
import com.alexzheng.onlineshop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Alex Zheng
 * @Date 2020/5/30 18:30
 * @Annotation 首页相关控制层
 */
@RestController
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private HeadLineService headLineService;

    /**
     * 获取头条列表
     *
     * @return
     */
    @GetMapping("/listmainpageinfo")
    private Map<String,Object> listMainPageInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        //获取所有商铺一级类别列表
        try{
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }

        List<HeadLine> headLineList = new ArrayList<>();
        try{
            //获取状态为可用(1)的头条
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }

        modelMap.put("success",true);
        return modelMap;

    }

}
