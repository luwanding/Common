package com.chyjr.uams.client.remoting;


import com.chyjr.uams.client.util.CString;
import com.chyjr.uams.dto.UserToken;


/**
 * 客户端数据验证类
 * @author jx_xudelin
 *
 */
public class ValidationBean  {
	
	public static boolean validationTokenCheckData(UserToken userLoginBean) {
		String token = userLoginBean.getToken();
		String Token_CacheKey =  userLoginBean.getTokenKey_Cache_Key();
		
		if(CString.isNull(token)){
			//userLoginBean.setOperatorMsg("用户验证:传入token为空。");
			userLoginBean.setOperatorResult(0);
			return false;
		}
		
		if(CString.isNull(Token_CacheKey)){
			//userLoginBean.setOperatorMsg("用户验证:传入Token_CacheKey为空。");
			userLoginBean.setOperatorResult(0);
			return false;
		}
		return true;
	}
	
	public static boolean  validationUserLoginData(UserToken userLoginBean) {
		
		
		if(CString.isNull(userLoginBean.getSessionId())){
			//userLoginBean.setOperatorMsg("用户登录:传入 CookieName 为空");
			userLoginBean.setOperatorResult(0);
			return false;
		}
		
		if(CString.isNull(userLoginBean.getLoginName())){
			///userLoginBean.setOperatorMsg("用户登录:传入 LoginName 为空");
			userLoginBean.setOperatorResult(0);
			return false;
		}
		
		/*if(CString.isNull(userLoginBean.getPassword())){
			userLoginBean.setOperatorMsg("用户登录:传入 Password 为空");
			userLoginBean.setOperatorResult(false);
			return false;
		}*/
		
		/*
		if(CString.isNull(userLoginBean.getSpace())){
			//userLoginBean.setOperatorMsg("用户登录:传入 Space 为空");
			userLoginBean.setOperatorResult(0);
			return false;
		}*/
		
		if(CString.isNull(userLoginBean.getUserID())){
			//userLoginBean.setOperatorMsg("用户登录:传入 UserID 为空");
			userLoginBean.setOperatorResult(0);
			return false;
		}
		
		return true;
	}

	public static boolean validationLogoutData(UserToken userLoginBean) {
		String token = userLoginBean.getToken();
		String Token_CacheKey =  userLoginBean.getTokenKey_Cache_Key();
		
		if(CString.isNull(token)){
			//userLoginBean.setOperatorMsg("用户登出:传入token为空。");
			userLoginBean.setOperatorResult(0);
			return false;
		}
		
		if(CString.isNull(Token_CacheKey)){
			//userLoginBean.setOperatorMsg("用户登出:传入Token_CacheKey为空。");
			userLoginBean.setOperatorResult(0);
			return false;
		}
		return true;
	}
}
