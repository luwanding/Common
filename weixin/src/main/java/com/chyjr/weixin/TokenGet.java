package com.chyjr.weixin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenGet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Map<String,String> token_Store = new HashMap<String,String>();
	private static final Map<String,Long> token_Time = new HashMap<String,Long>();
	
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		String url = req.getParameter("location");
		try {
			resp.getOutputStream().write(getToken(url).getBytes());
			resp.getOutputStream().flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String getToken(String url) throws Exception{
		String appid = "wxa4e207274ca54190";
		String secret = "a0c4096c4c9b13be6825ee2f6e407c17";
		URL token_url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
		String noncestr = getNonceStr(20);
		String timestamp = (""+System.currentTimeMillis()).substring(0, 10);
		if(token_Store.get(url) == null){
			System.out.println("生成url:"+url);
			getscript( url, token_url, timestamp, noncestr, appid);
		}else{
			Long tokenTime = token_Time.get(url);
			if((System.currentTimeMillis()/1000) - tokenTime >= 5000){
				System.out.println("重新生成url:"+url);
				getscript( url, token_url, timestamp, noncestr, appid);
			}
		}
		String script = token_Store.get(url);
		System.out.println("script:"+script);
		return script;
	}
	
	
	private static String getscript(String url,URL token_url,String timestamp,String noncestr,String appid) throws IOException{
		String token = TokenGet.getConnection(token_url);
		String script = "";
		String signature = "";
		
		if(token.indexOf("access_token") > 0 && token.indexOf("expires_in") > 0){
			String startFlag = "\"access_token\":\"";
			String endFlag = "\",";
			token = token.substring(token.indexOf(startFlag)+startFlag.length(),token.indexOf(endFlag));
			System.out.println("access_token:"+token);
			URL ticket_url = new URL("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi");
			String jsapi_ticket = TokenGet.getConnection(ticket_url);
			
			if(jsapi_ticket.indexOf("\"errmsg\":\"ok\"") > 0){
				startFlag = "\"ticket\":\"";
				int start = jsapi_ticket.indexOf(startFlag)+startFlag.length();
				jsapi_ticket = jsapi_ticket.substring(start,jsapi_ticket.indexOf(endFlag,start));
				System.out.println("jsapi_ticket:"+jsapi_ticket);
				String data = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+""
						+ "&url="+url; 
				signature = new SHA1().getDigestOfString(data.getBytes()).toLowerCase();
				script = "{debug:false,appId:\""+appid+"\",timestamp:\""+timestamp+"\",nonceStr:\""+noncestr+"\",signature:\""+signature+"\"}";
				token_Store.put(url, script);
				token_Time.put(url,(System.currentTimeMillis()/1000));
				return script;
			}
		}
		return "";
	}
	
	public static String getNonceStr(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }   
	
	public static String getConnection(URL url) throws IOException {
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("GET");
	        connection.setUseCaches(false);
	        connection.setInstanceFollowRedirects(true);
	        connection.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
	 
	        connection.connect();
	        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
	        char[] ch = new char[1024];
	        int size = reader.read(ch);
	        
	        connection.disconnect();
	        return new String(ch,0,size);
	    }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		doGet( req,  resp);
	}
	
	
	public  static void  main(String[] args){
		try {
			try {
				System.out.println(TokenGet.getToken("http://www.51jrq.com/wx/wx02/index.html?from=groupmessage&isappinstalled=1"));
				//System.out.println(TokenGet.getToken("http://www.51jrq.com/wx/wx01/index.html"));
				//System.out.println(TokenGet.getToken("http://www.51jrq.com/wx/wx01/index.html"));
				//System.out.println(TokenGet.getToken("http://www.51jrq.com/wx/wx02/index.html"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(System.currentTimeMillis());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
