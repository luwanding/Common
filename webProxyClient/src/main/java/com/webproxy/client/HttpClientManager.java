package com.webproxy.client;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获得HTTPS Client实例类
 * @author xudelin
 *
 */
 
@SuppressWarnings("deprecation")
public class HttpClientManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientManager.class);  
	
	private static final PoolingHttpClientConnectionManager phm = new PoolingHttpClientConnectionManager();
    
	static {
		phm.setMaxTotal(128);  
		phm.setDefaultMaxPerRoute(128);  
	}
	
	public static CloseableHttpClient getHttpClient(BasicCookieStore cookieStore) {
		 CloseableHttpClient hc = HttpClients.custom()
				 	.setDefaultCookieStore(cookieStore)
	                .setConnectionManager(phm)
	                .build();
		 return hc;
	}
	
	public static CloseableHttpClient getHttpsClient() {  
        try {  
        	  SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                  //信任所有
                  public boolean isTrusted(X509Certificate[] chain,
                                  String authType) throws CertificateException {
                      return true;
                  }
              }).build();
              SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
              return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception ex) {
            LOG.error("创建HTTPS安全连接时出现异常：", ex);
            return HttpClients.createDefault();
        }  
    }  
   
}
