package com.vst.core.communal.function;

import com.vst.common.tools.string.ToolsString;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串聚合统计次数
 * 聚合后数据如：“宋佳其,吴采妮,钟刚,郭蕾,李佳,吴采妮”
 * 则统计后为：“宋佳其:1,吴采妮:2,钟刚:1,郭蕾:1,李佳:1”
 * */
public class JoinStrCount extends UserDefinedAggregateFunction {
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 5100806892863617298L;

	@Override
    public StructType inputSchema() {
        return DataTypes.createStructType(new StructField[]{
                new StructField("inputStr", DataTypes.StringType, true, Metadata.empty())
        });
    }

    @Override
    public StructType bufferSchema() {
        return DataTypes.createStructType(new StructField[]{
                new StructField("bufferStr", DataTypes.StringType, true, Metadata.empty())
        });
    }

    @Override
    public DataType dataType() {
        return DataTypes.StringType;
    }

    @Override
    public boolean deterministic() {
        return true;
    }

    @Override
    public void initialize(MutableAggregationBuffer buffer) {
        buffer.update(0, "");
    }

    @Override
    public void update(MutableAggregationBuffer buffer, Row input) {
        String tmpStr = buffer.getString(0);
        String inputStr = input.getString(0);
        updateValue(buffer, tmpStr, inputStr);
    }

    @Override
    public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
        String tmpStr = buffer1.getString(0);
        String inputStr = buffer2.getString(0);
        updateValue(buffer1, tmpStr, inputStr);
    }

    /**
     * 数据合并
     * */
    private void updateValue(MutableAggregationBuffer buffer, String tmpStr, String inputStr) {
        if(!ToolsString.isEmpty(tmpStr) && !ToolsString.isEmpty(inputStr)){
            buffer.update(0, tmpStr + "," + inputStr);
        }else if(!ToolsString.isEmpty(tmpStr)){
            buffer.update(0, tmpStr);
        }else if(!ToolsString.isEmpty(inputStr)){
            buffer.update(0, inputStr);
        }else{
            buffer.update(0, "");
        }
    }

    /**
     * 计数
     * */
    @Override
    public String evaluate(Row buffer) {
        String tmpStr = buffer.getString(0);
        if(ToolsString.isEmpty(tmpStr)) return "";//如果数据为空则直接空串

        String[] tmpStrSplit = tmpStr.split(",");
        Map<String, Integer> strCount = new HashMap<>();
        for (String line : tmpStrSplit) {   //统计次数
            if(ToolsString.isEmpty(line)) continue;
            if(strCount.containsKey(line)) strCount.put(line,strCount.get(line) + 1);
            else strCount.put(line,1);
        }
        StringBuilder tmpBuff = new StringBuilder();
        for (Map.Entry<String, Integer> keyValue : strCount.entrySet()) {   //合并字符串
            tmpBuff.append(",").append(keyValue.getKey()).append(":").append(keyValue.getValue());
        }
        if(tmpBuff.length() > 0) tmpBuff.deleteCharAt(0);
        return tmpBuff.toString();
    }
}
