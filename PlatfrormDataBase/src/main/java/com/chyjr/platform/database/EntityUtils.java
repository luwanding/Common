package com.chyjr.platform.database;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wangxiaohua on 14-9-2.
 */

/**
 * @ProjectName:  [PlatfrormDataBase]
 * @Package:      [com.chyjr.platform.database.EntityUtils.java] 
 * @ClassName:    [EntityUtils]  
 * @Description:  [类反射]  
 * @Author:       [wangxiaohua]  
 * @CreateDate:   [Oct 14, 2014 5:48:14 PM]  
 * @UpdateUser:   [wangxiaohua]  
 * @UpdateDate:   [Oct 14, 2014 5:48:14 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class EntityUtils {

    public static void reflect(Object model) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, InstantiationException
    {
        // 获取实体类的所有属性，返回Field数组
        Field[] field = model.getClass().getDeclaredFields();
        // 获取属性的名字
        String[] modelName = new String[field.length];
        String[] modelType = new String[field.length];
        for (int i = 0; i < field.length; i++)
        {
            // 获取属性的名字
            String name = field[i].getName();
            modelName[i] = name;
            // 获取属性类型
            String type = field[i].getGenericType().toString();
            modelType[i] = type;

            //可访问私有变量
            field[i].setAccessible(true);

            // 将属性的首字母大写
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
                    .toUpperCase());

            if (type.equals("class java.lang.String"))
            {
                // 如果type是类类型，则前面包含"class "，后面跟类名
                Method m = model.getClass().getMethod("get" + name);
                // 调用getter方法获取属性值
                String value =null;
                try{
                     value = (String) m.invoke(model);
                }catch (Exception e){
                    return;
                }

                if (value != null)
                {
                   field[i].set(model,  field[i].getType().getConstructor(field[i].getType()).newInstance(toHtmlOnly(value)));
                }
            }
        }
    }

    /**
     * 过滤html标签
     * @param strSource
     * @return
     */
    public static String toHtmlOnly(String strSource) {
        if(StringUtils.isBlank(strSource)){
            return strSource;
        }
        if(strSource.toLowerCase().indexOf("<br")!=-1){
            strSource=strSource.replaceAll("<br>","\r\n");
            strSource=strSource.replaceAll("<br/>","\r\n");
            strSource=strSource.replaceAll("<br />","\r\n");
            strSource=strSource.replaceAll("<BR>","\r\n");
            strSource=strSource.replaceAll("<BR />","\r\n");
            strSource=strSource.replaceAll("<BR/>","\r\n");
        }

//        if(strSource.toLowerCase().indexOf())
        char charLt = '<';
        char charGt = '>';
        StringBuffer StrBufReturn = new StringBuffer();
        for (int i = 0; i < strSource.length(); i++) {
            if (strSource.charAt(i) == charLt)
                StrBufReturn.append("&lt;");
            else if (strSource.charAt(i) == charGt)
                StrBufReturn.append("&gt;");
            else
                StrBufReturn.append(strSource.charAt(i));
        }

        return StrBufReturn.toString().replaceAll("&lt;p&gt;","<p>").replaceAll("&lt;/p&gt;","</p>");
    }
}
