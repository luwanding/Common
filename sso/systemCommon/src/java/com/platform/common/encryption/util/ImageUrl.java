package com.platform.common.encryption.util;

/**
 * 上传图片的基本路径
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author wangfangw
 * @version $ ImageUrl.java, v 0.1 2011-8-31 上午10:37:03 wangfangw Exp $
 * @since JDK1.6
 */
public class ImageUrl {

	public static String IMAGEURL;

	public static String LOADIMAGEURL;

	public ImageUrl(String imageUrl, String loadImageUrl) {
		IMAGEURL = imageUrl;
		LOADIMAGEURL = loadImageUrl;
	}
}
