package com.alexzheng.onlineshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Alex Zheng
 * @Date 2020/6/13 17:07
 * @Annotation
 */
@RestController
public class Hello {

    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot ! Alex Zheng";
    }

}
