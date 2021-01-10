package com.alexzheng.onlineshop.interceptors.shopadmin;

import com.alexzheng.onlineshop.entity.PersonInfo;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author Alex Zheng
 * @Date 2020/6/12 0:06
 * @Annotation 判断用户是否已经登录系统
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 方法执行前做必要的拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从Session中取出用户信息
        Object object = request.getSession().getAttribute("user");
        if (object != null) {
            //若用户信息不为空，则将session里的永辉逆袭转换成PersonInfo实体类对象
            PersonInfo user = (PersonInfo) object;
            //非空判断，确保userId不为空且该账号的可用状态为1，并且用户类型为店家
            if (user != null & user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1) {
                return true;
            }
        }
        //若不满足登录验证，则直接跳转到账号登陆界面
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open ('" + request.getContextPath() + "/local/login?usertype=2','_self')");
        out.println("</script>");
        out.println("</html>");
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
