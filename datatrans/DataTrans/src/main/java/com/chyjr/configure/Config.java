package com.chyjr.configure;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {
	    //配置
	    private static DataTransCfg task;
	    /**
	     * 初始化本地缓存配置信息
	     *
	     * @return 返回配置信息实例
	     */
	    public static DataTransCfg initConfig(String cfgPath) {
	        File file = new File(cfgPath);
	        SAXReader reader = new SAXReader();
	        try {
	            Document document = reader.read(file);
	            Element root = document.getRootElement();
	            List<Element> conns = root.elements("connection");
	            Element formEle = conns.get(0);
	            //Element toEle = conns.get(1);
	            task  = new DataTransCfg();
	            ConnConfig connform = new ConnConfig();
	        	ConnConfig connto = new ConnConfig();
	        	
	        	connform.setUrl(formEle.elementText("url"));
	        	connform.setUser( formEle.elementText("user"));
	        	connform.setPassword(formEle.elementText("password"));
                task.setConnform(connform);
	            
	        	//connto.setUrl(toEle.elementText("url"));
                //connto.setUser( toEle.elementText("user"));
                //connto.setPassword(toEle.elementText("password"));
	            
	        	List<Element> tableEle = root.element("task").elements("table");
	        	List<Table> tables = new ArrayList<Table>();
	        	for(int i = 0; i < tableEle.size(); i ++){
	        		tableEle.get(i);
	        		Table table = new Table();
	        		table.setFromtabname(tableEle.get(i).attributeValue("from").trim());
	        		table.setTotabname(tableEle.get(i).attributeValue("to").trim());
	        		List<Element>  fields = tableEle.get(i).elements("f");
	        		Field[] fs = new Field[fields.size()];
	        		for(int j = 0;j < fields.size(); j++){
	        			fs[j] = new Field();
	        			fs[j].setFrom(fields.get(j).attributeValue("from").trim());
	        			fs[j].setTo(fields.get(j).attributeValue("to").trim());
	        			fs[j].setDefaultval(fields.get(j).getText());
	        		}
	        		table.setFields(fs);
	        		tables.add(table);
	        	}
	        	task.setTables(tables);
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	        return task;
	    }
}
