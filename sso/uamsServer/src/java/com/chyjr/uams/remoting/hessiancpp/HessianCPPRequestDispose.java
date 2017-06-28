package com.chyjr.uams.remoting.hessiancpp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chyjr.uams.dto.UserToken;
import com.chyjr.uams.remoting.impl.UserSSOService;
import com.chyjr.uams.spring.SpringBeanUtil;
import com.chyjr.uams.util.UAMSConstant;

import org.apache.commons.codec.binary.Base64;

public class HessianCPPRequestDispose {
	
	@Autowired
	private static UserSSOService userSSOService ;
	
	private static final Logger logger = Logger.getLogger(HessianCPPRequestDispose.class);
	
	private static String LEN_CHAR = "\r\n";
	
	/**
	 * Hessian C与C++接口 处理方法
	 * @param is
	 * @param os
	 */
	public static void dispose( HttpServletRequest request,HttpServletResponse response){
		
		if(userSSOService == null)
			userSSOService = (UserSSOService)SpringBeanUtil.getContext().getBean("userSSOService");
		logger.info("C与C++接口调用Hessian接口处理开始");
		OutputStream output = null;
		try {
			//读取从客户端传过来的数据
			String operator = request.getParameter("Operator");
			output = response.getOutputStream();
			if(UAMSConstant.CPP_UAMS_OPERATOR_0.equals(operator)){
				UserToken inToken = getLoginData(request);
				
				logger.info("C与C++接口调用Hessian接口处理：登录开始[SessionID:"+inToken.getSessionId()+",UserID:"+inToken.getUserID()+",LoginName:"+inToken.getLoginName()+"]");
				
				UserToken outToken = userSSOService.userSSOLogin(inToken);
				setLoginData(output,outToken);
				
				logger.info("C与C++接口调用Hessian接口处理：登录结束[SessionID:"+inToken.getSessionId()+",UserID:"+inToken.getUserID()+",LoginName:"+inToken.getLoginName()+" ,Result:"+outToken.getOperatorResult()+",ReturnCode"+outToken.getOperatorCode()+"]");
			} else if(UAMSConstant.CPP_UAMS_OPERATOR_1.equals(operator)){
				
				UserToken inToken = getCheckData(request);
				
				logger.info("C与C++接口调用Hessian接口处理：验证开始[Token:"+inToken.getToken()+",TokenCacheKey:"+inToken.getTokenKey_Cache_Key()+"]");
				
				UserToken outToken = userSSOService.tokenSSOCheck(inToken);
				setCheckData(output,outToken);
				
				logger.info("C与C++接口调用Hessian接口处理：验证结束[SessionID:"+outToken.getSessionId()+",UserID:"+outToken.getUserID()+",LoginName:"+outToken.getLoginName()+" ,Result:"+outToken.getOperatorResult()+",ReturnCode"+outToken.getOperatorCode()+"]");
				
			} else if(UAMSConstant.CPP_UAMS_OPERATOR_2.equals(operator)){
				UserToken inToken = getLogoutData(request);
				logger.info("C与C++接口调用Hessian接口处理：登出开始[Token:"+inToken.getToken()+",TokenCacheKey:"+inToken.getTokenKey_Cache_Key()+"]");
			
				UserToken outToken = userSSOService.userSSOLogout(inToken);
				setLogoutData(output,outToken);
				
				logger.info("C与C++接口调用Hessian接口处理：登出结束[Token:"+inToken.getToken()+ ",Result:"+outToken.getOperatorResult()+",ReturnCode"+outToken.getOperatorCode()+"]");
			}else{
				logger.error("C与C++接口调用Hessian接口处理：传入了不能识别的调用标识{"+operator+"}");
			}
			
		} catch (IOException e) {
			logger.error("C与C++接口调用异常："+e.getMessage(), e);
			
		} finally {
			if(output != null){
				try {
					output.flush();
					
				} catch (IOException e) {
					logger.error("C与C++接口调用异常："+e.getMessage(), e);
				}
				
				try {
					output.close();
				} catch (IOException e) {
					logger.error("C与C++接口调用异常："+e.getMessage(), e);
				}
				
			}
		}
		logger.info("C与C++接口调用Hessian接口处理结束");
	}
	
	/**
	 * 获得验证数据
	 * @param hessianInput
	 * @return
	 * @throws IOException 
	 */
	private static UserToken getCheckData(HttpServletRequest request) throws IOException{
		UserToken userToken = new UserToken();
		userToken.setToken(request.getParameter("Token"));
		userToken.setTokenKey_Cache_Key(request.getParameter("CacheKey"));
		return userToken;
	}
	
	/**
	 * 获得登录数据
	 * @param hessianInput
	 * @return
	 * @throws IOException 
	 */
	private static UserToken getLoginData(HttpServletRequest request) throws IOException{
		UserToken userToken = new UserToken();
		userToken.setSessionId(request.getParameter("SessionID"));
		userToken.setUserID(request.getParameter("UserID"));
		userToken.setLoginName(request.getParameter("UserName"));
		return userToken;
	}
	
	/**
	 * 获得登出数据
	 * @param hessianInput
	 * @return
	 * @throws IOException 
	 */
	private static UserToken getLogoutData(HttpServletRequest request) throws IOException{
		UserToken userToken = new UserToken();
		userToken.setToken(request.getParameter("Token"));
		userToken.setTokenKey_Cache_Key(request.getParameter("CacheKey"));
		return userToken;
	}
	
	
	
	
	/**
	 * 设置验证返回数据
	 * @param hessianInput
	 * @return
	 * @throws IOException 
	 */
	private static void setCheckData(OutputStream output,UserToken userToken) throws IOException{
		output.write(("OperatorResult="+userToken.getOperatorResult()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("OperatorCode="+userToken.getOperatorCode()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("SessionID="+userToken.getSessionId()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("UserName="+userToken.getLoginName()).getBytes("UTF-8"));
		output.write(LEN_CHAR.getBytes());
		output.write(("UserID="+userToken.getUserID()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("Token="+userToken.getToken()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("TokenKey="+userToken.getToken_Key()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("CacheKey="+userToken.getTokenKey_Cache_Key()).getBytes());
	}
	
	/**
	 * 设置登录返回数据
	 * @param hessianInput
	 * @return
	 * @throws IOException 
	 */
	private static void setLoginData(OutputStream output,UserToken userToken) throws IOException{
		output.write(("OperatorResult="+userToken.getOperatorResult()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("OperatorCode="+userToken.getOperatorCode()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("Token="+userToken.getToken()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("TokenKey="+userToken.getToken_Key()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("CacheKey="+userToken.getTokenKey_Cache_Key()).getBytes());
	}
	
	/**
	 * 设置登出返回数据
	 * @param hessianInput
	 * @return
	 * @throws IOException 
	 */
	private static void setLogoutData(OutputStream output,UserToken userToken) throws IOException{
		output.write(("OperatorResult="+userToken.getOperatorResult()).getBytes());
		output.write(LEN_CHAR.getBytes());
		output.write(("OperatorCode="+userToken.getOperatorResult()).getBytes());
	}
	
	
	
}
