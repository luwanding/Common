package com.chyjr.uams.remoting.impl;

/**
 * 
 * @author jx_xudelin
 *
 */
public interface UserSSOWebService {
	
	/**
	 * 用户登录接口
	 * @param cookieID 
	 * @param seqID 用户唯一序列号
	 * @param userID 用户登录名
	 * @param password 登录密码
	 * @param space 磁盘分区标识
	 * @return
	 */
	public String userSSOLogin(final String sessionId,final String userID,final String userName) ;
	
	/**
	 * 用户登出接口
	 * @param token
	 * @param key
	 * @return
	 */
	public String userSSOLogout(final String token,final String key) ;
	
	
	
	/**
	 * 票据验证
	 * @param token
	 * @param key
	 * @return 
	 */
	public String tokenSSOCheck(final String token,final String key);
}
