package com.vst.core.tasks;

import com.vst.core.service.impl.FeatureConfigMgrServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.service.OthersMgrService;
import com.vst.core.service.SparkSQLMgrService;
import com.vst.core.service.impl.OthersMgrServiceImpl;
import com.vst.core.service.impl.SparkSQLMgrServiceImpl;
import com.vst.core.tasks.StatOnline.SparkSessionFactory;

/**
 * @author joslyn
 * @date 2018年3月20日 上午10:49:14
 * @description 统计总入口
 * @version 1.0
 */
public class Stat {

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(Stat.class);
	
	private static SparkSession _sparkSession;
	
	/**
	 * sparkSQL管理类
	 */
	private static SparkSQLMgrService _sparkSQLMgrService;
	
	private OthersMgrService _othersMgrService;

	private FeatureConfigMgrServiceImpl _configMgrService;
	
	/**
	 * 初始化配置
	 */
	private void initConfig(){
		// 初始化spark sql
		PropertiesReader.getInstance().loadConfig();
		logger.info("===>>> " + PropertiesReader.getInstance().printInfo());
		
		// 启动刷新sql数据线程
		_sparkSQLMgrService = new SparkSQLMgrServiceImpl();
		_sparkSQLMgrService.initSQLCache();
		Thread flushSparkSQLTaks = new Thread(new FlushSQLTask(_sparkSQLMgrService), "flush-sparkSQL-task");
		flushSparkSQLTaks.start();
		logger.info("===>>> start flush-sparkSQL-task thread success!");
		
		// 初始化影片类型缓存
		_othersMgrService = new OthersMgrServiceImpl();
		_othersMgrService.initCache();
		Thread flushCacheTypeTaks = new Thread(new FlushCacheTypeTask(_othersMgrService), "flush-videoType-task");
		flushCacheTypeTaks.start();
		logger.info("===>>> start flush-videoType-task thread success!");

		// 初始化配置-特征配置缓存
		_configMgrService = new FeatureConfigMgrServiceImpl();
		_configMgrService.initCache();
		Thread flushConfigTaks = new Thread(new FlushCacheTypeTask(_configMgrService), "flush-config-task");
		flushConfigTaks.start();
		logger.info("===>>> start flush-config-task thread success!");

		// spark配置
		SparkConf sparkConf = new SparkConf().setAppName(PropertiesReader.getInstance().getProperty("spark_appName"))
											 .setMaster(PropertiesReader.getInstance().getProperty("spark_master"))
											 .set("spark.sql.warehouse.dir", "/mnt/disk1/sparkdata/")
											 .set("spark.streaming.stopGracefullyOnShutdown", "true");
		_sparkSession = SparkSessionFactory.getInstance(sparkConf);
	}
	
	public static void main(String[] args) {
		try {
			Stat stat = new Stat();
			stat.initConfig();
			Thread offlineThread = new Thread(new StatOffline(_sparkSession, _sparkSQLMgrService), "stat-offline-thread");
			offlineThread.start();
			Thread onlineThread = new Thread(new StatOnline(_sparkSQLMgrService), "stat-online-thread");
			onlineThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
