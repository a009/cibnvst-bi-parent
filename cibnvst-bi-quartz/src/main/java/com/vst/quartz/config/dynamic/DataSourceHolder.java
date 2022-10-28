package com.vst.quartz.config.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kai 
 * 数据源切换类
 * TODO: 2018/4/23
 */
public class DataSourceHolder {
	/** 
	*  contextHolder :
	* DynamicDataSource实现determineCurrentLookupKey方法,
	* Spring使用自定义数据源时就会调用这俩个方法,但是如果使用List或者Set等
	* 集合存储datasource String类型变量会造成线程修改混乱,因为spring单例会使实例对象变量数据共享
	* 也可以修改prototype来达到DynamicDataSource多例模式,推荐使用ThreadLocal存储
	* ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本
	*/
	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();
	
	private static final Logger log = LoggerFactory.getLogger(DataSourceHolder.class);

	public static void setDataSourceKey(String dataSourceKey) {
		CONTEXT_HOLDER.set(dataSourceKey);
	}

	public static String getDataSourceKey() {
		String key = CONTEXT_HOLDER.get();
		log.info("Thread:" + Thread.currentThread().getName()
				+ "dataSource key is " + key);
		return key;
	}

	public static void clearDataSourceKey() {
		CONTEXT_HOLDER.remove();
	}
}
