package com.chyjr.uams.util;

public class MemcacheConstant {
	
	public final static Long SECOND = new Long(1);

    public final static Long MINUTE = new Long(60);

    public final static Long HOUR = new Long(60 * 60);

    public final static Long DAY = new Long(60 * 60 * 24);

    public final static Long WEEK = new Long(60 * 60 * 24 * 7);
    
	// 用户Session KEY
	public final static String UAMS_USER_SESSION = "UAMS_USER_SESSION";
	
	//登录会话保存最长时间
	public final static int loginOutTime = (int)(2 * HOUR);
	
	
}
