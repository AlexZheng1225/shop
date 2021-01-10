package com.alexzheng.onlineshop.controller.frontend;

import com.alexzheng.onlineshop.dto.ShopExecution;
import com.alexzheng.onlineshop.entity.Area;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.entity.ShopCategory;
import com.alexzheng.onlineshop.service.AreaService;
import com.alexzheng.onlineshop.service.ShopCategoryService;
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
 * @Date 2020/5/31 15:31
 * @Annotation
 */
@RestController
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 返回商铺列表页里的shopcategory列表(二级列表或者一级列表[首页])，及区域信息列表
     *
     * @param request
     * @return
     */
    @GetMapping("/listshopspageinfo")
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //试着从前端请求中获取parentId
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //如果parentId存在，则取出该一级shopCategory下的二级shopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            //如果parentId不存在，则代表用户在访问首页，则取出所有一级ShopCateghory列表用于再首页展示
            //或从主页点击了“全部商店”进行登录
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }

        modelMap.put("shopCategoryList", shopCategoryList);

        //取出区域信息 用于展示选择
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }


    /**
     * 获取指定查询条件下的店铺列表
     *
     * @param request
     * @return
     */
    @GetMapping("/listshops")
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取一页需要显示的条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //非空判断
        if ((pageIndex > -1) && (pageSize > -1)) {
            //试着获取一级类别Id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //试着获取二级类别Id
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            //试着获取区域Id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            //试着获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            //获取组合之后的查询条件
            Shop shopCondition = compactShopCondition(parentId, shopCategoryId, areaId, shopName);
            //根据查询条件和分页信息获取店铺列表，并返回总数
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }

    private Shop compactShopCondition(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            //查询某个一级shopCategory下面的所有二级ShopCategory里面的店铺列表
            ShopCategory parentCategory = new ShopCategory();
            //子类别
            ShopCategory childCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }

        if(shopCategoryId!=-1L){
            //查询某个二级ShopCategory下面的所有店铺 列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }

        if(areaId!=-1L){
            //查询位于某个区域Id下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if(shopName!=null){
            //查询名字里包含shopName的店铺列表
            shopCondition.setShopName(shopName);
        }

        //前端展示的店铺必须是审核成功的店铺
        shopCondition.setEnableStatus(1);

        return shopCondition;

    }


}
