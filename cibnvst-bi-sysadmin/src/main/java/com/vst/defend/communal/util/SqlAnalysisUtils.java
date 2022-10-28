package com.vst.defend.communal.util;

import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author lhp
 * @date 2019-2-14 下午04:51:16
 * @version
 */
public class SqlAnalysisUtils {
	
	private static final String TABLE_TEMPLATE = "parquet.`/cibnvst/data/parquet/{date}/*/*/{table}.parquet`";

    /**
     * 解析SQL 表名 table_click_{20180101}
     * */
    public static String analysisTableName(String sql) throws ParseException {
        String localSql = sql;
        Pattern regex = Pattern.compile("table[_0-9\\{\\},a-zA-Z\\*\\-]+");
        Matcher matcher = regex.matcher(sql);
        while(matcher.find()){
            String group = matcher.group(0);
            String[] tableDate = group.split("_");
            if(tableDate.length == 3){
                if (tableDate[2].contains("-")){
                    String[] dateRange = tableDate[2].split("-");
                    if(dateRange.length == 2){
                        String date1 = dateRange[0].replace("{","");
                        String date2 = dateRange[1].replace("}","");
                        List<Integer> dateSection = VstTools.getDateSection(date1, date2, "yyyyMMdd");
                        String dateRangeStr = listToString(dateSection);
                        
                        localSql = localSql.replace(group, TABLE_TEMPLATE.replace("{date}","{"+dateRangeStr+"}").replace("{table}",tableDate[1]));
                    }
                }else{
                    localSql = localSql.replace(group, TABLE_TEMPLATE.replace("{date}","{"+tableDate[2]+"}").replace("{table}",tableDate[1]));
                }
            }
        }
        return localSql;
    }
    
    private static String listToString(List<Integer> list){
    	StringBuffer result = new StringBuffer();
    	if(list != null && list.size() > 0){
    		for(Integer i : list){
    			result.append(i).append(",");
    		}
    		result.deleteCharAt(result.length()-1);
    	}
    	return result.toString();
    }
}
