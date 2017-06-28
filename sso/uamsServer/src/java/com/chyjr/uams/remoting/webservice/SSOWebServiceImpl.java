package com.chyjr.uams.remoting.webservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.chyjr.uams.common.param.BuildSSOReturnParam;
import com.chyjr.uams.dto.UserToken;
import com.chyjr.uams.remoting.impl.UserSSOService;
import com.chyjr.uams.remoting.impl.UserSSOWebService;

/**
 * 用户操作统一  WebService 接口定义
 * @author jx_xudelin
 *
 */
public class SSOWebServiceImpl implements UserSSOWebService {
	
	@Autowired
    private UserSSOService userSSOService;
	
	/**
	 * 用户登录接口
	 * @param userID
	 * @param password
	 * @param ip
	 * @param space
	 * @return
	 */
	public String userSSOLogin(final String sessionId,final String userID,final String userName) {
		UserToken token = new  UserToken();
		token.setUserID(userID);
		token.setSessionId(sessionId);
		token.setLoginName(userName);
		
		token = userSSOService.userSSOLogin(token);
		
		return BuildSSOReturnParam.returnLoginTokenToXml(token.getToken(), token.getToken_Key(), token.getTokenKey_Cache_Key(), token.getOperatorResult(), token.getOperatorCode());
		
	}
	
	/**
	 * 用户登出接口
	 * @param token
	 * @param cacheKey
	 * @return
	 */
	public String userSSOLogout(final String tokenStr,final String cacheKey) {
		
		UserToken token = new  UserToken();
		token.setToken(tokenStr);
		token.setTokenKey_Cache_Key(cacheKey);
		
		token = userSSOService.userSSOLogout(token);
		
		return BuildSSOReturnParam.returnLogoutTokenToXml(token.getUserID(),token.getSessionId(),token.getOperatorResult(), token.getOperatorCode());
	}
	
	
	
	/**
	 * 票据验证
	 * @param token
	 * @param key
	 * @return 
	 */
	public String tokenSSOCheck(final String tokenStr,final String cacheKey){
		
		UserToken token = new  UserToken();
		token.setToken(tokenStr);
		token.setTokenKey_Cache_Key(cacheKey);
		
		token = userSSOService.tokenSSOCheck(token);

		return BuildSSOReturnParam.returnCheckTokenToXml(
				token.getUserID(),
				token.getSessionId(), 
				token.getLoginName(), 
				token.getToken(), 
				token.getToken_Key(), 
				token.getTokenKey_Cache_Key(), 
				token.getOperatorResult(), 
				token.getOperatorCode());
	}
	
	
	/**
	 * 
	 * @param map
	 * @return
	 
	public String toXmlString(UserToken userToken){
		
		if(userToken == null){
			return "";
		}
		
		if(userToken.getToken_Key()!= null && userToken.getToken_Key().length() > 0){
			userToken.setToken_Key("<![CDATA["+userToken.getToken_Key()+"]]>");
		}
		
		Field [] fields = userToken.getClass().getDeclaredFields();
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(XML_HEAD);
		strBuffer.append(XML_DOM_H);
		for(int i =0; i < fields.length; i++ ){
			
			try {
				fields[i].setAccessible(true);
				Object value = fields[i].get(userToken);
				
				if(value ==  null || "".equals(value.toString())){
					continue;
				}
				strBuffer.append("<").append(fields[i].getName()).append(">");
				strBuffer.append(value.toString() );
				strBuffer.append("</").append(fields[i].getName()).append(">");
			} catch (IllegalArgumentException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			}
			
		}
		strBuffer.append(XML_DOM_E);
		//System.out.println(strBuffer.toString());
		return strBuffer.toString();
	}
	
	public static void main(String[] args){
		UserToken userToken  = new UserToken();
		userToken.setLoginName("sdfsdf");
		userToken.setLoginName("asdfasdf");
		SSOWebServiceImpl sso = new SSOWebServiceImpl();
		System.out.println(sso.toXmlString(userToken));
	}*/
}