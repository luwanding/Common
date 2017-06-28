package com.chyjr.uams.client.test;

import com.chyjr.uams.client.cache.DefaultCache;

public class TestCache {
	
	public static void main(String args[]){
		DefaultCache dc = new DefaultCache("sssing",1024*1024*1,1000);
		long start = System.currentTimeMillis();
		for(int i = 0; i< 10000; i++){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     	dc.put("sdfsdf"+i, "asdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
	     	System.out.println(dc.size());
		}
		long end =System.currentTimeMillis() - start;
		System.out.println(end);
		System.out.println(dc.size());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dc.get("sdfsdf0"));
	}
}
