package com.platform.common.tool.log.log4j;

import org.apache.log4j.Logger;

import com.platform.common.tool.log.BasePrint;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author twotoo
 * @version $ Log4jPrintSql.java, v 0.1 May 11, 2011 4:00:33 PM twotoo Exp $
 * @since   JDK1.6
 */
public class Log4jPrintSql extends BasePrint{

    private final Logger logger = Logger.getLogger("CONSOLE");   
    @Override
    public void print(String message, String attrName, String sqlList) {
        
        logger.error(message);
    }

}
