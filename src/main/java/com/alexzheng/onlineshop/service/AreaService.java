package com.alexzheng.onlineshop.service;


import com.alexzheng.onlineshop.entity.Area;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 17:41 2020/4/6
 * @Annotation
 */
public interface AreaService {

    public static final String AREALISTKEY = "arealist";

    List<Area> getAreaList();

}
