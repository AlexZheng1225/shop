package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.cache.JedisUtil;
import com.alexzheng.onlineshop.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author Alex Zheng
 * @Date 2020/6/10 23:14
 * @Annotation
 */
@Service
public class CacheServiceImpl implements CacheService {

//    @Autowired
//    private JedisUtil.Keys jedisKeys;
//
//
//    @Override
//    public void removeFormCache(String keyPrefix) {
//        Set<String> keySet = jedisKeys.keys(keyPrefix+"*");
//        for (String key:keySet){
//            jedisKeys.del(key);
//        }
//    }
}
