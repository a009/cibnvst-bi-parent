package com.vst.core.tasks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vst.core.cache.CacheUserFeatures;
import com.vst.core.communal.function.BetweenStr;
import com.vst.core.communal.function.CastLong;
import com.vst.core.communal.function.FeatureCalculation;
import com.vst.core.communal.function.JoinStrCount;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function0;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.communal.bean.Msg;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.type.SqlType;
import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.service.CalculateTasksMgrService;
import com.vst.core.service.SparkSQLMgrService;
import com.vst.core.service.impl.OnlineTasksServiceImpl;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-26 下午05:48:05
 * @decription
 * @version 
 */
@SuppressWarnings("unchecked")
public class StatOnline implements Serializable, Runnable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5627457673204392L;

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(StatOnline.class);
	
	/**
	 * sparkSQL管理类
	 */
	private SparkSQLMgrService _sparkSQLMgrService;
	
	/**
	 * 构造器
	 * @param _sparkSession
	 * @param _sparkSQLMgrService
	 * @param _othersMgrService
	 */
	public StatOnline(SparkSQLMgrService sparkSQLMgrService) {
		_sparkSQLMgrService = sparkSQLMgrService;
	}
	
	@Override
	public void run(){
		final CalculateTasksMgrService offlineTasks = new OnlineTasksServiceImpl(_sparkSQLMgrService);
		String checkpoint = PropertiesReader.getInstance().getProperty("streaming_checkpoint");
		final String groupId = PropertiesReader.getInstance().getProperty("kafka_group_id_online");
		final int interval = ToolsNumber.parseInt(PropertiesReader.getInstance().getProperty("streaming_seconds"));
		JavaStreamingContext javaStreamingContext = JavaStreamingContext.getOrCreate(checkpoint, new Function0<JavaStreamingContext>() {
			private static final long serialVersionUID = 4654375635743207713L;
			@Override
			public JavaStreamingContext call() throws Exception {
				return kafkaToSparkStreaming(groupId, interval, offlineTasks);
			}
		});
		
		try {
			javaStreamingContext.start();
			javaStreamingContext.awaitTermination();
		} catch (Exception e) {
			logger.error("online error.", e);
		}
	}
	
	/**
	 * sparkstream从kafka拉取数据
	 * @param jsc
	 * @param groupId 消费id
	 * @param interval 分钟
	 * @param taskMgrService
	 */
	private JavaStreamingContext kafkaToSparkStreaming(String groupId, int interval, 
			final CalculateTasksMgrService taskMgrService){
		final List<SqlFactory> sqls = CacheSparkSqls.getInstance().getAllSqlsByState(SqlType.P_ONLINE);
		if(sqls.isEmpty()) {
			logger.info("===>>> real calculate sql is null.");
			return null;
		}
		List<String> topics = new ArrayList<String>();
		for(SqlFactory sql : sqls){
			topics.add(sql.getSqlTopic());
		}
		
		// spark配置
		SparkConf sparkConf = new SparkConf().setAppName(PropertiesReader.getInstance().getProperty("streaming_appName"))
											 .setMaster(PropertiesReader.getInstance().getProperty("spark_master"))
											 .set("spark.sql.warehouse.dir", "/mnt/disk1/sparkdata/");
		JavaStreamingContext jsc = new JavaStreamingContext(JavaSparkContext.fromSparkContext(SparkSessionFactory.getInstance(sparkConf).sparkContext()), Durations.seconds(interval));
		String checkpoint = PropertiesReader.getInstance().getProperty("streaming_checkpoint");
		jsc.checkpoint(checkpoint);
		JavaDStream<ConsumerRecord<String, String>> kafkaStream = KafkaUtils.createDirectStream(jsc, 
				LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String>Subscribe(topics, getKafkaParams(groupId)));
		kafkaStream.foreachRDD(new VoidFunction2<JavaRDD<ConsumerRecord<String, String>>, Time>() {
			private static final long serialVersionUID = 5362681494669490688L;
			@Override
			public void call(JavaRDD<ConsumerRecord<String, String>> rdd, Time time)
					throws Exception {
				long now = time.milliseconds();
				final String str = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss3, now);
				JavaRDD<JSONObject> lines = rdd.map(new Function<ConsumerRecord<String, String>, JSONObject>() {
					private static final long serialVersionUID = -4555059323127077565L;
					@Override
					public JSONObject call(ConsumerRecord<String, String> record)
							throws Exception {
						JSONObject json = (JSONObject) JSONValue.parse(record.value());
                        Msg msg = new Msg(json);
                        JSONObject newJson = msg.toStreamingJson();
                        newJson.put("real_date", ToolsNumber.parseLong(str.substring(0, 8)));
                        newJson.put("real_hh", ToolsNumber.parseLong(str.substring(8, 10)));
                        newJson.put("real_mm", ToolsNumber.parseLong(str.substring(10, 12)));
                        newJson.put("real_ss", ToolsNumber.parseLong(str.substring(12)));
                        return newJson;
					}
				});
				
				taskMgrService.runOnlineTasks(SparkSessionFactory.getInstance(rdd.context().getConf()), lines, sqls, now);
			}
		});
		return jsc;
	}
	
	private Map<String, Object> getKafkaParams(String groupId){
		Map<String, Object> param = new HashMap<String, Object>(5);
		param.put("group.id", groupId);
        param.put("auto.offset.reset", "latest");
        param.put("max.poll.records", "1000");// 一次性消费多少条数据
        param.put("session.timeout.ms", "30000");// 30s超时
        param.put("bootstrap.servers", PropertiesReader.getInstance().getProperty("kafka_broker_list"));
        param.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        param.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return param;
	}
	
	static class SparkSessionFactory{
		public static SparkSession _sparkSession = null;
		
		private SparkSessionFactory(){
		}
		
		public static SparkSession getInstance(SparkConf sparkConf){
			if(_sparkSession == null){
				_sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();
				_sparkSession.udf().register("castLong", new CastLong(), DataTypes.LongType);
				_sparkSession.udf().register("betweenStr", new BetweenStr(), DataTypes.LongType);
				_sparkSession.udf().register("joinStrCount", new JoinStrCount());
				_sparkSession.udf().register("calculation", new FeatureCalculation(JavaSparkContext.fromSparkContext(_sparkSession.sparkContext()).broadcast(CacheUserFeatures.getInstance().getAllFeatures())), DataTypes.StringType);
			}
			return _sparkSession;
		}
	}
}
