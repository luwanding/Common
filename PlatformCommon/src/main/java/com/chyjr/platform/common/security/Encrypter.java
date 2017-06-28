package com.chyjr.platform.common.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.chyjr.platform.common.util.mobile.MobileCommunicatePackateUtil;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.security.Encrypter.java] 
 * @ClassName:    [Encrypter]  
 * @Description:  [3DES 加解密操作类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 4:48:23 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 4:48:23 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public final class Encrypter {

    private static Cipher       ecipher;

    private static Cipher       dcipher;

    // 必须24个字符
    private static final String key = "*:@6$0!t*:@9$7!t*:@8$7!t";

    private static final String alg = "DESede";
    
    private static final Logger logger = Logger.getLogger(Encrypter.class);

    static {

        try {

            SecretKey skey = new SecretKeySpec(key.getBytes(), alg);

            ecipher = Cipher.getInstance(alg);

            dcipher = Cipher.getInstance(alg);

            ecipher.init(Cipher.ENCRYPT_MODE, skey);

            dcipher.init(Cipher.DECRYPT_MODE, skey);

        } catch (Exception e) {
        	logger.error("初始化安全加密类异常：",e);

        }
    }

    /**
     *
     * <pre>
     * 加密
     * </pre>
     *
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        if (str == null)
            return "";
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return Base64Support.toStr(enc);

        } catch (Exception e) {
        	logger.error("加密类异常：",e);

            return "";
        }

    }

    /**
     *
     * <pre>
     * 解密
     * </pre>
     *
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        if (str == null)
            return "";
        try {
            // Decode base64 to get bytes
            byte[] dec = Base64Support.fromStr(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");

        } catch (Exception e) {

        	logger.error("解密类异常：",e);

            return "";
        }
    }
    
    public  static void main(String[] args){
       System.out.println(decrypt("Xch1NgWuUJ2bpZrr7fPoF0LJbmxcY1bX5G9//PrWyy4="));
       System.out.println(encrypt("G0001#j#0000110j#08-05 09:28:48"));
    }
}
