package com.chyjr.login.module;

/**
 *@FILE_NAME: User.java
 *@Descripton: �û�
 *@author: jx_xudelin
 *@time: 2014-04-14
 *@version: 1.0
 */
public class User {
    
    private String loginName;//��¼�û���
    private String userName;//��ʾ�û���
    private String password;//����
    private String userId;//
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
