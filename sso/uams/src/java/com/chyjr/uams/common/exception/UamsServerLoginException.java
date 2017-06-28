package com.chyjr.uams.common.exception;

/**
 * SSO服务器端登录异常类
 * @author jx_xudelin
 *
 */
public class UamsServerLoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UamsServerLoginException(){
		super("SSO服务器抛出用户登录异常！");
	}
	
	public UamsServerLoginException(String msg){
		super("SSO服务器端抛出用户登录异常:"+msg);
	}
	
	public UamsServerLoginException(String msg,Throwable t){
		super("SSO服务器端抛出用户登录异常:"+msg,t);
	}
}
