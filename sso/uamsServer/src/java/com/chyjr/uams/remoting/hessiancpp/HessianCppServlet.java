package com.chyjr.uams.remoting.hessiancpp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class HessianCppServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(HessianCppServlet.class);
	/**
	 * Constructor of the object.
	 */
	public HessianCppServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		try{
			HessianCPPRequestDispose.dispose(request, response);
		} catch(Exception e){
			logger.error("C与C++接口调用异常："+e.getMessage(),e);
		}
		
		// 下面兩行讓中文字能正確顯示
		/*response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<BODY>");
		out.println("<p>HessianCPP Interface is ok !</p>");
		out.println("</BODY>");
		out.println("</HTML>");*/
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		try{
			HessianCPPRequestDispose.dispose(request,response);
		} catch(Exception e){
			logger.error("C与C++接口调用异常："+e.getMessage(),e);
		}
	}
	
	
	public String getHander(HttpServletRequest request){
		StringBuffer headBuf = new StringBuffer();
		headBuf.append("{\"Protocol\":\"" + request.getProtocol());
		headBuf.append("\",");
	    headBuf.append("\"Scheme\":\"" + request.getScheme());
	    headBuf.append("\",");
	    headBuf.append("\"Server-Name\":\"" + request.getServerName() );
	    headBuf.append("\",");
	    headBuf.append("\"Server-Port\":\"" + request.getServerPort());
	    headBuf.append("\",");
	    headBuf.append("\"Protocol\":\"" + request.getProtocol());
	    headBuf.append("\",");
	    headBuf.append("\"Server-Info\":\"" + getServletConfig().getServletContext().getServerInfo());
	    headBuf.append("\",");
	    headBuf.append("\"Remote-Addr\":\"" + request.getRemoteAddr());
	    headBuf.append("\",");
	    headBuf.append("\"Remote-Host\":\"" + request.getRemoteHost());
	    headBuf.append("\",");
	    headBuf.append("\"Character-Encoding\":\"" + request.getCharacterEncoding());
	    headBuf.append("\",");
	    headBuf.append("\"Content-Length\":\"" + request.getContentLength());
	    headBuf.append("\",");
	    headBuf.append("\"Content-Type\":\""+ request.getContentType());
	    headBuf.append("\",");
	    headBuf.append("\"Auth-Type\":\"" + request.getAuthType());
	    headBuf.append("\",");
	    headBuf.append("\"HTTP-Method\":\"" + request.getMethod());
	    headBuf.append("\",");
	    headBuf.append("\"Path-Info\":\"" + request.getPathInfo());
	    headBuf.append("\",");
	    headBuf.append("\"Path-Trans\":\"" + request.getPathTranslated());
	    headBuf.append("\",");
	    headBuf.append("\"Query-String\":\"" + request.getQueryString());
	    headBuf.append("\",");
	    headBuf.append("\"Remote-User\":\"" + request.getRemoteUser());
	    headBuf.append("\",");
	    headBuf.append("\"Session-Id\":\"" + request.getRequestedSessionId());
	    headBuf.append("\",");
	    headBuf.append("\"Request-URI\":\"" + request.getRequestURI());
	    headBuf.append("\",");
	    headBuf.append("\"Servlet-Path\":\"" + request.getServletPath());
	    headBuf.append("\",");
	    headBuf.append("\"Accept\":\"" + request.getHeader("Accept"));
	    headBuf.append("\",");
	    headBuf.append("\"Host\":\"" + request.getHeader("Host"));
	    headBuf.append("\",");
	    headBuf.append("\"Referer\":\"" + request.getHeader("Referer"));
	    headBuf.append("\",");
	    headBuf.append("\"Accept-Language\":\"" + request.getHeader("Accept-Language"));
	    headBuf.append("\",");
	    headBuf.append("\"Accept-Encoding\":\"" + request.getHeader("Accept-Encoding"));
	    headBuf.append("\",");
	    headBuf.append("\"User-Agent\":\"" + request.getHeader("User-Agent"));
	    headBuf.append("\",");
	    headBuf.append("\"Connection\":\"" + request.getHeader("Connection"));
	    headBuf.append("\",");
	    headBuf.append("\"Cookie\": " + request.getHeader("Cookie"));
	    headBuf.append("\",");
	    headBuf.append("\"Session-Size\":" + request.getSession().getValueNames().length);
	    headBuf.append("\",");
	    headBuf.append("\"Session\":");
	    for(int i = 0; i < request.getSession().getValueNames().length; i++){
	    	headBuf.append(request.getSession().getAttribute(request.getSession().getValueNames()[i])+",");
	    }
	    if(request.getSession().getValueNames().length > 0){
	    	headBuf.delete(headBuf.length()-1, headBuf.length());
	    }
	    headBuf.append("\",");
	    headBuf.append("\"Session-CreationTime\":" +  request.getSession().getCreationTime());
	    headBuf.append("\"Session-LastAccessed:\"" +  request.getSession().getLastAccessedTime());
	    headBuf.append("\"}");
	    return headBuf.toString();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		
	}

}
