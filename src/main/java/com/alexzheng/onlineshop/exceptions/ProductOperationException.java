package com.alexzheng.onlineshop.exceptions;

/**
 * @Author Alex Zheng
 * @Date created in 16:19 2020/5/14
 * @Annotation
 */
public class ProductOperationException extends RuntimeException {
    public ProductOperationException(String message) {
        super(message);
    }
}
