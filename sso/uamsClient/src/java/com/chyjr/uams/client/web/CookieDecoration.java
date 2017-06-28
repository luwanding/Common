package com.chyjr.uams.client.web;

import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 *@FILE_NAME: CookieDecoration.java
 *@Descripton:cookie包装类
 *@author: twotoo
 *@time: 2012-2-15
 *@version: 1.0
 */
public final class CookieDecoration {

	private final Logger logger = Logger.getLogger(CookieDecoration.class);

	private HttpServletRequest request;

	private HttpServletResponse response;

	public CookieDecoration(HttpServletRequest req, HttpServletResponse res) {

		request = req;

		response = res;
	}

	/**
	 *@Descripton: 获取COOKIE
	 *@author: twotoo
	 *@time: 2012-2-15 下午04:24:29
	 * @param name
	 * @return
	 */
	public String getCookieValue(String name) {

		logger.info("Cookie Name=" + name);

		Cookie[] cookies = request.getCookies();

		if (cookies == null) {

			logger.warn("cookies is null");

			return null;
		}

		Cookie cookie = null;

		for (int i = 0; i < cookies.length; i++) {

			cookie = cookies[i];

			logger.info("cookieName" + i + ":: " + cookie.getName());

			if (cookie.getName().equalsIgnoreCase(name)) {

				logger.info("cookie value=" + cookie.getValue());

				return cookie.getValue();
			}
		}

		return null;

	}

	/**
	 *@Descripton:设置COOKIE
	 *@author: twotoo
	 *@time: 2012-2-15
	 * @param name
	 * @param value
	 * @param domain
	 * @param expire
	 *            过期时间
	 */
	public void setCookie(String name, String value, String domain, int expire) {

		Cookie cookie = new Cookie(name, value);

		cookie.setDomain(domain);

		cookie.setPath("/");

		if (expire >= 0) {

			cookie.setMaxAge(expire);

		}

		response.addCookie(cookie);
	}

	/**
	 * 
	 *@Descripton:设置COOKIE，会话消失 COOKIE消失
	 *@author: twotoo
	 *@time: 2012-2-15
	 * @param name
	 * @param value
	 * @param domain
	 */
	public void setCookie(String name, String value, String domain) {
		setCookie(name, value, domain, -1);
	}

	/**
	 * 
	 *@Descripton: 清楚COOKIE
	 *@author: twotoo
	 *@time: 2012-2-15
	 * @param name
	 * @param domain
	 */
	public void clearCookie(String name, String domain) {
		
		setCookie(name, "", domain, 0);
	}

	/**
	 * 
	 *@Descripton:清除所有COOKIE
	 *@author: twotoo
	 *@time: 2012-2-15
	 */
	public void clearAllCookie() {

		Cookie[] cookies = request.getCookies();

		for (int i = 0; i < cookies.length; i++) {

			cookies[i].setValue(null);

		}

	}
	
	/**
	 * 
	 *@Descripton:获取cookie的域
	 *@author: twotoo
	 *@time: 2012-2-15
	 * @param name
	 * @return
	 */
	public String getCookieDomain(String name) {
		
		logger.info("Cookie name = " + name);
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies == null){
			
			return null;
		}
		
		Cookie cookie = null;
		
		for (int i = 0; i < cookies.length; i++) {
			
			cookie = cookies[i];
			
			if (cookie.getName().equalsIgnoreCase(name)) {
				
				logger.info("cookie.getDomain() = " + cookie.getDomain());
				
				return cookie.getDomain();
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 *@Descripton:获取某cookie的域
	 *@author: twotoo
	 *@time: 2012-2-15
	 * @param name
	 * @return
	 */
	public Cookie getCookie(String name) {
		
		logger.info("Cookie name = " + name);
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies == null){
			
			return null;
		}			

		Cookie cookie = null;
		
		for (int i = 0; i < cookies.length; i++) {
			
			cookie = cookies[i];
			
			if (cookie.getName().equalsIgnoreCase(name)) {
				
				return cookie;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 *@Descripton:判断COOKIE是否过期
	 *@author: twotoo
	 *@time: 2012-2-15
	 * @param strDate 上次访问的时间，格式：yyyy-MM-dd HH:mm:ss
	 * @param minutes  过期时间（以分为单位）
	 * @return true 过期 false 未过期
	 */
	public static boolean isCookieExpire(String strDate, int minutes) {

		Calendar c = Calendar.getInstance();
		
		int year = Integer.parseInt(strDate.substring(0, 4));
		
		int month = Integer.parseInt(strDate.substring(5, 7)) - 1;
		
		int day = Integer.parseInt(strDate.substring(8, 10));
		
		int hour = Integer.parseInt(strDate.substring(11, 13));
		
		int minute = Integer.parseInt(strDate.substring(14, 16));
		
		int second = Integer.parseInt(strDate.substring(17));
		
		c.set(year, month, day, hour, minute, second);
		
		c.add(Calendar.MINUTE, minutes);
		
		return c.before(Calendar.getInstance());
	}
	
}
