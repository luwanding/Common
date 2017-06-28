package com.platform.common.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.platform.common.encryption.util.Base64Support;

/**
 * <pre>
 *
 * </pre>
 *
 * @author twotoo
 * @version $ Encrypter.java, v 0.1 May 3, 2011 9:55:51 AM twotoo Exp $
 * @since   JDK1.6
 */
public final class Encrypter {

    private static Cipher       ecipher;

    private static Cipher       dcipher;

    // 必须24个字符
    private static final String key = "*:@7$8!t*:@7$8!t*:@7$8!t";

    private static final String alg = "DESede";

    static {

        try {

            SecretKey skey = new SecretKeySpec(key.getBytes(), alg);

            ecipher = Cipher.getInstance(alg);

            dcipher = Cipher.getInstance(alg);

            ecipher.init(Cipher.ENCRYPT_MODE, skey);

            dcipher.init(Cipher.DECRYPT_MODE, skey);

        } catch (Exception e) {

            e.printStackTrace();

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

            e.printStackTrace();

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

            e.printStackTrace();

            return "";
        }

    }
    public  static void main(String[] args){
       System.out.println(encrypt("root"));
    }
}
