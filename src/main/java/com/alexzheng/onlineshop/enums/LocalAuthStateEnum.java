package com.alexzheng.onlineshop.enums;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 14:24
 * @Annotation
 */
public enum LocalAuthStateEnum {
    LOGINFAIL(-1, "账号密码输入有误"),
    SUCCESS(0,"操作成功"),
    NULL_AUTH_INFO(-1001,"注册信息为空"),
    ONLY_ONE_ACCOUNT(-1002,"只能绑定一个账号");

    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    LocalAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static LocalAuthStateEnum stateOf(int state) {
        for (LocalAuthStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

}
