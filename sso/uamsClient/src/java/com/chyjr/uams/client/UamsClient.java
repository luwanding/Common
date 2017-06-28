package com.chyjr.uams.client;

import java.util.HashMap;
import java.util.Map;

import com.chyjr.uams.client.exception.NotInterfaceException;
import com.chyjr.uams.client.remoting.CallSSOHessianService;
import com.chyjr.uams.client.remoting.CallSSOServiceClient;
import com.chyjr.uams.client.remoting.CallSSOWebService;
import com.chyjr.uams.client.remoting.SSOCall;
import com.chyjr.uams.client.util.ClientConfigure;
import com.chyjr.uams.remoting.impl.UserSSOService;
/**
 * SSO 客户端调用方法
 * @author jx_xudelin
 *
 */
public class UamsClient {
	
	private static final Map<String,UserSSOService> OBJECT_CACHE = new HashMap();
	
	private final static UamsClient uamsClient = new UamsClient();
	
	/**
	 * 从指定配置文件中创建一个全局的SSO服务接口
	 * @return
	 */
	public static UserSSOService createFactory(String configFile) throws Exception{
		UserSSOService userSsoService = OBJECT_CACHE.get(configFile +"_"+ ClientConfigure.getInstance(configFile).getConnectType());
		if(userSsoService ==  null){
			synchronized(uamsClient){
				long connTimeout = ClientConfigure.getInstance(configFile).getConnectTimeout();
				long readTimeOut = ClientConfigure.getInstance(configFile).getReadTimeout();
				String url = ClientConfigure.getInstance().getConnectUrl();
				String serviceType = ClientConfigure.getInstance().getConnectType();
				
				if("hessian".equals(serviceType)){
					SSOCall ssoCall = new CallSSOHessianService(connTimeout,readTimeOut, url);
					userSsoService =  new CallSSOServiceClient(ssoCall,configFile);
					
				} else if ("webservice".equals(serviceType)){
					SSOCall ssoCall = new CallSSOWebService(url, (int)connTimeout);
					userSsoService =  new CallSSOServiceClient(ssoCall,configFile);
				}else {
					throw new NotInterfaceException("无法匹配到相对应的服务接口："+ClientConfigure.getInstance().getConnectType());
				}
				
				OBJECT_CACHE.put(configFile +"_"+  ClientConfigure.getInstance().getConnectType() , userSsoService);
			}
		}
		return userSsoService;
	}
	
	/**
	 * 从默认配置文件中创建一个全局的SSO服务接口
	 * @return
	 */
	public static UserSSOService  createFactory() throws Exception{
		return createFactory("/uamsClient.properties");
	}
	
	private UamsClient()
	{
	}
	
	
}
