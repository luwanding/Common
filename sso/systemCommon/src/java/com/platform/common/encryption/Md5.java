package com.platform.common.encryption;

import java.security.MessageDigest;

public class Md5 {
    
    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
                                        'c', 'd', 'e', 'f'     };
    
    public static String encode(String plainText) {
        
        return MD5(plainText, "UTF-8");
    }
    
    public final static String MD5(String plainText, String encoding) {

        try {
            
            byte[] strTemp;
            
            if (encoding != null){
                
                strTemp = plainText.getBytes(encoding);
           
            }else{
                
                strTemp = plainText.getBytes();
            }                
            
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            
            mdTemp.update(strTemp);
            
            byte[] md = mdTemp.digest();
            
            int j = md.length;
            
            char str[] = new char[j * 2];
            
            int k = 0;
            
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                //右移四位并截掉高四位
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                //直接截掉高四位
                str[k++] = hexDigits[byte0 & 0xf];
            }
            
            return new String(str);
            
        } catch (Exception e) {
            
            return null;
        }
    }
}