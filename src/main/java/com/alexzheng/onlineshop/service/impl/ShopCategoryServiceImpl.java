package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.cache.JedisUtil;
import com.alexzheng.onlineshop.dao.ShopCategoryDao;
import com.alexzheng.onlineshop.entity.ShopCategory;
import com.alexzheng.onlineshop.exceptions.ShopCategoryOperationException;
import com.alexzheng.onlineshop.service.ShopCategoryService;
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
 * @Date created in 18:50 2020/4/9
 * @Annotation
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

//    @Autowired
//    private JedisUtil.Keys jedisKeys;
//
//    @Autowired
//    private JedisUtil.Strings jedisSrings;



    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
        //定义redis的key前缀
//        String key = SCLISTKEY;
//        //定义接收对象
//        List<ShopCategory> shopCategoryList = null;
//        //定义jackson数据转换操作类
//        ObjectMapper mapper = new ObjectMapper();
//        //拼接出redis的key
//        if(shopCategoryCondition==null){
//            //查询条件为空，即列出所有一级列表，即parentId为null的店铺类别
//            key = key+"_allfirstlevel";
//        }else if(shopCategoryCondition!=null && shopCategoryCondition.getParent()!=null
//                && shopCategoryCondition.getParent().getShopCategoryId()!=null){
//            //若parentId不为空，则列出该parentId下的所有子类别(二级列表)
//            key = key+"_parent"+shopCategoryCondition.getParent().getShopCategoryId();
//        }else{
//            //列出所有子类别，不管属于哪个类，都列出来
//            key = key + "_allsecondlevel";
//        }
//        //判断key是否存在
//        if(!jedisKeys.exists(key)){
//            //若不存在则从数据库中取出相应数据
//            shopCategoryList  =shopCategoryDao.queryShopCategory(shopCategoryCondition);
//            String jsonString = null;
//            try{
//                jsonString = mapper.writeValueAsString(shopCategoryList);
//
//            }catch (Exception e){
//                logger.error("shopCategoryCondition Redis写入错误");
//                e.printStackTrace();
//                throw new ShopCategoryOperationException(e.getMessage());
//            }
//            //设置进redis中
//            jedisSrings.set(key,jsonString);
//        }else{
//            //若key存在，则直接从redis中取出相应数据
//            String jsonString = jedisSrings.get(key);
//            //指定要将string转换成的集合类型
//            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,ShopCategory.class);
//            try {
//                //将相关key对应的value里的strintg转换成对象的实体类集合
//                shopCategoryList = mapper.readValue(jsonString,javaType);
//            } catch (IOException e) {
//                logger.error("shopCategoryList Redis String转对象 错误");
//                e.printStackTrace();
//                throw new ShopCategoryOperationException(e.getMessage());
//            }
//        }
//        return shopCategoryList;
    }
}
