package com.vst.defend.service.tool.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.tool.VstConvertToolService;

/**
 * 
 * @author lhp
 * @date 2017-6-21 下午04:34:24
 * @description
 * @version
 */
@Service
public class VstConvertToolServiceImpl implements VstConvertToolService {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(VstConvertToolServiceImpl.class);
	
	/**
	 * 转换直播pid
	 */
	@Override
	public String convertLivePid(String pid) {
		String streamId = null;
		try {
			String url = "http://tv.cp81.ott.cibntv.net/i-tvbin/qtv_video/live_details/get_live_details?tv_cgi_ver=1.1&req_from=10000&format=json&Q-UA=QV%3D1%26PR%3DVIDEO%26PT%3DCH%26CHID%3D10009%26RL%3D1920*1008%26VN%3D1.1.0%26VN_CODE%3D121%26SV%3D4.4.2%26DV%3DK1%26VN_BUILD%3D0%26MD%3DK1%26BD%3D";
			url += "&pid=" + pid;
			String html = VstTools.getHtml(url);
			JSONObject json = JSONObject.parseObject(html);
			if(json != null){
				JSONObject data = json.getJSONObject("data");
				if(data != null){
					streamId = data.getString("stream_id");
				}
			}
		} catch(Exception e) {
			logger.error("转换直播pid失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return streamId;
	}
	
	/**
	 * 时间戳转北京时间
	 */
	@Override
	public String getBjTime(String timeStamp){
		String bjTime = null;
		try {
			Long time = Long.valueOf(timeStamp);
			Date date = new Date(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			bjTime = dateFormat.format(date);
		} catch(Exception e) {
			logger.error("时间戳转北京时间失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return bjTime;
	}
	
	/**
	 * 北京时间转时间戳
	 */
	public long getTimeStamp(String bjTime){
		long timeStemp = 0;
		try {
			SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date date = dateFormat.parse(bjTime);
		    timeStemp = date.getTime();
		} catch(Exception e) {
			logger.error("北京时间转时间戳失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return timeStemp;
	}
	
	/**
	 * 获取腾讯图片
	 */
	@Override
	public String getTencentPic(String videoUrl) {
		String imgUrl = null;
		try {
			String url = "http://vpic.video.qq.com/37987864/{vid}_ori_3.jpg";
			int index = videoUrl.indexOf("?");
			if(index != -1){	//情况1：参数带vid
				//获取参数列表
				String paramStr = videoUrl.substring(index+1);
				if(!ToolsString.isEmpty(paramStr)){
					String[] params = paramStr.split("&");
					for(String param : params){
						String[] entry = param.split("=");
						String key = entry[0];
						String value = entry[1];
						if("vid".equals(key)){
							imgUrl = url.replace("{vid}", value);
							return imgUrl;
						}
					}
				}
			}
			//情况2：vid在地址中
			String[] strs = videoUrl.split("/");
			String lastStr = strs[strs.length-1];
			//去后缀
			if(lastStr.indexOf(".html") != -1){
				lastStr = lastStr.substring(0, lastStr.indexOf(".html"));
			}else if(lastStr.indexOf(".shtml") != -1){
				lastStr = lastStr.substring(0, lastStr.indexOf(".shtml"));
			}
			strs = lastStr.split("_");
			lastStr = strs[strs.length-1];
			imgUrl = url.replace("{vid}", lastStr);
		} catch(Exception e) {
			logger.error("获取腾讯图片失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
		return imgUrl;
	}
}
