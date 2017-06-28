package com.chyjr.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *@FILE_NAME: LoginViewController.java
 *@Descripton:登录首页
 *@author: jx_xudelin
 *@time: 2014-04-14
 *@version: 1.0
 */
public class LoginViewController extends AbstractController {
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1)
            throws Exception {
        return new ModelAndView("login");
    }

}
