package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.LocalAuth;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.utils.DESUtil;
import com.alexzheng.onlineshop.utils.MD5;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 12:04
 * @Annotation
 */
@SpringBootTest
public class LocalAuthDaoTest{

    @Autowired
    private LocalAuthDao localAuthDao;

    private static final String username = "Alex";
    private static final String password = "123456";

    @Test
    public void queryLocalByUserNameAndPwd() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, MD5.getMd5(password));
         Assert.assertEquals("郑小城",localAuth.getPersonInfo().getName());
    }

    @Test
    public void queryLocalByUserId() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        Assert.assertEquals("郑小城",localAuth.getPersonInfo().getName());
    }

    @Test
    public void insertLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(10L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUsername("AAAAAAA");
        localAuth.setPassword("123123123");
        localAuth.setCreateTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        Assert.assertEquals(1,effectedNum);
    }

    @Test
    public void updateLocalAuth() {
        int effectedNum = localAuthDao.updateLocalAuth(10L,"AAAAAAA","123123123",password+"new",new Date());
        Assert.assertEquals(1,effectedNum);
        //查询该条平台账号最新信息
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(10L);
        //输出新密码
        System.out.println(localAuth.getPassword());
    }
}