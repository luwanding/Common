package com.chyjr.platform.search;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @ProjectName:  [PlatformSearchClient]
 * @Package:      [com.chyjr.platform.search.SolrSearchServer.java] 
 * @ClassName:    [SolrSearchServer]  
 * @Description:  [获得、初始化Solr HTTP 客户端]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:41:14 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:41:14 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class SolrSearchServer {
	  
	private static Map<String,HttpSolrServer> httpQueryServers = new HashMap<String,HttpSolrServer>();
	private static Logger logger = Logger.getLogger(SolrSearchServer.class);

    public SolrSearchServer(String configPath){
        try {
			init( configPath );
		} catch (Exception e) {
			logger.error("初始化搜索客户端异常："+e.getMessage() , e);
		}
	 }
	 
	 /**
	  * 初始化Solr HTTP Client
	 * @throws Exception 
	  */
	 private synchronized void init(String configPath) throws Exception{
		 if(httpQueryServers.size() == 0){
			 Map<String,SolrSearchServerConfig> solrConfigs = SolrSearchServerConfig.getInstance(configPath);
			 logger.debug("开始初始化Solr查询客户端：################################");
			 
			 for(Entry<String, SolrSearchServerConfig> entry : solrConfigs.entrySet()){
			   	HttpSolrServer httpQueryServer = new HttpSolrServer(entry.getValue().getUrl());  
			   	httpQueryServer.setSoTimeout(entry.getValue().getSoTimeout());  // socket read timeout  
			   	httpQueryServer.setConnectionTimeout(entry.getValue().getConnectionTimeout());  
			   	httpQueryServer.setDefaultMaxConnectionsPerHost(entry.getValue().getMaxTotalConntion());  
			   	httpQueryServer.setMaxTotalConnections(entry.getValue().getMaxTotalConntion());
			    httpQueryServer.setFollowRedirects(entry.getValue().isFollowRedirects());  // defaults to false
			    //allowCompression defaults to false.  
			    //Server side must support gzip or deflate for this to have any effect.  
			    //是否采用gzip压缩传输
			    httpQueryServer.setAllowCompression(entry.getValue().isAllowCompression());  
			    httpQueryServer.setMaxRetries(entry.getValue().getMaxRetries()); // defaults to 0.  > 1 not recommended.
			    httpQueryServers.put(entry.getValue().getClientId(), httpQueryServer);
			    printStartLog(entry.getValue());
			 }
			 logger.debug("已完成初始化Solr查询客户端：################################");
		 }
		 
	}
	 
	 /**
	  * 打印solr http客户端 启动日志
	  * @param cfg
	  */
	private void printStartLog(SolrSearchServerConfig cfg){
		StringBuffer logstr = new StringBuffer();
	    logstr.append("启动Solr HTTP client:");
	    logstr.append(cfg.getClientId());
	    logstr.append("\r\n");
	    logstr.append("url:");
	    logstr.append(cfg.getUrl());
	    logstr.append("\r\n");
	    logstr.append("SoTimeout:");
	    logstr.append(cfg.getSoTimeout());
	    logstr.append("\r\n");
	    logstr.append("ConnectionTimeout:");
	    logstr.append(cfg.getConnectionTimeout());
	    logstr.append("\r\n");
	    logstr.append("DefaultMaxConnectionsPerHost:");
	    logstr.append(cfg.getDefaultMaxConnectionsPerHost());
	    logstr.append("\r\n");
	    logstr.append("MaxTotalConnections:");
	    logstr.append(cfg.getMaxTotalConntion());
	    logstr.append("\r\n");
	    logstr.append("FollowRedirects:");
	    logstr.append(cfg.isFollowRedirects());
	    logstr.append("\r\n");
	    logstr.append("AllowCompression:");
	    logstr.append(cfg.isAllowCompression());
	    
	    logstr.append("\r\n");
	    logstr.append("MaxRetries:");
	    logstr.append(cfg.getMaxRetries());
	    logstr.append("\r\n\r\n\r\n");
	    logger.debug(logstr.toString());
	}
	    
	    /**
	     * 获得Solr的查询实例
	     * @return HttpSolrServer 对象
	     */
	    public HttpSolrServer getInstance(String clientId){
	    	return httpQueryServers.get(clientId);
	    }


}
