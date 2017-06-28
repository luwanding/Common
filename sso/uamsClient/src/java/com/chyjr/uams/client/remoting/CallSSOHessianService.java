package com.chyjr.uams.client.remoting;

import com.caucho.hessian.client.HessianProxyFactory;
import com.chyjr.uams.dto.UserToken;
import com.chyjr.uams.remoting.impl.UserSSOService;

public class CallSSOHessianService implements SSOCall {
	
	private HessianProxyFactory factory;
	private String url = "";
	
	public CallSSOHessianService(final long connTimeOut, final long readTimeOut,final String url){
		factory = new HessianProxyFactory();
		factory.setConnectTimeout(connTimeOut);
		factory.setReadTimeout(readTimeOut);
		this.url = url;
	}

	@Override
	public UserToken ssoCheck(UserToken token) throws Exception {
		UserSSOService userSSOService = (UserSSOService) factory.create(UserSSOService.class,this.url);
		return userSSOService.tokenSSOCheck(token);
	}

	@Override
	public UserToken ssoLogin(UserToken token) throws Exception {
		UserSSOService userSSOService = (UserSSOService) factory.create( UserSSOService.class,this.url);
		return userSSOService.userSSOLogin(token);
	}

	@Override
	public UserToken ssoLogout(UserToken token) throws Exception {
		UserSSOService userSSOService = (UserSSOService) factory.create( UserSSOService.class, this.url);
		return userSSOService.userSSOLogin(token);
	}

}
