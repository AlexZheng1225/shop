package com.alexzheng.onlineshop.service;


import com.alexzheng.onlineshop.dto.WechatAuthExecution;
import com.alexzheng.onlineshop.entity.WechatAuth;
import com.alexzheng.onlineshop.exceptions.WechatAuthOperationException;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 20:57
 * @Annotation
 */
public interface WechatAuthService {

    /**
     * 通过openId查找平台对应的微信账号
     *
     * @param openId
     * @return
     */
    WechatAuth getWeChatAuthByOpenId(String openId);


    /**
     * 注册本平台微信账号
     *
     * @param wechatAuth
     * @return
     * @throws WechatAuthOperationException
     */
    WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;

}
