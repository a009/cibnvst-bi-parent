package com.vst.defend.communal.bean;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ReportBean {
	/**
	 * 返回的数据--类型为jsonObject
	 */
	public List<JSONObject> data;
	
	/**
	 * 返回的数据--类型为Map
	 */
	public List<Map<String, Object>> mapData;
	
	/**
	 * 返回的数据，按date分类
	 */
	Map<Integer, List<Map<String, Object>>> dateMap;
	
	/**
	 * 返回的数据的条数,即单页有多少行
	 */
	public long singleSize;
	
	/**
	 * 数据库实际存在的条数
	 */
	public long totalSize;
	
	/**
	 * 总量
	 */
	public long sum;
	
	public String title;
	public int resultType;

	public List<JSONObject> getData() {
		return data;
	}

	public void setData(List<JSONObject> data) {
		this.data = data;
	}
	
	public List<Map<String, Object>> getMapData() {
		return mapData;
	}

	public void setMapData(List<Map<String, Object>> mapData) {
		this.mapData = mapData;
	}
	
	public Map<Integer, List<Map<String, Object>>> getDateMap() {
		return dateMap;
	}

	public void setDateMap(Map<Integer, List<Map<String, Object>>> dateMap) {
		this.dateMap = dateMap;
	}
	
	public long getSingleSize() {
		return singleSize;
	}

	public void setSingleSize(long singleSize) {
		this.singleSize = singleSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public int getResultType() {
		return resultType;
	}

	public void setResultType(int resultType) {
		this.resultType = resultType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}
}
