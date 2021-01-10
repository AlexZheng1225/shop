package com.alexzheng.onlineshop.dao;


import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.entity.WechatAuth;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 20:43
 * @Annotation
 */
@SpringBootTest
public class WechatAuthDaoTest{

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Test
    public void queryWechatInfoByOpenId() {
        String openId = "shdgshedfsdgfjksdgfjk";
        WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId(openId);
        System.out.println(wechatAuth);
    }

    @Test
    public void insertWechatAuth() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(13L);
        WechatAuth wechatAuth = new WechatAuth();
        //给微信账号绑定上用户信息
        wechatAuth.setPersonInfo(personInfo);
        //随意设置上openId
        wechatAuth.setOpenId("sadasdasdadasdasdasd");
        wechatAuth.setCreateTime(new Date());
        int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
        Assert.assertEquals(1,effectedNum);
    }
}