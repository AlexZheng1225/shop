package com.alexzheng.onlineshop.exceptions;

/**
 * @Author Alex Zheng
 * @Date created in 15:10 2020/4/28
 * @Annotation
 */
public class ProductCategoryOperationExecption extends RuntimeException {

    public ProductCategoryOperationExecption(String msg) {
        super(msg);
    }
}
