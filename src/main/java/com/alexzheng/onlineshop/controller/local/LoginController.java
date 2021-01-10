package com.alexzheng.onlineshop.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 18:13
 * @Annotation
 */
@Controller
@RequestMapping("/local")
public class LoginController {

    /**
     * 绑定账号页路由
     *
     * @return
     */
    @GetMapping(value = "/accountbind")
    private String accountBind(){
        return "local/accountbind";
    }

    /**
     * 修改账号路由页
     *
     * @return
     */
    @GetMapping("/changepsw")
    private String changepsw(){
        return "local/changepsw";
    }

    /**
     * 登录账号路由页
     *
     * @return
     */
    @GetMapping("/login")
    private  String login(){
        return "local/login";
    }

}
