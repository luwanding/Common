package com.chyjr.uams.client.remoting;

import java.net.URL;
import java.net.MalformedURLException;

import org.codehaus.xfire.client.Client;

import com.chyjr.uams.client.util.ParseXml;
import com.chyjr.uams.dto.UserToken;

public class CallSSOWebService implements SSOCall {
	
	private Client client;
	
	public CallSSOWebService(final String url, final int connTimeOut) throws MalformedURLException, Exception{
		client = new Client(new URL(url));
		client.setTimeout(connTimeOut);
		
	}
	@Override
	public UserToken ssoCheck(final UserToken token) throws Exception {
		Object[] resultObj = client.invoke("tokenSSOCheck", new Object[] { token.getToken(),token.getTokenKey_Cache_Key() });
		return ParseXml.xmlToMap((String)resultObj[0]);
	}

	@Override
	public UserToken ssoLogin(UserToken token) throws Exception {
		Object[] resultObj = client.invoke("userSSOLogin", new Object[] { token.getSessionId(),token.getUserID() ,token.getLoginName() });
		return ParseXml.xmlToMap((String)resultObj[0]);
	}

	@Override
	public UserToken ssoLogout(UserToken token) throws Exception {
		Object[] resultObj = client.invoke("userSSOLogout", new Object[] { token.getToken(),token.getTokenKey_Cache_Key()});
		return ParseXml.xmlToMap((String)resultObj[0]);
	}

}
