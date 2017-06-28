package com.chyjr.uams.common.util;

public class CString {
	
	public static boolean isNull(String str){
		if(str == null || str.length() == 0){
			return true;
		}
		return false;
	}

}
