package com.chyjr.platform.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.MethodObject.java] 
 * @ClassName:    [MethodObject]  
 * @Description:  [类反射]  
 * @Author:       [wangxiaohua]  
 * @CreateDate:   [Oct 14, 2014 5:06:12 PM]  
 * @UpdateUser:   [wangxiaohua]  
 * @UpdateDate:   [Oct 14, 2014 5:06:12 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class MethodObject {

    public static void testReflect(Object model) throws SecurityException,
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

            //关键。。。可访问私有变量
            field[i].setAccessible(true);


            // 将属性的首字母大写
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
                    .toUpperCase());

            if (type.equals("class java.lang.String"))
            {
                // 如果type是类类型，则前面包含"class "，后面跟类名
                Method m = model.getClass().getMethod("get" + name);
                // 调用getter方法获取属性值
                String value = (String) m.invoke(model);
                if (value != null)
                {
                    //给属性设置
                    field[i].set(model,  field[i].getType().getConstructor(field[i].getType()).newInstance(NumUtil.toHtmlOnly(value)));
                    System.out.println("attribute value:" + value);
                }

            }
            if (type.equals("class java.lang.Integer"))
            {
                Method m = model.getClass().getMethod("get" + name);
                Integer value = (Integer) m.invoke(model);
                if (value != null)
                {
                    System.out.println("attribute value:" + value);
                }
            }
            if (type.equals("class java.lang.Short"))
            {
                Method m = model.getClass().getMethod("get" + name);
                Short value = (Short) m.invoke(model);
                if (value != null)
                {
                    System.out.println("attribute value:" + value);
                }
            }
            if (type.equals("class java.lang.Double"))
            {
                Method m = model.getClass().getMethod("get" + name);
                Double value = (Double) m.invoke(model);
                if (value != null)
                {
                    System.out.println("attribute value:" + value);
                }
            }
            if (type.equals("class java.lang.Boolean"))
            {
                Method m = model.getClass().getMethod("get" + name);
                Boolean value = (Boolean) m.invoke(model);
                if (value != null)
                {
                    System.out.println("attribute value:" + value);
                }
            }
            if (type.equals("class java.util.Date"))
            {
                Method m = model.getClass().getMethod("get" + name);
                Date value = (Date) m.invoke(model);
                if (value != null)
                {
                    System.out.println("attribute value:"
                            + value.toLocaleString());
                }
            }
        }
    }
}
