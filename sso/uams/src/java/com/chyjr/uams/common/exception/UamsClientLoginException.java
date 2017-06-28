package com.chyjr.uams.common.exception;

/**
 * SSO客户端登录异常类
 * @author jx_xudelin
 *
 */
public class UamsClientLoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UamsClientLoginException(){
		super("SSO服务器端抛出用户登录异常！");
	}
	
	public UamsClientLoginException(String msg){
		super("SSO服务器端抛出用户登录异常:"+msg);
	}
	
	public UamsClientLoginException(String msg,Throwable t){
		super("SSO服务器端抛出用户登录异常:"+msg,t);
	}
}
