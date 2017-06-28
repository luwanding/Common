package com.chyjr.uams.security.test;

import com.chyjr.uams.security.HexUtils;
import com.chyjr.uams.security.TokenUtils;

public class TestRun {
	
	public static void main(String args[]) {

		/*String key = HexUtils.getEncryptKey();
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < 10; i ++){
			ThreadTest thread = new ThreadTest();
			thread.start();
		}*/
		
		//
		
		System.out.println(TokenUtils.decodeCacheKey("3DE2F742B2574CC5F9CCF86C8AE5F2DD848621DAE4C5F6A9BE41C13CA27DF88F588D0419F7F03B617C6E62B03BC14915",Config.getInstance().getCacheIdKey()));
		
		
		//System.out.println(TokenUtils.generateToken("UAMST##t5gu1xztaal1dy55dvgno3vt##187035","%!##@$%|$#$%(^)$}$*{^*+%"));
		
		//System.out.println(TokenUtils.decodeCacheKey(TokenUtils.generateToken("1231231asdfasdf许德麟##ygpt2355q0ti3mfcenukym55##146271##1330515392763##-406606902","jyNtCKnLSagrVei0pCH0xj34"),"jyNtCKnLSagrVei0pCH0xj34"));
		
		// for(int i = 0; i< 10000; i++){
		// /String strToken =
		// TokenUtils.generateToken("416F71051AD0AF07013F81A27C0D154325A804BE1FB772FD",key);

		// System.out.println(strToken);
		// }
		// System.out.println(System.currentTimeMillis() - startTime);

	}
}
