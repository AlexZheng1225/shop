package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.cache.JedisUtil;
import com.alexzheng.onlineshop.dao.HeadLineDao;
import com.alexzheng.onlineshop.entity.HeadLine;
import com.alexzheng.onlineshop.exceptions.HeadLineOperationException;
import com.alexzheng.onlineshop.service.HeadLineService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date 2020/5/30 18:27
 * @Annotation
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private HeadLineDao headLineDao;

//    @Autowired
//    private JedisUtil.Keys jedisKeys;
//
//    @Autowired
//    private JedisUtil.Strings jedisSrings;



    @Override
    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        return headLineDao.queryHeadLine(headLineCondition);
//        //定义redis的key前缀
//        String key = HLLISTKEY;
//        //定义接收对象
//        List<HeadLine> headLineList = null;
//        //定义json数据转换操作类
//        ObjectMapper mapper = new ObjectMapper();
//        //拼接出redis的key
//        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
//            key = key+"_"+headLineCondition.getEnableStatus();
//        }
//        if (!jedisKeys.exists(key)) {
//            headLineList = headLineDao.queryHeadLine(headLineCondition);
//            String jsonString = null;
//            try {
//                jsonString = mapper.writeValueAsString(headLineList);
//            } catch (Exception e) {
//                logger.error("headLineList Redis写入错误");
//                e.printStackTrace();
//                throw new HeadLineOperationException(e.getMessage());
//            }
//            jedisSrings.set(key, jsonString);
//        } else {
//            String jsonString = jedisSrings.get(key);
//            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
//            try {
//                headLineList = mapper.readValue(jsonString, javaType);
//            } catch (IOException e) {
//                logger.error("areaList Redis String转对象 错误");
//                e.printStackTrace();
//                throw new HeadLineOperationException(e.getMessage());
//            }
//        }
//        return headLineList;
    }

}
