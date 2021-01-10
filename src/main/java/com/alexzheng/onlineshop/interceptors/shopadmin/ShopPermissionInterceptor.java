package com.alexzheng.onlineshop.interceptors.shopadmin;

import com.alexzheng.onlineshop.entity.Shop;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date 2020/6/12 0:21
 * @Annotation 判断用户是否有相关权限进行操作
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

    /**
     * 主要做事前拦截，用户操作发生前，改写preHandle里的逻辑，进行用户操作权限的拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中获取当前店铺
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //从session中获取可操作的店铺列表
        @SuppressWarnings("unchecked")
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
        for (Shop shop : shopList) {
            //若当前店铺在可操作列表中则返回true
            if (shop.getShopId().equals(currentShop.getShopId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
