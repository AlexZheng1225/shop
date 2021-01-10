package com.alexzheng.onlineshop.service;

import com.alexzheng.onlineshop.dto.LocalAuthExecution;
import com.alexzheng.onlineshop.entity.LocalAuth;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.enums.LocalAuthStateEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 15:33
 * @Annotation
 */
@SpringBootTest
public class LocalAuthServiceTest {

    @Autowired
    private LocalAuthService localAuthService;


    @Test
    public void bindLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(10L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUsername("AlexZheng");
        localAuth.setPassword("123123");
        LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
        Assert.assertEquals(LocalAuthStateEnum.SUCCESS.getState(), le.getState());
        localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());
        //打印用户名字
        System.out.println("username:" + localAuth.getPersonInfo().getName());
        System.out.println("password:" + localAuth.getPassword());
    }

    @Test
    public void modifyLocalAuth() {
        long userId = 10L;
        LocalAuthExecution le = localAuthService.modifyLocalAuth(userId,"AlexZheng","123123","123456");
        Assert.assertEquals(LocalAuthStateEnum.SUCCESS.getState(), le.getState());
        LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd("AlexZheng","123456");
        //打印名字和密码
        System.out.println("username:" + localAuth.getPersonInfo().getName());
        System.out.println("password:" + localAuth.getPassword());
    }
}