package com.vst.core.tasks;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.communal.util.ToolsHdfs;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-11 下午09:33:09
 * @decription
 * @version 
 */
public class PutFileIntoHdfsTask implements Runnable{
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(PutFileIntoHdfsTask.class);
	
	/**
	 * 
	 */
	private static SparkSession _sparkSession;
	
	/**
	 * 分隔符
	 */
	private static final String SEPARATOR = "/";
	
	/**
	 * 判断文件是否完全写完的间隔时间标准，3分钟
	 */
	private static final long INTERVAL_TIME = 3*60*1000L;
	
	/**
	 * 工程启动时间
	 */
	private static final long PROJECT_START_TIME = System.currentTimeMillis();

	@Override
	public void run() {
		SparkConf sparkConf = new SparkConf()
								.setAppName(PropertiesReader.getInstance().getProperty("spark_appName"))
								.setMaster(PropertiesReader.getInstance().getProperty("spark_master"));
		_sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();
		ToolsHdfs hdfs = new ToolsHdfs();
		while(true){
			try {
				String localPath = PropertiesReader.getInstance().getProperty("hdfs_data_local_path");
				File dir = new File(localPath);
				if(dir.exists()){
					String hdfsPath = PropertiesReader.getInstance().getProperty("hdfs_data_path");
					long now = System.currentTimeMillis();
					for(File dateDir : dir.listFiles()){// 日期文件夹yyyyMMdd
						String date = dateDir.getName();
						if(dateDir.exists() && dateDir.isDirectory()){
							for(File hhDir : dateDir.listFiles()){// 小时文件夹HH
								String hh = hhDir.getName();
								if(hhDir.exists() && hhDir.isDirectory()){
									for(File mmDir : hhDir.listFiles()){// 分文件mm
										String mm = mmDir.getName();
										if(mmDir.exists() && mmDir.isDirectory()){
											for(File topicDir : mmDir.listFiles()){// topic文件夹
												if(topicDir.exists() && topicDir.isDirectory()){
													String topic = topicDir.getName();
													for(File file : topicDir.listFiles()){// topic文件
														if(file.exists() && file.isFile()){
															// 文件的修改时间大于INTERVAL_TIME分钟，并且工程启动时间超过INTERVAL_TIME分钟
															if(now - file.lastModified() > INTERVAL_TIME
																	&& now - PROJECT_START_TIME > INTERVAL_TIME
																	&& file.getName().endsWith(".dat")){
																try {
																	StringBuilder lastPathDir = new StringBuilder(hdfsPath).append(SEPARATOR)
																									.append(date).append(SEPARATOR)
																									.append(hh).append(SEPARATOR)
																									.append(mm).append(SEPARATOR)
																									.append(topic);
																	String path = "file://" + file.getAbsolutePath();
																	hdfs.uploadFile(path, "/kafkaDataTemp" + file.getAbsolutePath());
																	Dataset<Row> json = _sparkSession.read().json("/kafkaDataTemp" + file.getAbsolutePath());
																	json.coalesce(5).write().mode(SaveMode.Append).parquet(lastPathDir.toString());
																	file.renameTo(new File(file.getAbsolutePath().replace(".dat", ".finished")));
																	logger.info("===>>> put file[" + file.getAbsolutePath() + "] into hdfs success!");
																	hdfs.deleteFile("/kafkaDataTemp", true);
																} catch (Exception e) {
																	logger.error("===>>> put file[" + file.getAbsolutePath() + "] into hdfs failure!", e);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("save file into hdfs error. ERROR:", e);
			}
			
			try {
				Thread.sleep(5*1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}// 1分钟执行一次
		}
	}

}
