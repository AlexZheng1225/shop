package com.alexzheng.onlineshop.utils;


import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Alex Zheng
 * @Date created in 14:00 2020/4/17
 * @Annotation 验证码工具类
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){

        //从图片中获取的验证码
        String verifyCodeExpected = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        //实际输入验证码
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");

        //对比
        if(verifyCodeActual==null||!verifyCodeActual.equals(verifyCodeExpected)){
            return false;
        }
        return true;

    }
}
