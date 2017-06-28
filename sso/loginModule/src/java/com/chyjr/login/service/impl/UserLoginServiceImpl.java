package com.chyjr.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chyjr.login.dao.IUserLoginDao;
import com.chyjr.login.module.ArgumentModule;
import com.chyjr.login.module.User;
import com.chyjr.login.service.IUserLoginService;
import com.chyjr.web.login.LoginCookie;
import com.chyjr.web.model.UserLoginBean;

/**
 *@FILE_NAME: UserLoginServiceImpl.java
 *@Descripton:��¼ҵ��ʵ����
 *@author: jx_xudelin
 *@time: 2014-04-18
 *@version: 1.0
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements IUserLoginService {
    
    @Autowired
    private IUserLoginDao userLoginDao;
    
    @Override
    public boolean login(ArgumentModule arg) throws Exception {
        User  user = new User();
        user.setLoginName(arg.getLoginName());
        user.setPassword(arg.getPassword());
        user = userLoginDao.selectUser(user);
        if(user == null || !user.getPassword().equals(arg.getPassword())){
            return false;
        }
        UserLoginBean userToken = new UserLoginBean();
        userToken.setUserID(user.getUserId());
        userToken.setUserLoginName(arg.getLoginName());
        LoginCookie log = new LoginCookie();
        userToken.setSessionId(arg.getSessionId());
        log.buildCookie(userToken,arg.getRequest(),arg.getResponse());
        return true;
    }
    
}
