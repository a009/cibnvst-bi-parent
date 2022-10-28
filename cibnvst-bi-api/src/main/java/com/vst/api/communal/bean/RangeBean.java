package com.vst.api.communal.bean;

import com.vst.api.communal.type.RangeEnum;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-21 上午10:16:47
 * @decription
 * @version 
 */
public class RangeBean {

	/**
	 * 格式是否合法，true：是，false：否
	 */
	private boolean isRight = false;
	
	/**
	 * 格式类型
	 */
	private String format;
	
	/**
	 * 构造器
	 * @param format
	 */
	public RangeBean(String format) {
		this.format = format;
	}

	/**
	 * 根据range值来获取类型
	 * @param range 格式说明：如果是数字类型，用[-,+]表示负无穷大到正无穷大；如果是字符串或boolean类型，用{"a","b"}来枚举，用![0, 10]来表示本身字符串长度的限定
	 * @return
	 */
	private RangeEnum getType(String range){
		if(!ToolsString.isEmpty(range)){
			if(!ToolsString.isEmpty(ToolsString.matcher(range, "^\\[-?(\\d{0,}),\\+?(\\d{0,})\\]$", 0))){
				String[] values = range.replace("[", "").replace("]", "").split(",");
				if(values.length == 2 && 
						(values[0].trim().equals("-") || values[1].trim().equals("+")
								|| (ToolsNumber.isNumber(values[0]) && ToolsNumber.isNumber(values[1]) 
										&& ToolsNumber.parseLong(values[0]) <= ToolsNumber.parseLong(values[1])))){
					return RangeEnum.A;
				}
			}else if(!ToolsString.isEmpty(ToolsString.matcher(range, "^\\{\"(\\w+)((.(\\w+))?){0,}\"(,\"(\\w+)((.(\\w+))?){0,}\"){0,}\\}$", 0))){
				return RangeEnum.B;
			}else if(!ToolsString.isEmpty(ToolsString.matcher(range, "^\\!\\[(\\d+),(\\d+)\\]", 0))){
				String[] values = range.replace("![", "").replace("]", "").split(",");
				if(values.length == 2 && 
						(ToolsNumber.parseLong(values[0]) >= 0L && ToolsNumber.isNumber(values[1]) 
								&& ToolsNumber.parseLong(values[0]) <= ToolsNumber.parseLong(values[1]))){
					return RangeEnum.C;
				}
			}else{
				return RangeEnum.ERROR;
			}
		}
		return RangeEnum.NONE;
	}
	
	/**
	 * 校验range设置格式是否合法
	 * @param obj 
	 * @return
	 */
	public RangeBean checkRange(Object obj){
		switch (getType(format)) {
		case A:
			if(obj instanceof Number){
				String[] values = format.replace("[", "").replace("]", "").split(",");
				long start = ToolsNumber.parseLong(values[0], Long.MIN_VALUE);
				long end = ToolsNumber.parseLong(values[1], Long.MAX_VALUE);
				long number = ToolsNumber.parseLong(ToolsString.checkEmpty(obj));
				if(start <= number && number <= end){
					isRight = true;
				}
			}
			break;
		
		case B:
			if(obj instanceof String || obj instanceof Boolean){
				isRight = format.contains("\"" + obj + "\"");
			}
			break;

		case C:
			if(obj instanceof String){
				String[] values = format.replace("![", "").replace("]", "").split(",");
				long start = ToolsNumber.parseLong(values[0], Long.MIN_VALUE);
				long end = ToolsNumber.parseLong(values[1], Long.MAX_VALUE);
				long number = ToolsString.checkEmpty(obj).length();
				if(start <= number && number <= end){
					isRight = true;
				}
			}
			break;
			
		case NONE:
			isRight = true;// topic校验格式设置错误的，都默认给true
			break;
			
		case ERROR:
			isRight = false;
			break;
		default:
			isRight = false;
			break;
		}
		return this;
	}
	 
	
	public boolean isRight() {
		return isRight;
	}

	@Override
	public String toString() {
		return "RangeBean [format=" + format + ", isRight=" + isRight + "]";
	}
}
