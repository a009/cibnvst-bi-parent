package com.vst.core.communal.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.cache.CacheFileDatas;
import com.vst.core.cache.CacheOffsets;
import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.communal.sql.SqlFactory;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-10 下午08:43:15
 * @decription
 * @version 
 */
public class ToolsKafka {
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(ToolsKafka.class);
	
	/**
	 * 单例对象
	 */
	private static final ToolsKafka _cache = new ToolsKafka();

	/**
	 * 生产者对象
	 */
	private Consumer<String, String> _consumer;
	
	/**
	 * 获取kafka生产者对象
	 * @return
	 */
	private ToolsKafka(){
		_consumer = new KafkaConsumer<String, String>(getKafkaParams(PropertiesReader.getInstance().getProperty("kafka_group_id_offline")));
	}
	
	/**
	 * 获取单例实例
	 * @return
	 */
	public static ToolsKafka getInstance(){
		return _cache;
	}
	
	private Map<String, Object> getKafkaParams(String groupId){
		Map<String, Object> param = new HashMap<String, Object>(5);
		param.put("group.id", groupId);
        param.put("auto.offset.reset", "earliest");
        param.put("enable.auto.commit", "false");// 禁用自动提交
        param.put("max.poll.records", "1000");// 一次性消费多少条数据
        param.put("session.timeout.ms", "30000");// 30s超时
        param.put("bootstrap.servers", PropertiesReader.getInstance().getProperty("kafka_broker_list"));
        param.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        param.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return param;
	}
	
	/**
	 * 消费多个topic
	 * @param topics
	 */
	public void consumerMessages(List<String> topics){
		if(_consumer != null && topics != null && !topics.isEmpty()){
			_consumer.subscribe(topics, new ConsumerRebalanceListener() {
				@Override
				public void onPartitionsRevoked(Collection<TopicPartition> arg0) {
				}
				
				@Override
				public void onPartitionsAssigned(Collection<TopicPartition> tps) {
					for(TopicPartition tp : tps){
						String topic = tp.topic();
						Map<Integer, Long> offsetsMap = CacheOffsets.getInstance().getOffset(topic);
						if(offsetsMap != null && !offsetsMap.isEmpty()){
							_consumer.seek(tp, offsetsMap.get(tp.partition()) == null ? 0 : offsetsMap.get(tp.partition()));
						}
					}
				}
			});
			
			while(true){
				if(CacheSparkSqls.getInstance().isStopPullTasks()) {
					try {
						Thread.sleep(3000);// 3秒后再试
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				ConsumerRecords<String, String> records = _consumer.poll(500);
				if(!records.isEmpty()){
					for(ConsumerRecord<String, String> record : records){
						// 因为消息是按照时间顺序写入的，所以只需要按顺序遍历topic数据即可
						long rectime = ToolsNumber.parseLong(ToolsString.matcher(record.value(), "\"rectime\":(\\d+)"));
						if(rectime <= 0){
							logger.error("rectime is not right ===>>>" + record.value());
						}else{
							String topic = record.topic();
							CacheFileDatas.getInstance().addData(topic, record.value());
							CacheOffsets.getInstance().saveOffsetIntoCache(topic, record.partition(), record.offset() + 1);
						}
					}
					// 刷新数据到文件中
					Map<String, Long> posMap = CacheFileDatas.getInstance().flushDataIntoFile();
					for(String topic : posMap.keySet()){
						SqlFactory sqlFactory = CacheSparkSqls.getInstance().getPullDataSql(topic);
						long position = posMap.get(topic);
						String nextTimeStr = String.valueOf(position);
						sqlFactory.setSqlRunPosition(new String[]{nextTimeStr.substring(0, 8), nextTimeStr.substring(8, 10), nextTimeStr.substring(10)});
						sqlFactory.setSqlRuntime(System.currentTimeMillis());
						// 保存偏移量
						CacheOffsets.getInstance().saveOffsetsIntoFile(topic);
					}
				}
			}
		}
	}
	
	/**
	 * 提交偏移量
	 */
	public void commit(){
		if(_consumer != null){
			_consumer.commitAsync();
		}
	}
}
