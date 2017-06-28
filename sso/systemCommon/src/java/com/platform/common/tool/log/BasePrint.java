package com.platform.common.tool.log;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author twotoo
 * @version $ BasePrint.java, v 0.1 May 11, 2011 3:26:48 PM twotoo Exp $
 * @since   JDK1.6
 */
public abstract class BasePrint implements IBasePrint{

   public abstract void print(String message, String attrName, String sqlList);

}
