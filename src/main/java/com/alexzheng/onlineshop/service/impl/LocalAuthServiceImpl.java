package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.dao.LocalAuthDao;
import com.alexzheng.onlineshop.dto.LocalAuthExecution;
import com.alexzheng.onlineshop.entity.LocalAuth;
import com.alexzheng.onlineshop.enums.LocalAuthStateEnum;
import com.alexzheng.onlineshop.exceptions.LocalAuthOperationExecption;
import com.alexzheng.onlineshop.service.LocalAuthService;
import com.alexzheng.onlineshop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 14:35
 * @Annotation
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
        //使用MD5对密码进行加密
        return localAuthDao.queryLocalByUserNameAndPwd(username, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationExecption {
        //非空判断，传入的LocalAuth账号密码，用户信息以及用户信息中的userId保证不能为空
        if (localAuth == null || localAuth.getPassword() == null ||
                localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        //查询是否已经绑定账号
        LocalAuth tempLocalAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
        if (tempLocalAuth != null) {
            //如果绑定则直接返回消息并退出，保证账号一致性
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        //若之前没有绑定账号，则开始绑定
        try {
            //对传入密码进行加密
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            if (effectedNum <= 0) {
                throw new LocalAuthOperationExecption("本地账号绑定失败");
            } else {
                tempLocalAuth = localAuthDao.queryLocalByUserNameAndPwd(localAuth.getUsername(),localAuth.getPassword());
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, tempLocalAuth);
            }
        } catch (Exception e) {
            throw new LocalAuthOperationExecption("insertLocalAuth error:" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword) throws LocalAuthOperationExecption {
        //非空判断，以及判断新旧密码是否相同
        if (userId != null && username != null && password != null && newPassword != null && !password.equals(newPassword)) {
            try{
                //密码要加密
                int effectedNum = localAuthDao.updateLocalAuth(userId,username,MD5.getMd5(password), MD5.getMd5(newPassword),new Date());
                if(effectedNum<=0){
                    throw new LocalAuthOperationExecption("更新账号信息失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            }catch (Exception e){
                throw new LocalAuthOperationExecption("modifyLocalAuth error:"+e.toString());
            }
        }else{
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
