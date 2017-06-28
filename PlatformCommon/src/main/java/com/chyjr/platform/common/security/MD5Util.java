package com.chyjr.platform.common.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.security.MD5Util.java] 
 * @ClassName:    [MD5Util]  
 * @Description:  [MD5 加解密工具类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 4:49:26 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 4:49:26 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class MD5Util {
	
	/**
	 * to hex
	 * @param array
	 * @return
	 */
    public static String hex(byte[] array)  {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
    /**
     * MD5 加密
     * @param message
     * @return
     */
    public static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {
        
        }
        return message;
    }
    
    
    public  static void main(String[] args){
    	String admin = "admin";
    	String fieldString = null;
//    	for(int i = 1 ; i< 10; i++){
//    		fieldString = admin +i;
//    		fieldString = MD5Util.md5Hex(fieldString);
//    		System.out.println("admin" + i +"= " +fieldString );
//    	}
    	System.out.println(MD5Util.md5Hex("chenwenjing").toUpperCase());
//    	System.out.println(MD5Util.md5Hex("admin2"));
    	System.out.println(Encrypter.encrypt(MD5Util.md5Hex("chenwenjing")));
//    	//System.out.println(Encrypter.decrypt("asdfsadf"));
    	System.out.println(Encrypter.encrypt("chenwenjing"));
//    	System.out.println(Encrypter.encrypt(MD5Util.md5Hex("1")));
//    	System.out.println(Encrypter.encrypt(MD5Util.md5Hex("1")));
    }
}
