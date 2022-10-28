package com.vst.api.communal.util;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.vst.api.cache.CacheSysConfig;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-21 下午04:23:59
 * @decription
 * @version 
 */
public class KafkaUtils {
	
	/**
	 * 是否初始化，true：是，false：否
	 */
	private static boolean isInit = false;

	/**
	 * 生产者对象
	 */
	private static Producer<String, String> producer;
	
	/**
	 * 连续失败最大次数
	 */
	private static int MAX_FAILURE_TIMES = 5;
	
	/**
	 * 失败次数
	 */
	private static int FAILURE_TIMES = 0;
	
	/**
	 * 获取kafka生产者对象
	 * @return
	 */
	private static void initConfig(boolean forceInit){
		if(forceInit || producer == null){
			Properties props = new Properties();    
	        props.put("bootstrap.servers", CacheSysConfig.getInstance().getStringValue("bootstrap.servers"));
	        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);//send message with 5ms delay
	        props.put(ProducerConfig.ACKS_CONFIG, "1");
	        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); 
	        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	        producer = new KafkaProducer<String, String>(props);
	        isInit = true;
		}
	}
	
	/**
	 * 发送数据
	 * @param topic
	 * @param data
	 */
	public static void sendMessage(String topic, String data) throws Exception{
		if(!isInit){// 如果没有初始化属性，先初始化
			initConfig(false);
		}
		if(!ToolsString.isEmpty(topic) && !ToolsString.isEmpty(data)){
			try {
				// 当失败次数达到最大失败次数，就需要重新初始化
				if(FAILURE_TIMES >= MAX_FAILURE_TIMES){
					producer.close(10L, TimeUnit.SECONDS);
					initConfig(true);
					FAILURE_TIMES = 0;// 重置失败次数
				}
				producer.send(new ProducerRecord<String, String>(topic, data.toString()));
			} catch (Exception e) {
				FAILURE_TIMES++;
				throw new RuntimeException("kafka send message error!", e);
			}
		}
	}
}
