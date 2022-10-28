package com.vst.core.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.util.PropertiesReader;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.OffsetMetadataAndError;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetFetchRequest;
import kafka.javaapi.OffsetFetchResponse;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;

/**
 * @author weiwei(joslyn)
 * @date 2017-10-19 下午10:14:06
 * @decription
 * @version 
 */
public class CacheOffsets {
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(CacheOffsets.class);

	/**
	 * 单例类
	 */
	private static final CacheOffsets _cache = new CacheOffsets();
	
	/**
	 * 存储所有的topic偏移量，从集群环境加载数据
	 * Map<topic, Map<partition, offset>
	 */
	private Map<String, Map<Integer, Long>> _offsetsFromCluster;
	
	/**
	 * 存储所有的topic偏移量，从本地文件加载数据
	 * Map<topic, Map<partition, offset>
	 */
	private Map<String, Map<Integer, Long>> _offsetsFromFile;
	
	/**
	 * 存储所有的topic偏移量，从内存中加载
	 * Map<topic, Map<partition, offset>
	 */
	private Map<String, Map<Integer, Long>> _offsetsFromCache;
	
	/**
	 * 构造器私有化
	 */
	private CacheOffsets(){
		_offsetsFromCluster = new HashMap<String, Map<Integer,Long>>();
		_offsetsFromFile = new HashMap<String, Map<Integer,Long>>();
		_offsetsFromCache = new HashMap<String, Map<Integer,Long>>();
	}
	
	/**
	 * 获取单例对象
	 * @return
	 */
	public static CacheOffsets getInstance(){
		return _cache;
	}
	
	/**
	 * 根据topic获取偏移量
	 * @param topic
	 * @return
	 */
	public Map<Integer, Long> getOffset(String topic){
		// 如果topic为空，直接返回空
		if(ToolsString.isEmpty(topic)) return null;
		Map<String, Map<Integer, Long>> offsetsMap = null;
		// 优先从本地文件加载偏移量
		if(_offsetsFromFile.isEmpty()){
			loadOffsetsFromFile(PropertiesReader.getInstance().getProperty("kafka_offset_file_path"));
			// 如果本地文件存在，就读取本地文件
			if(!_offsetsFromFile.isEmpty()){
				offsetsMap = _offsetsFromFile;
			}else if(_offsetsFromCluster.isEmpty()){// 如果本地文件不存在，从集群获取
				loadOffsetsFromCluster(PropertiesReader.getInstance().getProperty("kafka_group_id_offline"), topic, 
						PropertiesReader.getInstance().getProperty("kafka_broker_list").split(","), 
						PropertiesReader.getInstance().getPropertyInt("kafka_broker_port"), _offsetsFromCluster);
				if(!_offsetsFromCluster.isEmpty()){
					offsetsMap = _offsetsFromCluster;
				}
			}
		}else{
			offsetsMap = _offsetsFromFile;
		}
		
		if(offsetsMap != null && !offsetsMap.isEmpty()){
			return offsetsMap.get(topic);
		}
		return null;
	}
	
	/**
	 * 保存偏移量到内存中
	 * @param topic
	 * @param partition
	 * @param offset
	 */
	public void saveOffsetIntoCache(String topic, int partition, long offset){
		Map<Integer, Long> partitionMap = _offsetsFromCache.get(topic);
		if(partitionMap == null){
			partitionMap = new HashMap<Integer, Long>(5);
			_offsetsFromCache.put(topic, partitionMap);
		}
		partitionMap.put(partition, offset);
	}
	
	/**
	 * 保存偏移量到文件中
	 * @param topic
	 * @param offset
	 */
	public void saveOffsetsIntoFile(String topic){
		if(!ToolsString.isEmpty(topic)){
			boolean isSuccess = false;
			int tryCount = 0;
//			// 从集群中加载偏移量
//			Map<String, Map<Integer, Long>> offsetsMap = new HashMap<String, Map<Integer,Long>>();
//			loadOffsetsFromCluster(PropertiesReader.getInstance().getProperty("kafka_group_id_offline"), topic, 
//						PropertiesReader.getInstance().getProperty("kafka_broker_list").split(","), 
//						PropertiesReader.getInstance().getPropertyInt("kafka_broker_port"), offsetsMap);
			Map<Integer, Long> partitionMap = _offsetsFromCache.get(topic);
			if(partitionMap == null || partitionMap.isEmpty()) return;
			do {
				isSuccess = saveOffsetsAsFile(topic, partitionMap);
				tryCount++;
				if(tryCount > 1){
					logger.info("===>>> trying (" + tryCount + ") to save topic[" + topic + "] offset to file ".concat(isSuccess ? "success!" : "failure!"));
				}else{
					logger.info("===>>> save topic[" + topic + "] offset to file ".concat(isSuccess ? "success!" : "failure!"));
				}
			} while (!isSuccess && tryCount < 3);
			
			// 如果保存文件成功，再写入到缓存变量中
			if(isSuccess){
				for(Integer partition : partitionMap.keySet()){
					Map<Integer, Long> map = _offsetsFromFile.get(topic);
					if(map == null){
						map = new HashMap<Integer, Long>(5);
						_offsetsFromFile.put(topic, map);
					}
					map.put(partition, partitionMap.get(partition));
					
					map = _offsetsFromCluster.get(topic);
					if(map == null){
						map = new HashMap<Integer, Long>(5);
						_offsetsFromCluster.put(topic, map);
					}
					map.put(partition, partitionMap.get(partition));
				}
			}
		}
	}
	
	private boolean saveOffsetsAsFile(String topic, Map<Integer, Long> partitionMap){
		StringBuilder data = new StringBuilder();
		for(Integer partition : partitionMap.keySet()){
			data.append(partition).append("[@!@]").append(partitionMap.get(partition)).append("\r\n");
		}
		boolean isSuccess = ToolsFile.writeBytesToFile(PropertiesReader.getInstance().getProperty("kafka_offset_file_path") + "/" + topic + ".topic", data.toString().getBytes());
		return isSuccess;
	}
	
	/**
	 * 从配置文件中加载偏移量
	 * @param filePath
	 */
	private void loadOffsetsFromFile(String filePath){
		File file = new File(filePath);
		if(file.exists() && file.isDirectory()){
			for(File topicFile : file.listFiles()){
				List<String> list = ToolsFile.readFileToList(topicFile, "utf-8");
				if(list != null && !list.isEmpty()){
					for(String line : list){
						if(ToolsString.isEmpty(line)) continue;
						String[] values = line.split("\\[@!@]");
						if(values.length != 2) continue;
						// 校验topic
						String topic = topicFile.getName().replace(".topic", "");
						if(ToolsString.isEmpty(topic)) continue;
						// 校验分区
						int partition = ToolsNumber.parseInt(values[0]);
						if(partition < 0) continue;
						long offset = ToolsNumber.parseLong(values[1], 0);
						
						Map<Integer, Long> partitionMap = _offsetsFromFile.get(topic);
						if(partitionMap == null){
							partitionMap = new HashMap<Integer, Long>(5);
							_offsetsFromFile.put(topic, partitionMap);
						}
						partitionMap.put(partition, offset);
					}
				}
			}
		}
	}
	
	/**
	 * 从线上集群环境中获取偏移量
	 * @param groupId 分组id
	 * @param topic 会话专题
	 * @param brokers kafka集群地址，不含端口号
	 * @param port 集群端口号
	 */
	private void loadOffsetsFromCluster(String groupId, String topic, String[] brokers, int port, Map<String, Map<Integer, Long>> offsetsFromCluster){
		// 默认消费的偏移量是当前分区最早的偏移量
		// 获取当前topic分区对应的分区元数据（主要包括leader节点的连接信息）
		TreeMap<Integer, PartitionMetadata> partitionMetadataMap = this.getLeader(groupId, topic, brokers);
		if(partitionMetadataMap != null && !partitionMetadataMap.isEmpty()){
			for(Map.Entry<Integer, PartitionMetadata> entry : partitionMetadataMap.entrySet()){
				int partition = entry.getKey();
				String host = entry.getValue().leader().host();
				String clientId = "client_" + topic + "_" + partition;
				SimpleConsumer sc = new SimpleConsumer(host, port, 10000, 64*1024, groupId);
				long readOffset = this.getLastOffset(sc, groupId, topic, partition, kafka.api.OffsetRequest.LatestTime(), clientId);
				Map<Integer, Long> partitionMap = offsetsFromCluster.get(topic);
				if(partitionMap == null){
					partitionMap = new HashMap<Integer, Long>(5);
					offsetsFromCluster.put(topic, partitionMap);
				}
				partitionMap.put(partition, readOffset);
			}
		}
	}
	
	private long getLastOffset(SimpleConsumer sc, String groupId, String topic, int partition, long whichTime, String clientId){
		long offset = -1l;
		TopicAndPartition tap = new TopicAndPartition(topic, partition);
		List<TopicAndPartition> requestInfo = new ArrayList<TopicAndPartition>();
		requestInfo.add(tap);
		OffsetFetchRequest request = new OffsetFetchRequest(groupId, requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientId);
		OffsetFetchResponse response = sc.fetchOffsets(request);
		// 获取返回值
		Map<TopicAndPartition, OffsetMetadataAndError> returnOffsetMetadata = response.offsets();
		if(returnOffsetMetadata != null && !returnOffsetMetadata.isEmpty()){
			OffsetMetadataAndError offsetMeta = returnOffsetMetadata.get(tap);
			if(offsetMeta.error() == ErrorMapping.NoError()){
				offset = offsetMeta.offset();
			}
		}
		
		if(offset < 0){
			Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfoMap = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>(1);
			requestInfoMap.put(tap, new PartitionOffsetRequestInfo(whichTime, 1));
			
			OffsetRequest req = new OffsetRequest(requestInfoMap, kafka.api.OffsetRequest.CurrentVersion(), clientId);
			OffsetResponse res = sc.getOffsetsBefore(req);
			if(res.hasError()){
				logger.error("Error fetching offset. errorCode = " + res.errorCode(topic, partition));
				return -1l;
			}
			offset = res.offsets(topic, partition)[0];
		}
		return offset;
	}
	
	private TreeMap<Integer, PartitionMetadata> getLeader(String groupId, String topic, String[] brokers){
		if(brokers != null){
			TreeMap<Integer, PartitionMetadata> result = new TreeMap<Integer, PartitionMetadata>();
			for(String broker : brokers){
				SimpleConsumer sc = null;
				try {
					// 创建简单消费者对象
					String[] values = broker.split(":");
					sc = new SimpleConsumer(values[0], ToolsNumber.parseInt(values[1]), 10000, 64*1024, groupId);
					// 创建获取参数的topic名称集合
					List<String> topics = Collections.singletonList(topic);
					// 创建请求参数
					TopicMetadataRequest topicRequest = new TopicMetadataRequest(topics);
					// 请求数据，得到返回对象
					TopicMetadataResponse topicResponse = sc.send(topicRequest);
					for(TopicMetadata metadata : topicResponse.topicsMetadata()){
						for(PartitionMetadata part : metadata.partitionsMetadata()){
							result.put(part.partitionId(), part);
						}
					}
				} catch (Exception e) {
					logger.error("getLeader error. broker = [" + broker + "], groupId = [" + groupId + "], topic = [" + topic + "], ERROR:", e);
				} finally {
					if(sc != null) sc.close();
				}
			}
			return result;
		}
		return null;
	}
}
