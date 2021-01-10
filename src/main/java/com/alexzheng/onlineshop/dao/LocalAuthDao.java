package com.alexzheng.onlineshop.dao;

import com.alexzheng.onlineshop.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 0:00
 * @Annotation
 */
public interface LocalAuthDao {

    /**
     * 通过账号和密码查询对应信息，登录的时候使用
     *
     * @param username
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);

    /**
     * 通过userId查询对应的localAuth
     *
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(@Param("userId") long userId);

    /**
     * 添加平台账号
     *
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);


    /**
     * 通过userId，username password更改密码
     *
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @param lastEditTime
     * @return
     */
    int updateLocalAuth(@Param("userId") long userId, @Param("username") String username, @Param("password") String password,
                        @Param("newPassword") String newPassword, @Param("lastEditTime") Date lastEditTime);

}
