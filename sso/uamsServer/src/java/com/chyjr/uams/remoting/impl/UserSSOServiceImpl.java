package com.chyjr.uams.remoting.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chyjr.uams.common.cache.MemcacheConstant;
import com.chyjr.uams.common.constant.UAMSConstant;
import com.chyjr.uams.common.param.BuildSSOReturnParam;
import com.chyjr.uams.common.util.CString;
import com.chyjr.uams.dto.UserToken;
import com.chyjr.uams.remoting.impl.UserSSOService;
import com.chyjr.uams.security.HexUtils;
import com.chyjr.uams.security.TokenUtils;
import com.chyjr.uams.security.util.SecurityConstant;
import com.chyjr.uams.util.ServerConfigure;
import com.platform.cache.ICacheControllerService;

/**
 * 用户SSO服务接口实现类
 * @author jx_xudelin
 *
 */
@Service("userSSOService")
public class UserSSOServiceImpl implements UserSSOService {
	
	private final Logger logger = Logger.getLogger(UserSSOServiceImpl.class);
	
	
	@Autowired
	private ICacheControllerService cacheClient;
	
	//登录时间的一半
	private final long LOGIN_HALF_TIME = (ServerConfigure.getInstance().getLogintimeout()*1000)/2;

	/**
	 * 用户登录接口
	 */
	public UserToken userSSOLogin(final UserToken userToken) {
		logger.info("新用户登录："+userToken.getLoginName()+" 开始登录。");
		try{
			//1 、用户登录
			UserToken result = ssoLogin(userToken);
			logger.info("新用户登录："+userToken.getLoginName()+" 登录结束。");
			return result;
		}catch(Exception e){
			logger.error("用户："+userToken.getLoginName()+" 登录失败。",e);
			return BuildSSOReturnParam.returnLoginTokenToObject("", "", "", 0, "0000001");
		}
		
	}
	
	/**
	 * 用户登出接口
	 * @param param
	 * @return
	 */
	public UserToken userSSOLogout(final UserToken userToken) {
		try{
			logger.info("用户登出：token["+userToken.getToken()+"] CacheKey["+ userToken.getTokenKey_Cache_Key() +"] 开始登出。");
			//1、校验
			if(check(userToken)){
				logger.error("用户登出失败：token["+userToken.getToken()+"],tokenKey[" + userToken.getTokenKey_Cache_Key()+"] 数据校验无法通过。");
				return BuildSSOReturnParam.returnLogoutTokenToObject("", "", 0, "1000001");
			}
			
			//先解密CacheKey
			UserToken userTokenCacheKeyObj = decodeCacheKey(userToken);
			if(userTokenCacheKeyObj == null){
				logger.error("用户登出失败：token["+userToken.getToken()+"],tokenKey[" + userToken.getTokenKey_Cache_Key()+"] 无法解出CacheKey["+userToken.getTokenKey_Cache_Key()+"]。");
				return BuildSSOReturnParam.returnLogoutTokenToObject("", "", 0, "1000002");
			}
			
			logger.info("用户登出：userID ["+userTokenCacheKeyObj.getUserID()+"],CacheKey["+ userToken.getTokenKey_Cache_Key() +"] 开始登出。");
			
			//清除缓存
			clearTicket(userTokenCacheKeyObj);

			logger.info("用户："+userTokenCacheKeyObj.getUserID()+" 登出成功。");
		    return BuildSSOReturnParam.returnLogoutTokenToObject(userTokenCacheKeyObj.getUserID(),userTokenCacheKeyObj.getSessionId(), 1, "1000000");
		}catch(Exception e){
			logger.error("用户：Token["+userToken.getToken() +"] ["+userToken.getTokenKey_Cache_Key()+"] 登出失败。",e);
			return BuildSSOReturnParam.returnLogoutTokenToObject("","", 0, "1000003");
		}
	}
	
	/**
	 * 票据验证
	 * @param param
	 * @return 
	 */
	public UserToken tokenSSOCheck(final UserToken userToken){
		
		try{
			
			logger.info("用户校验：token["+userToken.getToken()+"] CacheKey["+ userToken.getTokenKey_Cache_Key() +"] 开始校验。");
			//1、数据校验
			if(check(userToken)){
				logger.error("用户校验失败：{"+userToken.toString()+"} 数据校验无法通过。");
				return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "2000001");
			}
			
			logger.info("用户校验：token["+userToken.getToken()+"] CacheKey["+ userToken.getTokenKey_Cache_Key() +"] 开始解密CacheKey。");
			//2、先解密CacheKey
			UserToken userTokenCacheKeyObj = decodeCacheKey(userToken);
			if(userTokenCacheKeyObj == null){
				logger.error("用户校验失败：{"+userToken.toString()+"} 无法解出CacheKey。");
				return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "2000002");
			}
			
			logger.info("用户校验：UserID["+userTokenCacheKeyObj.getUserID()+"] CacheKey["+ userToken.getTokenKey_Cache_Key() +"] CacheKey解密完成。");
			
			//3、获得TOKEN动态加密串,如果已无法取到key那么系统就返回校验失败的标识
			String token_encrypt_key = getCacheKeyByCache(userTokenCacheKeyObj);
			if(CString.isNull(token_encrypt_key)){
				logger.error("用户校验 :{"+userToken.toString()+"} 用户登录超时，已无法找到缓存中的Token解密串！");
				return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "2000003");
			}
			
			logger.info("用户校验：UserID["+userTokenCacheKeyObj.getUserID()+"] TokenKey["+ token_encrypt_key +"] 获取Token解密Key完成。");
			
			//4、解密TOKEN串
			UserToken userTokenObj = decodeTicket(userToken,token_encrypt_key);
			if(userTokenObj == null){
				logger.error("用户校验 :{"+userToken.toString()+"} 无法解密 。");
				return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "2000004");
			}
			
			logger.info("用户校验：UserID["+userTokenObj.getUserID()+"] UserName["+ userTokenObj.getLoginName() +"] Token解密完成。");
			
			long loginedTimeStamp = System.currentTimeMillis() - userTokenObj.getLoginTimeStamp();
			//5、如果登录已超时
			if(loginedTimeStamp > (ServerConfigure.getInstance().getLogintimeout()*1000) ){
				clearTicket(userTokenObj);
				logger.error("用户校验: 校验用户登录已超时。{"+userTokenObj.toString()+"}");
				return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "2000006");
			}
			
			logger.info("用户校验：UserID["+userTokenObj.getUserID()+"] UserName["+ userTokenObj.getLoginName() +"] 登录超时验证完成。");
			
			
			//6、校验用户正确性
			if(userTokenObj.getUserID().equals(userTokenCacheKeyObj.getUserID()) 
					&& userTokenObj.getSessionId().equals(userTokenCacheKeyObj.getSessionId()))
			{
				logger.info("用户校验：UserID["+userTokenObj.getUserID()+"] UserName["+ userTokenObj.getLoginName() +"] 登录信息验证完成开始检查是否需要重新登录。");
				
				//6.1、如果离超时间还有一半左右，就重新生成Ticket
				if ( loginedTimeStamp >= LOGIN_HALF_TIME) 
				{
					//6.1.1、删除旧Ticket
					clearTicket(userTokenObj);
					
					String userName = userTokenObj.getLoginName();
					String userID = userTokenObj.getUserID();
					String sessionID = userTokenObj.getSessionId();
					
					//6.1.2、重新登录
					userTokenObj = ssoLogin(userTokenObj);
					logger.info("用户："+userName+" 重新登录完成登录结果为：["+userTokenObj.getOperatorResult()+"]");
					
					
					if(userTokenObj.getOperatorResult() == 1){
					   userTokenObj.setOperatorResult(2);
					   userTokenObj.setOperatorCode("2000000");
					   userTokenObj.setLoginName(userName);
					   userTokenObj.setUserID(userID);
					   userTokenObj.setSessionId(sessionID);
					}
				}else{
					logger.info("用户校验：UserID["+userTokenObj.getUserID()+"] UserName["+ userTokenObj.getLoginName() +"] 校验信息正常完成。");
					userTokenObj.setToken(userToken.getToken());
					userTokenObj.setToken_Key(token_encrypt_key);
					userTokenObj.setTokenKey_Cache_Key(userToken.getTokenKey_Cache_Key());
					userTokenObj.setOperatorResult(1);
					userTokenObj.setOperatorCode("2000000");
				}
				logger.info("用户校验：UserID["+userTokenObj.getUserID()+"] UserName["+ userTokenObj.getLoginName() +"] Result["+userTokenObj.getOperatorResult()+"] 校验完成。");
				//7、 校验成功
				return BuildSSOReturnParam.returnCheckTokenToObject(userTokenObj.getUserID(), 
						userTokenObj.getSessionId(), 
						userTokenObj.getLoginName(), 
						userTokenObj.getToken(), 
						userTokenObj.getToken_Key(), 
						userTokenObj.getTokenKey_Cache_Key(),
						userTokenObj.getOperatorResult(), userTokenObj.getOperatorCode());
				
			}else{
				logger.error("用户校验: 校验用户信息不相等。Token LoginName ["+userTokenObj.getLoginName()+"] CacheKey LoginName["+userTokenCacheKeyObj.getLoginName()+"]");
				return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "2000005");
			}
		} catch(Exception e){
			logger.error("用户：Token["+userToken.getToken() +"] ["+userToken.getTokenKey_Cache_Key()+"] 校验失败。",e);
			return BuildSSOReturnParam.returnCheckTokenToObject("", "", "", "", "", "", 0, "2000007");
		}
	}
	
	
	/**
	 * 读取缓存中的TICKET动态加密串
	 * @param userToken
	 * @return
	 */
	private String getCacheKeyByCache(final UserToken userToken){
		String ticketCacheKey = MemcacheConstant.getToken_encryptKey_key(userToken);
		return (String)cacheClient.getCacheClient().get(ticketCacheKey);
	}
	
	/**
	 * 解密cacheKey
	 * @param userToken
	 * @return
	 */
	private UserToken decodeCacheKey(final UserToken userToken){
		//先解密CacheKey
		String cacheKey = TokenUtils.decodeCacheKey(userToken.getTokenKey_Cache_Key(),ServerConfigure.getInstance().getCacheIdKey());
		return UserToken.buildCacheKeyObj(cacheKey, SecurityConstant.splitFlag);
	}
	
	/**
	 * 解密Ticket串
	 * @param userToken  存储加密Ticket串的对象
	 * @param key 解密码
	 * @return
	 */
	private UserToken decodeTicket(final UserToken userToken, String key){
		//解密TOKEN串
		String tokenStr = TokenUtils.decodeToken(userToken.getToken(),key);
		return UserToken.buildTokenObj(tokenStr, SecurityConstant.splitFlag);
	}
	
	/**
	 * 校验用户退出、验证 时校验数据方法
	 * @param userToken
	 * @return
	 */
	private boolean check(final UserToken userToken ){
		return CString.isNull( userToken.getToken()) || CString.isNull(userToken.getTokenKey_Cache_Key());
	}
	
	/**
	 * 清除用户Ticket标识
	 * @param userToken
	 */
	private void clearTicket(final UserToken userToken ){
		//删除TOKEN
		cacheClient.getCacheClient().remove(MemcacheConstant.getToken_Key(userToken));
		//删除Token Key
		cacheClient.getCacheClient().remove(MemcacheConstant.getToken_encryptKey_key(userToken));
	}
	
	/**
	 * 用户登录方法
	 * @param userToken
	 */
	private UserToken ssoLogin(final UserToken userToken){
		
		String tokenStr = userToken.buildTokenStr(UAMSConstant.splitFlag);
		String token = "";
		String tokenKey = "";
		String cacheKey = "";
		
		if(tokenStr == null){
			return BuildSSOReturnParam.returnLoginTokenToObject(token, tokenKey, cacheKey, 0, "0000001");
		}else{
			try{
				//生成动态加密KEY
				tokenKey = HexUtils.getEncryptKey();
				logger.info("用户："+userToken.getLoginName()+" 生成TokenKey["+tokenKey+"]。");
				
				userToken.setLoginTimeStamp(System.currentTimeMillis());
				//生成Token
				token = TokenUtils.generateToken( tokenStr,tokenKey);
				logger.info("用户："+userToken.getLoginName()+" 生成token["+tokenKey+"]。");
				
				//把Token存入缓存
				String token_key = MemcacheConstant.getToken_Key(userToken);
				cacheClient.getCacheClient().putTimeOut(token_key, token,(int)ServerConfigure.getInstance().getLogintimeout() );
				
				logger.info("用户："+userToken.getLoginName()+" Token 已缓存 token_key["+token_key+"] token["+token+"]。");
				
				//把 解密Token 的Key 存入缓存
				String token_encryptKey_key = MemcacheConstant.getToken_encryptKey_key(userToken);
				cacheClient.getCacheClient().putTimeOut(token_encryptKey_key, tokenKey,(int)ServerConfigure.getInstance().getLogintimeout());
				
				logger.info("用户："+userToken.getLoginName()+" Token解密Key 已缓存 token_encryptKey_key["+token_encryptKey_key+"] tokenKey["+tokenKey+"]。");
				
				//加密存储动态密码的CacheID
				cacheKey = TokenUtils.generateCacheKey(userToken.buildCacheKeyStr(userToken,SecurityConstant.splitFlag),ServerConfigure.getInstance().getCacheIdKey());
				
				
				logger.info("用户："+userToken.getLoginName()+" 登录正常。");
				return BuildSSOReturnParam.returnLoginTokenToObject(token, tokenKey, cacheKey, 1, "0000000");
			}catch (Exception e){
				logger.error("用户："+userToken.getLoginName()+" 登录出现异常。",e);
				return BuildSSOReturnParam.returnLoginTokenToObject(token, tokenKey, cacheKey, 0, "0000001");
			}
			
		}
	}
	
	
	public static void main(String[] args){
	}
	
}