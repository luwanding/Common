package com.chyjr.platform.search;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ProjectName:  [PlatformSearchClient]
 * @Package:      [com.chyjr.platform.search.SolrSearchServer.java] 
 * @ClassName:    [SolrSearchServerConfig]  
 * @Description:  [Solr HTTP 客户端 配置]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:41:14 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:41:14 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class SolrSearchServerConfig {
	
	 private String url ;
	 private int soTimeout;//http Client 等待读取超时
	 private int connectionTimeout;//http 连接服务器超时
	 private int defaultMaxConnectionsPerHost;//允许一个客户端开启的最大连接数量
	 private int maxTotalConntion;//允许开启连接的最大值
	 private boolean followRedirects;//是否允许请求转发与重定向
	 private boolean allowCompression; //是否做传输压缩
	 private int maxRetries;//错误重试次数
	 private String clientId ;//客户端编号
	 
	 private static volatile Map<String,SolrSearchServerConfig> config = new  HashMap<String,SolrSearchServerConfig>(10);
	 private static Logger logger = Logger.getLogger(SolrSearchServerConfig.class);
	 
	/**
	  * 通过指定Solr客户端配置文件地址，加载配置。
	  * @param path
	 * @throws DocumentException 
	  */
	 public static Map<String,SolrSearchServerConfig> getInstance(String path) throws Exception{
		 if(config.size() == 0){
			 synchronized(config){
				 if(config.size() == 0){
					 Document doc;
					 SAXReader saxRead = new SAXReader();
					 File xmlFile = new File(path);
					 if(xmlFile.isFile()){
					   doc = saxRead.read(xmlFile);
				       Element root = doc.getRootElement();
				       List<Element> eles = root.elements("client");

				       for(int i = 0; i < eles.size(); i ++){
				    	   Element client = eles.get(i);
                           SolrSearchServerConfig solrConfig = new SolrSearchServerConfig();
				    	   solrConfig.url = client.element("url").getData().toString();
				    	   solrConfig.soTimeout = Integer.parseInt(client.element("soTimeout").getData().toString());
				    	   solrConfig.connectionTimeout = Integer.parseInt(client.element("connectionTimeout").getData().toString());
				    	   solrConfig.defaultMaxConnectionsPerHost = Integer.parseInt(client.element("defaultMaxConnectionsPerHost").getData().toString());
				    	   solrConfig.maxTotalConntion =  Integer.parseInt(client.element("maxTotalConntion").getData().toString());
				    	   solrConfig.followRedirects = Boolean.parseBoolean(client.element("followRedirects").getData().toString());
				    	   solrConfig.allowCompression = Boolean.parseBoolean(client.element("allowCompression").getData().toString());
				    	   solrConfig.maxRetries =  Integer.parseInt(client.element("maxRetries").getData().toString());
				    	   solrConfig.clientId = client.attributeValue("id");
				    	   config.put(solrConfig.clientId, solrConfig);
				       }
					 }else{
						 logger.error("solr 客户端配置文件不存在["+path+"].");
					 }
				 }
			 }
		 }
		 return config;
	 }
	 
	 
	 
	 
	public String getClientId() {
		return clientId;
	}

	public String getUrl() {
			return url;
		}



		public Integer getSoTimeout() {
			return soTimeout;
		}



		public Integer getConnectionTimeout() {
			return connectionTimeout;
		}



		public int getDefaultMaxConnectionsPerHost() {
			return defaultMaxConnectionsPerHost;
		}



		public int getMaxTotalConntion() {
			return maxTotalConntion;
		}



		public boolean isFollowRedirects() {
			return followRedirects;
		}



		public boolean isAllowCompression() {
			return allowCompression;
		}



		public int getMaxRetries() {
			return maxRetries;
		}
}
