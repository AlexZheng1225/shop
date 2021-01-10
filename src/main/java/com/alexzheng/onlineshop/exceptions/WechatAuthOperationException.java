package com.alexzheng.onlineshop.exceptions;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 21:19
 * @Annotation
 */
public class WechatAuthOperationException extends RuntimeException {
    public WechatAuthOperationException(String msg) {
        super(msg);
    }
}
