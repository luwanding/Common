package com.chyjr.uams.common.exception;

public class UamsServerTokenCheckException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UamsServerTokenCheckException(){
		super("SSO服务器端抛出用户验证异常！");
	}
	
	public UamsServerTokenCheckException(String msg){
		super("SSO服务器端抛出用户验证异常:"+msg);
	}
	
	public UamsServerTokenCheckException(String msg,Throwable t){
		super("SSO服务器端抛出用户验证异常:"+msg,t);
	}

}
