package com.vst.common.tools.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/** 
 * @文件名：BeanToMapUtils.java 
 * @作用： 
 * @作者：fucheng
 * @创建时间：2014年3月6日 
 */  
public class BeanToMapUtils {  
	
    /** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param clazz 要转化的类型 
     * @param map 包含属性值的 map 
     * @return 转化出来的 JavaBean 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InstantiationException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */  
    public static <T> T toBean(Class<T> clazz, Map<String,Object> map) {  
        T obj = null;  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);  
            obj = clazz.newInstance(); // 创建 JavaBean 对象  
  
            // 给 JavaBean 对象的属性赋值  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (int i = 0; i < propertyDescriptors.length; i++) {  
                PropertyDescriptor descriptor = propertyDescriptors[i];  
                String propertyName = descriptor.getName();  
                if (map.containsKey(propertyName)) {  
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。  
                    Object value = map.get(propertyName);  
                    if ("".equals(value)) {  
                        value = null;  
                    }  
                    Object[] args = new Object[1];  
                    args[0] = value;  
                    try {  
                        descriptor.getWriteMethod().invoke(obj, args);  
                    } catch (InvocationTargetException e) {  
                        System.out.println("字段映射失败");  
                    }  
                }  
            }  
        } catch (IllegalAccessException e) {  
            System.out.println("实例化 JavaBean 失败");  
        } catch (IntrospectionException e) {  
            System.out.println("分析类属性失败");  
        } catch (IllegalArgumentException e) {  
            System.out.println("映射错误");  
        } catch (InstantiationException e) {  
            System.out.println("实例化 JavaBean 失败");  
        }  
        return (T) obj;  
    }  
  
    /** 
     * 将一个 JavaBean 对象转化为一个 Map 
     * @param bean 要转化的JavaBean 对象 
     * @return 转化出来的 Map 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */  
    public static Map<String,String> toMap(Object bean) {  
        Class<? extends Object> clazz = bean.getClass();  
        Map<String, String> returnMap = new HashMap<String, String>();  
        BeanInfo beanInfo = null;  
        try {  
            beanInfo = Introspector.getBeanInfo(clazz);  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (int i = 0; i < propertyDescriptors.length; i++) {  
                PropertyDescriptor descriptor = propertyDescriptors[i];  
                String propertyName = descriptor.getName();  
                if (!propertyName.equals("class")) {  
                    Method readMethod = descriptor.getReadMethod();  
                    String result = null;  
                    result = (String) readMethod.invoke(bean, new Object[0]);  
                    if (null != propertyName) {  
                        propertyName = propertyName.toString();  
                    }  
                    if (null != result) {  
                        result = result.toString();  
                    }  
                    returnMap.put(propertyName, result);  
                }  
            }  
        } catch (IntrospectionException e) {  
            System.out.println("分析类属性失败");  
        } catch (IllegalAccessException e) {  
            System.out.println("实例化 JavaBean 失败");  
        } catch (IllegalArgumentException e) {  
            System.out.println("映射错误");  
        } catch (InvocationTargetException e) {  
            System.out.println("调用属性的 setter 方法失败");  
        }  
        return returnMap;  
    }  
  
}  
