package com.vst.core.communal.sql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import org.json.simple.JSONObject;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.number.ToolsRandom;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.cache.CacheType;
import com.vst.core.communal.bean.Video;
import com.vst.core.communal.bean.VideoTopic;

/**
 * @author joslyn
 * @date 2017年12月2日 下午7:48:18
 * @description
 * @version 1.0
 */
public class SaveSqlInfo implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6483783215561719099L;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 插入字段集合
	 */
	private List<Save> insertParams;
	
	/**
	 * 环比字段集合
	 */
	private List<Save> momParams;
	
	/**
	 * 格式化字段集合
	 */
	private List<Save> formatParams;
	
	/**
	 * 算环比查询key
	 */
	private String queryKey;
	
	/**
	 * 逗号
	 */
	private static final String COMMA = ",";
	
	/**
	 * 构造器
	 * @param tableName 表名
	 * @param insertParams 插入字段集合
	 * @param momParams 环比字段集合
	 */
	public SaveSqlInfo(String tableName, List<JSONObject> dataList) {
		insertParams = new ArrayList<Save>();
		momParams = new ArrayList<Save>();
		formatParams = new ArrayList<Save>();
		this.tableName = tableName;
		this.init(dataList);
	}
	
	private void init(List<JSONObject> dataList){
		if(dataList != null && !dataList.isEmpty()){
			for(JSONObject json : dataList){
				// 表名称
				String tableName = ToolsString.checkEmpty(json.get("vst_save_table"));
				if(ToolsString.isEmpty(tableName)) continue;
				
				// 类型，1、主键插入字段，2：普通插入字段，3：算环比key字段，4：算环比value字段
				int type = ToolsNumber.parseInt(String.valueOf(json.get("vst_save_type")));
				if(type < 1) continue;
				
				// 如果字段名为空，过滤
				String name = ToolsString.checkEmpty(json.get("vst_save_name"));
				if(ToolsString.isEmpty(name)) continue;
				
				// 当该字段是主键的时候，字段长度不为空
				int length = ToolsNumber.parseInt(String.valueOf(json.get("vst_save_length")));
				if(type == 1 && length <= 0) continue;
				
				// 字段值类型，1：字符串，2：整型，3：浮点数
				int dataType = ToolsNumber.parseInt(String.valueOf(json.get("vst_save_data_type")));
				if(dataType < 1) continue;
				
				Save save = new Save();
				save.setTableName(tableName);
				save.setName(name);
				save.setDataType(dataType);
				save.setLength(length);
				save.setType(type);
				save.setDefaultVaule(ToolsString.checkEmpty(json.get("vst_save_default")));
				save.setFormat(ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("vst_save_is_format"))) == 1);
				save.setFormatType(ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("vst_save_format_type"))));
				save.setFormatUnion(ToolsString.checkEmpty(json.get("vst_save_format_union")));
				
				if(type == 1 || type == 2){
					insertParams.add(save);
				}else if(type == 3 || type == 4){
					momParams.add(save);
				}else if(type == 5){
					queryKey = save.getName();
				}
				
				if(save.isFormat()){
					formatParams.add(save);
				}
			}
		}
	}
	
	/**
	 * 得到插入语句
	 * @return
	 */
	public String toInsertSQL(){
		if(ToolsString.isEmpty(tableName)){
			throw new RuntimeException("tableName is null.");
		}
		if(insertParams == null || insertParams.isEmpty()){
			throw new RuntimeException("insert params is null.");
		}
		
		StringBuilder sb = new StringBuilder("insert into ").append(tableName).append(" (");
		for(int i = 0; i < insertParams.size() - 1; i++){
			Save save = insertParams.get(i);
			sb.append(save.getName()).append(COMMA);
		}
		sb.append(insertParams.get(insertParams.size() - 1).getName()).append(") values (");
		
		for(int i = 0; i < insertParams.size() - 1; i++){
			sb.append("?").append(COMMA);
		}
		sb.append("?);");
		return sb.toString();
	}

	/**
     * 拼接插入SQL
     * @param data 数据
     * @return SQL
     */
    public List<String> toInsertSQLValues(List<Map<String, Object>> data) {
        if (ToolsString.isEmpty(tableName)) {
            throw new RuntimeException("tableName is null.");
        }
        if (insertParams == null || insertParams.isEmpty()) {
            throw new RuntimeException("insert params is null.");
        }
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("insert values is null.");
        }

        StringBuilder sb = new StringBuilder("insert into ").append(tableName).append(" (");
        for (int i = 0; i < insertParams.size() - 1; i++) {
            Save save = insertParams.get(i);
            sb.append(save.getName()).append(COMMA);
        }
        sb.append(insertParams.get(insertParams.size() - 1).getName()).append(") values ");

		int split = 2000;
		int index = 1;
		List<String> sqls = new ArrayList<>();
		StringBuilder values = new StringBuilder(sb);
		for (Map<String, Object> map : data) {
			values.append("(");
			for (Save save : insertParams) {
			    String v = ToolsString.checkEmpty(map.get(save.getName())).replace("\\","\\\\").replace("'","\\'");
				values.append("'").append(v).append("',");
			}
			values.deleteCharAt(values.length() - 1);
			values.append("),");
			if(index % split == 0){
				values.deleteCharAt(values.length() - 1);
				sqls.add(values.append(";").toString());
				values = new StringBuilder(sb);
			}else if(index == data.size()){
				values.deleteCharAt(values.length() - 1);
				sqls.add(values.append(";").toString());
			}
			index += 1;
		}
        return sqls;
    }
	
	/**
	 * 得到删除语句
	 * @param date
	 * @return
	 */
	public String toDeleteSQL(String date){
		return "delete from " + tableName + " where " + queryKey + "=" + date;
	}
	
	/**
	 * 获取查询历史维度的数据语句
	 * @param date
	 * @return
	 */
	public String toQueryHistorySQL(String date){
		if(ToolsString.isEmpty(tableName)){
			throw new RuntimeException("tableName is null.");
		}
		if(momParams == null || momParams.isEmpty()){
			throw new RuntimeException("query params is null.");
		}
		
		StringBuilder sb = new StringBuilder("select ");
		for(int i = 0; i < momParams.size() - 1; i++){
			Save save = momParams.get(i);
			sb.append(save.getName()).append(COMMA);
		}
		sb.append(momParams.get(momParams.size() - 1).getName()).append(" from ").append(tableName);
		sb.append(" where ").append(queryKey).append("=").append(date);
		return sb.toString();
	}
	
	/**
	 * 获取查询环比keys
	 * @return
	 */
	public List<Save> toQueryHistorySQLKeys(){
		if(ToolsString.isEmpty(tableName)){
			throw new RuntimeException("tableName is null.");
		}
		if(momParams == null || momParams.isEmpty()){
			throw new RuntimeException("query params is null.");
		}
		
		List<Save> result = new ArrayList<Save>(); 
		for(Save save : momParams){
			if(save.getType() == 3){
				result.add(save);
			}
		}
		return result;
	}
	
	/**
	 * 获取查询环比values
	 * @return
	 */
	public List<Save> toQueryHistorySQLValues(){
		if(ToolsString.isEmpty(tableName)){
			throw new RuntimeException("tableName is null.");
		}
		if(momParams == null || momParams.isEmpty()){
			throw new RuntimeException("query params is null.");
		}
		
		List<Save> result = new ArrayList<Save>(); 
		for(Save save : momParams){
			if(save.getType() == 4){
				result.add(save);
			}
		}
		return result;
	}
	
	/**
	 * 格式化map
	 * @param map
	 * @param tableName
	 * @param date
	 */
    public void format(Map<String, Object> map, int date, Map<String, Map<String, String>> yesterdayData,
                       Map<String, Map<String, String>> weekData, Map<String, Map<String, String>> monthData,
                       Map<String, Map<String, String>> mergeData) {
		for(Save save : formatParams){
			if(save.getType() == 1){// 如果是主键
				if(save.getDataType() == 1){// 如果是字符串类型
					map.put(save.getName(), ToolsRandom.getRandom(save.getLength()));
				}else if(save.getDataType() == 2){// 如果是整数
					map.put(save.getName(), ToolsNumber.parseInt(ToolsRandom.getRandomNumber(save.getLength())));
				}
			}else if(save.getFormatType() == 1){// 如果是日期
				map.put(save.getName(), date);
			}else if(save.getFormatType() == 2){// 如果是新增时间
				map.put(save.getName(), System.currentTimeMillis());
			}else if(save.getFormatType() == 3){// 如果是操作人
				map.put(save.getName(), "bi_operator");
			}else if(save.getFormatType() == 4){// 如果是均值，保留两位小数
				String[] params = ToolsString.checkEmpty(save.getFormatUnion()).split(",");
				if(params.length != 2) continue;
				long pv = ToolsNumber.parseLong(String.valueOf(map.get(params[0])), 0);
				long uv = ToolsNumber.parseLong(String.valueOf(map.get(params[1])), 1);
				map.put(save.getName(), ToolsNumber.divide(pv, uv));
			}else if(save.getFormatType() == 5){// 如果是均值，不保留小数
				String[] params = ToolsString.checkEmpty(save.getFormatUnion()).split(",");
				if(params.length != 2) continue;
				long playtime = ToolsNumber.parseLong(String.valueOf(map.get(params[0])), 0);
				long uv = ToolsNumber.parseLong(String.valueOf(map.get(params[1])), 1);
				map.put(save.getName(), playtime / uv);
			}else if(save.getFormatType() == 12){// 均值百分比，保留两位小数
				String[] params = ToolsString.checkEmpty(save.getFormatUnion()).split(",");
				if(params.length != 2) continue;
				long playtime = ToolsNumber.parseLong(String.valueOf(map.get(params[0])), 0);
				long uv = ToolsNumber.parseLong(String.valueOf(map.get(params[1])), 1);
				map.put(save.getName(), ToolsNumber.divideForPercent(playtime, uv));
			}else if(save.getFormatType() == 6){// 如果是毫秒转秒
				long time = ToolsNumber.parseLong(String.valueOf(map.get(save.getName())));
				map.put(save.getName(),  time / 1000);
			}else if(save.getFormatType() == 7){// 如果是字符串截取
				String value = ToolsString.checkEmpty(map.get(save.getName()));
				if(value.length() > save.getLength()){
					map.put(save.getName(), value.substring(0, save.getLength()).concat("..."));
				}else if(value.length() == 0){
					map.put(save.getName(), save.getDefaultVaule());
				}
			}else if(save.getFormatType() == 8){// 如果是算天环比
				String param = ToolsString.checkEmpty(save.getFormatUnion());
				if(ToolsString.isEmpty(param)) continue;
				List<Save> keys = toQueryHistorySQLKeys();
				String currKey = "";
				// 组装keys
				for(Save keySave : keys){
					currKey += ToolsString.checkEmpty(map.get(keySave.getName())) + "[@!@]";
				}
				currKey = currKey.substring(0, currKey.lastIndexOf("[@!@]"));
				Map<String, String> valueMap = yesterdayData.get(currKey);
				if(valueMap != null && !valueMap.isEmpty()){
					long yesterdayAmount = ToolsNumber.parseLong(valueMap.get(param));
					long pv = ToolsNumber.parseLong(String.valueOf(map.get(param)), 0);
					map.put(save.getName(), ToolsNumber.divideForPercent(pv - yesterdayAmount, yesterdayAmount));
				}else{
					map.put(save.getName(), save.getDefaultVaule());
				}
			}else if(save.getFormatType() == 9){// 如果是算周环比
				String param = ToolsString.checkEmpty(save.getFormatUnion());
				if(ToolsString.isEmpty(param)) continue;
				List<Save> keys = toQueryHistorySQLKeys();
				String currKey = "";
				// 组装keys
				for(Save keySave : keys){
					currKey += ToolsString.checkEmpty(map.get(keySave.getName())) + "[@!@]";
				}
				currKey = currKey.substring(0, currKey.lastIndexOf("[@!@]"));
				Map<String, String> valueMap = weekData.get(currKey);
				if(valueMap != null && !valueMap.isEmpty()){
					long yesterdayAmount = ToolsNumber.parseLong(valueMap.get(param));
					long pv = ToolsNumber.parseLong(String.valueOf(map.get(param)), 0);
					map.put(save.getName(), ToolsNumber.divideForPercent(pv - yesterdayAmount, yesterdayAmount));
				}else{
					map.put(save.getName(), save.getDefaultVaule());
				}
			}else if(save.getFormatType() == 10){// 如果是算vv月环比
				String param = ToolsString.checkEmpty(save.getFormatUnion());
				if(ToolsString.isEmpty(param)) continue;
				List<Save> keys = toQueryHistorySQLKeys();
				String currKey = "";
				// 组装keys
				for(Save keySave : keys){
					if(save.getDataType() == 1){// 字符串
						currKey += ToolsString.checkEmpty(map.get(keySave.getName())) + "[@!@]";
					}else if(save.getDataType() == 2){// 整数
						currKey += ToolsNumber.parseLong(ToolsString.checkEmpty(map.get(keySave.getName()))) + "[@!@]";
					}else if(save.getDataType() == 3){// 浮点数
						currKey += ToolsNumber.parseDouble(ToolsString.checkEmpty(map.get(keySave.getName()))) + "[@!@]";
					}else if(save.getDataType() == 4){// 布尔值
						currKey += Boolean.valueOf(ToolsString.checkEmpty(map.get(keySave.getName()))) + "[@!@]";
					}
				}
				currKey = currKey.substring(0, currKey.lastIndexOf("[@!@]"));
				Map<String, String> valueMap = monthData.get(currKey);
				if(valueMap != null && !valueMap.isEmpty()){
					long monthAmount = ToolsNumber.parseLong(valueMap.get(param));
					long pv = ToolsNumber.parseLong(String.valueOf(map.get(param)), 0);
					map.put(save.getName(), ToolsNumber.divideForPercent(pv - monthAmount, monthAmount));
				}else{
					map.put(save.getName(), save.getDefaultVaule());
				}
			}else if(save.getFormatType() == 11){// 如果是校正属性
				String param = ToolsString.checkEmpty(save.getFormatUnion());
				if(ToolsString.isEmpty(param)) continue;
				String[] props = param.split(",");
				if(props.length < 0) continue;
				
				String key = ToolsString.checkEmpty(map.get(save.getName()));
				Video video = CacheType.getInstance().getVideo(key);
				if(video != null){
					for(String prop : props){
						String[] values = prop.split("[|]");
						if(values.length != 2) continue;
						if(values[0].equals("cid")){
							map.put(values[1], video.getCid());
						}else if(values[0].equals("specId")){
							map.put(values[1], video.getSpecialType());
						}else if(values[0].equals("title")){
							map.put(values[1], video.getTitle());
						}
					}
				}else{
					VideoTopic topic =  CacheType.getInstance().getVideoTopic(key);
					if(topic != null){
						for(String prop : props){
							String[] values = prop.split("[|]");
							if(values.length != 2) continue;
							if(values[0].equals("cid")){
								map.put(values[1], topic.getCid());
							}else if(values[0].equals("specId")){
								map.put(values[1], topic.getSpecialType());
							}else if(values[0].equals("template")){
								map.put(values[1], topic.getTemplateType());
							}else if(values[0].equals("title")){
								map.put(values[1], topic.getTitle());
							}
						}
					}
				}
            } else if (save.getFormatType() == 13) { //map数据合并   str:double,str1:double2
                List<Save> keys = toQueryHistorySQLKeys();
                if(keys == null || keys.isEmpty()) continue;
                StringBuilder currKey = new StringBuilder();
                // 组装keys
                for (Save keySave : keys) {
					currKey.append("[@!@]").append(ToolsString.checkEmpty(map.get(keySave.getName())));
                }
				if(currKey.length() > 0) currKey.delete(0,5);
                Map<String, String> valueMap = mergeData.get(currKey.toString());
                if (valueMap != null && !valueMap.isEmpty()) {
                    String nowStrMap = ToolsString.checkEmpty(map.get(save.getName()));
                    String oldStrMap = ToolsString.checkEmpty(valueMap.get(save.getName()));

                    Map<String, String> nowMap = ToolsString.strToMap(nowStrMap, ",", ":");
                    Map<String, String> oldMap = ToolsString.strToMap(oldStrMap, ",", ":");

                    Map<String, String> dataMap = new HashMap<>();
                    if (nowMap != null && oldMap != null) {
                        for (Map.Entry<String, String> nowKeyValue : nowMap.entrySet()) {    //左联 和内联
                            String key = nowKeyValue.getKey();
                            BigDecimal sum = new BigDecimal(nowKeyValue.getValue());
                            if (oldMap.containsKey(key)) {
                                BigDecimal r = new BigDecimal(oldMap.get(key));
								dataMap.put(key, ToolsString.checkEmpty(sum.add(r).floatValue()));
                            }else{
								dataMap.put(key, ToolsString.checkEmpty(sum));
							}
                        }
                        for (Map.Entry<String, String> oldKeyValue : oldMap.entrySet()) {   //右联
                            String key = oldKeyValue.getKey();
                            BigDecimal sum = new BigDecimal(oldKeyValue.getValue());
                            if (!nowMap.containsKey(key)) {
                                dataMap.put(key, ToolsString.checkEmpty(sum.floatValue()));
                            }
                        }
                    }else if(nowMap != null){ dataMap.putAll(nowMap);
                    }else if(oldMap != null) dataMap.putAll(oldMap);

                    String mergeStr = ToolsString.mapToStr(dataMap, ",", ":");
                    map.put(save.getName(), mergeStr);
                }
            }
        }
    }
	/** 
	 * @Description: 算环比的时间字段
	 */
	public String getQueryKey() {
		return queryKey;
	}

	/**
	 * 获取columns
	 * @return
	 */
	public List<Save> getColumns(){
		return insertParams;
	}
	
	/**
	 * 查询主键
	 * @return
	 */
	public Save getPrimary(){
		for(Save save : insertParams){
			if(save.getType() == 1){
				return save;
			}
		}
		return null;
	}
	
	/**
	 * 是否包含月环比属性
	 * @return
	 */
	public boolean containsRatio(int type){
		for(Save save : formatParams){
			if(save.getFormatType() == type){
				return true;
			}
		}
		return false;
	}
}
