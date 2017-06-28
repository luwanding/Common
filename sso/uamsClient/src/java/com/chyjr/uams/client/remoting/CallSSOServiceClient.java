package com.chyjr.uams.client.remoting;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.chyjr.uams.client.cache.CacheFactory;
import com.chyjr.uams.client.util.CString;
import com.chyjr.uams.client.util.ClientConfigure;
import com.chyjr.uams.common.cache.MemcacheConstant;
import com.chyjr.uams.common.constant.UAMSConstant;
import com.chyjr.uams.common.param.BuildSSOReturnParam;
import com.chyjr.uams.dto.UserToken;
import com.chyjr.uams.remoting.impl.UserSSOService;
import com.chyjr.uams.security.TokenUtils;
import com.chyjr.uams.security.util.SecurityConstant;

public class CallSSOServiceClient implements UserSSOService{
	
	private static final Logger Log = Logger.getLogger(CallSSOServiceClient.class);
	private SSOCall ssoCall;
	private String configFile;
	
	public CallSSOServiceClient(final SSOCall ssoCall,final String configFile){
		this.ssoCall = ssoCall;
		this.configFile = configFile;
	}
	
	public UserToken tokenSSOCheck(final UserToken userToken) {
		try {
			Log.info("用户校验：TOKEN ["+userToken.getToken()+"], cacheKey ["+userToken.getTokenKey_Cache_Key()+"] 开始校验。");
			//1、验证数据合法性
			if(!ValidationBean.validationTokenCheckData(userToken)){
				Log.error("用户校验失败：token["+userToken.getToken()+"],tokenKey[" + userToken.getTokenKey_Cache_Key()+"] 数据校验无法通过。");
				return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "5000001");
			}
			
			Log.info("用户校验：TOKEN ["+userToken.getToken()+"], cacheKey ["+userToken.getTokenKey_Cache_Key()+"] 开始转换 userTokenCacheKey 对象。");
			//2、转换对象
			UserToken userTokenCacheKey = buildCacheKeyStrToObj(userToken);
			if(userTokenCacheKey == null){
				Log.error("用户校验失败：{"+userToken.toString()+"} 无法把CacheKey 转换成对象。");
				return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "5000002");
			}
			
			Log.info("用户校验：TOKEN ["+userToken.getToken()+"], cacheKey ["+userToken.getTokenKey_Cache_Key()+"] userID:[" 
					+ userTokenCacheKey.getUserID()
					+ "] SessionID:["+userTokenCacheKey.getSessionId()+"]"+
					" 已转换转换 userTokenCacheKey 对象。");
			
			//3、获取储存Token的内存Key 地址
			String key = MemcacheConstant.getToken_Key(userTokenCacheKey);
			String cache_key = MemcacheConstant.getToken_encryptKey_key(userTokenCacheKey)+UAMSConstant.splitFlag+userTokenCacheKey.getCacheKeyHashCode();
			UserToken tokenObj ;
			
			
			Log.info("用户校验：key ["+key+"], cache_key ["+cache_key+"] userID:[" 
					+ userTokenCacheKey.getUserID()
					+ "] SessionID:["+userTokenCacheKey.getSessionId()+"]"+
					" 已转换转换 userTokenCacheKey 对象。");
			
			//4、判断客户端是否开启缓存
			if(ClientConfigure.getInstance(this.configFile).getIsCache()){
				
				Log.info("用户校验：userID["+userTokenCacheKey.getUserID()+"], sessionID["+userTokenCacheKey.getSessionId()+"] 本地已使用缓存正在从本地获取用户校验信息。");
				
				//5、获取Token 动态加密串
				String tokenKey = (String)CacheFactory.getInstance(this.configFile).getObjectOfCache(cache_key);
				
				Log.info("用户校验：userID["+userTokenCacheKey.getUserID()+"], sessionID["+userTokenCacheKey.getSessionId()+"] tokenKey["+tokenKey+"] 已获取Token串密。");
				
				if(!CString.isNull(tokenKey)){
					Log.info("用户校验：userID["+userTokenCacheKey.getUserID()+"], sessionID["+userTokenCacheKey.getSessionId()+"] 开始解Token串密。");
					String tokenStr = TokenUtils.decodeToken(userToken.getToken(), tokenKey);
					Log.info("用户校验：userID["+userTokenCacheKey.getUserID()+"], sessionID["+userTokenCacheKey.getSessionId()+"] 已解出Token串密 tokenStr["+tokenStr+"]。");
					tokenObj = UserToken.buildTokenObj(tokenStr,  SecurityConstant.splitFlag);

					if(tokenObj == null){
						clearTicket(key ,cache_key);
						Log.error("用户校验失败：userID["+userTokenCacheKey.getUserID()+"], sessionID["+userTokenCacheKey.getSessionId()+"] 无法把tokenStr 转换成对象。");
						return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "5000002");
					}
					else
					{
						Log.info("用户校验：userID["+tokenObj.getUserID()+"], sessionID["+tokenObj.getSessionId()+"] ,LoginName ["+tokenObj.getLoginName()+"] 已解出Token开始校验时间。");
						
						//用户超时验证
						long loginTime = System.currentTimeMillis() - (tokenObj.getLoginTimeStamp() + ClientConfigure.getInstance(this.configFile).getMaxLifeTime());
						if ( loginTime > 0) {
							
							Log.info("用户校验失败：userID["+tokenObj.getUserID()+"], sessionID["+tokenObj.getSessionId()+"] ,LoginName ["+tokenObj.getLoginName()+"] 登录已超时["+loginTime+"]毫秒 须重新登录。");
							return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "5000005");
						} else if(tokenObj.getUserID().equals(userTokenCacheKey.getUserID()) && 
								tokenObj.getSessionId().equals(userTokenCacheKey.getSessionId()))
						{
							
							Log.info("用户校验通过：userID["+tokenObj.getUserID()+"], sessionID["+tokenObj.getSessionId()+"] ,LoginName ["+tokenObj.getLoginName()+"] 开始判断是否需要重新刷新Ticket。");
							
							//如果离超时间还有一半左右
							loginTime = (System.currentTimeMillis() - tokenObj.getLoginTimeStamp());
							long loginTimeHelf = ClientConfigure.getInstance(this.configFile).getMaxLifeTime()/2;
							if (loginTime >= loginTimeHelf) {
								
								Log.info("用户校验通过：userID["+tokenObj.getUserID()+"], sessionID["+tokenObj.getSessionId()+"] ,LoginName ["+tokenObj.getLoginName()+"] 需要重新刷新Ticket。");
								
								//调用SSO登录服务
								tokenObj = ssoCall.ssoCheck(userToken);
								
								if(tokenObj.getOperatorResult() == 2){
									CacheFactory.getInstance(this.configFile).putObjectToCache(key, tokenObj.getToken());
									CacheFactory.getInstance(this.configFile).putObjectToCache(cache_key, tokenObj.getToken_Key());
								}
								
								Log.info("用户校验通过：userID["+tokenObj.getUserID()+"], sessionID["+tokenObj.getSessionId()+"] ,LoginName ["+tokenObj.getLoginName()+"] 重新刷新Ticket结果["+tokenObj.getOperatorResult()+"]。");
							}else{
								tokenObj.setOperatorCode("5000000");
								tokenObj.setOperatorResult(1);
								
								Log.info("用户校验通过：userID["+userTokenCacheKey.getUserID()+"], sessionID["+userTokenCacheKey.getSessionId()+"] ,LoginName ["+tokenObj.getLoginName()+"] 解密完成 返回结果。");
								
							}
							
							return BuildSSOReturnParam.returnCheckTokenToObject(tokenObj.getUserID(), 
									tokenObj.getSessionId(), 
									tokenObj.getLoginName(), 
									tokenObj.getToken(), 
									tokenObj.getToken_Key(), 
									tokenObj.getTokenKey_Cache_Key(), 
									tokenObj.getOperatorResult(), 
									tokenObj.getOperatorCode());
						} else {
							Log.info("用户校验失败：Token {userID["+tokenObj.getUserID()+"], sessionID["+tokenObj.getSessionId()+"]}" +
									"Token {userID["+userTokenCacheKey.getUserID()+"], sessionID["+userTokenCacheKey.getSessionId()+"]}"+
									" 无法匹配用户  。");
							return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "5000004");
						}
					}
				}else{
					
					//调用SSO登录服务
					Log.info("用户校验：本地缓存无法找到该用户，直接从服务器验证开始");
					tokenObj = ssoCall.ssoCheck(userToken);
					key = MemcacheConstant.getToken_Key(userTokenCacheKey);
					//cache_key = MemcacheConstant.getToken_encryptKey_key(userTokenCacheKey);
					if(tokenObj.getOperatorResult() == 1 || tokenObj.getOperatorResult() == 2){
						CacheFactory.getInstance(this.configFile).putObjectToCache(key, tokenObj.getToken());
						CacheFactory.getInstance(this.configFile).putObjectToCache(cache_key, tokenObj.getToken_Key());
					}
					Log.info("用户校验：本地缓存无法找到该用户，直接从服务器验证返回结果。userID["+tokenObj.toString()+"]");
					return BuildSSOReturnParam.returnCheckTokenToObject(tokenObj.getUserID(), 
							tokenObj.getSessionId(), 
							tokenObj.getLoginName(), 
							tokenObj.getToken(), 
							tokenObj.getToken_Key(), 
							tokenObj.getTokenKey_Cache_Key(), 
							tokenObj.getOperatorResult(), 
							tokenObj.getOperatorCode());
				}
			} else {
				
				Log.info("用户校验：本地缓存已禁用，直接从服务器验证开始");
				//调用SSO服务
				tokenObj = ssoCall.ssoCheck(userToken);
				
				Log.info("用户校验：本地缓存已禁用，到服务器验证结果:{"+tokenObj.toString()+"}");
				
				//添加本地缓存
				if(ClientConfigure.getInstance().getIsCache() && tokenObj != null && tokenObj.getOperatorResult() == 1 ||  tokenObj.getOperatorResult() == 2){
					CacheFactory.getInstance(this.configFile).putObjectToCache(key, tokenObj.getToken());
					CacheFactory.getInstance(this.configFile).putObjectToCache(cache_key, tokenObj.getToken_Key());
				}
				return BuildSSOReturnParam.returnCheckTokenToObject(tokenObj.getUserID(), 
						tokenObj.getSessionId(), 
						tokenObj.getLoginName(), 
						tokenObj.getToken(), 
						tokenObj.getToken_Key(), 
						tokenObj.getTokenKey_Cache_Key(), 
						tokenObj.getOperatorResult(), 
						tokenObj.getOperatorCode());
			}
		} catch (Exception e) {
			Log.error("用户校验失败：校验出现异常。",e);
			return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "5000006");
		}
	}
	
	@Override
	public UserToken userSSOLogin(final UserToken userToken) {
		UserToken result = new UserToken();
		try {
			
			//验证数据合法性
			if(!ValidationBean.validationUserLoginData(userToken)){
				Log.error("用户登录失败：loginName["+userToken.getLoginName()+"] userID["+userToken.getUserID()+"] sessionID["+userToken.getSessionId()+"] 校验登录数据无法通过。");
				return BuildSSOReturnParam.returnLoginTokenToObject("", "", "", 0, "3000001");
			}
			
			result = ssoCall.ssoLogin(userToken);
			
			if(result == null){
				Log.error("用户登录失败：loginName["+userToken.getLoginName()+"] userID["+userToken.getUserID()+"] sessionID["+userToken.getSessionId()+"] 无法返回登录结果。");
				return BuildSSOReturnParam.returnLoginTokenToObject("", "", "", 0, "3000002");
			}
			
			Log.info("用户登录正常：结果为："+result.toString());
			
			UserToken userTokenCacheKey = buildCacheKeyStrToObj(result);
			
			//储存Token的内存Key 地址
			String key = MemcacheConstant.getToken_Key(userToken);
			String cache_key = MemcacheConstant.getToken_encryptKey_key(userToken)+ UAMSConstant.splitFlag + userTokenCacheKey.getCacheKeyHashCode();;
			
			//添加本地缓存
			if(result != null && ClientConfigure.getInstance().getIsCache() && result.getOperatorResult() == 1){
				CacheFactory.getInstance(this.configFile).putObjectToCache(key, result.getToken());
				CacheFactory.getInstance(this.configFile).putObjectToCache(cache_key, result.getToken_Key());
			}
			return BuildSSOReturnParam.returnLoginTokenToObject(result.getToken(), result.getToken_Key(), result.getTokenKey_Cache_Key(), result.getOperatorResult(), result.getOperatorCode());
		} catch (Exception e) {
			Log.error("用户登录失败：loginName["+userToken.getLoginName()+"] userID["+userToken.getUserID()+"] sessionID["+userToken.getSessionId()+"] 登录失败。",e);
			return BuildSSOReturnParam.returnLoginTokenToObject("", "", "", 0, "3000002");
		}
	}
	
	/**
	 * 用户退出
	 */
	@Override
	public UserToken userSSOLogout(final UserToken userToken) {
		UserToken result = new UserToken();
		try {
			//验证数据合法性
			if(!ValidationBean.validationLogoutData(userToken)){
				Log.error("用户登出失败：token["+userToken.getToken()+"],tokenKey[" + userToken.getTokenKey_Cache_Key()+"] 数据校验无法通过。");
				return BuildSSOReturnParam.returnLogoutTokenToObject("","",0, "4000001");
			}
			
			Log.error("用户登出操作开始。");
			result = ssoCall.ssoLogout(userToken);
			
			Log.error("用户登出操作结束。返回结果["+result.toString()+"]");
			
			UserToken userTokenCacheKey = buildCacheKeyStrToObj(userToken);
			//储存Token的内存Key 地址
			String key = MemcacheConstant.getToken_Key(result);
			String cache_key = MemcacheConstant.getToken_encryptKey_key(result) + UAMSConstant.splitFlag + userTokenCacheKey.getCacheKeyHashCode();
			
			if(result == null){
				Log.error("用户登出失败：token["+userToken.getToken()+"],tokenKey[" + userToken.getTokenKey_Cache_Key()+"] 服务器无法返回结果。");
				return BuildSSOReturnParam.returnLogoutTokenToObject("","",0, "4000002");
			}
			
			//清除本地缓存
			if(ClientConfigure.getInstance().getIsCache()){
				clearTicket(key ,cache_key);
			}
			
			return BuildSSOReturnParam.returnLogoutTokenToObject(result.getUserID(),result.getSessionId(),result.getOperatorResult(), result.getOperatorCode());
		} catch (Exception e) {
			Log.error("用户登出失败：token["+userToken.getToken()+"],tokenKey[" + userToken.getTokenKey_Cache_Key()+"] 用户登出出现未知异常。",e);
			return BuildSSOReturnParam.returnLogoutTokenToObject("","",0, "4000002");
		}
	}
	
	
	/**
	 * 获得解密后的CacheKey 标识
	 * @throws IOException 
	 * 
	 */
	private UserToken buildCacheKeyStrToObj(final UserToken userToken) throws IOException{
	       String cacheKey = TokenUtils.decodeCacheKey(userToken.getTokenKey_Cache_Key(),ClientConfigure.getInstance(this.configFile).getCacheIdKey());
	       return UserToken.buildCacheKeyObj(cacheKey, SecurityConstant.splitFlag);
	}
	
	/**
	 * 清除缓存
	 * @param ticketKey
	 * @param ticketCache_Key
	 * @throws IOException 
	 */
	private void clearTicket(String ticketKey ,String ticketCache_Key) throws IOException{
		CacheFactory.getInstance(this.configFile).removeObjectOfCache(ticketKey);
		CacheFactory.getInstance(this.configFile).removeObjectOfCache(ticketCache_Key);
	}
}
