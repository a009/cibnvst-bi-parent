package com.vst.core.communal.sql.sqlFilter;

import java.io.Serializable;

import org.json.simple.JSONObject;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-26 下午02:14:51
 * @decription 筛选条件
 * @version 
 */
public class Filter implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7908407501717541503L;
	
	/**
	 * 筛选key
	 */
	private String key;
	
	/**
	 * 筛选值
	 */
	private String value;
	
	/**
	 * 匹配类型，1：匹配（适用于字符串），2：等于，3：不等于，4：大于等于，5：大于，6：小于等于，7：小于，8：为空，9：包含，10：正则匹配，11：区间，
	 * 12：字符串转数组长度比较，13：字符串转数组后元素比较
	 */
	private int matchType;
	
	/**
	 * 筛选匹配上后处理类型，1：通过，2：不通过
	 */
	private int yesType;
	
	/**
	 * 筛选未匹配上后处理类型，1：通过，2：不通过
	 */
	private int noType;
	
	/**
	 * 条件所限制的包名
	 */
	private String pkg;
	
	/**
	 * 条件所屏蔽的包名
	 */
	private String pkgBlock;
	
	/**
	 * 构造器
	 * @param key
	 * @param value
	 * @param matchType
	 * @param yesType
	 * @param noType
	 */
	public Filter(String key, String value, int matchType, int yesType, int noType, String pkg, String pkgBlock) {
		this.key = key;
		this.value = value;
		this.matchType = matchType;
		this.yesType = yesType;
		this.noType = noType;
		this.pkg = pkg;
		this.pkgBlock = pkgBlock;
	}

	/**
	 * 根据条件做匹配
	 * @param objs
	 * @return
	 */
	public boolean doFilter(JSONObject json){
		boolean flag = checkPkg(json);
		if(flag) {
			switch (matchType) {
			case 1:
				flag = match(json);
				break;
			case 2:
				flag = equals(json);
				break;
			case 3:
				flag = notEquals(json);
				break;
			case 4:
				flag = moreAndEquals(json);
				break;
			case 5:
				flag = moreThanEquals(json);
				break;
			case 6:
				flag = lessAndEquals(json);
				break;
			case 7:
				flag = lessThanEquals(json);
				break;
			case 8:
				flag = isNull(json);
				break;
			case 9:
				flag = contains(json);
				break;
			case 10:
				flag = regex(json);
				break;
			case 11:
				flag = compare(json);
				break;
			case 12:
				flag = strToArrayLengthCompare(json);
				break;
			case 13:
				flag = strToArrayPositionCompare(json);
				break;
			default:
				break;
			}
		}
		return flag;
	}
	
	private boolean checkPkg(JSONObject json){
		String currPkg = ToolsString.checkEmpty(json.get("pkg"));
		if(ToolsString.isEmpty(currPkg)) return true;
		return containsChannel(pkg, pkgBlock, currPkg);
	}
	
	private static boolean containsChannel(String pkg, String pkgBlock, String currPkg){
		if("0".equals(pkg) || pkg.startsWith(currPkg + ",") || pkg.contains("," + currPkg + ",")
				|| pkg.endsWith("," + currPkg)
				|| pkg.equals(currPkg)){
			if("0".equals(pkgBlock) || (!pkgBlock.startsWith(currPkg + ",") 
					&& !pkgBlock.contains("," + currPkg + ",") 
					&& !pkgBlock.endsWith("," + currPkg)
					&& !pkgBlock.equals(currPkg))){
				return true;
			}
		}
		return false;
	}
	
	private boolean match(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key)){
				return value.equals(String.valueOf(json.get(key)));
			}
		}
		return false;
	}
	
	private boolean equals(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key)){
				return ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), -2L) == ToolsNumber.parseLong(value);
			}
		}
		return false;
	}
	
	private boolean notEquals(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key)){
				return ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), -2L) != ToolsNumber.parseLong(value);
			}
		}
		return false;
	}
	
	private boolean moreAndEquals(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key)){
				return ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), -2L) >= ToolsNumber.parseLong(value);
			}
		}
		return false;
	}
	
	private boolean moreThanEquals(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key)){
				return ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), -2L) > ToolsNumber.parseLong(value);
			}
		}
		return false;
	}
	
	private boolean lessAndEquals(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key)){
				return ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), 0L) <= ToolsNumber.parseLong(value);
			}
		}
		return false;
	}
	
	private boolean lessThanEquals(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key)){
				return ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), 0L) < ToolsNumber.parseLong(value);
			}
		}
		return false;
	}
	
	private boolean isNull(JSONObject json){
		return json == null || ToolsString.isEmpty(json.get(key));
	}
	
	private boolean contains(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				return ToolsString.checkEmpty(json.get(key)).contains(value);
			}
		}
		return false;
	}
	
	private boolean regex(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				return !ToolsString.isEmpty(ToolsString.matcher(String.valueOf(json.get(key)), value, 0));
			}
		}
		return false;
	}
	
	private boolean compare(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				String[] values = value.split("~");
				if(values.length == 2){
					long start = ToolsNumber.parseLong(values[0], 0);
					long end = ToolsNumber.parseLong(values[1], 0);
					long compareValue = ToolsNumber.parseLong(String.valueOf(json.get(key)), -1);
					return start <= compareValue && compareValue <= end;
				}
			}
		}
		return false;
	}
	
	private boolean strToArrayLengthCompare(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				String[] values = value.split("\\[@!@]");
				if(values.length == 3){
					String currValue = ToolsString.checkEmpty(json.get(key));
					String[] array = currValue.split(values[0]);
					if(values[1].equals(">=")){
						return array.length >= ToolsNumber.parseInt(values[2], Integer.MAX_VALUE);
					}else if(values[1].equals(">")){
						return array.length > ToolsNumber.parseInt(values[2], Integer.MAX_VALUE);
					}else if(values[1].equals("=")){
						return array.length == ToolsNumber.parseInt(values[2], Integer.MAX_VALUE);
					}else if(values[1].equals("<=")){
						return array.length <= ToolsNumber.parseInt(values[2], Integer.MIN_VALUE);
					}else if(values[1].equals("<")){
						return array.length < ToolsNumber.parseInt(values[2], Integer.MIN_VALUE);
					}
				}
			}
		}
		return false;
	}
	
	private boolean strToArrayPositionCompare(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				String[] values = value.split("\\[@!@]");
				if(values.length == 4){
					// 正值代表正序索引，负值代表倒序索引，从0开始计数
					int index = ToolsNumber.parseInt(values[1], Integer.MIN_VALUE);
					if(Math.abs(index) >= 4) return false;
					String currValue = ToolsString.checkEmpty(json.get(key));
					String[] array = currValue.split(values[0]);
					if(array.length < Math.abs(index)) return false;
					String compareObj = ToolsString.checkEmpty(index >= 0 ? array[index] : array[array.length + index]).trim();
					String operateType = values[2];
					String compareValue = ToolsString.checkEmpty(values[3]).trim();
					if(operateType.equals(">=")){
						return ToolsNumber.parseInt(compareObj)  >= ToolsNumber.parseInt(compareValue, Integer.MAX_VALUE);
					}else if(operateType.equals(">")){
						return ToolsNumber.parseInt(compareObj)  > ToolsNumber.parseInt(compareValue, Integer.MAX_VALUE);
					}else if(operateType.equals("=")){
						return ToolsNumber.parseInt(compareObj) == ToolsNumber.parseInt(compareValue, Integer.MAX_VALUE);
					}else if(operateType.equals("<=")){
						return ToolsNumber.parseInt(compareObj) <= ToolsNumber.parseInt(compareValue, Integer.MIN_VALUE);
					}else if(operateType.equals("<")){
						return ToolsNumber.parseInt(compareObj) < ToolsNumber.parseInt(compareValue, Integer.MIN_VALUE);
					}else if(operateType.equals("equals")){
						return compareObj.equals(compareValue);
					}else if(operateType.equals("contains")){
						return compareObj.contains(compareValue);
					}else if(operateType.equals("startsWith")){
						return compareObj.startsWith(compareValue);
					}else if(operateType.equals("endsWith")){
						return compareObj.endsWith(compareValue);
					}
				}
			}
		}
		return false;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}
	
	public int getYesType() {
		return yesType;
	}

	public void setYesType(int yesType) {
		this.yesType = yesType;
	}

	public int getNoType() {
		return noType;
	}

	public void setNoType(int noType) {
		this.noType = noType;
	}
	
	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	
	public String getPkgBlock() {
		return pkgBlock;
	}

	public void setPkgBlock(String pkgBlock) {
		this.pkgBlock = pkgBlock;
	}

	@Override
	public String toString() {
		return "Filter [key=" + key + ", value=" + value + ", matchType="
				+ matchType + ", yesType=" + yesType + ", noType=" + noType
				+ ", pkg=" + pkg + "]";
	}
}
