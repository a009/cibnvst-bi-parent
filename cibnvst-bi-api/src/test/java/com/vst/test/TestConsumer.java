package com.vst.test;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-10-19 上午11:39:20
 * @decription
 * @version 
 */
public class TestConsumer {

	/**
	 * 是否初始化，true：是，false：否
	 */
	private static boolean isInit = false;

	/**
	 * 生产者对象
	 */
	private static Consumer<String, String> consumer;
	
	/**
	 * 获取kafka生产者对象
	 * @return
	 */
	private static void initConfig(){
		if(consumer == null){
			Properties props = new Properties();    
//	        props.put("bootstrap.servers", "123.59.182.229:9092,123.59.182.230:9092,123.59.182.231:9092,123.59.182.232:9092,123.59.182.233:9092");
//	        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);//send message with 5ms delay
//	        props.put(ProducerConfig.ACKS_CONFIG, "1");
//	        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); 
//	        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        
			props.put("group.id", "test1");
//			props.put("auto.offset.reset", "earliest");
			props.put("bootstrap.servers", "123.59.182.229:9092,123.59.182.230:9092,123.59.182.231:9092,123.59.182.232:9092,123.59.182.233:9092");
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        consumer = new KafkaConsumer<String, String>(props);
	        isInit = true;
		}
	}
	
	/**
	 * 发送数据
	 * @param topic
	 * @param data
	 */
	public static void getMessage(String topic){
		if(!isInit){// 如果没有初始化属性，先初始化
			initConfig();
		}
		if(consumer != null && !ToolsString.isEmpty(topic)){
			consumer.subscribe(Arrays.asList(topic));
			while(true){
//				System.out.println(consumer.listTopics().keySet());
				ConsumerRecords<String, String> records = consumer.poll(100);
				for(ConsumerRecord<String, String> record : records){
					System.out.printf("partition=%d ,offset=%d, key=%s, value=%s", record.partition(), record.offset(), record.key(), record.value());
					System.out.println();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		getMessage("click");
	}
	
}
