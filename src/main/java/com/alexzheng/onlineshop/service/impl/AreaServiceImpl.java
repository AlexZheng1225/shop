package com.alexzheng.onlineshop.service.impl;


import com.alexzheng.onlineshop.cache.JedisUtil;
import com.alexzheng.onlineshop.dao.AreaDao;
import com.alexzheng.onlineshop.entity.Area;
import com.alexzheng.onlineshop.exceptions.AreaOperationExecption;
import com.alexzheng.onlineshop.service.AreaService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 17:42 2020/4/6
 * @Annotation
 */
@Service
public class AreaServiceImpl implements AreaService {

    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private AreaDao areaDao;

//    @Autowired
//    private JedisUtil.Keys jedisKeys;
//
//    @Autowired
//    private JedisUtil.Strings jedisSrings;


    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
//        String key = AREALISTKEY;
//        List<Area> areaList = null;
//        ObjectMapper mapper = new ObjectMapper();
//        if(!jedisKeys.exists(key)){
//            areaList  =areaDao.queryArea();
//            String jsonString = null;
//            try{
//                jsonString = mapper.writeValueAsString(areaList);
//            }catch (Exception e){
//                logger.error("areaList Redis写入错误");
//                e.printStackTrace();
//                throw new AreaOperationExecption(e.getMessage());
//            }
//            jedisSrings.set(key,jsonString);
//        }else{
//            String jsonString = jedisSrings.get(key);
//            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
//            try {
//                areaList = mapper.readValue(jsonString,javaType);
//            } catch (IOException e) {
//                logger.error("areaList Redis String转对象 错误");
//                e.printStackTrace();
//                throw new AreaOperationExecption(e.getMessage());
//            }
//        }
//        return areaList;
    }
}
