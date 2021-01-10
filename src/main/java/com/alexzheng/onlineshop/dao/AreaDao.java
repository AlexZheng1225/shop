package com.alexzheng.onlineshop.dao;


import com.alexzheng.onlineshop.entity.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 17:13 2020/4/6
 * @Annotation
 */
public interface AreaDao {

    /**
     * 列出区域列表
     * @return
     */
    List<Area> queryArea();

}
