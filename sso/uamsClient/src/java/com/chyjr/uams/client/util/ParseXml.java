package com.chyjr.uams.client.util;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Document;
import org.dom4j.Element;

import com.chyjr.uams.dto.UserToken;

/**
 * 
 * @author jx_xudelin
 *
 */
public class ParseXml {
	
	private static final Logger Log = Logger.getLogger(ParseXml.class);
	
	/**
	 * Xml转换成对象
	 * @param xml
	 * @param result
	 * @return
	 */
	public static UserToken xmlToMap(final String xml){
		if(xml == null){
			return null;
		}
		Document document = null ;
		Element root = null;
		UserToken userToken = new UserToken();
		try {
			document = DocumentHelper.parseText(xml);
			root = document.getRootElement();
			List<Element> nodes = root.elements();
			Field[] fields = userToken.getClass().getDeclaredFields();
			
			for(int i = 0 ; i < nodes.size(); i++){
				Element node = nodes.get(i);	
				for(int j = 0; j < fields.length; j ++){
					if(fields[j].getName().equals(node.getName())){
						fields[j].setAccessible(true);
						fields[j].set(userToken, toType(node.getText(),fields[j].getType()));
						
					}
				}
			}
		} catch (Exception e) {
			Log.error("解析xml异常：xml text " +xml,e);
		}finally{
			if(root != null)
				root.clearContent();
			
			if(document != null)
				document.clearContent();
		}
		return userToken;
	}
	
	
	private static Object toType(Object obj, Object type){
		Object value = new Object();
		if(type.toString().indexOf(".Long") > -1){
			obj = (obj == null || obj.equals("")) ? "0":obj;
			value =  Long.parseLong((String)obj);
		}else if(type.toString().indexOf(".Integer") > -1 ){
			obj = (obj == null || obj.equals("")) ? "0":obj;
			value =  Integer.parseInt((String)obj);
		}else if(type.toString().indexOf(".String") > -1 ){
			value =  (String)obj;
		}else if(type.toString().indexOf(".Short") > -1){
			obj = (obj == null || obj.equals("")) ? "0":obj;
			value =  Short.parseShort((String)obj);
		}else if(type.toString().indexOf(".Double") > -1){
			obj = (obj == null || obj.equals("")) ? "0":obj;
			value =  Double.parseDouble((String)obj);
		}else if(type.toString().indexOf(".Float") > -1){
			obj = (obj == null || obj.equals("")) ? "0":obj;
			value =  Float.parseFloat((String)obj);
		}else if(type.toString().indexOf(".Boolean") > 0){
			value =  Boolean.parseBoolean((String)obj);
		}
		return value;
	}
}
