package com.alexzheng.onlineshop.dao;


import com.alexzheng.onlineshop.entity.WechatAuth;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 17:53
 * @Annotation
 */
public interface WechatAuthDao {

    /**
     * 通过openId查询对应本平台的微信账号
     *
     * @param openId
     * @return
     */
    WechatAuth queryWechatInfoByOpenId(String openId);

    /**
     * 添加对应本平台的微信账号
     *
     * @param wechatAuth
     * @return
     */
    int insertWechatAuth(WechatAuth wechatAuth);

}
