package com.vst.core.communal.bean;

import java.io.Serializable;

import org.json.simple.JSONObject;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.util.ToolsIp;
import com.vst.core.communal.util.ToolsIp.IP;

/**
 * @author joslyn
 * @date 2017年11月28日 下午12:07:08
 * @description
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class Msg implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3126280314048451523L;
	
	/*********** 设备信息 ***********/
	private String uuid; // 设备唯一标识（VST自定义唯一id）
	private String session; // 会话字段 
	private String androidId; // 安卓id
	private String deviceId; // 安卓设备id
	private String analyticId;
	private String cpuName; // cpu型号
	private String deviceName; // 设备名称
	private int dpi; // 分辨率
	private String screen; // 屏幕尺寸
	private String buildBoard; // 主板
	private String wlan0Mac; // 无线mac地址
	private String eth0Mac; // 有线mac地址
	private boolean touch; // 是否是触屏模式，true：是，false：否
	private int cpuCnt; // cpu个数
	private int limitMem; // 限制内存
	private int largeMem; // 最大内存
	private String bdCpu;	// cpu
	private String bdModel;	// 机型
	private String serial; // 设备序列号
	
	/*********** apk信息 ***********/
	private String pkg; // 包名
	private String channel; // 渠道号
	private String verName; // 显示版本号
	private int verCode; // 升级版本号
	private String pluginPkg;// 插件包名
	private int pluginVerCode;// 插件版本
	
	/*********** 用户属性 ***********/
	private boolean isVip; // 是否是vip
	private String city; // 用户所在城市
	private String cityCode; // 用户所在城市code
	private String province; // 用户所在省份
	private String country; // 用户所在国家
	private String isp; // 网络运营商
	private String ip; // 用户ip
	private String ipaddr; // 用户ip(服务端识别的)
	private String x; // 用户x坐标
	private String y; // 用户y坐标
	private String uid;// 用户vst账号id
	private String openId;// 用户qq或者微信openId
	
	/*********** 用户行为属性 ***********/
	private String kafkaTopic; // 数据topic 
	private String entry1; // 影片进入路径
	private String entry1Id; // 影片进入路径id
	private int pos; // 用户点击的位置坐标
	private String page; // 当前页
	private String prePage; // 前一页
	
	/*********** 影片相关属性 ***********/
	private long time; // 操作开始时间
	private long endTime; // 操作结束时间
	private long rectime; // 服务端接收数据时间
	private int prevue; // 影片vip类型
	private int cid; // 影片分类
	private String name; // 影片名称
	private String subName; // 集数标题
	private String nameId; // 影片id
	private int specId; // 影片专区id
	private long duration; // 影片播放时长
	private String site; // 影片源平台
	private String definition; // 影片清晰度
	
	/*********** 专题相关属性 ***********/
	private String topic; // 专题名称
	private String topicId; // 专题id 
	private String topicType; // 专题类型
	private String topicCid; // 专题分类id
	
	private String adType; // 广告类型
	private String optType; // 预约收藏，yes是，no否
	private String url; // 图片地址
	
	/*********** 自定义事件相关属性 ***********/
	private String eventType; // 事件类型
	private String eventName; // 事件名称
	private String eventKey; // 事件key
	private String eventValue; // 事件value

	/*********** 区块信息 ***********/
	private String blockId; //区块ID
	private int index;	//区块索引序号
	private int blockPos;

	private int refreshType;	//7 表示智能推荐
	private String loginType;	//qq、wx

	private int osVersion;		// android api级别，详情请 查阅：https://docs.microsoft.com/zh-cn/xamarin/android/app-fundamentals/android-api-levels?tabs=macos
	private String decorationTitle;

	private int exposureIndex;	//曝光序号，从1开始
	
	/**
	 * 构造器
	 * @param json
	 */
	public Msg(JSONObject json) {
		parse(json);
	}
	
	private void checkJson(JSONObject json){
		String ip = ToolsString.checkEmpty(json.get("ip"));
		if(ToolsString.isEmpty(ip)){
			ip = ToolsString.checkEmpty(json.get("ipaddr"));
		}
		// 根据ip获取国家，省份，城市，网络运营商等信息
		IP info = ToolsIp.getInstance().info(ip);
		if(info != null){
			json.put("country", ToolsString.checkEmpty(info.getCountry()));
			json.put("province", ToolsString.checkEmpty(info.getProvince()));
			json.put("city", ToolsString.checkEmpty(info.getCity()));
			json.put("isp", ToolsString.checkEmpty(info.getIsp()));
			json.put("x", ToolsString.checkEmpty(info.getX()));
			json.put("y", ToolsString.checkEmpty(info.getY()));
			json.put("cityCode", ToolsString.checkEmpty(info.getCityCode()));
		}
	}
	
	public Msg parse(JSONObject json){
		if(json != null && !json.isEmpty()){
			checkJson(json);
			uuid = ToolsString.checkEmpty(json.get("uuid"));
			session = ToolsString.checkEmpty(json.get("session"));
			androidId = ToolsString.checkEmpty(json.get("androidId"));
			deviceId = ToolsString.checkEmpty(json.get("deviceId"));
			analyticId = ToolsString.checkEmpty(json.get("analyticId"));
			cpuName = ToolsString.checkEmpty(json.get("cpuName"));
			deviceName = ToolsString.checkEmpty(json.get("deviceName"));
			dpi = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("dpi")));
			screen = ToolsString.checkEmpty(json.get("screen"));
			buildBoard = ToolsString.checkEmpty(json.get("buildBoard"));
			wlan0Mac = ToolsString.checkEmpty(json.get("wlan0Mac"));
			eth0Mac = ToolsString.checkEmpty(json.get("eth0Mac"));
			touch = "true".equals(ToolsString.checkEmpty(json.get("touch")));
			cpuCnt = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("cpuCnt")));
			limitMem = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("limitMem")));
			largeMem = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("largeMem")));
			bdCpu = ToolsString.checkEmpty(json.get("bdCpu"));
			bdModel = ToolsString.checkEmpty(json.get("bdModel"));
			serial = ToolsString.checkEmpty(json.get("serial"));
			
			pkg = ToolsString.checkEmpty(json.get("pkg"));
			channel = ToolsString.checkEmpty(json.get("channel"));
			verName = ToolsString.checkEmpty(json.get("verName"));
			verCode = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("verCode")));
			pluginPkg = ToolsString.checkEmpty(json.get("pluginPkg"));
			pluginVerCode = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("pluginVercode")));
			
			isVip = "true".equals(ToolsString.checkEmpty(json.get("isVip")));
			city = ToolsString.checkEmpty(json.get("city"));
			cityCode = ToolsString.checkEmpty(json.get("cityCode"));
			province = ToolsString.checkEmpty(json.get("province"));
			country = ToolsString.checkEmpty(json.get("country"));
			isp = ToolsString.checkEmpty(json.get("isp"));
			ip = ToolsString.checkEmpty(json.get("ip"));
			ipaddr = ToolsString.checkEmpty(json.get("ipaddr"));
			x = ToolsString.checkEmpty(json.get("x"));
			y = ToolsString.checkEmpty(json.get("y"));
			
			kafkaTopic = ToolsString.checkEmpty(json.get("kafkaTopic"));
			entry1 = ToolsString.checkEmpty(json.get("entry1"));
			entry1Id = ToolsString.checkEmpty(json.get("entry1Id"));
			pos = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("pos")));
			page = ToolsString.checkEmpty(json.get("page"));
			prePage = ToolsString.checkEmpty(json.get("prePage"));
			y = ToolsString.checkEmpty(json.get("y"));
			uid = ToolsString.checkEmpty(json.get("uid"));
			openId = ToolsString.checkEmpty(json.get("openId"));
			
			time = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get("time")));
			endTime = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get("endTime")));
			rectime = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get("rectime")));
			prevue = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("prevue")));
			cid = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("cid")));
			name = ToolsString.checkEmpty(json.get("name")).replace("\r\n", "").replace("\n\t", "").replace("\n", "");
			subName = ToolsString.checkEmpty(json.get("subName")).replace("\r\n", "").replace("\n\t", "").replace("\n", "");
			nameId = ToolsString.checkEmpty(json.get("nameId"));
			specId = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("specId")));
			if(specId < 0) specId = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("specid")));
			duration = ToolsNumber.parseLong(ToolsString.checkEmpty(json.get("duration")));
			site = ToolsString.checkEmpty(json.get("site"));
			definition = ToolsString.checkEmpty(json.get("definition"));
			
			topic = ToolsString.checkEmpty(json.get("topic"));
			topicId = ToolsString.checkEmpty(json.get("topicId"));
			topicType = ToolsString.checkEmpty(json.get("topicType"));
			topicCid = ToolsString.checkEmpty(json.get("topicCid"));
			
			adType = ToolsString.checkEmpty(json.get("adType"));
			optType = ToolsString.checkEmpty(json.get("optType"));
			url = ToolsString.checkEmpty(json.get("url"));
			
			eventType = ToolsString.checkEmpty(json.get("eventType"));
			eventName = ToolsString.checkEmpty(json.get("eventName"));
			eventKey = ToolsString.checkEmpty(json.get("eventKey"));
			eventValue = ToolsString.checkEmpty(json.get("eventValue"));

			blockId = ToolsString.checkEmpty(json.get("blockId"));
			index = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("index")));
			blockPos = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("blockPos")));

			loginType = ToolsString.checkEmpty(json.get("loginType"));	//登录类型 qq、wx
			refreshType = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("refreshType")));	//7表示智能推荐

			osVersion = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("osVersion")));	//android api级别
			decorationTitle = ToolsString.checkEmpty(json.get("decorationTitle"));

			exposureIndex = ToolsNumber.parseInt(ToolsString.checkEmpty(json.get("exposureIndex")));	//曝光序号
		}
		return this;
	}

	@Override
	public String toString(){
		return toJson().toString();
	}
	
	public JSONObject toStreamingJson(){
		JSONObject json = toJson();
		for(Object key : json.keySet()){
			Object value = json.get(key);
			if(value != null){
				if(value instanceof Integer){
					json.put(key, ToolsNumber.parseLong(value.toString()));
				}
			}
		}
		return json;
	}
	
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		json.put("uuid", uuid);
		json.put("session", session);
		json.put("androidId", androidId);
		json.put("deviceId", deviceId);
		json.put("analyticId", analyticId);
		json.put("cpuName", cpuName);
		json.put("deviceName", deviceName);
		json.put("dpi", dpi);
		json.put("screen", screen);
		json.put("buildBoard", buildBoard);
		json.put("wlan0Mac", wlan0Mac);
		json.put("eth0Mac", eth0Mac);
		json.put("touch", touch);
		json.put("cpuCnt", cpuCnt);
		json.put("limitMem", limitMem);
		json.put("largeMem", largeMem);
		json.put("bdCpu", bdCpu);
		json.put("bdModel", bdModel);
		json.put("serial", serial);
		
		json.put("pkg", pkg);
		json.put("channel", channel);
		json.put("verName", verName);
		json.put("verCode", verCode);
		json.put("pluginPkg", pluginPkg);
		json.put("pluginVercode", pluginVerCode);
		
		json.put("isVip", isVip);
		json.put("city", city);
		json.put("cityCode", cityCode);
		json.put("province", province);
		json.put("country", country);
		json.put("isp", isp);
		json.put("ip", ip);
		json.put("ipaddr", ipaddr);
		json.put("x", x);
		json.put("y", y);
		json.put("uid", uid);
		json.put("openId", openId);
		
		json.put("kafkaTopic", kafkaTopic);
		json.put("entry1", entry1);
		json.put("entry1Id", entry1Id);
		json.put("pos", pos);
		json.put("page", page);
		json.put("prePage", prePage);
		json.put("time", time);
		json.put("endTime", endTime);
		json.put("rectime", rectime);
		json.put("prevue", prevue);
		json.put("cid", cid);
		json.put("name", name);
		json.put("subName", subName);
		json.put("nameId", nameId);
		json.put("specId", specId);
		json.put("duration", duration);
		json.put("site", site);
		json.put("definition", definition);
		
		json.put("topic", topic);
		json.put("topicId", topicId);
		json.put("topicType", topicType);
		json.put("topicCid", topicCid);
		
		json.put("adType", adType);
		json.put("optType", optType);
		json.put("url", url);
		
		json.put("eventType", eventType);
		json.put("eventName", eventName);
		json.put("eventKey", eventKey);
		json.put("eventValue", eventValue);

		json.put("blockId", blockId);
		json.put("index", index);
		json.put("blockPos",blockPos);

		json.put("loginType", loginType);
		json.put("refreshType",refreshType);

		json.put("osVersion", osVersion);	// android api级别
		json.put("decorationTitle", decorationTitle);

		json.put("exposureIndex", exposureIndex);	//曝光序号
		return json;
	}
}
