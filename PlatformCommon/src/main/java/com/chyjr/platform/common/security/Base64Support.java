package com.chyjr.platform.common.security;

import java.io.IOException;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.security.Base64Support.java] 
 * @ClassName:    [Base64Support]  
 * @Description:  [base64 编解码 类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 4:47:11 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 4:47:11 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class Base64Support {

	/**
	 * 解码
	 * @param bytes
	 * @return
	 */
    public static String toStr(byte[] bytes) {
        String str = new sun.misc.BASE64Encoder().encode(bytes);

        if (str == null)
            return "";

        str = str.replaceAll("\\+", "_");
        str = str.replaceAll("/", "-");
        str = str.replaceAll("=", ".");
        str = str.replaceAll("\\s", "");

        return str;
    }
    
    /**
     * 编码
     * @param str
     * @return
     * @throws IOException
     */
    public static byte[] fromStr(String str) throws IOException {

        if (str == null)
            return null;

        str = str.replaceAll("_", "+");
        str = str.replaceAll("-", "/");
        str = str.replaceAll("\\.", "=");

        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

        return dec;
    }

}
