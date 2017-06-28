package com.chyjr.uams.client.remoting;
import com.chyjr.uams.dto.UserToken;

public interface SSOCall {
	
	public UserToken ssoLogin(final UserToken token) throws Exception;
	
	public UserToken ssoLogout(final UserToken token) throws Exception ;
	
	public UserToken ssoCheck(final UserToken token) throws Exception ;
}
