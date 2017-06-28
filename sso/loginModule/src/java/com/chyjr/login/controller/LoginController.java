package com.chyjr.login.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.chyjr.login.module.ArgumentModule;
import com.chyjr.login.service.IUserLoginService;
import com.chyjr.login.util.LoginConstants;
import com.chyjr.uams.common.util.CString;
import com.platform.cache.ICacheControllerService;

/**
 *@FILE_NAME: LoginController.java
 *@Descripton:登录控制类
 *@author: jx_xudelin
 *@time: 2014-04-14
 *@version: 1.0
 */
public class LoginController extends AbstractController {
    
    @Autowired
    private IUserLoginService userLoginService;
    
    @Autowired
    private ICacheControllerService cacheClient;
    
    private static int ONE_Minute = 30;
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response)throws Exception {
        String loginName = request.getParameter("user_name");
        String password = request.getParameter("user_password");
        
        if(!ischeck(loginName, password)){
            request.setAttribute("msg", "抱歉，用户名和密码都不允许为空,请您重新输入！");
            return new ModelAndView("login");
        }
        
        if(!issecurity(loginName,true)){
            request.setAttribute("msg", "抱歉，您的登录过于频繁请稍后重试！");
            return new ModelAndView("login");
        }
        
        ArgumentModule arg = new ArgumentModule();
        arg.setLoginName(loginName);
        arg.setPassword(password);
        arg.setRequest(request);
        arg.setResponse(response);
        arg.setSessionId(request.getSession().getId());
        boolean result = userLoginService.login(arg);
        
        if(!result){
            issecurity(loginName,result);
            request.setAttribute("msg", "抱歉，您输入的用户名或密码不正确！");
            return new ModelAndView("login");
        }
        return  new ModelAndView("index");
    }
    
    
    /**
     * 登录安全性检查,防止频繁使用同一个用户猜用户密码
     * @param loginName
     * @return
     */
    private boolean issecurity(String loginName,boolean result){
        String key = LoginConstants.CACHE_KEY + loginName;
        Object object = cacheClient.getCacheClient().get(key);
        if(object == null){
            if(!result){
                 cacheClient.getCacheClient().addTimeOut(key,"1", ONE_Minute);
            }
        }else{
            Integer loginCount = Integer.valueOf(object.toString());
            if(loginCount >= 3){
                return false;
            }else{
                if(!result){
                    loginCount = loginCount +1;
                    
                    cacheClient.getCacheClient().replace(key, loginCount);
                }
            }
        }
        return true;
    }
    
    /**
     * 登录数据合法性校验
     * @param loginName
     * @param password
     * @return
     */
    private boolean ischeck(String loginName, String password){
        if(CString.isNull(loginName) || CString.isNull(password)){
            return false;
        }
        return true;
    }


    public void setCacheClient(ICacheControllerService cacheClient) {
        this.cacheClient = cacheClient;
    }
    
    
}
