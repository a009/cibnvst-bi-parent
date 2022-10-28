package com.vst.core.communal.sql;

import com.vst.common.tools.string.ToolsString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class Feature implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 8602908042799073726L;

	/**
     * 包名
     */
    private String pkg;

    /**
     * 专区类型
     */
    private Long specId;

    /**
     * 分类
     */
    private Long cid;

    /**
     * 特征类型  1影片类型 2上映时间 3演员 4地区
     */
    private Long type;

    /**
     * 特征名称  -2:全部,-1:其它
     */
    private String name;

    /**
     * 特征值
     */
    private Double value;

    public Feature(String pkg, Long specId, Long cid, Long type, String name, Double value) {
        this.pkg = pkg;
        this.specId = specId;
        this.cid = cid;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * 指标类型
     */
    public enum FeatureType {
        type(1L), year(2L), actor(3L), area(4L);

        public Long value;

        FeatureType(Long value) {
            this.value = value;
        }

        public static FeatureType getFeatureType(long value) {
            if (value == type.value) return type;
            else if (value == year.value) return year;
            else if (value == actor.value) return actor;
            else if (value == area.value) return area;
            else throw new IllegalArgumentException("Parameter error. " + value);
        }


        /**
         * List<Feature>返回Map
         */
        public static Map<String, Map<FeatureType, Map<String, BigDecimal>>> listFeatureToMap(List<Feature> listFeature) {
            Map<String, Map<FeatureType, Map<String, BigDecimal>>> map = null; //返回map
            if (listFeature != null) {
                map = new HashMap<>();
                for (Feature feature : listFeature) {
                    String key = feature.pkg + "-" + feature.specId + "-" + feature.cid;
                    FeatureType featureType = getFeatureType(feature.getType());

                    Map<FeatureType, Map<String, BigDecimal>> types = map.get(key);
                    Map<String, BigDecimal> keyValue;
                    if (types == null) {
                        types = new HashMap<>();
                        keyValue = new HashMap<>();
                        keyValue.put(feature.name, new BigDecimal(ToolsString.checkEmpty(feature.value)));
                    } else {
                        keyValue = types.get(featureType);
                        if (keyValue == null) {
                            keyValue = new HashMap<>();
                        }
                        keyValue.put(feature.name, new BigDecimal(ToolsString.checkEmpty(feature.value)));
                    }
                    types.put(featureType, keyValue);
                    map.put(key, types);
                }
            }
            return map;
        }
    }

    /**
     * 指标名称
     */
    public enum FeatureName {
        whole("-2"), other("-1");

        public String value;

        FeatureName(String value) {
            this.value = value;
        }
    }
}
