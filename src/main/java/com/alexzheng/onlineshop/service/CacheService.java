package com.alexzheng.onlineshop.service;

/**
 * @Author Alex Zheng
 * @Date 2020/6/10 23:11
 * @Annotation 删除k-v
 */
public interface CacheService {

    /**
     * 依据key前缀删除匹配该模式下所有的k-v
     * 如：传入shopcategory，则shopcategory_allfirstlevel等以shopcategory开头的k-v都会被清空
     *
     * @param keyPrefix
     */
//    void removeFormCache(String keyPrefix);

}
