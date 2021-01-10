package com.alexzheng.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 11:18
 * @Annotation 用户授权token,根据文档中返回的json数据创建该实体类
 */
@Data
public class UserAccessToken {

    /**
     * 获取到的凭证
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒
     */
    @JsonProperty("expires_in")
    private String expiresIn;

    /**
     * 表示更新令牌，用来获取下一次的访问令牌，这里没太大用处
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 该用户在此公众号下的身份标识，对于此微信号具有唯一性
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * 表示权限范围，这里可省略
     */
    @JsonProperty("scope")
    private String scope;

}
