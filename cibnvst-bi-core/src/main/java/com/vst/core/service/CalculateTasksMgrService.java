package com.vst.core.service;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import org.json.simple.JSONObject;

import com.vst.core.communal.sql.SqlFactory;

/**
 * @author weiwei(joslyn)
 * @date 2017-10-24 下午11:15:04
 * @decription 计算任务管理类
 * @version 
 */
public abstract class CalculateTasksMgrService implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3058448008453861651L;

	/**
	 * 执行离线任务
	 * @param sqls
	 */
	public void runOfflineTasks(List<SqlFactory> sqls){
		throw new UnsupportedOperationException("not support this method!");
	}
	
	/**
	 * 执行在线任务
	 * @param lines
	 * @param sqls
	 */
	public void runOnlineTasks(SparkSession sparkSession, JavaRDD<JSONObject> lines, List<SqlFactory> sqls, long batchTime){
		throw new UnsupportedOperationException("not support this method!");
	}
	
}
