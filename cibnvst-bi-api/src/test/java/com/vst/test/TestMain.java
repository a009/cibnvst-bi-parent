package com.vst.test;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-20 下午06:01:50
 * @decription
 * @version 
 */
public class TestMain {
	
	/**
	 * 是否初始化，true：是，false：否
	 */
	private static boolean isInit = false;

	/**
	 * 生产者对象
	 */
	private static Producer<String, String> producer;
	
	/**
	 * 获取kafka生产者对象
	 * @return
	 */
	private static void initConfig(){
		if(producer == null){
			Properties props = new Properties();    
//	        props.put("serializer.class", "kafka.serializer.StringEncoder");  
	        props.put("bootstrap.servers", "123.59.182.229:9092,123.59.182.230:9092,123.59.182.231:9092,123.59.182.232:9092,123.59.182.233:9092");
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
	public static void sendMessage(String topic, String data){
		if(!isInit){// 如果没有初始化属性，先初始化
			initConfig();
		}
		if(producer != null && !ToolsString.isEmpty(topic) && data != null && !data.isEmpty()){
			producer.send(new ProducerRecord<String, String>(topic, data));
//			producer.flush();
//			try {
//				producer.send(new ProducerRecord<String, String>(topic, data, data), new Callback() {
//					
//					@Override
//					public void onCompletion(RecordMetadata arg0, Exception arg1) {
//						System.out.println(">>>>>>>>>>>"+arg0.topic());
//					}
//				}).get();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		}
	}

	public static void main(String[] args) throws IOException {
//		JSONObject json = new JSONObject();
//		json.put("channel", "91vst");
//		json.put("pkg", "net.myvst.v2");
//		json.put("verCode", 0);
//		json.put("verName", "4.0.5");
//		String s = ToolsHttp.httpPost("http://localhost:8080/cibnvst-bi-api/action/user.dat", null, ToolsIO.compressToByteArray(json.toString()));
//		System.out.println(s);
//		String s = "{\"net.myvst.v2\",\"com.vst.itv52.v1\",\"com.vst.live\",\"com.vst.box\"}";
//		s = "{\"a\"}";
//		System.out.println(ToolsString.matcher(s, "^\\{\"(\\w+)((.(\\w+))?){0,}\"(,\"(\\w+)((.(\\w+))?){0,}\"){0,}\\}$", 0));
//		JSONObject json = new JSONObject();
//		json.put("a", "1");
//		json.put("b", 2);
//		json.put("c", true);
		for(int i = 13; i < 16; i++){
			sendMessage("test", "{\"test" + i + "\":" + i + "}");
		}
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
