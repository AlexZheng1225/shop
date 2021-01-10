package com.alexzheng.onlineshop.exceptions;

/**
 * @Author Alex Zheng
 * @Date created in 17:04 2020/5/7
 * @Annotation
 */
public class ShopOperationException extends RuntimeException {

    public ShopOperationException(String msg) {
        super(msg);
    }
}
