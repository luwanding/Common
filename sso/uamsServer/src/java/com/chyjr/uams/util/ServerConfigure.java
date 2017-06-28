package com.chyjr.uams.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 服务器配置
 * @author jx_xudelin
 *
 */
public class ServerConfigure {
	
	
	//登录超时时间
	private long logintimeout = 0;
	
	//加密CacheIDKey
	private String cacheIdKey = "";
	
	private String defaultfile = "/config/uamsServer.properties";
	
	
	private final static ServerConfigure serverConfigure = new ServerConfigure();

	
	private ServerConfigure(){
		
	}
	
	public synchronized static ServerConfigure getInstance(){
		if(serverConfigure.logintimeout == 0){
			serverConfigure.load();
		}
		return serverConfigure;
	}
	
	private synchronized  void load(final String path){
		InputStream in;
	    Properties prop = new Properties();
		in = serverConfigure.getClass().getResourceAsStream(path);
		try {
			prop.load(in);
			serverConfigure.logintimeout = Long.parseLong(prop.getProperty("uams.login.timeout"));
			serverConfigure.cacheIdKey = prop.getProperty("uams.login.cacheidkey");
			
		} catch (IOException e) {
			System.out.println(ServerConfigure.class.getName() +" 加载 uams.properties 文件出错:"+e.getMessage());
		} finally{
			prop.clear();
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	private  void load(){
		load(defaultfile);
	}
	
	
	
	public long getLogintimeout() {
		return logintimeout;
	}
	
	public String getCacheIdKey() {
		return cacheIdKey;
	}
	
	
}
