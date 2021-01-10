package com.alexzheng.onlineshop.config.controller;

import com.alexzheng.onlineshop.interceptors.shopadmin.ShopLoginInterceptor;
import com.alexzheng.onlineshop.interceptors.shopadmin.ShopPermissionInterceptor;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @Author Alex Zheng
 * @Date 2020/6/15 11:36
 * @Annotation 配置spring-web相关
 * 开启mvc，自动注入spring容器
 * 实现WebMvcConfigurer接口，配置视图解析器
 * (当一个类)实现接口ApplicationContextAware后，这个类就可以方便的获得ApplicationContext中所有的bean
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    //创建Spring容器
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //图片路径解析
        //根据操作系统变化而变化（E:/all/pictest/images/upload/ | /home/alexzheng/images/upload/）
        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/all/pictest/images/upload/");
    }

    /**
     * 定义默认的请求处理器
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 创建ViewResolver
     *
     * @return
     */
    @Bean(value = "viewResolver")
    public ViewResolver createViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        //设置Spring容器
        viewResolver.setApplicationContext(this.applicationContext);
        //取消缓存
        viewResolver.setCache(false);
        //设置视图解析前缀
        viewResolver.setPrefix("/WEB-INF/html/");
        //设置视图解析后缀
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    /**
     * 配置文件上传解析器
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        //1024*1024*20
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(20971520);
        return multipartResolver;
    }


    @Value("${kaptcha.border}")
    private String border;

    @Value("${kaptcha.textproducer.font.color}")
    private String fcolor;

    @Value("${kaptcha.image.width}")
    private String width;

    @Value("${kaptcha.textproducer.char.string}")
    private String cString;

    @Value("${kaptcha.image.height}")
    private String height;

    @Value("${kaptcha.textproducer.font.size}")
    private String fsize;

    @Value("${kaptcha.noise.color}")
    private String nColor;

    @Value("${kaptcha.textproducer.char.length}")
    private String clength;

    @Value("${kaptcha.textproducer.font.names}")
    private String fnames;

    /**
     * Kaptcha验证码相关配置
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(),"/Kaptcha");
        // 无边框
        servlet.addInitParameter("kaptcha.border",border);
        // 字体颜色
        servlet.addInitParameter("kaptcha.textproducer.font.color", fcolor);
        // 图片宽度
        servlet.addInitParameter("kaptcha.image.width", width);
        // 使用哪些字符生成验证码
        servlet.addInitParameter("kaptcha.textproducer.char.string", cString);
        // 图片高度
        servlet.addInitParameter("kaptcha.image.height", height);
        // 字体大小
        servlet.addInitParameter("kaptcha.textproducer.font.size", fsize);
        // 干扰线的颜色
        servlet.addInitParameter("kaptcha.noise.color", nColor);
        // 字符个数
        servlet.addInitParameter("kaptcha.textproducer.char.length", clength);
        // 字体
        servlet.addInitParameter("kaptcha.textproducer.font.names", fnames);
        return servlet;
    }

    /**
     * 添加拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String interceptPath = "/shopadmin/**";
        //注册拦截器
        InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
        //配置拦截器的路径
        loginIR.addPathPatterns(interceptPath);
        //注册其他拦截器
        InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
        permissionIR.addPathPatterns(interceptPath);
        //配置不拦截路径
        //shoplist page
        permissionIR.excludePathPatterns("/shopadmin/shoplist");
        permissionIR.excludePathPatterns("/shop/getshoplist");
        permissionIR.excludePathPatterns("/shop/getshopbyid");
        //shopregister page
        permissionIR.excludePathPatterns("/shop/getshoexcludePathPatternspinitinfo");
        permissionIR.excludePathPatterns("/shop/registershop");
        permissionIR.excludePathPatterns("/shopadmin/shopoperation");
        //shopmanage page
        permissionIR.excludePathPatterns("/shopadmin/shopmanage");
        permissionIR.excludePathPatterns("/shop/getshopmanagementinfo");
    }
}
