package com.vst.core.service;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.SparkSession;
import org.json.simple.JSONObject;

import com.vst.core.cache.CacheType;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.service.impl.HbaseLoaderServiceImpl;
import com.vst.core.service.impl.HdfsLoaderServiceImpl;

/**
 * @author joslyn
 * @date 2017年12月19日 下午5:05:12
 * @description
 * @version 1.0
 */
public abstract class AbstractLoaderService {

	/**
	 * 创建loader
	 * @param path
	 * @return
	 */
	public static AbstractLoaderService createLoader(String path){
		AbstractLoaderService loader = null;
		if(path.startsWith("hdfs:/")){
			loader = new HdfsLoaderServiceImpl();
		}else if(path.startsWith("hbase:/")){
			loader = new HbaseLoaderServiceImpl();
		}
		return loader;
	}
	
	/**
	 * 解析path
	 * @param sqlFactory
	 * @param allowDeleteData
	 * @return
	 */
	public abstract JSONObject processLoadPath(SqlFactory sqlFactory, boolean allowDeleteData);
	
	/**
	 * 加载数据的抽象方法
	 * @param sparkSession spark会话管理类
	 * @param path 数据加载的path，其中日期已经替换
	 * @param sqlFactory sql的工厂类
	 * @param cacheTypeBC 类型缓存
	 * @return
	 */
	public abstract JavaRDD<JSONObject> load(SparkSession sparkSession, String path, 
			final SqlFactory sqlFactory, final Broadcast<CacheType> cacheTypeBC);
}
