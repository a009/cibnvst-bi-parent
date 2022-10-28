package com.vst.defend.communal.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weiwei
 * @date 2014-10-27 下午07:52:32
 * @description javaBean工具类
 * @version
 */
@SuppressWarnings("unchecked")
public class BeanUtils {

	/**
	 * 构造器私有化
	 */
	private BeanUtils() {

	}

	/**
	 * map转bean
	 * 
	 * @param map
	 * @param bean
	 *            bean不能为空！
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	public static Object mapToBean(Map<String, Object> map, Class bean)
			throws IllegalArgumentException, IllegalAccessException,
			IntrospectionException, InstantiationException,
			InvocationTargetException, ParseException {
		if (map == null || map.isEmpty() || bean == null) {
			return null;
		}

		// 获取属性类
		BeanInfo beanInfo = Introspector.getBeanInfo(bean);
		// 实例化javabean对象
		Object obj = bean.newInstance();

		// 获取属性集合
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		// 遍历属性集合，给map中有的属性赋值
		for (PropertyDescriptor p : properties) {
			String name = p.getName();
			Object value = map.get(name);
			if (!"class".equals(name) && value != null) {
				String type = p.getPropertyType().getSimpleName();
				setValue(type, value, p, obj);
			}
		}

		return obj;
	}

	/**
	 * bean转map
	 * 
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static Map<String, Object> beanToMap(Object bean)
			throws IntrospectionException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (bean == null) {
			return null;
		}

		Map<String, Object> result = new HashMap<String, Object>();

		// 获取属性类
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());

		// 获取属性集合
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		// 遍历属性集合，给map中有的属性赋值
		for (PropertyDescriptor p : properties) {
			// 获取属性名称
			String pName = p.getName();
			if (!"class".equals(pName)) {
				Object value = p.getReadMethod().invoke(bean, new Object[0]);

				if (value == null)
					result.put(pName, "");
				else
					result.put(pName, value);
			}
		}

		return result;
	}

	/**
	 * 如果map中的某字段类型不匹配bean中的，需要转型
	 * 
	 * @param type
	 * @param value
	 * @param p
	 * @param bean
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 */
	private static void setValue(String type, Object value,
			PropertyDescriptor p, Object bean) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ParseException {
		if (value != null) {
			if (type.equals("String")) {
				// 第一个参数:从中调用基础方法的对象 第二个参数:用于方法调用的参数
				p.getWriteMethod().invoke(bean, new Object[] { value + "" });
			} else if (type.equals("int") || type.equals("Integer")) {
				p.getWriteMethod().invoke(bean,
						new Object[] { new Integer(value + "") });
			} else if (type.equals("double") || type.equals("Double")) {
				p.getWriteMethod().invoke(bean,
						new Object[] { new Double(value + "") });
			} else if (type.equals("float") || type.equals("Float")) {
				p.getWriteMethod().invoke(bean,
						new Object[] { new Float(value + "") });
			} else if (type.equals("long") || type.equals("Long")) {
				p.getWriteMethod().invoke(bean,
						new Object[] { new Long(value + "") });
			} else if (type.equals("boolean") || type.equals("Boolean")) {
				p.getWriteMethod().invoke(bean,
						new Object[] { Boolean.valueOf(value + "") });
			} else if (type.equals("BigDecimal")) {
				p.getWriteMethod().invoke(bean,
						new Object[] { new BigDecimal(value + "") });
			} else if (type.equals("Date")) {
				Date date = null;
				if (value.getClass().getName().equals("java.util.Date")) {
					date = (Date) value;
				} else {
					String format = ((String) value).indexOf(":") > 0 ? "yyyy-MM-dd hh:mm:ss"
							: "yyyy-MM-dd";
					SimpleDateFormat sf = new SimpleDateFormat(format);
					date = sf.parse((String) (value));
				}
				if (date != null) {
					p.getWriteMethod().invoke(bean, new Object[] { date });
				}
			} else if (type.equals("byte[]")) {
				p.getWriteMethod().invoke(bean,
						new Object[] { new String(value + "").getBytes() });
			}
		}
	}
}
