package com.chyjr.platform.common.util.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.chyjr.platform.common.security.Encrypter;
import com.chyjr.platform.common.util.JSONUtils;
import com.chyjr.platform.datamodule.MobilePackageBean;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.mobile.MobileCommunicatePackateUtil.java] 
 * @ClassName:    [MobileCommunicatePackateUtil]  
 * @Description:  [移动终端报文解析和组装]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 4:18:29 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 4:18:29 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class MobileCommunicatePackateUtil {
	
	/**
	 *报文14位头长度 
	 */
	private final static int HEAD_LEN = 14;
	private final static int LEN_SIZE = 7;
	private final static int BUFF_SIZE = 8196;
	private final static int PACK_MAX = 65536;
	private final static int ENCRYPT_LEN = 50;
	private final static String EN_CODING = "UTF-8";
	private final static String FORMAT_JSON  = "j";
	
	private final static String SPLIT_STR = "#";
	private final static String FORMAT_XML  = "x";
	private final static String FORMAT_HTML5 = "h";
	private final static String FORMAT_FILE  = "f";
	private final static int[]  REPORT_HEAD = new int[]{5,1,7,1};
	private final static String ERROR_CODE_0000001 = "0000001";//报文不全
	private final static String ERROR_CODE_0000002 = "0000002";//报文解析失败
	private final Logger logger = Logger.getLogger(MobileCommunicatePackateUtil.class);
	
	
	//report 
	private final static String REP_TRANS_CODE = "transcode";
	private final static String REP_PACKET_FORMAT = "packetformat";
	private final static String REP_TERMIANL_TYPE = "type";
	private final static String REP_LENGTH = "length";

	/**
	 * 打包并发送
	 * @param packate 包内容
	 * @param response 响应用户请求
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public void pack(MobilePackageBean packate, HttpServletResponse response){
		try{
			response.setHeader("Content-type", "text/html;charset="+EN_CODING);  
			//response.setCharacterEncoding(EN_CODING);
			
			StringBuilder str = new StringBuilder();
			String json = JSONUtils.writeJson(packate.getContent());
			
			if("i".equals(packate.getTerminalType())){ //ios
				json = json.replaceAll("<p>", "  ");
				json = json.replaceAll("</p>", "");
				json = json.replaceAll("&rdquo;", "");
				json = json.replaceAll("&ldquo;", "");
				json = json.replaceAll("<br />", "<br>");
				json = json.replaceAll("<br/>", "<br>");
				json = json.replaceAll("&nbsp;", "");
			}
			str.append(json);
			
			logger.debug("mobilePack ["+packate.getTransCode()+"]交易请求响应打包完成:" + str.toString());
			String src = str.toString().replaceAll("\r\n", "\\r\\n");
			//src = src.replaceAll("\n", "\\n");
			response.setContentLength(src.getBytes(EN_CODING).length);
			OutputStream out = response.getOutputStream();
			int off = 0;
			int len = 8019;
			byte[] bt = src.getBytes(EN_CODING);
			while(off < bt.length){
				int wsize = 0;
				if( off + len > bt.length){
					wsize = bt.length - off;
				}else{
					wsize = len;
				}
				out.write(bt,off,wsize);
			    off += wsize;
			    out.flush();
			}
			response.setBufferSize( bt.length);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("mobilePack 发送报文异常：", e);
		}
	}
	
	
	
	private String getLengthToStr(int len){
		String lenStr = String.valueOf(len);
		int l = lenStr.length();
		for(int i = LEN_SIZE; i >l ; i--){
			lenStr = "0"+lenStr;
		}
		return lenStr;
		
	} 
	
	private int getUnicodeCount(String str){
		
		if(str == null || str.length() == 0){
			return 0;
		}
		return str.getBytes().length;
	}
	
	
	 public static boolean isLetter(char c) {   
	        int k = 0x80;   
	        return c / k == 0 ? true : false;   
	     }   
	    
	/**
	 * 接收并解析移动端报文
	 * @param request Client请求
	 * @return
	 * @throws IOException 
	 */
	public MobilePackageBean unpack(HttpServletRequest request ){
		MobilePackageBean mobile = new MobilePackageBean();
		try{
			
			if(request.getAttribute("requestContnet") !=  null){
				mobile = (MobilePackageBean )request.getAttribute("requestContnet");
				return mobile;
			}
			int readLength = request.getContentLength();
			BufferedReader reader = new BufferedReader(new InputStreamReader( request.getInputStream(),EN_CODING));
			char[] b = new char[BUFF_SIZE];
			int readLen = reader.read(b);
			int readMaxLen = readLen ;
			StringBuilder readStr = new  StringBuilder( );
			readStr.append(new String(b,0,readLen));
			while(readMaxLen < readLength){
				b = new char[BUFF_SIZE];
				readLen = reader.read(b);
				if(readLen > -1){
					readMaxLen += readLen;
					readStr.append(new String(b,0,readLen));
				}else{
					break;
				}
			}
			//if(readLen == readLength){
			logger.debug("mobilePack 请求完整内容:"+ readStr);
			Map<String,Object> contentVal = JSONUtils.parseJson(readStr.toString(),Map.class);
			Map<String,String> head = (Map<String,String>)contentVal.get("head");
			mobile.setTransCode(head.get(REP_TRANS_CODE));
			mobile.setPackageFormat(head.get(REP_PACKET_FORMAT));
			mobile.setTerminalType(head.get(REP_TERMIANL_TYPE));
			mobile.setLength(readLength);
			mobile.setContent((Map)contentVal.get("data"));
//			}else{
//				mobile.setReturnCode(ERROR_CODE_0000001);
//				logger.error("mobilePack 读取报文头异常。");
//			}
		}catch(Exception e){
			logger.error("mobilePack 解析移动端请求报文失败！",e);
			mobile.setReturnCode(ERROR_CODE_0000002);
		}
		return mobile;
	}
	
	
	private String[] check(BufferedReader read) throws IOException{
		char[] b = new char[ENCRYPT_LEN];
		int readLen = read.read(b);
		
		if(readLen < ENCRYPT_LEN){
			logger.error("mobilePack 报文校验出错,不是一个有效的移动端请求。");
			return null;
		}
		String encrypt = new String(b);
		logger.debug("mobilePack 加密校验串["+encrypt+"] ");
		encrypt = Encrypter.decrypt(encrypt.trim());
		logger.debug("mobilePack 解密校验串["+encrypt+"] ");
		return encrypt.split(SPLIT_STR);
	}
	
	
	public static void main(String[] args){
		try {
			System.out.println(JSONUtils.parseJson("{\"a\":\"\",\"content\":\"\"}",Map.class));
			double a = 100000;
			for(int i = 0; i <100; i ++){
				a = a + a * 0.08;
				System.out.println(a);
			}
			System.out.println(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
