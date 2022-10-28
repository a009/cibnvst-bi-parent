package com.vst.core.communal.sql.sqlFilter;

import java.io.Serializable;
import java.util.HashSet;

import org.json.simple.JSONObject;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-26 下午05:50:39
 * @decription 重新封装json的action配置
 * @version 
 */
@SuppressWarnings("unchecked")
public class Action implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4456947482354936332L;

	/**
	 * 重新封装的属性key
	 */
	private String key;
	
	/**
	 * 重新封装的属性value
	 */
	private String value;
	
	/**
	 * 操作类型，1：在开头拼接字符串，2：在结尾拼接字符串，3：移除字符串，4：加，5：减，6：乘，7：除，8：取模，9：取模算整，10：重置默认值
	 */
	private int operateType;
	
	/**
	 * 构造器
	 * @param key
	 * @param value
	 * @param operateType
	 */
	public Action(String key, String value, int operateType) {
		this.key = key;
		this.value = value;
		this.operateType = operateType;
	}

	/**
	 * 针对json根据配置进行操作
	 * @param json
	 * @return
	 */
	public boolean doAction(JSONObject json){
		boolean flag = false;
		switch (operateType) {
		case 1:
			flag = startSplice(json);
			break;
		case 2:
			flag = endSplice(json);
			break;
		case 3:
			flag = removeStr(json);
			break;
		case 4:
			flag = add(json);
			break;
		case 5:
			flag = reduce(json);
			break;
		case 6:
			flag = multiply(json);
			break;
		case 7:
			flag = divide(json);
			break;
		case 8:
			flag = mold(json);
			break;
		case 9:
			flag = moldToInteger(json);
			break;
		case 10:
			flag = resetValue(json);
			break;
		case 11:
			flag = merge(json);
			break;
		case 12:
			flag = mergeDistinct(json);
			break;
		default:
			break;
		}
		return flag;
	}

    private boolean mergeDistinct(JSONObject json) {
        if (json != null && !json.isEmpty()) {
            if (!ToolsString.isEmpty(key) && !ToolsString.isEmpty(value)) {
                String newColumnName = value;
                String[] keys = key.split("\\[@!@]");
                HashSet<String> set = new HashSet<>();
				for (String name : keys) {
					String v = ToolsString.checkEmpty(json.get(name));
					if(ToolsString.isEmpty(v)) continue;
					for (String s : v.split(",")) set.add(s);
				}
                json.put(newColumnName, ToolsString.mkString(set.toArray(), ","));
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean merge(JSONObject json) {
        if (json != null && !json.isEmpty()) {
            if (!ToolsString.isEmpty(key) && !ToolsString.isEmpty(value)) {
                String newColumnName = value;
                String[] keys = key.split("\\[@!@]");
                String[] values = new String[keys.length];
                for (int i = 0; i < keys.length; i++) {
                    if (json.containsKey(keys[i])) {
                        values[i] = ToolsString.checkEmpty(json.get(keys[i]));
                    }
                }
                json.put(newColumnName, ToolsString.mkString(values, ","));
                return true;
            }
            return false;
        }
        return false;
    }
	
	private boolean startSplice(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				String oldValue = ToolsString.checkEmpty(json.get(key));
				json.put(key, value.concat(oldValue));
				return true;
			}
		}
		return false;
	}
	
	private boolean endSplice(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				String oldValue = ToolsString.checkEmpty(json.get(key));
				json.put(key, oldValue.concat(value));
				return true;
			}
		}
		return false;
	} 
	
	private boolean removeStr(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				String oldValue = ToolsString.checkEmpty(json.get(key));
				json.put(key, oldValue.replace(value, ""));
				return true;
			}
		}
		return false;
	}  
	
	private boolean add(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				long oldValue = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), 0);
				json.put(key, oldValue + ToolsNumber.parseLong(value, 0));
				return true;
			}
		}
		return false;
	}
	
	private boolean reduce(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				long oldValue = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), 0);
				json.put(key, oldValue - ToolsNumber.parseLong(value, 0));
				return true;
			}
		}
		return false;
	}
	
	private boolean multiply(JSONObject json){
		if(json != null && !json.isEmpty()){
			if(json.containsKey(key) && !ToolsString.isEmpty(value)){
				long oldValue = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), 0);
				json.put(key, oldValue*ToolsNumber.parseLong(value, 0));
				return true;
			}
		}
		return false;
	}
	
	private boolean divide(JSONObject json){
		if(json != null && !json.isEmpty()){
			long valueLong = ToolsNumber.parseLong(value, 0);
			if(json.containsKey(key) && !ToolsString.isEmpty(value) && valueLong != 0){
				long oldValue = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), 0);
				json.put(key, oldValue / valueLong);
				return true;
			}
		}
		return false;
	}
	
	private boolean mold(JSONObject json){
		if(json != null && !json.isEmpty()){
			long valueLong = ToolsNumber.parseLong(value, 0);
			if(json.containsKey(key) && !ToolsString.isEmpty(value) && valueLong > 0){
				long oldValue = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), 0);
				json.put(key, oldValue % valueLong);
				return true;
			}
		}
		return false;
	}
	
	private boolean moldToInteger(JSONObject json){
		if(json != null && !json.isEmpty()){
			long valueLong = ToolsNumber.parseLong(value, 0);
			if(json.containsKey(key) && !ToolsString.isEmpty(value) && valueLong > 0){
				long oldValue = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get(key)), 0);
				json.put(key, oldValue - (oldValue % valueLong));
				return true;
			}
		}
		return false;
	}
	
	private boolean resetValue(JSONObject json){
		if(json != null && !json.isEmpty()){
			String[] keys = key.split("\\[@!@]");
			String[] values = value.split("\\[@!@]");
			if(keys.length == values.length){
				for(int i = 0; i < keys.length; i++){
					Object obj = json.get(keys[i]);
					if(obj != null){
						if(obj instanceof String){
							json.put(keys[i], values[i]);
						}else if(obj instanceof Integer){
							json.put(keys[i], ToolsNumber.parseInt(values[i]));
						}else if(obj instanceof Long){
							json.put(keys[i], ToolsNumber.parseLong(values[i]));
						}else if(obj instanceof Boolean){
							json.put(keys[i], Boolean.valueOf(values[i]));
						}else if(obj instanceof Double){
							json.put(keys[i], ToolsNumber.parseDouble(values[i]));
						}
					}else{// 按数字类型处理
						json.put(keys[i], ToolsNumber.parseLong(values[i]));
					}
				}
				return true;
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

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	@Override
	public String toString() {
		return "Action [key=" + key + ", value=" + value + ", operateType=" + operateType + "]";
	}
	
}
