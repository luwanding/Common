package com.chyjr.platform.common.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * 内部类,作为工具类的单例,以便通过上层进行key,加密向量的动态修改
 * 
 * @author jx_xudelin
 * 
 */
public final class SecurityManager {
	private String desStr = "12345678";
	//private String algorithm = "DESede/CBC/PKCS7Padding";
	private String algorithm = "DESede/CBC/PKCS5Padding";
	

	private static SecurityManager instance;

	private byte[] desInitValue = HexUtils.strToByte(desStr);// 默认向量
	private IvParameterSpec ivspec = new IvParameterSpec(desInitValue);

	/**
	 * 屏蔽构造方法
	 */
	private SecurityManager() {

	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	static SecurityManager getInstance() {
		if (instance == null) {
			synchronized (SecurityManager.class) {
				if (instance == null) {
					Security.addProvider(new com.sun.crypto.provider.SunJCE());
					Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());// 添加PKCS7Padding支持
					instance = new SecurityManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public  byte[] encrypt(byte[] arrB, String keyStr) throws Exception {
		Cipher encryptCipher  = Cipher.getInstance(algorithm);
		encryptCipher.init(Cipher.ENCRYPT_MODE, getKey(keyStr), ivspec);
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public  String encrypt(String strIn, String keyStr) throws Exception {
		Cipher encryptCipher  = Cipher.getInstance(algorithm);
		encryptCipher.init(Cipher.ENCRYPT_MODE, getKey(keyStr), ivspec);
		return HexUtils.toHexString(encrypt(strIn.getBytes(), keyStr));
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public  byte[] decrypt(byte[] arrB, String keyStr) throws Exception {
		Cipher  decryptCipher = Cipher.getInstance(algorithm);
		decryptCipher.init(Cipher.DECRYPT_MODE, getKey(keyStr), ivspec);
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 */
	public String decrypt(String strIn, String keyStr) throws Exception {
		return HexUtils.toHexString(decrypt(HexUtils.fromHexString(strIn),
				keyStr));
	}

	private Key getKey(String str) {
		Key key = new SecretKeySpec(str.getBytes(), algorithm);
		return key;
	}

	private byte chr2hex(String chr) {
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

	public String getDesStr() {
		return desStr;
	}

	/**
	 * 动态修改加密向量
	 * 
	 * @param desStr
	 */
	public void setDesStr(String desStr) {
		this.desStr = desStr;
		this.desInitValue = HexUtils.strToByte(this.desStr);
		this.ivspec = new IvParameterSpec(this.desInitValue);
	}

}