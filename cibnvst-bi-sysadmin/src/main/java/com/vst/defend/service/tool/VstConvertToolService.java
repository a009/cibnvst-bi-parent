package com.vst.defend.service.tool;

import org.springframework.stereotype.Service;

/**
 * 
 * @author lhp
 * @date 2017-6-21 下午04:33:52
 * @description
 * @version
 */
@Service
public interface VstConvertToolService {
	/**
	 * 转换直播pid
	 * @param pid
	 * @return
	 */
	String convertLivePid(String pid);
	
	/**
	 * 时间戳转北京时间
	 * @param timeStamp
	 * @return
	 */
	String getBjTime(String timeStamp);
	
	/**
	 * 北京时间转时间戳
	 * @param bjTime
	 * @return
	 */
	long getTimeStamp(String bjTime);
	
	/**
	 * 获取腾讯图片
	 * @param videoUrl
	 * @return
	 */
	String getTencentPic(String videoUrl);
}
