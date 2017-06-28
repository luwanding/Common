package com.platform.common.tool.log.http;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.platform.common.tool.log.BasePrint;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author twotoo
 * @version $ HttpPrintSql.java, v 0.1 May 11, 2011 3:26:00 PM twotoo Exp $
 * @since   JDK1.6
 */
public class HttpPrintSql extends BasePrint{

    @Override
    public void print(String message, String attrName, String sqlList) {
        
        HttpServletRequest request = ServletActionContext.getRequest();
        
        if (checkRequest(request, attrName, sqlList)) {
            
            List<String> logs = (List<String>) request.getAttribute(sqlList);
            
            logs.add(message);
            
            request.setAttribute(sqlList, logs);
        }        
       
    }
    
    private boolean checkRequest(HttpServletRequest request, String attrName, String sqlList) {

        if (request != null) {

            String attribute = request.getParameter(attrName);

            if (attribute != null) {

                Object list = request.getAttribute(sqlList);

                if (list == null) {

                    request.setAttribute(sqlList, new ArrayList());

                }

                return true;
            }

            return false;
        }

        return false;
    }

}
