package com.vst.core.communal.function;

import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.FeatureConfig;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.api.java.UDF5;

import java.util.List;

/**
 * 该函数用作指标计算，指标计算参数和规则如下：
 * param pkg 包名
 * param specId 专区类型
 * param cid 分类
 * param type (影片类型1、影片年份2、演员3、影片区域4)
 * param data '喜剧:1,科幻:1,冒险:5'
 * 调用方式：calculation(pkg,specId,cid,1,'喜剧:1,科幻:1,冒险:5')
 *
 * 参数设置详解：
 * 如果设置指标为全部(-2)并且值为0.1将得到如下结果：
 *      '喜剧:0.1,科幻:0.1,冒险:0.5'
 * 如果设置指标为 喜剧为0.2 其它(-1)为0.1 将得到如下结果：
 *      '喜剧:0.2,科幻:0.1,冒险:0.5'
 * 如果只设置 喜剧为0.2  将得到如下结果：
 *      '喜剧:0.2'
 */
public class FeatureCalculation implements UDF5<String, Long, Long, Integer, String, String> {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 8604161305282976436L;

    private Broadcast<List<FeatureConfig>> bufferFeaturesRule;

    public FeatureCalculation(Broadcast<List<FeatureConfig>> allFeaturesBC) {
//        if (allFeaturesBC == null || allFeaturesBC.value().isEmpty()) { throw new NullPointerException("Broadcast allFeaturesBC is null"); }
        this.bufferFeaturesRule = allFeaturesBC;
    }

    @Override
    public String call(String pkg, Long specId, Long cid, Integer type, String paramStr) {
        if(ToolsString.isEmpty(paramStr) || !paramStr.contains(":") || this.bufferFeaturesRule == null) return null;    //如果格式不正确或者数据不存在直接返回null
        FeatureConfig config = FeatureConfig.searchFeatureConfig(bufferFeaturesRule.value(),pkg,specId.intValue(),cid.intValue());
        if(config == null) return null;                 //找不到指标
        String[] multiterm = paramStr.split(",");
        String resut = config.featureComputor(multiterm,type);
        if(ToolsString.isEmpty(resut)) return null;     //没有规则计算过
        return resut;
    }
}
