package com.chyjr.uams.security.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {
	//登录超时时间
	private long logintimeout = 0;
	
	//加密CacheIDKey
	private String cacheIdKey = "";
	
	private String defaultfile = "/config/uamsServer.properties";
	
	
	private final static Config serverConfigure = new Config();

	
	private Config(){
		
	}
	
	public synchronized static Config getInstance(){
		if(serverConfigure.logintimeout == 0){
			serverConfigure.load();
		}
		return serverConfigure;
	}
	
	private synchronized  void load(final String path){
		InputStream in;
	    Properties prop = new Properties();
	    File file = new File("D:\\work\\sso\\Trunk\\系统开发\\sourceCode\\uams\\src\\resources\\config\\uamsServer.properties");
		in = serverConfigure.getClass().getResourceAsStream(path);
		try {
			prop.load(new java.io.FileInputStream(file));
			serverConfigure.logintimeout = Long.parseLong(prop.getProperty("uams.login.timeout"));
			serverConfigure.cacheIdKey = prop.getProperty("uams.login.cacheidkey");
			
		} catch (IOException e) {
			System.out.println(Config.class.getName() +" 加载 uams.properties 文件出错:"+e.getMessage());
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
