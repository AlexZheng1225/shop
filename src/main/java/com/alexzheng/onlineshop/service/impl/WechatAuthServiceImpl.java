package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.dao.PersonInfoDao;
import com.alexzheng.onlineshop.dao.WechatAuthDao;
import com.alexzheng.onlineshop.dto.WechatAuthExecution;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.entity.WechatAuth;
import com.alexzheng.onlineshop.enums.WechatAuthStateEnum;
import com.alexzheng.onlineshop.exceptions.WechatAuthOperationException;
import com.alexzheng.onlineshop.service.WechatAuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 21:20
 * @Annotation
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService {

    /**
     * log4j日志
     *
     * */
    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Autowired
    private PersonInfoDao personInfoDao;


    @Override
    public WechatAuth getWeChatAuthByOpenId(String openId) {
        return wechatAuthDao.queryWechatInfoByOpenId(openId);
    }

    @Override
    public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
        if(wechatAuth==null&&wechatAuth.getOpenId()==null){
            return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
        }

        try{
            wechatAuth.setCreateTime(new Date());
            //如果微信账号中夹带用户信息且userId为空，则认为用户首次使用本平台且通过微信登录
            //则自动创建用户信息
            if(wechatAuth.getPersonInfo()!=null && wechatAuth.getPersonInfo().getUserId()==null){
                try{
                    wechatAuth.getPersonInfo().setCreateTime(new Date());
                    wechatAuth.getPersonInfo().setEnableStatus(1);
                    PersonInfo personInfo = wechatAuth.getPersonInfo();
                    //下面行语句执行会将数据库中的userId赋值回personInfo对象中
                    int effectedNum = personInfoDao.insertPersonInfo(personInfo);
                    wechatAuth.setPersonInfo(personInfo);
                    if(effectedNum <=0){
                        throw new WechatAuthOperationException("用户信息添加失败");
                    }
                }catch (Exception e){
                    logger.error("insertPersonInfo error:"+e.toString());
                    throw new WechatAuthOperationException("insertPersonInfo error:"+e.getMessage());
                }
            }

            //创建专属本平台的微信账号
            int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
            if(effectedNum<=0){
                throw new WechatAuthOperationException("账号创建失败");
            }else {
                return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,wechatAuth);
            }
        }catch (Exception e){
            logger.error("insertWechatAuth error:"+e.toString());
            throw new WechatAuthOperationException("insertWechatAuth error:"+e.getMessage());
        }
    }
}
