package com.vst.test.util;

import com.vst.common.tools.file.ToolsFile;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-16 上午11:06:55
 * @decription
 * @version 
 */
public class ResetTopicsOffset {

	private static final String[] TOPICS = new String[]{
									"user", "advertisement", "app_click", "click", "custom_event",
									"enter_app", "flowLog", "flow_log", "four_home_item_click", "home_user", "menu_click",
									"movie_click", "movie_hold", "movie_order", "movie_play", "page", "picture",
									"qq_user", "search", "setting_click", "topic_click", "test"};
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 5; i++){
			sb.append(i).append("[@!@]").append("0").append("\r\n");
		}
		for(String topic : TOPICS){
			ToolsFile.writeBytesToFile("/home/hadoop/config/kafkaOffsets/offline/" + topic + ".topic", sb.toString().getBytes());
		}
		
	}
	
}
