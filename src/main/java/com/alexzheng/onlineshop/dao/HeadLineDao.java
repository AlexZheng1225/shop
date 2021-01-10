package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date 2020/5/30 16:46
 * @Annotation
 */
public interface HeadLineDao {

    /**
     * 根据传入的查询条件(头条名查询头条)
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
