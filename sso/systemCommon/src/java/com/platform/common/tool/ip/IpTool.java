package com.platform.common.tool.ip;

import javax.servlet.http.HttpServletRequest;

public class IpTool {
	
	private static IpTool m_instance = null;

	public synchronized static IpTool getInstance() {

		if (m_instance == null) {

			m_instance = new IpTool();

		}

		return m_instance;
	}

	private IpTool() {}
	
	public String getIpFromRequest(HttpServletRequest request){
		
		String ip = request.getHeader("X-Forwarded-For");
		
		if (ip == null) {
			
			ip = request.getRemoteAddr();
			
		} else {
			
			String[] ars = ip.split(",");
			
			ip = ars[ars.length - 1].trim();
			
		}
		
		return ip;
	}

}
