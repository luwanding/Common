package com.chyjr.login.dao;

import com.chyjr.login.module.User;

/**
 *@FILE_NAME: IUserLoginDao.java
 *@Descripton:��¼��ݿ�����ӿ�
 *@author: jx_xudelin
 *@time: 2014-04-14
 *@version: 1.0
 */
public interface IUserLoginDao {
    
    /**
     * ��ݵ�¼��Ʋ�ѯ�û���Ϣ
     * @param user
     * @return
     * @throws Exception
     */
    public User selectUser(User user)throws Exception;
}
