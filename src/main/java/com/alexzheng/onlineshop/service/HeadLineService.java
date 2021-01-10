package com.alexzheng.onlineshop.service;

import com.alexzheng.onlineshop.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date 2020/5/30 18:26
 * @Annotation
 */
public interface HeadLineService {


    public static final String HLLISTKEY = "headlinelist";


    /**
     * 根据传入的条件返回指定的头条列表
     *
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

}
