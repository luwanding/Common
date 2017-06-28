package com.platform.common.tool.ip.qq;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

/**
 * 工具类，提供一些方便的方法
 */
public class Util {

   /**
     * 从ip的字符串形式得到字节数组形式
     * 
     * @param ip
     *            字符串形式的ip
     * @return 字节数组形式的ip
     */
    public static byte[] getIpByteArrayFromString(String ip)throws Exception {
       
        byte[] ret = new byte[4];
        
        StringTokenizer st = new StringTokenizer(ip, ".");
        
        try {
        	
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            
        } catch (Exception e) {
        	
            throw e;
        }
        
        return ret;
    }

  

    /**
     * 根据某种编码方式将字节数组转换成字符串
     * 
     * @param b
     *            字节数组
     * @param offset
     *            要转换的起始位置
     * @param len
     *            要转换的长度
     * @param encoding
     *            编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len,
            String encoding) {
    	
        try {
        	
            return new String(b, offset, len, encoding);
            
        } catch (UnsupportedEncodingException e) {
        	
            return new String(b, offset, len);
            
        }
    }
}
