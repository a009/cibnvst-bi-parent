package com.vst.defend.communal.util;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;

/**
 * @author weiwei
 * @date 2014-11-19 下午02:30:44
 * @description 
 * @version	
 */
public class LogUtils {

	/**
	 * 构造器私有化
	 */
	private LogUtils(){
		
	}
	
	/**
	 * 操作日志格式化（只支持普通bean类比较，不支持两个map做比较）
	 * @param operateType
	 * @param oldObj bean类，老数据比较类
	 * @param newObj bean类，新数据比较类
	 * @param filterFields 过滤掉不记录的字段
	 * @return
	 * @throws IntrospectionException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static String logFormat(int operateType, Object oldObj, Object newObj, String... filterFields) 
		throws IntrospectionException, InstantiationException, IllegalAccessException{
		// 返回结果
		StringBuilder sb = new StringBuilder();
		
		// 新增
		if(operateType == VstConstants.OPERATE_TYPE_ADD){
			sb.append("新增操作，新增内容：");
			
			Field[] newFields = newObj.getClass().getDeclaredFields();
			for(int i = 0; i < newFields.length; i++){
				newFields[i].setAccessible(true);
				String name = newFields[i].getName();
				Object value = newFields[i].get(newObj);
				
				if(value != null){
					sb.append(name).append("=").append(value).append(",");
				}
			}
		}else if(operateType == VstConstants.OPERATE_TYPE_UPDATE){
			sb.append("修改操作，修改前值：");
			String content = compareClass(sb, oldObj, newObj, filterFields);
			// 移除最后一个逗号
			if(sb.lastIndexOf(",") != -1){
				sb.replace(sb.lastIndexOf(","), sb.length(), "");
			}
			sb.append("，修改后值：").append(content);
		}else if(operateType == VstConstants.OPERATE_TYPE_DEL){
			sb.append("删除操作，删除内容：");
			
			Field[] oldFields = oldObj.getClass().getDeclaredFields();
			for(int i = 0; i < oldFields.length; i++){
				oldFields[i].setAccessible(true);
				String name = oldFields[i].getName();
				Object value = oldFields[i].get(oldObj);
				
				if(value != null){
					sb.append(name).append("=").append(value).append(",");
				}
			}
		}
		
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : sb.toString();
	}
	
	/**
	 * 比较两个类的异同
	 * @param sb
	 * @param oldObj
	 * @param newObj
	 * @param filterFields
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static String compareClass(StringBuilder sb, Object oldObj, Object newObj, String... filterFields) 
				throws IllegalArgumentException, IllegalAccessException{
		// 返回结果
		StringBuilder sb2 = new StringBuilder();
		
		if(oldObj != null){
			Field[] oldFields = oldObj.getClass().getDeclaredFields();
			Field[] newFields = newObj.getClass().getDeclaredFields();
			label : for(int i = 0; i < newFields.length; i++){
				oldFields[i].setAccessible(true);
				newFields[i].setAccessible(true);
				
				String name = oldFields[i].getName();
				Object value = oldFields[i].get(oldObj);
				
				String name2 = newFields[i].getName();
				Object value2 = newFields[i].get(newObj);
				
				// 过滤掉的字段不记录
				for(String filterName : filterFields){
					if(name2.equals(filterName)) continue label;
				}
				
				// 如果新旧类该字段都为空，不比较
				if(value == null && value2 == null){
					continue;
				}
				
				// 如果修改前为空，修改后不为空
				if(value == null && value2 != null){
					sb.append(name).append("=").append(value).append(",");
					sb2.append(name2).append("=").append(value2).append(",");
				}else if(value != null && value2 == null){// 如果修改前不为空，修改后为空
					//sb.append(name).append("=").append(value).append(",");
					//sb2.append(name2).append("=").append(value2).append(",");
				}else if(value != null && value2 != null && !value.toString().equals(value2.toString())){// 如果修改前后都不为空
					sb.append(name).append("=").append(value).append(",");
					sb2.append(name2).append("=").append(value2).append(",");
				}
			}
		}else{
			Field[] newFields = newObj.getClass().getDeclaredFields();
			for(int i = 0; i < newFields.length; i++){
				newFields[i].setAccessible(true);
				
				String name = newFields[i].getName();
				Object value = newFields[i].get(newObj);
				
				// 这种情况直接打印出修改后不为空的值
				if(value != null){
					sb2.append(name).append("=").append(value).append(",");
				}
				
			}
		}
		
		return sb2.toString();
	}
}
