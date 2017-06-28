package com.chyjr.uams.security;

import org.apache.log4j.Logger;

/**
 * 加密/解码工具类
 * 
 */
public class DesEncryptTool {
	private static SecurityManager securityManager = SecurityManager.getInstance();
	private static Logger logger = Logger.getLogger("DesEncryptTool");

	/**
	 * 3DES解密字节数组
	 * 
	 * @param b
	 *            待加密的字节数组
	 * @return 3DES加密后的节节数组
	 */
	public static byte[]  decrypt(byte[] b, String keyStr) {
		try {
			return securityManager.decrypt(b, keyStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("3DES解密出错:" + ex.getMessage() + "\r\n value["+HexUtils.toHexString(b)+"]key:["+keyStr+"]", ex);
			return null;
		}
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串,异常返回空
	 */
	public  static String decrypt(String strIn, String keyStr) {
		try {
			byte[] bit = decrypt(HexUtils.fromHexString(strIn), keyStr);
			if(bit != null)
				return new String(bit,"UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("3DES解密出错:" + ex.getMessage() + "\r\n key:"+keyStr, ex);
		}
		return null;
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(byte[] arrB, String keyStr) {
		try {
			return securityManager.decrypt(arrB, keyStr);
		} catch (Exception ex) {
			logger.error("3DES加密出错:" + ex.getMessage() + "\r\n key:"+keyStr, ex);
			return null;
		}
	}

	/**
	 * 加密字符串
	 * 
	 * @param str
	 *            待加密的字符串
	 * @return 3DES加密后的字符串
	 */
	public static String encrypt(String str, String keyStr) {
		try {
			return securityManager.encrypt(str, keyStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("3DES加密出错:" + ex.getMessage() + "\r\n key:"+keyStr, ex);
			return null;
		}
	}

	/**
	 * 获取当前加密向量
	 * 
	 * @return
	 */
	public static String getDesStr() {
		return securityManager.getDesStr();
	}

	/**
	 * 动态修改加密向量
	 * 
	 * @param desStr
	 */
	public static void setDesStr(String desStr) {
		securityManager.setDesStr(desStr);
	}

	/**
	 * 从指定字符串生成密钥
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 */
	public static byte[] HexStringToByteArray(String s) {
		byte[] buf = new byte[s.length() / 2];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (byte) (chr2hex(s.substring(i * 2, i * 2 + 1)) * 0x10 + chr2hex(s
					.substring(i * 2 + 1, i * 2 + 2)));
		}
		return buf;
	}

	private static byte chr2hex(String chr) {
		if (chr.equals("0")) {
			return 0x00;
		} else if (chr.equals("1")) {
			return 0x01;
		} else if (chr.equals("2")) {
			return 0x02;
		} else if (chr.equals("3")) {
			return 0x03;
		} else if (chr.equals("4")) {
			return 0x04;
		} else if (chr.equals("5")) {
			return 0x05;
		} else if (chr.equals("6")) {
			return 0x06;
		} else if (chr.equals("7")) {
			return 0x07;
		} else if (chr.equals("8")) {
			return 0x08;
		} else if (chr.equals("9")) {
			return 0x09;
		} else if (chr.equals("A")) {
			return 0x0a;
		} else if (chr.equals("B")) {
			return 0x0b;
		} else if (chr.equals("C")) {
			return 0x0c;
		} else if (chr.equals("D")) {
			return 0x0d;
		} else if (chr.equals("E")) {
			return 0x0e;
		} else if (chr.equals("F")) {
			return 0x0f;
		}
		return 0x00;
	}

	public static void main(String args[]) throws Exception {

	}
}


