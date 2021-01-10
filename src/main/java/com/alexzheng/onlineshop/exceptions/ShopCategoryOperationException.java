package com.alexzheng.onlineshop.exceptions;

/**
 * @Author Alex Zheng
 * @Date 2020/6/10 22:28
 * @Annotation
 */
public class ShopCategoryOperationException extends RuntimeException {
    public ShopCategoryOperationException(String message) {
        super(message);
    }
}
