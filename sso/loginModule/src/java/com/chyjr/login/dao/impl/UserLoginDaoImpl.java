package com.chyjr.login.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chyjr.login.dao.IUserLoginDao;
import com.chyjr.login.module.User;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 *@FILE_NAME: UserLoginDaoImpl.java
 *@Descripton:��¼��ݿ����ʵ��
 *@author: jx_xudelin
 *@time: 2014-04-14
 *@version: 1.0
 */
@Repository("userLoginDao")
public class UserLoginDaoImpl implements IUserLoginDao {
    @Autowired  
    private SqlMapClient sqlMapClient;

    @Override
    public User selectUser(User user) throws Exception {
        return (User)sqlMapClient.queryForObject("selectUser", user);
    } 
    
    
}
