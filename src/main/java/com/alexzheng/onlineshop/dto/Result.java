package com.alexzheng.onlineshop.dto;

import lombok.Data;

/**
 * @Author Alex Zheng
 * @Date created in 17:14 2020/5/27
 * @Annotation 封装json对象，所有返回结果都使用它
 */
@Data
public class Result<T> {
    private boolean success;

    private T data;

    private String errorMsg;

    private int errorCode;

    public Result() {
    }

    //成功时的构造器
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    //错误时的构造器
    public Result(boolean success, String errorMsg, int errorCode) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }
}
