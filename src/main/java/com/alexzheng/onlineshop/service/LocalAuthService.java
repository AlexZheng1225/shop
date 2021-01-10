package com.alexzheng.onlineshop.service;


import com.alexzheng.onlineshop.dto.LocalAuthExecution;
import com.alexzheng.onlineshop.entity.LocalAuth;
import com.alexzheng.onlineshop.exceptions.LocalAuthOperationExecption;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 13:02
 * @Annotation
 */
public interface LocalAuthService {

    /**
     * 通过账号密码获取平台信息
     *
     * @param username
     * @param password
     * @return
     */
    LocalAuth getLocalAuthByUsernameAndPwd(String username, String password);

    /**
     * 通过userId获取平台账号信息
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * 修改平台账号得登陆密码
     *
     * @param localAuth
     * @return
     * @throws LocalAuthOperationExecption
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationExecption;

    /**
     * 修改平台登陆账号密码
     *
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @return
     * @throws LocalAuthOperationExecption
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword) throws LocalAuthOperationExecption;


}
