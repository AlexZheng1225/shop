package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.PersonInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 20:37
 * @Annotation
 */
@SpringBootTest
public class PersonInfoDaoTest{

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void quertPersonInfoById() {
        long userId = 1L;
        PersonInfo personInfo = personInfoDao.quertPersonInfoById(userId);
        System.out.println(personInfo.toString());
    }

    @Test
    public void insertPersonInfo() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("Alex");
        personInfo.setGender("女");
        personInfo.setUserType(1);
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setEnableStatus(1);
        int effectedNum = personInfoDao.insertPersonInfo(personInfo);
        Assert.assertEquals(1,effectedNum);
    }
}