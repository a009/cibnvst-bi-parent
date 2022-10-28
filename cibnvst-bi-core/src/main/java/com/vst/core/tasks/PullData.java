package com.vst.core.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.type.SqlType;
import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.communal.util.ToolsIp;
import com.vst.core.communal.util.ToolsKafka;
import com.vst.core.service.SparkSQLMgrService;
import com.vst.core.service.impl.SparkSQLMgrServiceImpl;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-11 上午11:06:16
 * @decription
 * @version 
 */
public class PullData {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(PullData.class);
	
	/**
	 * sparkSQL管理类
	 */
	private static SparkSQLMgrService _sparkSQLMgrService;
	
	public static void main(String[] args) {
		// 加载配置文件
		PropertiesReader.getInstance().loadConfig();
		logger.info("configs ===>>>"+PropertiesReader.getInstance().printInfo());
		
		// 启动刷新sql数据线程
		_sparkSQLMgrService = new SparkSQLMgrServiceImpl();
		_sparkSQLMgrService.initSQLCache();
		Thread flushSparkSQLTask = new Thread(new FlushSQLTask(_sparkSQLMgrService), "flush-sparkSQL-task");
		flushSparkSQLTask.start();
		logger.info("===>>> start flush-sparkSQL-task thread success!");
		// 加载ip库文件
		ToolsIp.getInstance().init();
		
		// 开启保存数据任务
		Thread intoHdfsTask = new Thread(new PutFileIntoHdfsTask(), "put-file-into-hdfs-thread");
		intoHdfsTask.start();
		
		// 开启删除本地临时数据任务
		Thread deleteLocalFileTask = new Thread(new DeleteLocalFileTask(), "delete-local-file-thread");
		deleteLocalFileTask.start();
		
		PullData pull = new PullData();
		// 刷新sql到数据库
		Thread syncOffsetTask = new Thread(pull.new WriteSQLTask(), "sync-offset-task");
		syncOffsetTask.start();
		
		// 启动拉取离线数据任务
		pull.runSaveOfflineTasks();
	}
	
	/**
	 * 执行拉取离线数据任务
	 */
	private void runSaveOfflineTasks(){
		// 获取所有拉取数据执行的任务
		List<SqlFactory> sqls = CacheSparkSqls.getInstance().getAllSqlsByState(SqlType.P_PULLDATA);
		List<String> topics = new ArrayList<String>();
		for(SqlFactory sql : sqls){
			topics.add(sql.getSqlTopic());
		}
		ToolsKafka.getInstance().consumerMessages(topics);
	}
	
	private class WriteSQLTask implements Runnable{

		/**
		 *  任务线程
		 */
		@Override
		public void run() {
			while(true){
				try {
					try {
						Thread.sleep(60*1000L);// 1分钟执行一次
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					String offsetDir = PropertiesReader.getInstance().getProperty("kafka_offset_file_path");
					List<SqlFactory> sqls = CacheSparkSqls.getInstance().getAllSqlsByState(SqlType.P_PULLDATA);
					for(SqlFactory sql : sqls){
						String topic = sql.getSqlTopic();
						if(ToolsString.isEmpty(topic)) continue;
						String offsets = ToolsFile.readFileToString(offsetDir + "/" + topic + ".topic", "utf-8");
						if(!ToolsString.isEmpty(offsets)){
							sql.setSqlRunPosition(new String[]{offsets.replace("\r\n", ";")});
							_sparkSQLMgrService.syncSQLIntoDB(sql);
						}
					}
					logger.info("write sql into db success!");
				} catch (Exception e) {
					logger.error("write sql into db error!. ERROR:", e);
				}
			}
		}
		
	}
}
