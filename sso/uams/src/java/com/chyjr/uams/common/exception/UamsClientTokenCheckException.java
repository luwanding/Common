package com.chyjr.uams.common.exception;

public class UamsClientTokenCheckException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UamsClientTokenCheckException(){
		super("SSO客户端抛出用户验证异常！");
	}
	
	public UamsClientTokenCheckException(String msg){
		super("SSO客户端抛出用户验证异常:"+msg);
	}
	
	public UamsClientTokenCheckException(String msg,Throwable t){
		super("SSO客户端抛出用户验证异常:"+msg,t);
	}

}
