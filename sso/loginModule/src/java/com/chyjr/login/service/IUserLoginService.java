package com.chyjr.login.service;

import com.chyjr.login.module.ArgumentModule;

/**
 *@FILE_NAME: IUserLoginService.java
 *@Descripton:��¼ҵ��ӿ�
 *@author: jx_xudelin
 *@time: 2014-04-18
 *@version: 1.0
 */
public interface IUserLoginService {
    
    public boolean login(ArgumentModule arg) throws Exception;
}
