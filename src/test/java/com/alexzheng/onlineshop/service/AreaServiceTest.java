package com.alexzheng.onlineshop.service;

import com.alexzheng.onlineshop.entity.Area;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 17:45 2020/4/6
 * @Annotation
 */
@SpringBootTest
public class AreaServiceTest{

    @Autowired
    private AreaService areaService;

    @Autowired
    private CacheService cacheService;

    @Test
    public void getAreaList() throws Exception {
        List<Area> areaList = areaService.getAreaList();
        Assert.assertEquals("北苑",areaList.get(0).getAreaName());
//        cacheService.removeFormCache(AreaService.AREALISTKEY);
//        areaList = areaService.getAreaList();
    }

}