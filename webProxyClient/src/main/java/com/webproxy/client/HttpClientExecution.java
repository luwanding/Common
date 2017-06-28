package com.webproxy.client;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * HTTP客户端工具类， 包括分发HTTP请求到代理服务器。
 * 
 * @author xudelin
 *
 */
public class HttpClientExecution {

	private static final short HTTP_STATUS_CODE_301 = 301;
	private static final short HTTP_STATUS_CODE_302 = 302;
	private static final String UC_AGENT_FLAG = "User-Agent";
	private static final int SOCK_TIMEOUT = 5000;
	private static final int CONN_TIMEOUT = 5000;

	private static Logger Log = Logger.getLogger(HttpClientExecution.class);

	private static final List<String> AGENT_FLAG = new ArrayList<String>();
	private static final List<HttpHost> SERVER_IP = new ArrayList<HttpHost>();

	static {
		Properties pp = new Properties();
		try {
			pp.load(HttpClientExecution.class.getResourceAsStream("/webProxyClient.properties"));

			// 加载多个Client agent 用于
			String agent = pp.getProperty("uc_agent",
							"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
			String[] agents = agent.split("&&");
			for (int i = 0; i < agents.length; i++) {
				AGENT_FLAG.add(agents[i]);
			}

			String server = pp.getProperty("proxy_server", "localhost:9999");
			String[] servers = server.split("&&");
			for (int i = 0; i < servers.length; i++) {
				String[] serverAddress = servers[i].split(":");
				HttpHost inet = new HttpHost(serverAddress[0], Integer.parseInt(serverAddress[1]));
				SERVER_IP.add(inet);
			}
		} catch (IOException e) {
			Log.error("加载HTTPClient参数文件[./webProxyClient.properties]异常", e);
		}
	}
	
	private static final Random r = new Random(SERVER_IP.size());
	
	private static HttpHost getProxyServer() {
		Log.debug(MessageFormat.format("开始从[{0}]个代理服务器中随机获取代理, timestamp:{1}", SERVER_IP.size(), System.currentTimeMillis()));
		HttpHost host = SERVER_IP.get(r.nextInt(SERVER_IP.size()));
		Log.debug(MessageFormat.format("选择好的代理服务器为：{0}, timestamp:{1}", host, System.currentTimeMillis()));
		return host;
	}
	
	private static String getUC_Agent() {
		Random random = new Random();
		return AGENT_FLAG.get(random.nextInt(AGENT_FLAG.size()));
	}
	
	/**
	 * 发起post 请求
	 * @param client
	 * @param parames
	 * @param encode
	 * @param url
	 * @param headerMap
	 * @return
	 */
	public static HttpResult doPost(CloseableHttpClient client,
			List<NameValuePair> parames, String encode, String url,
			Map<String, String> headerMap) {
		CloseableHttpResponse response = null;
		HttpEntity resp_entity = null;
		HttpHost proxy = getProxyServer();
		Log.debug(MessageFormat
				.format("开始调用 http post 方法 所用代理为：{0} \r\n 请求参数:[url:{1} \r\n parames:{2} \r\n encode:{3} \r\n http_head:{4} \r\n timestamp:{5} ",
						proxy, url, parames, encode, headerMap, System.currentTimeMillis()));
		HttpPost httppost = new HttpPost(url);
		try {
			RequestConfig config = RequestConfig.custom()
					.setSocketTimeout(SOCK_TIMEOUT)
					.setConnectTimeout(CONN_TIMEOUT)
					.setConnectionRequestTimeout(CONN_TIMEOUT).setProxy(proxy)
					.build();
			httppost.setConfig(config);

			UrlEncodedFormEntity entity = null;
			if (StringUtils.isBlank(encode)) {
				entity = new UrlEncodedFormEntity(parames);
			} else {
				entity = new UrlEncodedFormEntity(parames, encode);
			}
			httppost.setEntity(entity);
			if (headerMap != null) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					httppost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			httppost.setHeader("Accept-Encoding", "gzip, deflate, sdch"); 
			httppost.setHeader(UC_AGENT_FLAG, getUC_Agent());
			response = client.execute(httppost);
			HttpResult result = new HttpResult();
			resp_entity = response.getEntity();
			result.setResponseByte(EntityUtils.toByteArray(resp_entity));
			result.setResponseHeads(response.getAllHeaders());
			result.setResponseCode((short) response.getStatusLine().getStatusCode());
			if (HTTP_STATUS_CODE_301 == response.getStatusLine().getStatusCode()
					|| HTTP_STATUS_CODE_302 == response.getStatusLine().getStatusCode()) {
				if (response.getHeaders("Location").length > 0)
					result.setLocation(response.getHeaders("Location")[0].getValue());
			}
			Log.debug("doPost 调用完成准备返回结果, timestamp:" + System.currentTimeMillis());
			return result;
		} catch (Exception e) {
			httppost.abort();
			Log.error(
					MessageFormat
							.format("调用dopost 方法异常：\r\nhttp post 方法 所用代理为：{0} \r\n 请求参数:[url:{1} \r\n parames:{2} \r\n encode:{3} \r\n http_head:{4} \r\n timestamp:{5} ",
									proxy, url, parames, encode, headerMap, System.currentTimeMillis()), e);

		} finally {
			release(response, resp_entity);
		}
		return null;
	}

	private static void release(CloseableHttpResponse response,
			HttpEntity resp_entity) {
		Log.debug("开始释放 CloseableHttpResponse 与 entity, timestamp:" + System.currentTimeMillis());
		if (resp_entity != null)
			try {
				EntityUtils.consume(resp_entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (response != null) {
			try {
				response.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Log.debug("释放 CloseableHttpResponse 与 entity 完成, timestamp:" + System.currentTimeMillis());

	}

	/**
	 * @param client
	 * @param
	 * @param url
	 * @param headerMap
	 * @return
	 */
	public static HttpResult doGet(CloseableHttpClient client, String url,
			Map<String, String> headerMap, Header[] headers) {
		CloseableHttpResponse response = null;
		HttpEntity resp_entity = null;
		HttpHost proxy = getProxyServer();
		Log.debug(MessageFormat
				.format("开始调用 http get 方法 所用代理为：{0} \r\n 请求参数:[url:{1}  \r\n http_head:{2} \r\n timestamp:{3}",
						proxy, url, headerMap, System.currentTimeMillis()));
		HttpGet httpget = new HttpGet(url);
		try {
			if (headerMap != null) {
				headerMap.put("Accept", "*/*");		
				headerMap.put("Accept-Encoding", "gzip, deflate");
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					httpget.setHeader(entry.getKey(), entry.getValue());
				}
			}
			
			httpget.setHeader(UC_AGENT_FLAG, getUC_Agent());
			RequestConfig config = RequestConfig.custom()
					.setSocketTimeout(SOCK_TIMEOUT)
					.setConnectTimeout(CONN_TIMEOUT)
					.setConnectionRequestTimeout(CONN_TIMEOUT).setProxy(proxy)
					.build();
			httpget.setConfig(config);
			if (headers != null)
				httpget.setHeaders(headers); 
			
			response = client.execute(httpget);
			HttpResult result = new HttpResult();
			
			result.setResponseByte(EntityUtils.toByteArray(response.getEntity()));
			result.setResponseHeads(response.getAllHeaders());
			result.setResponseCode((short) response.getStatusLine()
					.getStatusCode());
			if (HTTP_STATUS_CODE_301 == response.getStatusLine().getStatusCode()
					|| HTTP_STATUS_CODE_302 == response.getStatusLine().getStatusCode()) {
				if (response.getHeaders("Location").length > 0)
					result.setLocation(response.getHeaders("Location")[0].getValue());
			}
			Log.debug("doGet 调用完成准备返回结果, timstamp:" + System.currentTimeMillis());
			return result;
		} catch (Exception e) {
			httpget.abort();
			Log.error(MessageFormat
							.format("调用 doGet 方法异常:\r\n 所用代理为：{0} \r\n 请求参数:[url:{1}  \r\n http_head:{2} \r\n timestamp:{3}",
									proxy, url, headerMap, System.currentTimeMillis()), e);
		} finally {
			release(response, resp_entity);
		}
		return null;
	}
	
	public static void main(String [] args){
		
//		CloseableHttpClient client = HttpClients.createDefault();
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 1; i++){
////			HttpResult result = HttpClientExecution.doGet(client, "http://www.51jrq.com", null, null);
//			//System.out.println(new String(result.getResponseByte()));
//		}
//		long endTime = System.currentTimeMillis();
//		
//		System.out.println(endTime - start);
	}
}
