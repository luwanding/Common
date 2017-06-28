package com.chyjr.exports;

import com.chyjr.configure.ConnConfig;
import com.chyjr.configure.DataTransCfg;
import com.chyjr.configure.Field;
import com.chyjr.configure.Table;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hp
 * Date: 14-9-10
 * Time: 下午7:24
 * To change this template use File | Settings | File Templates.
 */
public class IndustryMappingUtils {

    public static Map<String,String> getIndustryMapping(String path){
        File file = new File(path);
        SAXReader reader = new SAXReader();
        try {
            Map<String,String> map = new LinkedHashMap<>();
            Document document = reader.read(file);
            Element root = document.getRootElement();
            List<Element> list = root.elements("m");
            for(Element element : list){
                String keyword = element.attributeValue("keyword").trim();
                String value = element.attributeValue("value").trim();
                map.put(keyword,value);
            }
            return map;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
