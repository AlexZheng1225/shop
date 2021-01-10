package com.alexzheng.onlineshop.dto;

import com.alexzheng.onlineshop.entity.LocalAuth;
import com.alexzheng.onlineshop.enums.LocalAuthStateEnum;
import com.alexzheng.onlineshop.enums.LocalAuthStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 14:16
 * @Annotation
 */
@Data
public class LocalAuthExecution {

    private int state;

    private String stateInfo;

    private int count;

    private LocalAuth localAuth;

    private List<LocalAuth> localAuthList;

    /**
     * 失败的构造器
     */
    public LocalAuthExecution(LocalAuthStateEnum localAuthStateEnum) {
    }

    /**
     * 成功的构造器 单个
     */
    public LocalAuthExecution(LocalAuthStateEnum localAuthStateEnum, LocalAuth localAuth) {
    }


    /**
     * 成功的构造器 多个
     */
    public LocalAuthExecution(LocalAuthStateEnum localAuthStateEnum, List<LocalAuth> localAuthList) {
    }


}
