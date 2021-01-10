package com.alexzheng.onlineshop.exceptions;

/**
 * @Author Alex Zheng
 * @Date 2020/6/10 16:10
 * @Annotation
 */
public class LocalAuthOperationExecption extends RuntimeException {
    public LocalAuthOperationExecption(String message) {
        super(message);
    }
}
