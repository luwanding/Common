package com.chyjr.uams.client.exception;

/**
 * 无法找到服务接口异常类
 * @author jx_xudelin
 *
 */
public class NotInterfaceException extends Exception {
	
	public NotInterfaceException(String msg){
		super(msg);
	}
	public NotInterfaceException(String msg, java.lang.Throwable t){
		super(msg,t);
	}

}
