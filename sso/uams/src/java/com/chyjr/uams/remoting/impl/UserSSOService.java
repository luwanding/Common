package com.chyjr.uams.remoting.impl;


import com.chyjr.uams.dto.UserToken;

/**
 * 用户SSO提供对外服务接口定义
 * @author jx_xudelin
 * @param <UserToken>
 *
 */
public interface UserSSOService {
	
	/**
	 * 用户登录接口
	 */
	public UserToken userSSOLogin(final UserToken userToken);
	
	/**
	 * 用户登出接口
	 * @param userToken
	 * @return
	 */
	public UserToken userSSOLogout(final UserToken userToken) ;
	
	
	/**
	 * 票据验证
	 * @param userToken
	 * @return 
	 */
	public UserToken tokenSSOCheck(final UserToken userToken);
}
