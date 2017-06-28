package com.platform.common.encryption.util;

import java.io.IOException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author twotoo
 * @version $ Base64Support.java, v 0.1 May 3, 2011 10:06:02 AM twotoo Exp $
 * @since   JDK1.6
 */
public class Base64Support {


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
