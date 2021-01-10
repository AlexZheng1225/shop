package com.alexzheng.onlineshop.dto;

import com.alexzheng.onlineshop.entity.WechatAuth;
import com.alexzheng.onlineshop.enums.WechatAuthStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 21:00
 * @Annotation
 */
@Data
public class WechatAuthExecution {

    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    private int count;

    private WechatAuth wechatAuth;

    private List<WechatAuth> wechatAuthList;

    public WechatAuthExecution() {
    }

    //操作失败的构造器
    public WechatAuthExecution(WechatAuthStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //操作成功的构造器-一个
    public WechatAuthExecution(WechatAuthStateEnum stateEnum,WechatAuth wechatAuth) {
        this.wechatAuth = wechatAuth;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //操作成功的构造器-多个
    public WechatAuthExecution(WechatAuthStateEnum stateEnum,List<WechatAuth> wechatAuthList) {
        this.wechatAuthList = wechatAuthList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

}
