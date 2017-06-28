package com.chyjr.platform.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.SpringBeansUtil.java] 
 * @ClassName:    [SpringBeansUtil]  
 * @Description:  [spring 获取bean上下文类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:15:03 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:15:03 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class SpringBeansUtil implements ApplicationContextAware{
	
	private static ApplicationContext ctx;
	
	public static Object getBean(String id){
		if(ctx == null){
			throw new java.lang.NullPointerException("ApplicationContext is null");
		}
		return ctx.getBean(id);
		
	}
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ctx = arg0;
		
	}

}
