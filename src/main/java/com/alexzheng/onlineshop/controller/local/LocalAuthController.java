package com.alexzheng.onlineshop.controller.local;

import com.alexzheng.onlineshop.dto.LocalAuthExecution;
import com.alexzheng.onlineshop.entity.LocalAuth;
import com.alexzheng.onlineshop.entity.PersonInfo;
import com.alexzheng.onlineshop.enums.LocalAuthStateEnum;
import com.alexzheng.onlineshop.service.LocalAuthService;
import com.alexzheng.onlineshop.utils.CodeUtil;
import com.alexzheng.onlineshop.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Alex Zheng
 * @Date 2020/6/11 15:59
 * @Annotation
 */
@RestController
@RequestMapping("local") //TODO
public class LocalAuthController {

    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 创建平台账号
     *
     * @param request
     * @return
     */
    @PostMapping("/bindlocalauth")
    private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的验证码");
            return modelMap;
        }

        //获取输入的账号和密码
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        //从session中获取当前用户信息（微信登录的时候用户信息便被存储并放进session中）
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        if (userName != null && password != null && user != null && user.getUserId() != null) {
            //创建LocalAuth对象并赋值
            LocalAuth localAuth = new LocalAuth();
            localAuth.setPersonInfo(user);
            localAuth.setUsername(userName);
            localAuth.setPassword(password);
            //绑定账号
            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", le.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }

        return modelMap;
    }

    /**
     * 修改本地账号密码
     *
     * @param request
     * @return
     */
    @PostMapping("/changelocalauthpwd")
    private Map<String, Object> changeLocalAuthPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的验证码");
            return modelMap;
        }
        //获取账号 原密码和修改密码
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");

        //从session中获取当前用户信息（微信登录的时候用户信息便被存储并放进session中）
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        if (userName != null && password != null && user != null &&
                user.getUserId() != null && newPassword != null && !newPassword.equals(password)) {
            try {
                //查看原账号信息是否与输入账号一致 不一致则认为非法操作
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUsername().equals(userName)) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入账号非本次登录账号");
                }
                //修改密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
                if (LocalAuthStateEnum.SUCCESS.getState() == le.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }


    /**
     * 本地账号登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logincheck")
    private Map<String, Object> logincheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取时候需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的验证码");
        }

        //获取前端输入账号和密码
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");

        if (userName != null && password != null) {
            //传入账号和密码去获取平台账号信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName, password);
            if (localAuth != null) {
                modelMap.put("success",true);
                request.getSession().setAttribute("user",localAuth.getPersonInfo());
            }else{
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名或密码为空");
        }

        return modelMap;
    }

    /**
     * 本地账号登出
     * 同时注销session
     *
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    private Map<String, Object> loginout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //将用户session置为空
        request.getSession().setAttribute("user",null);
        modelMap.put("success",true);
        return modelMap;
    }


}
