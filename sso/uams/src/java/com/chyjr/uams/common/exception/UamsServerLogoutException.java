package com.chyjr.uams.common.exception;

/**
 * 服务器端登出异常类
 * @author jx_xudelin
 *
 */
public class UamsServerLogoutException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UamsServerLogoutException(){
		super("SSO服务器端抛出用户登出异常！");
	}
	
	public UamsServerLogoutException(String msg){
		super("SSO服务器端抛出用户登出异常:"+msg);
	}
	
	public UamsServerLogoutException(String msg,Throwable t){
		super("SSO服务器端抛出用户登出异常:"+msg,t);
	}
}
