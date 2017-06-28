package com.chyjr.platform.common.security.test;

public class ThreadTest extends Thread{

	
	public void run(){
		int i=0;
		while(true){
			i ++;
			try{
				//String str = HexUtils.getEncryptKey();
			/*String token = TokenUtils.generateToken("徐德搜索sdfasdfasdfas徐德林dfasdfasd"+i+"fasdf徐德林asasdfasdfasdf", str);
			System.out.println(token);
			String key = Config.getInstance().getCacheIdKey();
			String value = TokenUtils.decodeCacheKey(token, str);
			System.out.println(value);*/
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
