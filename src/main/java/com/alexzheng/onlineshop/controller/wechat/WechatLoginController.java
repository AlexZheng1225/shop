package com.alexzheng.onlineshop.controller.wechat;

import com.alexzheng.onlineshop.dto.UserAccessToken;
import com.alexzheng.onlineshop.dto.WechatAuthExecution;
import com.alexzheng.onlineshop.dto.WechatUser;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.entity.WechatAuth;
import com.alexzheng.onlineshop.enums.WechatAuthStateEnum;
import com.alexzheng.onlineshop.service.PersonInfoService;
import com.alexzheng.onlineshop.service.WechatAuthService;
import com.alexzheng.onlineshop.utils.wechat.WechatUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Author Alex Zheng
 * @Date 2020/6/9 10:58
 * @Annotation 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器中访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx71b1e44aa47a43bd&redirect_uri=http://onlineshop.zczdy.top/onlineshop/wechatlogin/logincheck&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则将会获取到code，之后在通过code获取access_token 进而获取用户信息
 */
@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {

    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private static final String FRONTEND = "1";

    private static final String SHOPEND = "2";

    @Autowired
    private WechatAuthService wechatAuthService;

    @Autowired
    private PersonInfoService personInfoService;

    @GetMapping("/logincheck")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("-------------wechat login get...-------------");

        //获取微信公众号传输过来的code，通过code课获取access_token，进而获取用户信息
        String code = request.getParameter("code");
        //这个state可以用来传我们的自定义信息，方便程序调用
        String roleType = request.getParameter("state");

        logger.debug("wechat login code:" + code);

        WechatUser user = null;
        String openId = null;
        WechatAuth auth = null;

        if (code != null) {
            UserAccessToken token;
            try {
                //通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                logger.debug("weixin login token:" + token.toString());
                String accessToken = token.getAccessToken();
                //通过token获取openId
                openId =token.getOpenId();
                //通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken,openId);
                logger.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId",openId);
                auth = wechatAuthService.getWeChatAuthByOpenId(openId);
            } catch (IOException e) {
                logger.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }

        if(auth==null){
            PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
            auth = new WechatAuth();
            auth.setPersonInfo(personInfo);
            auth.setOpenId(openId);
            if(FRONTEND.equals(roleType)){
                personInfo.setUserType(1);
            }else{
                personInfo.setUserType(2);
            }
            auth.setCreateTime(new Date());
            WechatAuthExecution ae = wechatAuthService.register(auth);
            if(ae.getState()!= WechatAuthStateEnum.SUCCESS.getState()){
                return null;
            }else{
                personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
                //这里将个人信息设置进session中，方便后期根据PersonInfo中得userId查询及创建LocalAuth
                request.getSession().setAttribute("user",personInfo);
            }
        }

        //若用户点击前端展示系统按钮则进入前端展示系统
        if(FRONTEND.equals(roleType)){
            return "frontend/index";
        }else{
            //否则进入后台
            return "shopadmin/shoplist";
        }
    }

}
