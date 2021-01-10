package com.alexzheng.onlineshop.dao;


import com.alexzheng.onlineshop.entity.PersonInfo;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 17:51
 * @Annotation
 */
public interface PersonInfoDao {

    /**
     * 通过用户Id查询用户
     *
     * @param userId
     * @return
     */
    PersonInfo quertPersonInfoById(long userId);



    /**
     * 添加用户信息
     *
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);

}
