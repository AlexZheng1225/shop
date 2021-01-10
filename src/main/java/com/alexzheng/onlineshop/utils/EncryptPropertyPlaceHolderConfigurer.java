package com.alexzheng.onlineshop.utils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @Author Alex Zheng
 * @Date 2020/6/10 13:52
 * @Annotation
 */
public class EncryptPropertyPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {

    /**
     * 需要加密的字段数组
     */
    private String[] encryptPropNames = {"jdbc.username","jdbc.password"};

    /**
     * 对关键属性进行转换
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if(isEncryptProp(propertyName)){
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        }else {
            return propertyValue;
        }
    }

    /**
     * 该属性是否已经加密
     *
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName){
        //若等于需要加密的的field， 则进行加密
        for(String encryptpropertyName:encryptPropNames){
            if(encryptpropertyName.equals(propertyName)){
                return true;
            }
        }
        return false;
    }

}
