package com.chyjr.uams.client.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 *@FILE_NAME: CookiesConfig.java
 *@Descripton:COOKIES配置文件类
 *@author: twotoo
 *@time: 2012-2-16
 *@version: 1.0
 */

public class CookiesConfig {
	private static final Logger Log = Logger.getLogger(CookiesConfig.class);
	private  Properties config;  
	
	public CookiesConfig(){
		try {
			config = new Properties();
			String path = System.getProperty("resources.config.path");
		    if(path == null)
		    	path = System.getenv("resources.config.path");
		    String modle = System.getProperty("app.config.model");
		    if(modle == null){
		    	modle = System.getProperty("app.config.model");
		    }
		    if(modle != null && !"".equals(modle)){
		    	String filePath = path +File.separator +modle + File.separator + "cookies.properties";
		    	InputStream in = new FileInputStream(filePath);
		    	config.load(in);
		    }else{
		    	InputStream in = CookiesConfig.class.getResourceAsStream("/cookies.properties");
		    	config.load(in);
		    }
		}catch (Exception e) {
			Log.error("Cookies 文件加载失败", e);
			e.printStackTrace();
		}   
	}
	
	public CookiesConfig(Properties config){
		this.config = config;
	}

    public CookiesConfig(String pathname){
    	InputStream in;
		try {
			in = new FileInputStream(pathname);
			config.load(in);			
		} catch (Exception e) {
			Log.error("Cookies 文件加载失败", e);
			e.printStackTrace();
		}     	
    }
    
    public  String getDomain(){
    	
    	return config.getProperty("cookie.domain");
    }
    
    public  String getUserTokenName()
    {
        return config.getProperty("cookie.userTokenName");

    }
    
    public  int getLoginTimeOut()
    {
        return Integer.parseInt(config.getProperty("cookie.loginTimeOut"));

    }
    
    public  String getUserTokenKeyCacheName()
    {
        return config.getProperty("cookie.userTokenKeyCacheName");

    }
    
    
    public static void main(String[] args){
    	CookiesConfig c = new CookiesConfig("");
    	System.out.println(c.getUserTokenName());
    }

}

