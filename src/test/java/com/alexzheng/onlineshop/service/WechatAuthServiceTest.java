package com.alexzheng.onlineshop.service;

import com.alexzheng.onlineshop.dto.WechatAuthExecution;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.entity.WechatAuth;
import com.alexzheng.onlineshop.enums.WechatAuthStateEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 21:45
 * @Annotation
 */
@SpringBootTest
public class WechatAuthServiceTest{

    @Autowired
    private WechatAuthService wechatAuthService;

    @Test
    public void register() {
        WechatAuth wechatAuth = new WechatAuth();
        PersonInfo personInfo = new PersonInfo();
        String openId = "dsagjkgdajkgdkgdw";
        //给微信账号设置用户信息但不设置用户Id
        //目标：创建微信账号的时候自动创建用户信息
        personInfo.setCreateTime(new Date());
        personInfo.setName("Test Name");
        personInfo.setUserType(1);
        wechatAuth.setOpenId(openId);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setCreateTime(new Date());
        WechatAuthExecution we = wechatAuthService.register(wechatAuth);
        Assert.assertEquals(WechatAuthStateEnum.SUCCESS.getState(),we.getState());
        wechatAuth = wechatAuthService.getWeChatAuthByOpenId(openId);
        System.out.println(wechatAuth.getPersonInfo().getName());
    }
}