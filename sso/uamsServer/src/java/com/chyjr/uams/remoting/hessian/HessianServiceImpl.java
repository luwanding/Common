package com.chyjr.uams.remoting.hessian;

import org.springframework.beans.factory.annotation.Autowired;

import com.chyjr.uams.dto.UserToken;
import com.chyjr.uams.remoting.impl.UserSSOService;
import com.chyjr.uams.spring.SpringBeanUtil;


/**
 * 用户操作统一Hessian 接口定义
 * @author jx_xudelin
 *
 */

public class HessianServiceImpl implements UserSSOService {
	
	@Autowired
    private UserSSOService userSSOService;
	
	public HessianServiceImpl(){
		if(userSSOService == null)
		userSSOService = (UserSSOService)SpringBeanUtil.getContext().getBean("userSSOService");
	}
	/**
	 * 用户登录接口
	 */
	@Override
	public UserToken userSSOLogin(final UserToken userToken) {
		return userSSOService.userSSOLogin(userToken);
	}
	
	/**
	 * 用户登出接口
	 * @param param
	 * @return
	 */
	public UserToken userSSOLogout(final UserToken userToken) {
		return userSSOService.userSSOLogout(userToken);
	}
	
	
	
	/**
	 * 票据验证
	 * @param param
	 * @return 
	 */
	public UserToken tokenSSOCheck(final UserToken userToken){
		return userSSOService.tokenSSOCheck(userToken);
	}
}
