package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.HeadLine;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date 2020/5/30 17:04
 * @Annotation
 */
@SpringBootTest
public class HeadLineDaoTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void queryHeadLine() {
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        Assert.assertEquals(4,headLineList.size());
    }
}