package com.chyjr.uams.common.cache;

import com.chyjr.uams.common.constant.UAMSConstant;
import com.chyjr.uams.dto.UserToken;

public class MemcacheConstant {
	
	public final static Long SECOND = new Long(1);

    public final static Long MINUTE = new Long(60);

    public final static Long HOUR = new Long(60 * 60);

    public final static Long DAY = new Long(60 * 60 * 24);

    public final static Long WEEK = new Long(60 * 60 * 24 * 7);
    
 // 存储用户TOKEN 内容 头部标识
	public final static String UAMS_USER_TOKEN = "UAMST";
	
	// 存储用户TOKEN 解密字符串头部标识
	public final static String UAMS_USER_TOKEN_KEY = "UAMSTK";
	
	
	
	/**存储Token的Key标识生成*/
	public static String getToken_Key(final UserToken userToken){
		return MemcacheConstant.UAMS_USER_TOKEN + UAMSConstant.splitFlag + userToken.getSessionId()+ UAMSConstant.splitFlag  + userToken.getUserID();
	}
	
	/**存储Token的动态密码的Key标识生成*/
	public static String getToken_encryptKey_key(final UserToken userToken){
		return MemcacheConstant.UAMS_USER_TOKEN_KEY + UAMSConstant.splitFlag + userToken.getSessionId()+ UAMSConstant.splitFlag  + userToken.getUserID();
	}
	
	//登录会话保存最长时间
	public static long getLoginOutTime(long time){
		return (time * 2);
	}
	
	public static String getTokenKeyCachekey(final UserToken userToken){
		return userToken.getSessionId()+ UAMSConstant.splitFlag  + userToken.getUserID();
	}
	
	
	
	
	
}
