package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.Area;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;



/**
 * @Author Alex Zheng
 * @Date created in 17:17 2020/4/6
 * @Annotation
 */
@SpringBootTest
public class AreaDaoTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void queryArea() throws Exception {
        List<Area> areaList = areaDao.queryArea();
        System.out.println(areaList.toString());
        Assert.assertEquals(2,areaList.size());
    }

}