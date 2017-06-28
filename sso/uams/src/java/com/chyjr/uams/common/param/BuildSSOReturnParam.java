package com.chyjr.uams.common.param;

import com.chyjr.uams.dto.UserToken;

public class BuildSSOReturnParam {
	
	private final static String XML_HEAD = "<?xml version= \"1.0\" encoding= \"UTF-8\"?>";
	private final static String XML_DOM_H = "<uams>";
	private final static String XML_DOM_E = "</uams>";
	
	/**
	 * 登录统一返回对象值
	 * @param token 已加密Ticket串
	 * @param tokenKey 加密Ticket用到的动态Key
	 * @param CacheKey 缓存动态Key的加密值
	 * @param operatorResult 返回操作结果
	 * @param operatorCode 返回操作代码
	 * @return
	 */
	public static UserToken returnLoginTokenToObject(final String token, final String tokenKey, final String cacheKey, final int operatorResult, final String operatorCode){
		UserToken userToken = new UserToken();
		userToken.setToken(token);
		userToken.setToken_Key(tokenKey);
		userToken.setTokenKey_Cache_Key(cacheKey);
		userToken.setOperatorResult(operatorResult);
		userToken.setOperatorCode(operatorCode);
		return userToken; 
	}
	
	
	
	
	/**
	 * 登录统一返回XML值
	 * @param token 已加密Ticket串
	 * @param tokenKey 加密Ticket用到的动态Key
	 * @param CacheKey 缓存动态Key的加密值
	 * @param operatorResult 返回操作结果
	 * @param operatorCode 返回操作代码
	 * @return
	 */
	public static String returnLoginTokenToXml(final String token, final String tokenKey, final String CacheKey, final int operatorResult, final String operatorCode){
		
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(XML_HEAD);
		strBuffer.append(XML_DOM_H);
		
		strBuffer.append("<token>");
		strBuffer.append(token);
		strBuffer.append("</token>");
		
		strBuffer.append("<token_Key><![CDATA[");
		strBuffer.append(tokenKey);
		strBuffer.append("]]></token_Key>");
		
		strBuffer.append("<tokenKey_Cache_Key>");
		strBuffer.append(CacheKey);
		strBuffer.append("</tokenKey_Cache_Key>");
		
		
		strBuffer.append("<operatorResult>");
		strBuffer.append(operatorResult);
		strBuffer.append("</operatorResult>");
		
		
		strBuffer.append("<operatorCode>");
		strBuffer.append(operatorCode);
		strBuffer.append("</operatorCode>");
		strBuffer.append(XML_DOM_E);

		
		return strBuffer.toString(); 
	}
	
	
	/**
	 * 验证统一返回对象值
	 * @param token 已加密Ticket串
	 * @param tokenKey 加密Ticket用到的动态Key
	 * @param CacheKey 缓存动态Key的加密值
	 * @param operatorResult 返回操作结果
	 * @param operatorCode 返回操作代码
	 * @param userID 用户登录编号
	 * @param sessionId 
	 * @param loginName 用户登录名称
	 * @return
	 */
	public static UserToken returnCheckTokenToObject(final String userID,final String sessionId ,final String loginName,final String token, final String tokenKey, final String cacheKey, final int operatorResult, final String operatorCode){
		UserToken userToken = new UserToken();
		userToken.setLoginName(loginName);
		userToken.setUserID(userID);
		userToken.setSessionId(sessionId);
		userToken.setToken(token);
		userToken.setToken_Key(tokenKey);
		userToken.setTokenKey_Cache_Key(cacheKey);
		userToken.setOperatorResult(operatorResult);
		userToken.setOperatorCode(operatorCode);
		return userToken; 
	}
	
	
	
	
	/**
	 * 验证统一返回XML值
	 * @param token 已加密Ticket串
	 * @param tokenKey 加密Ticket用到的动态Key
	 * @param CacheKey 缓存动态Key的加密值
	 * @param operatorResult 返回操作结果
	 * @param operatorCode 返回操作代码
	 * @param userID 用户登录编号
	 * @param sessionId 
	 * @param loginName 用户登录名称
	 * @return
	 */
	public static String returnCheckTokenToXml(final String userID,final String sessionId ,final String loginName,final String token, final String tokenKey, final String CacheKey, final int operatorResult, final String operatorCode){
		
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(XML_HEAD);
		strBuffer.append(XML_DOM_H);
		
		strBuffer.append("<loginName>");
		strBuffer.append(loginName);
		strBuffer.append("</loginName>");
		
		strBuffer.append("<sessionId>");
		strBuffer.append(sessionId);
		strBuffer.append("</sessionId>");
		
		strBuffer.append("<userID>");
		strBuffer.append(userID);
		strBuffer.append("</userID>");
		
		
		strBuffer.append("<token>");
		strBuffer.append(token == null ?"": token);
		strBuffer.append("</token>");
		
		strBuffer.append("<token_Key><![CDATA[");
		strBuffer.append(tokenKey);
		strBuffer.append("]]></token_Key>");
		
		strBuffer.append("<tokenKey_Cache_Key>");
		strBuffer.append(CacheKey == null ?"": CacheKey);
		strBuffer.append("</tokenKey_Cache_Key>");
		
		
		strBuffer.append("<operatorResult>");
		strBuffer.append(operatorResult);
		strBuffer.append("</operatorResult>");
		
		
		strBuffer.append("<operatorCode>");
		strBuffer.append(operatorCode);
		strBuffer.append("</operatorCode>");
		strBuffer.append(XML_DOM_E);

		
		return strBuffer.toString(); 
	}
	
	
	/**
	 * 登出统一返回对象值
	 * @param operatorResult 返回操作结果
	 * @param operatorCode 返回操作代码
	 * @return
	 */
	public static UserToken returnLogoutTokenToObject(final String userID,final String sessionID,final int operatorResult, final String operatorCode){
		UserToken userToken = new UserToken();
		userToken.setOperatorResult(operatorResult);
		userToken.setOperatorCode(operatorCode);
		userToken.setUserID(userID);
		userToken.setSessionId(sessionID);
		return userToken; 
	}
	
	
	
	
	/**
	 * 登出统一返回XML值
	 * @param operatorResult 返回操作结果
	 * @param operatorCode 返回操作代码
	 * @return
	 */
	public static String returnLogoutTokenToXml(final String userID,final String sessionID, final int operatorResult, final String operatorCode){
		
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(XML_HEAD);
		strBuffer.append(XML_DOM_H);
		
		strBuffer.append("<sessionId>");
		strBuffer.append(sessionID);
		strBuffer.append("</sessionId>");
		
		strBuffer.append("<userID>");
		strBuffer.append(userID);
		strBuffer.append("</userID>");
		
		strBuffer.append("<operatorResult>");
		strBuffer.append(operatorResult);
		strBuffer.append("</operatorResult>");
		
		
		strBuffer.append("<operatorCode>");
		strBuffer.append(operatorCode);
		strBuffer.append("</operatorCode>");
		strBuffer.append(XML_DOM_E);

		
		return strBuffer.toString(); 
	}
}
