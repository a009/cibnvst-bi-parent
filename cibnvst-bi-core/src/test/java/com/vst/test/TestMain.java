package com.vst.test;

import java.io.IOException;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-15 下午04:08:00
 * @decription
 * @version 
 */
public class TestMain {

	public static void main(String[] args) throws IOException {
//		String[] brokers = "123.59.182.229,123.59.182.230,123.59.182.231,123.59.182.232,123.59.182.233".split(",");
//		CacheOffsets.getInstance().excute("test2", "joslyn", brokers, 9092);
//		for(String s : KafkaConfig.configNames().toString().split(",")){
//			System.out.println(s);
//		}
		
//		long now = System.currentTimeMillis() - 50*60*1000;
//		System.out.println(ToolsNumber.parseInt(ToolsDate.formatDate(ToolsDate.mm, now)) / 10);
//		String user = "{\"screen\":\"1920x1080\",\"rectime\":1509958252457,\"kafkaTopic\":\"advertisement\",\"androidId\":\"0\",\"pkg\":\"net.myvst.v2\",\"largeMem\":512,\"bdModel\":\"Letv S50 Air\",\"wlan0Mac\":\"\",\"eth0Mac\":\"c8:0e:77:76:12:c0\",\"nameId\":\"\",\"ip\":\"58.34.166.57\",\"bdCpu\":\"armeabi-v7a\",\"ipaddr\":\"58.34.162.69\",\"name\":\"\",\"cpuCnt\":4,\"verName\":\"4.0.5\",\"verCode\":4005,\"adType\":2,\"uuid\":\"c244274f-685a-4bb4-9c6e-1415e3572ac6\",\"dpi\":240,\"serial\":\"unknown\",\"channel\":\"91vst\",\"limitMem\":192}";
//		System.out.println(ToolsHttp.httpPost("http://123.59.182.229/action/test", null, ToolsIO.compressToByteArray(user)));
		
		
//		String str = "20171107 21:23";
//		Date time = ToolsDate.parseDate("yyyyMMdd HH:mm", str);
//		System.out.println(ToolsDate.formatDate(ToolsDate.yyyy_MM_dd_HH_mm, stime.getTime() + 10*60*1000));
		System.out.println(binarySearch(new int[]{1,2,3,4,5,6,7,8}, 1, 0, 7));
		System.out.println(binarySearch(new int[]{1,2,3,4,5,6,7,8}, 1));
	}
	
	private static int binarySearch(int[] searchArray, int key){
		// 取中间位置下标
		int index = searchArray.length / 2;
		if(searchArray[index] == key){
			return index;
		}
		
		int start = 0; 
		int end = searchArray.length - 1;
		while(start <= end){
			index = (end - start) / 2 + start;
			if(searchArray[index] < key){
				start = index + 1;
			}else if(searchArray[index] > key){
				end = index - 1;
			}else{
				return index;
			}
		}
		return -1;
	}
	
	public static int binarySearch(int[] searchArray, int key, int start, int end){
		// 取中间位置下标
		int index = searchArray.length / 2;
		if(searchArray[index] == key){
			return index;
		}
		
		if(start <= end){
			index = (end - start) / 2 + start;
			if(searchArray[index] < key){
				start = index + 1;
				return binarySearch(searchArray, key, start, end);
			}else if(searchArray[index] > key){
				end = index - 1;
				return binarySearch(searchArray, key, start, end);
			}else{
				return index;
			}
		}
		return -1;
	}
}
