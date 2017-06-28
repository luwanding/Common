package com.chyjr.uams.dto;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 *@FILE_NAME: UserToken.java
 *@Descripton: 用户COOKIES的标记类
 *@author: twotoo
 *@time: 2012-2-16
 *@version: 1.0
 */

public class UserToken implements Serializable {

	//登录名
	private String loginName;	

	//客户端CookieName
	private String sessionId;
	
	//磁盘分区标识
	private String space;
	
	//用户编号
	private String userID;
	
	//Token串
	private String token;
	
	//Token解密Key
	private String token_Key;
	
	
	//Token解密Key 存储的 索引Key
	private String tokenKey_Cache_Key; 
	
	//内存过期时间
	private Long logoutTime;
	
	//登录时间
	private Long loginTimeStamp;
	
	//返回代码
	private String  operatorCode;
	
	private String cacheKeyHashCode ;
	
	
	public String getCacheKeyHashCode() {
		return cacheKeyHashCode;
	}

	public void setCacheKeyHashCode(String cacheKeyHashCode) {
		this.cacheKeyHashCode = cacheKeyHashCode;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**操作结果标识 0= 失败， 1 = 成功，2=须重新写Cookies*/  
	private Integer operatorResult;

	public String getSpace() {
		return this.space;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getUserID() {
		return this.userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken_Key() {
		return this.token_Key;
	}

	public void setToken_Key(String tokenKey) {
		this.token_Key = tokenKey;
	}

	public String getTokenKey_Cache_Key() {
		return this.tokenKey_Cache_Key;
	}

	public void setTokenKey_Cache_Key(String tokenKeyCacheKey) {
		this.tokenKey_Cache_Key = tokenKeyCacheKey;
	}
	
	public long getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(long logoutTime) {
		this.logoutTime = logoutTime;
	}

	
	/**操作结果标识 0= 失败， 1 = 成功，2=须重新写Cookies*/  
	public int getOperatorResult() {
		return this.operatorResult;
	}
	
	/**操作结果标识 0= 失败， 1 = 成功，2=须重新写Cookies*/  
	public void setOperatorResult(int operatorResult) {
		this.operatorResult = operatorResult;
	}


	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public long getLoginTimeStamp() {
		return this.loginTimeStamp;
	}

	public void setLoginTimeStamp(long loginTimeStamp) {
		this.loginTimeStamp = loginTimeStamp;
	}
	
	
	
	/**
     *@Descripton:生成成TokenKey_Cache_Key 用于存储密钥
	 *@author: jx_xudelin
	 *@time: 2012-2-17
	 * @param split
	 * @return
	 */
	public String buildCacheKeyStr(UserToken token,String split){
		
        StringBuffer sbf = new StringBuffer();
		sbf.append(token.getUserID());
		sbf.append(split);
		sbf.append(token.getSessionId());
		
		return sbf.toString();
	}
	
	
	/**
     *@Descripton:生成成TokenKey_Cache_Key 用于存储密钥
	 *@author: jx_xudelin
	 *@time: 2012-2-17
	 * @param split
	 * @return
	 */
	public static UserToken buildCacheKeyObj(String cacheKeyStr,String split){
		if(cacheKeyStr == null || split == null)
		{
			return null;
		}
		String[] slist = cacheKeyStr.split(split);
		
		if (slist.length < 2) {
			return null;
		}

		UserToken userToken = new UserToken();
		
		userToken.setUserID(slist[0]);
		userToken.setSessionId(slist[1]);
		userToken.setCacheKeyHashCode(slist[2]);
		return userToken;
	}
	
	
	

	/**
	 * 
	 *@Descripton:将TOKEN转换成STRING
	 *@author: twotoo
	 *@time: 2012-2-16
	 * @param split
	 * @return
	 */
	public String buildTokenStr(String split) {
		
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(getLoginName());
		
		sbf.append(split);
		sbf.append(getSessionId());
		
		sbf.append(split);
		sbf.append(getUserID());
		
		sbf.append(split);
		sbf.append(System.currentTimeMillis());

		return sbf.toString();
	}

	/**
	 * 
	 *@Descripton:将字符串转换成TOKEN对象
	 *@author: twotoo
	 *@time: 2012-2-16
	 * @param tokenStr
	 * @param split
	 * @return
	 */
	public static UserToken buildTokenObj(String tokenStr, String split) {
		
		if(tokenStr == null || split == null)
		{
			return null;
		}
		String[] slist = tokenStr.split(split);
		
		if (slist.length < 5) {
			System.out.println("token 解析异常：["+tokenStr+"]");
			return null;
		}

		UserToken userToken = new UserToken();
		userToken.setLoginName(slist[0]);
		userToken.setSessionId(slist[1]);
		userToken.setUserID(slist[2]);
		userToken.setLoginTimeStamp(Long.parseLong(slist[3]));
		return userToken;
	}
	
	
	public String toString(){
		
		StringBuffer str = new StringBuffer();
		str.append("loginName[");
		str.append(loginName);
		str.append("],");
		
		str.append("sessionId[");
		str.append(sessionId);
		str.append("],");
		
		str.append("space[");
		str.append(space);
		str.append("],");
		
		str.append("userID[");
		str.append(userID);
		str.append("],");
		
		str.append("token[");
		str.append(token);
		str.append("],");
		
		
		str.append("CacheKey[");
		str.append(tokenKey_Cache_Key);
		str.append("],");
		
		str.append("operatorCode[");
		str.append(operatorCode);
		str.append("],");
		
		
		str.append("token_Key[");
		str.append(token_Key);
		str.append("],");
		
		
		str.append("operatorResult[");
		str.append(operatorResult);
		str.append("],");
		
		return str.toString();
	}
	
	
	
	public void refreshTimeStamp() {
		this.loginTimeStamp = System.currentTimeMillis();
		
	}

}
