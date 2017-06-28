package com.chyjr.uams.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.chyjr.uams.client.web.CookiesConfig;

/**
 * 
 * @author jx_xudelin
 *
 */
public class ClientConfigure {
	
	
	//与服务端通信通道类型
	private String connectType = "";
	//与服务端通信地址
	private String connectUrl = "";
	//是否启用本地缓存
	private boolean isCache ;
	//本地缓存最大大小
	private long    maxCacheSize;
	//本地缓存元素的生命最长缓存时间
	private long    maxLifeTime;
	//建立连接超时时间
	private long connectTimeout = 5000;

	//读操作超时时间
	private long readTimeout=5000;
	
	private static String defaultfile = "/uamsClient.properties"; 
	
	
	private final static Map<String,ClientConfigure> CLIENT_CONFIG_CACHE = new HashMap();

	
	
	
	private ClientConfigure(){
		
	}
	
	//加密CacheIDKey
	private String cacheIdKey = "";
	
	public  static synchronized ClientConfigure getInstance(String file) throws IOException{
		ClientConfigure clientConfigure = CLIENT_CONFIG_CACHE.get(file);
		if(CLIENT_CONFIG_CACHE.size() == 0 || clientConfigure == null){
			clientConfigure = load(file);
			CLIENT_CONFIG_CACHE.put(file, clientConfigure);
		}
		return clientConfigure;
	}
	
	
	public  static ClientConfigure getInstance() throws IOException{
		ClientConfigure clientConfigure = CLIENT_CONFIG_CACHE.get(ClientConfigure.defaultfile);
		if(CLIENT_CONFIG_CACHE.size() == 0 || clientConfigure == null){
			return getInstance(ClientConfigure.defaultfile);
		}
		return clientConfigure;
	}
	
	
	private synchronized static  ClientConfigure load(String file) throws IOException{
		InputStream in;
	    Properties prop = new Properties();
	    
	    ClientConfigure  clientConfigure = new ClientConfigure();
	    String path = System.getProperty("resources.config.path");
	    if(path == null)
	    	path = System.getenv("resources.config.path");
	    String modle = System.getProperty("app.config.model");
	    if(modle == null){
	    	modle = System.getProperty("app.config.model");
	    }

	    if(modle != null && !"".equals(modle)){
	    	String filePath = path +File.separator +modle + File.separator + file;
	    	in = new FileInputStream(filePath);	    	
	    }else{
	    	in = clientConfigure.getClass().getResourceAsStream(file);
	    }
	    
		try {
			prop.load(in);
			
			clientConfigure.connectType = prop.getProperty("UAMS.ConnectType");
			clientConfigure.connectUrl = prop.getProperty("UAMS.ConnectUrl");
			clientConfigure.isCache = Boolean.parseBoolean(prop.getProperty("UAMS.IsCache"));
			clientConfigure.cacheIdKey = prop.getProperty("uams.login.cacheidkey");
			if(clientConfigure.isCache){
				clientConfigure.maxCacheSize = Long.parseLong(prop.getProperty("UAMS.MaxCacheSize"));
				clientConfigure.maxLifeTime = Long.parseLong(prop.getProperty("UAMS.MaxLifeTime"));
			}
			return clientConfigure;
		} catch (IOException e) {
			System.out.println(ClientConfigure.class.getName() +" 加载 config.properties 文件出错:"+e.getMessage());
			throw e;
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
	
	
	public void setConnectType(String connectType) {
        this.connectType = connectType;
    }


    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }


    public void setCache(boolean isCache) {
        this.isCache = isCache;
    }


    public void setMaxCacheSize(long maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }


    public void setMaxLifeTime(long maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }


    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }


    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }


    public void setCacheIdKey(String cacheIdKey) {
        this.cacheIdKey = cacheIdKey;
    }


    public String getCacheIdKey() {
		return cacheIdKey;
	}
	
	public long getConnectTimeout() {
		return connectTimeout;
	}

	public  long getReadTimeout() {
		return readTimeout;
	}
	
	public String getConnectType() {
		return connectType;
	}

	public String getConnectUrl() {
		return connectUrl;
	}

	public boolean getIsCache() {
		return isCache;
	}

	public long getMaxCacheSize() {
		return maxCacheSize;
	}

	public long getMaxLifeTime() {
		return maxLifeTime;
	}
	
}
