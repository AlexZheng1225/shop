package com.alexzheng.onlineshop.service;


import com.alexzheng.onlineshop.entity.PersonInfo;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 22:17
 * @Annotation
 */
public interface PersonInfoService {

    /**
     * 根据用户Id获取personInfo信息
     *
     * @param userId
     * @return
     */
    PersonInfo getPersonInfoById(Long userId);

}
