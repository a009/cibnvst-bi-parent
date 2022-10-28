package com.vst.core.communal.sql;

import com.sun.istack.Nullable;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 指标配置类
 */
public class FeatureConfig implements Serializable {

	private static final long serialVersionUID = -3198527857567689781L;
	private String pkg;     //指标所属包
    private int specId;     //指标所属专区
    private int cid;        //指标所属分类
    private List<FeatureInfo> featureInfoList = new ArrayList<>();    //指标信息

    public FeatureConfig(String pkg, int specId, int cid) {
        this.pkg = pkg;
        this.specId = specId;
        this.cid = cid;
    }

    public String getPkg() { return pkg; }

    public void setPkg(String pkg) { this.pkg = pkg; }

    public int getSpecId() { return specId; }

    public void setSpecId(int specId) { this.specId = specId; }

    public int getCid() { return cid; }

    public void setCid(int cid) { this.cid = cid; }

    public List<FeatureInfo> getFeatureInfoList() { return featureInfoList; }

    public void setFeatureInfo(int type, String name, BigDecimal value) { this.featureInfoList.add(new FeatureInfo(type, name, value)); }

    public boolean match(String pkg, int specId, int cid) { return this.specId == specId && this.cid == cid && Objects.equals(pkg, this.pkg); }

	public List<FeatureInfo> searchFeatureInfosByType(int type) {
        List<FeatureInfo> typeFeatureInfo = new ArrayList<>();
        for (FeatureInfo featureInfo : featureInfoList) { if (featureInfo.match(type)) typeFeatureInfo.add(featureInfo); }
        if(!typeFeatureInfo.isEmpty()) Collections.sort(typeFeatureInfo);
        return typeFeatureInfo;
    }

    public static FeatureConfig searchFeatureConfig(List<FeatureConfig> featureConfigs, String pkg, int specId, int cid) {
        for (FeatureConfig featureConfig : featureConfigs) { if (featureConfig.match(pkg, specId, cid)) return featureConfig; }
        return null;
    }

    public String featureComputor(String[] data,int type) {
        if (data == null || data.length == 0) return "";        //非法参数返回空串
        List<FeatureInfo> infos = searchFeatureInfosByType(type);
        StringBuilder computor = new StringBuilder();
        foreach:for (FeatureInfo computorRule : infos) {
            switch (computorRule.name.trim()) {
                case "-2":  //所有
                    for (String line : data) {      //每条数据对应一个分值
                        if (ToolsString.isEmpty(line)) continue;    //数据是空串跳过
                        String[] lineSplit = line.split(":");
                        if (lineSplit.length != 2) continue;    //非法格式跳过
                        if (computorRule.type == 2 && lineSplit[0].length() != 4) continue; //非法年份过滤
                        BigDecimal num = new BigDecimal(ToolsNumber.parseInt(lineSplit[1]));
                        float multiply = num.multiply(computorRule.getValue()).floatValue();
                        String computorLine = lineSplit[0] + ":" + multiply;
                        computor.append(",").append(computorLine);
                    }
                    break foreach;
                case "-1":
                    List<String> perMatch = new ArrayList<>();
                    for (FeatureInfo featureInfo : infos) {   //计算完全匹配
                        if (featureInfo.name.trim().equals("-1")) continue;    //如果为其它跳过
                        for (String line : data) {
                            if (ToolsString.isEmpty(line)) continue;    //数据是空串跳过
                            String[] lineSplit = line.split(":");
                            if (lineSplit.length != 2) continue;        //非法格式跳过
                            String key = lineSplit[0];
                            if (!featureInfo.name.trim().equals(key)) continue; //非完全匹配跳过
                            if (featureInfo.type == 2 && key.length() != 4) continue; //非法年份过滤

                            BigDecimal num = new BigDecimal(ToolsNumber.parseInt(lineSplit[1]));
                            float multiply = num.multiply(featureInfo.getValue()).floatValue();
                            String computorLine = lineSplit[0] + ":" + multiply;
                            computor.append(",").append(computorLine);
                            perMatch.add(lineSplit[0]);
                        }
                    }
                    for (String line : data) {      //计算未包含完全匹配的数据
                        if (ToolsString.isEmpty(line)) continue;    //数据是空串跳过
                        String[] lineSplit = line.split(":");
                        if (lineSplit.length != 2) continue;    //非法格式跳过
                        String key = lineSplit[0];

                        if (perMatch.contains(key)) continue;   //完全匹配跳过
                        if (computorRule.type == 2 && key.length() != 4) continue; //非法年份过滤

                        BigDecimal num = new BigDecimal(ToolsNumber.parseInt(lineSplit[1]));
                        float multiply = num.multiply(computorRule.getValue()).floatValue();
                        String computorLine = lineSplit[0] + ":" + multiply;
                        computor.append(",").append(computorLine);
                    }
                    break foreach;
                default:    //计算完全匹配
                    for (String line : data) {
                        if (ToolsString.isEmpty(line)) continue;        //数据是空串跳过
                        String[] lineSplit = line.split(":");
                        if (lineSplit.length != 2) continue;            //非法格式跳过
                        String key = lineSplit[0];
                        if (!computorRule.name.trim().equals(key)) continue;    //不相等说明不是完全匹配内容
                        if (computorRule.type == 2 && key.length() != 4) continue; //非法年份过滤

                        BigDecimal num = new BigDecimal(ToolsNumber.parseInt(lineSplit[1]));
                        float multiply = num.multiply(computorRule.getValue()).floatValue();
                        String computorLine = lineSplit[0] + ":" + multiply;
                        computor.append(",").append(computorLine);
                    }
            }
        }
        if (computor.length() > 0) computor.deleteCharAt(0);
        return computor.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureConfig that = (FeatureConfig) o;
        return specId == that.specId &&
                cid == that.cid &&
                Objects.equals(pkg, that.pkg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkg, specId, cid);
    }

    /**
     * 指标类
     */
    public static class FeatureInfo implements Comparable<Object>,Serializable {
		private static final long serialVersionUID = 7606649000131815769L;
		private int type;    //指标类型（影片类型、影片年份、演员、影片地区）
        private String name;    //指标名称（-2 全部）
        private BigDecimal value;   //指标初始值
        private int sort;

        FeatureInfo(int type, String name, BigDecimal value) {
            this.type = type;
            this.name = name;
            this.value = value;
            if("-2".equals(name.trim())) sort = -1;      //靠前面
            else if("-1".equals(name.trim())) sort = 0 ; //其次
            else sort = 1 ;                              //靠最后
        }

        public int getType() { return type; }

        public void setType(int type) { this.type = type; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public BigDecimal getValue() { return value; }

        public void setValue(BigDecimal value) { this.value = value; }

        boolean match(int type) { return this.type == type; }

        @Override
        public int compareTo(@Nullable Object o) {
            if(o instanceof FeatureInfo){
                FeatureInfo feature = (FeatureInfo) o;
                return this.sort - feature.sort;
            }
            return 0;
        }
    }
}
