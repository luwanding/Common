package org.littleshoot.proxy.start;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.littleshoot.proxy.Launcher;

public class ProxyServerStartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
		String val = " port "+config.getInitParameter("--port");
		this.init();
		Launcher.main();
	}

}
