package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.dao.PersonInfoDao;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 22:19
 * @Annotation
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;


    @Override
    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.quertPersonInfoById(userId);
    }
}
