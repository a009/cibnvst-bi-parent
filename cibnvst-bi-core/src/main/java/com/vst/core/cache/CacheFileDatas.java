package com.vst.core.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.bean.Msg;
import com.vst.core.communal.util.PropertiesReader;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-10 下午09:53:17
 * @decription
 * @version 
 */
public class CacheFileDatas {
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(CacheFileDatas.class);

	/**
	 * 单例类
	 */
	private static final CacheFileDatas _cache = new CacheFileDatas();
	
	/**
	 * 分隔符
	 */
	private static final String SEPARATOR = "/";
	
	/**
	 * 任务队列
	 */
	private ConcurrentHashMap<String, ConcurrentMap<String, ConcurrentLinkedQueue<String>>> _dataFactory;
	
	/**
	 * 构造器私有化
	 */
	private CacheFileDatas(){
		_dataFactory = new ConcurrentHashMap<String, ConcurrentMap<String, ConcurrentLinkedQueue<String>>>();
	}
	
	/**
	 * 获取单例实例
	 * @return
	 */
	public static CacheFileDatas getInstance(){
		return _cache;
	}
	
	/**
	 * 添加缓存数据
	 * @param topic
	 * @param data
	 */
	public void addData(String topic, String data){
		ConcurrentMap<String, ConcurrentLinkedQueue<String>> factory = _dataFactory.get(topic);
		if(factory == null){
			factory = new ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>();
			_dataFactory.put(topic, factory);
		}
		long rectime = ToolsNumber.parseLong(ToolsString.matcher(data, "\"rectime\":(\\d+)"));
		String time = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm_ss3, rectime);
		String mm = String.valueOf(ToolsNumber.parseInt(ToolsDate.formatDate(ToolsDate.mm, rectime)) / 10);
		String key = time.substring(0, 10) + mm;
		
		ConcurrentLinkedQueue<String> datas = factory.get(key);
		if(datas == null){
			datas = new ConcurrentLinkedQueue<String>();
			factory.put(key, datas);
		}
		datas.add(data);
		
		// 删除空的日期属性
		for(String currTopic : _dataFactory.keySet()){
			ConcurrentMap<String, ConcurrentLinkedQueue<String>> currFactory = _dataFactory.get(currTopic);
			for(Map.Entry<String, ConcurrentLinkedQueue<String>> entry : currFactory.entrySet()){
				String date = entry.getKey();
				if(entry.getValue().isEmpty()){
					currFactory.remove(date);
				}
			}
		}
	}
	
	/**
	 * 把数据写入本地文件
	 * @param topic
	 * @param date
	 * @param hh
	 * @param mm
	 * @throws IOException
	 */
	public long flushDataIntoFile(String topic){
		long maxPostition = 0l;
		Map<String, ConcurrentLinkedQueue<String>> factory = _dataFactory.get(topic);
		if(factory != null && !factory.isEmpty()){
			for(Map.Entry<String, ConcurrentLinkedQueue<String>> entry : factory.entrySet()){
				String key = entry.getKey();
				long postition = ToolsNumber.parseLong(key);
				if(postition > maxPostition){
					maxPostition = postition;
				}
				ConcurrentLinkedQueue<String> datas = entry.getValue();
				String date = key.substring(0, 8);
				String hh = key.substring(8, 10);
				String mm = key.substring(10);
				try {
					String hdfsLocalPath = new StringBuilder(PropertiesReader.getInstance().getProperty("hdfs_data_local_path"))
												.append(SEPARATOR).append(date)
												.append(SEPARATOR).append(hh)
												.append(SEPARATOR).append(mm)
												.append(SEPARATOR).append(topic).append(".parquet")
												.append(SEPARATOR).append(topic).append("-")
												.append(PropertiesReader.getInstance().getProperty("serverHostName")).append(".dat").toString();
					StringBuilder sb = new StringBuilder();
					String line = null;
					while((line = datas.poll()) != null){
						JSONObject json = (JSONObject) JSONValue.parse(line);
						Msg msg = new Msg(json);
						sb.append(msg.toString()).append("\r\n");
					}
					boolean flag = ToolsFile.writeBytesToFile(hdfsLocalPath, sb.toString().getBytes("utf-8"), true);
					logger.info("===>>> save kafka topic[" + topic + "][" + date + "][" + hh + "][" + mm + "] data into local file " + (flag ? "succcess" : "failure"));
				} catch (Exception e) {
					logger.error("save kafka topic[" + topic + "][" + date + "][" + hh + "][" + mm + "] data into local file error.", e);
				}
			}
		}
		return maxPostition;
	}
	
	/**
	 * 把数据写入本地文件
	 * @param date
	 * @param hh
	 * @param mm
	 * @throws IOException
	 */
	public Map<String, Long> flushDataIntoFile(){
		Map<String, Long> result = new HashMap<String, Long>();
		for(String topic : _dataFactory.keySet()){
			Map<String, ConcurrentLinkedQueue<String>> factory = _dataFactory.get(topic);
			if(factory != null && !factory.isEmpty()){
				for(Map.Entry<String, ConcurrentLinkedQueue<String>> entry : factory.entrySet()){
					String key = entry.getKey();
					long postition = ToolsNumber.parseLong(key);
					Long maxPostition = result.get(topic);
					if(maxPostition == null || postition > maxPostition.longValue()){
						result.put(topic, postition);
					}
					ConcurrentLinkedQueue<String> datas = entry.getValue();
					String date = key.substring(0, 8);
					String hh = key.substring(8, 10);
					String mm = key.substring(10);
					try {
						String hdfsLocalPath = new StringBuilder(PropertiesReader.getInstance().getProperty("hdfs_data_local_path"))
													.append(SEPARATOR).append(date)
													.append(SEPARATOR).append(hh)
													.append(SEPARATOR).append(mm)
													.append(SEPARATOR).append(topic).append(".parquet")
													.append(SEPARATOR).append(topic).append("-")
													.append(PropertiesReader.getInstance().getProperty("serverHostName")).append(".dat").toString();
						StringBuilder sb = new StringBuilder();
						String line = null;
						while((line = datas.poll()) != null){
							JSONObject json = (JSONObject) JSONValue.parse(line);
							Msg msg = new Msg(json);
							sb.append(msg.toString()).append("\r\n");
						}
						boolean flag = ToolsFile.writeBytesToFile(hdfsLocalPath, sb.toString().getBytes("utf-8"), true);
						logger.info("===>>> save kafka topic[" + topic + "][" + date + "][" + hh + "][" + mm + "] data into local file " + (flag ? "succcess" : "failure"));
					} catch (Exception e) {
						logger.error("save kafka topic[" + topic + "][" + date + "][" + hh + "][" + mm + "] data into local file error.", e);
					}
				}
			}
		}
		return result;
	}
}
