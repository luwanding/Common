package com.chyjr.uams.security;

import com.chyjr.uams.security.util.SecurityConstant;

/**
 * 生成令牌
 * 
 * @author jx_xudelin
 * 
 */
public class TokenUtils {

	/**
	 * 加密存储动态密码的CacheID
	 * 
	 * @param TokenKeystr
	 *            存储令牌解密Key的CacheID
	 * @return
	 */
	public static String generateCacheKey(String CacheKeystr, String key) {
		return DesEncryptTool.encrypt(CacheKeystr + SecurityConstant.splitFlag
				+ HexUtils.getHashCode(), key);
	}

	/**
	 * 解密密存储动态密码的CacheID
	 * 
	 * @param TokenKey
	 *            存储令牌解密 KEY的已加密索引
	 * @return
	 */
	public static String decodeCacheKey(String CacheKey, String key) {
		return DesEncryptTool.decrypt(CacheKey, key);
	}

	/**
	 * 传入加密Key和加密向量生成令牌
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @param keyStr
	 *            加密Key值
	 * @return 返回加密后的字符串
	 */
	public static String generateToken(String str, String keyStr) {
		return DesEncryptTool.encrypt(str + SecurityConstant.splitFlag
				+ HexUtils.getHashCode(), keyStr);
	}

	/**
	 * 传入加密Key和加密向量生成令牌
	 * 
	 * @param strToken
	 *            需要加密的字符串
	 * @param keyStr
	 *            加密Key值
	 * @return 返回加密后的字符串
	 */
	public static String decodeToken(String strToken, String keyStr) {
		return DesEncryptTool.decrypt(strToken, keyStr);
	}

	
}
