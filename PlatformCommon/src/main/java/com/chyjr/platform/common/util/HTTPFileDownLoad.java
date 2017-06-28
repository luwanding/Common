package com.chyjr.platform.common.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.HTTPFileDownLoad.java] 
 * @ClassName:    [HTTPFileDownLoad]  
 * @Description:  [HTTP 文件下载]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 4:58:13 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 4:58:13 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class HTTPFileDownLoad {
	
	//写文件缓冲大小
	final private int BUFSIZE = 8196; 
	
	private final Logger logger = Logger.getLogger(HTTPFileDownLoad.class);

	/**
	 * 下载文件主方法
	 * @param req
	 * @param resp
	 * @param filename
	 * @param original_filename
	 * @throws IOException
	 */
    public void doDownload( HttpServletRequest req, HttpServletResponse resp,
                             String path, String filename ){
        
    	File f = new File(path + filename);
        int  length = 0;
        ServletOutputStream op = null ;
        DataInputStream in = null ;
		try {
			op = resp.getOutputStream();
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/octet-stream" );
	        resp.setContentLength((int)f.length());
	        resp.setHeader("Content-Disposition", "attachment; filename=\"" + new   String(filename.getBytes("GB2312"), "ISO8859_1") + "\"");
	        
	        //写流缓冲
	        byte[] bbuf = new byte[BUFSIZE];
	        in = new DataInputStream(new FileInputStream(f));
	        
	        while ((in != null) && ((length = in.read(bbuf)) != -1))
	        {
	            op.write(bbuf,0,length);
	        }
		} catch (IOException e) {
			logger.error("下载["+filename+"] 文件时异常:"+e.getMessage());
		} finally{
			try{
			if(in != null)
				in.close();
			if(op != null){
		        op.flush();
		        op.close();
			}
			} catch(IOException e){
				logger.error("下载["+filename+"] 文件时，关闭文件流异常:"+e.getMessage());
			}
		}
    }
	
}
