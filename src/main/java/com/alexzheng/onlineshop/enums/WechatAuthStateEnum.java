package com.alexzheng.onlineshop.enums;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 21:03
 * @Annotation
 */
public enum WechatAuthStateEnum {
    LOGINFALL(-1, "openId输入有误"),

    SUCCESS(0, "操作成功"),

    NULL_AUTH_INFO(-1001, "注册信息为空");

    private int state;

    private String stateInfo;

    WechatAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static WechatAuthStateEnum stateOf(int index) {
        for (WechatAuthStateEnum stateEnum : values()) {
            if (stateEnum.getState()==index) {
                return stateEnum;
            }
        }
        return null;
    }

}
