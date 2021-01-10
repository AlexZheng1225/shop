package com.alexzheng.onlineshop.controller.shopadmin;

import com.alexzheng.onlineshop.dto.ImageFileHolder;
import com.alexzheng.onlineshop.dto.ShopExecution;
import com.alexzheng.onlineshop.entity.Area;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.entity.ShopCategory;
import com.alexzheng.onlineshop.enums.ShopStateEnum;
import com.alexzheng.onlineshop.service.AreaService;
import com.alexzheng.onlineshop.service.ShopCategoryService;
import com.alexzheng.onlineshop.service.ShopService;
import com.alexzheng.onlineshop.utils.CodeUtil;
import com.alexzheng.onlineshop.utils.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Alex Zheng
 * @Date created in 18:17 2020/4/7
 * @Annotation
 */
@RestController
@RequestMapping("/shop")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    /**
     * 此方法在session中设置了currentShop
     *
     * @param request
     * @return
     */
    @GetMapping("/getshopmanagementinfo")
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //首先需要获取店铺Id
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            //此处生成currentShop并存储到session中
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/onlineshop/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }

    /**
     * 获取商铺列表
     *
     * @param request
     * @return
     */
    @GetMapping("/getshoplist")
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //TODO session数据需要修改
        //------------------
//        PersonInfo user = new PersonInfo();
//        user.setUserId(1L);
//        user.setName("郑小城");
//        request.getSession().setAttribute("user", user);
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        //------------------
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", se.getShopList());
            //列出店铺成功后，将店铺放入session中作为拦截器 权限验证依据
            //即该账号只能操作自己的店铺
            request.getSession().setAttribute("shopList",se.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 修改商铺信息
     *
     * @param request
     * @return
     */
    @PostMapping("/modifyshop")
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        //1.接受并转化相应的参数，包括店铺信息以及图片信息
        Map<String, Object> modelMap = new HashMap<>();

        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("eeeMsg", "验证码输入错误，请重新输入");
            return modelMap;
        }

        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }


        //获取前端文件流
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //获取前端传过来的图片
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不得为空");
            return modelMap;
        }

        //2.修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se;
            if (shopImg == null) {
                se = shopService.modifyShop(shop, null);
            } else {
                ImageFileHolder imageFileHolder = null;
                try {
                    imageFileHolder = new ImageFileHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                } catch (IOException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "ImageFileHolder图片转换失败");
                    e.printStackTrace();
                }
                se = shopService.modifyShop(shop, imageFileHolder);
            }

            if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺Id");
            return modelMap;
        }
    }


    /**
     * 通过shopId获取商铺信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getshopbyid")
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getShopById(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "获取商铺信息错误--" + e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @GetMapping("/getshopinitinfo")
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("success", true);
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 添加商铺
     *
     * @param request
     * @return
     */
    @PostMapping("/registershop")
    private Map<String, Object> registerShop(HttpServletRequest request) {
        //1.接受并转化相应的参数，包括店铺信息以及图片信息
        Map<String, Object> modelMap = new HashMap<>();

        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("eeeMsg", "验证码输入错误，请重新输入");
            return modelMap;
        }

        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //获取前端文件流
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //获取前端传过来的图片
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不得为空");
            return modelMap;
        }

        //2.注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ImageFileHolder imageFileHolder = null;
            try {
                imageFileHolder = new ImageFileHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "ImageFileHolder图片转换失败");
                e.printStackTrace();
            }

            ShopExecution se = shopService.addShop(shop, imageFileHolder);
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
                //该用户可以操作的店铺列表 存储在Session中
                @SuppressWarnings("unchecked")
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if (shopList == null || shopList.size() == 0) {
                    shopList = new ArrayList<>();
                }
                shopList.add(se.getShop());
                request.getSession().setAttribute("shopList", shopList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺相关信息");
            return modelMap;
        }
    }


}
