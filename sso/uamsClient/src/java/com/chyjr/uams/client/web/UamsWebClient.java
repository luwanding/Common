package com.chyjr.uams.client.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.chyjr.uams.client.UamsClient;
import com.chyjr.uams.dto.UserToken;

public class UamsWebClient {
	private final Logger logger = Logger.getLogger(UamsWebClient.class);

	private CookiesConfig cookieConfig ;
	private CookieDecoration cookieDecoration;
	
	public UamsWebClient(HttpServletRequest request,
			HttpServletResponse response) {
		this.cookieConfig = new CookiesConfig();
		this.cookieDecoration = new CookieDecoration(request, response);
	}

	public UamsWebClient(HttpServletRequest request,
			HttpServletResponse response,String cookieFilePath) {
		this.cookieConfig = new CookiesConfig(cookieFilePath);
		this.cookieDecoration = new CookieDecoration(request, response);
	}
	
	public UamsWebClient(HttpServletRequest request,
			HttpServletResponse response,Properties config) {
		this.cookieConfig = new CookiesConfig(config);
		this.cookieDecoration = new CookieDecoration(request, response);
	}

	/**
	 * 构建Token
	 * @param loginName
	 * @param userId
	 * @param sessionId
	 * @throws Exception
	 */
	public void buildToken(String loginName, String userId, String sessionId)
			throws Exception {

		UserToken ut = new UserToken();

		ut.setLoginName(loginName);

		ut.setUserID(userId);

		ut.setSessionId(sessionId);

		UserToken rut = UamsClient.createFactory().userSSOLogin(ut);

		if (rut != null && rut.getOperatorResult() == 1) {

			updateToken(rut);
		}

	}
	
	
	/**
	 * 构建Token
	 * @param loginName
	 * @param userId
	 * @param sessionId
	 * @throws Exception
	 */
	public UserToken isBuildToken(String loginName, String userId, String sessionId)
			throws Exception {

		UserToken ut = new UserToken();

		ut.setLoginName(loginName);

		ut.setUserID(userId);

		ut.setSessionId(sessionId);

		UserToken rut = UamsClient.createFactory().userSSOLogin(ut);

		if (rut != null && rut.getOperatorResult() == 1) {

			updateToken(rut);
			return rut;
		}else{
			return null;
		}

	}

	/**
	 * 验证Token
	 * @return
	 * @throws Exception
	 */
	public boolean checkToken() throws Exception {

		UserToken ut = getUserCookiesToken();
		if (ut == null) {
			logger.warn("do not find cookies token!");
			return false;
		}

		UserToken rut = UamsClient.createFactory().tokenSSOCheck(ut);

		if (rut != null && rut.getOperatorResult() == 1) {
			updateToken(rut);

			return true;
		}

		return false;

	}
	
	
	
	/**
	 * 验证Token
	 * @return
	 * @throws Exception
	 */
	public UserToken checkToken(UserToken ut) throws Exception {

		UserToken rut = UamsClient.createFactory().tokenSSOCheck(ut);

		if (rut != null && rut.getOperatorResult() == 1) {
			updateToken(rut);

			return rut;
		}

		return null;

	}
	
	
	/**
	 * 验证Token
	 * @return
	 * @throws Exception
	 */
	public boolean checkToken(String tokenKey,String token) throws Exception {

		UserToken ut = getUserCookiesToken(tokenKey,token);
		if (ut == null) {
			logger.warn("do not find cookies token!");
			return false;
		}

		UserToken rut = UamsClient.createFactory().tokenSSOCheck(ut);

		if (rut != null && rut.getOperatorResult() == 1) {
			updateToken(tokenKey,token,rut);

			return true;
		}

		return false;

	}
	
	
	/**
	 * 验证Token
	 * @return
	 * @throws Exception
	 */
	public UserToken getTokenInfo() throws Exception {

		UserToken ut = getUserCookiesToken();
		if (ut == null) {
			return null;
		}

		UserToken rut = UamsClient.createFactory().tokenSSOCheck(ut);

		return rut;

	}
	
	

	/**
	 * 销毁Token
	 * @throws Exception
	 */
	public void destoryToken() throws Exception {

		UserToken ut = getUserCookiesToken();
		if (ut != null) {
			UserToken rut = UamsClient.createFactory().userSSOLogout(ut);
		}

		clearUserToken();

	}
	

	protected UserToken getUserCookiesToken() {

		String token = cookieDecoration.getCookieValue(cookieConfig
				.getUserTokenName());

		String tokenKeyCacheKey = cookieDecoration.getCookieValue(cookieConfig
				.getUserTokenKeyCacheName());

		if (token == null || tokenKeyCacheKey == null) {

			return null;
		}

		UserToken userToken = new UserToken();

		userToken.setToken(token);

		userToken.setTokenKey_Cache_Key(tokenKeyCacheKey);

		return userToken;
	}
	
	protected UserToken getUserCookiesToken(String tokenKey,String token) {

		String tokenVal = cookieDecoration.getCookieValue(token);

		String tokenKeyCacheKey = cookieDecoration.getCookieValue(tokenKey);

		if (tokenVal == null || tokenKeyCacheKey == null) {

			return null;
		}

		UserToken userToken = new UserToken();

		userToken.setToken(tokenVal);

		userToken.setTokenKey_Cache_Key(tokenKeyCacheKey);

		return userToken;
	}

	private void updateToken(UserToken ut) {
		try {

			cookieDecoration.setCookie(cookieConfig.getUserTokenName(),
					URLEncoder.encode(ut.getToken(), "UTF-8"),
					cookieConfig.getDomain(), -1);

			cookieDecoration.setCookie(
					cookieConfig.getUserTokenKeyCacheName(),
					URLEncoder.encode(ut.getTokenKey_Cache_Key(), "UTF-8"),
					cookieConfig.getDomain(), -1);
			
			logger.info("set cookies token value [domain:" + cookieConfig.getDomain() +cookieConfig.getUserTokenName() + ":" + ut.getToken() + "]");

		} catch (UnsupportedEncodingException e) {
			clearUserToken();

			logger.error(e);
		}
	}
	
	private void updateToken(String tokenKey,String token,UserToken ut) {
		try {

			cookieDecoration.setCookie(token,
					URLEncoder.encode(ut.getToken(), "UTF-8"),
					cookieConfig.getDomain(), -1);

			cookieDecoration.setCookie(	tokenKey,
					URLEncoder.encode(ut.getTokenKey_Cache_Key(), "UTF-8"),
					cookieConfig.getDomain(), -1);
			
			logger.info("set cookies token value [domain:" + cookieConfig.getDomain() +cookieConfig.getUserTokenName() + ":" + ut.getToken() + "]");

		} catch (UnsupportedEncodingException e) {
			clearUserToken();

			logger.error(e);
		}
	}


	protected void clearUserToken() {

		clearUserToken(cookieConfig.getUserTokenName());

		clearUserToken(cookieConfig.getUserTokenKeyCacheName());
	}

	protected void clearUserToken(String cookieName) {

		cookieDecoration.clearCookie(cookieName, cookieConfig.getDomain());

	}

}
