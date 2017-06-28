package com.platform.common.tool.log;


import java.util.HashMap;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;

public class PrintSqlToPageAppender extends AppenderSkeleton {
	
	private String                  attrName;

    private String                  sqlList;
    
    private String                  className;

    private HashMap<String, String> logPrint;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    @SuppressWarnings("unchecked")
    protected void append(LoggingEvent e) {

        try{
            
            if(ServletActionContext.getContext() != null){
                
                HttpServletRequest request = ServletActionContext.getRequest();
                
                Object print = check(request);
                
                if(print != null){
                    
                    ((IBasePrint) print).print(this.getLayout().format(e), attrName, sqlList);
                }
                
            }
            
        }catch(Exception error){
            
            error.printStackTrace(); 
            
            return;
        }

    }

    private Object check(HttpServletRequest request) {

        if (request != null) {

            String attribute = request.getParameter(attrName);

            if (attribute != null && logPrint != null && logPrint.containsKey(attribute)) {
                
                try {
                    
                    return Class.forName(logPrint.get(attribute)).newInstance();
                    
                } catch (Exception e) {
                    
                	e.printStackTrace();
                    
                    return null;
                }

            }
        }

        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public String getSqlList() {
        return sqlList;
    }

    public void setSqlList(String sqlList) {
        this.sqlList = sqlList;
    }

    public HashMap<String, String> getLogPrint() {
        return logPrint;
    }

    public void setLogPrint(HashMap<String, String> logPrint) {
        this.logPrint = logPrint;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        
        if(className != null && !"".equals(className.trim())){
            
            String[] classLog = className.split(",");
            
            logPrint = new HashMap<String, String>();
            
            for(int i = 0 ; i < classLog.length; i++){
                
                String log = classLog[i];
                
                if(log != null && log.indexOf("=") > -1){
                    
                    String key = log.substring(0,log.indexOf("=")).trim();
                    
                    String value = log.substring(log.indexOf("=")+1).trim();
                    
                    logPrint.put(key, value);
                }
            }
        }

    }

}
