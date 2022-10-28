package com.vst.core.communal.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.common.tools.file.ToolsFile;
import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-15 下午02:55:59
 * @decription
 * @version 
 */
public class ToolsIp {
	
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(ToolsIp.class);

	/**
	 * 单例类
	 */
	private static final ToolsIp _cache = new ToolsIp();
	
	/**
	 * 缓存所有的ip信息
	 */
	private List<IP> _cacheIpInfos;
	
	public static final int START_IP = 0;
	public static final int END_IP = 1;
	public static final int COUNTRY = 2;
	public static final int PROVINCE = 3;
	public static final int CITY = 4;
	public static final int ISP = 6;
	public static final int X = 7;
	public static final int Y = 8;
	public static final int CITY_CODE = 11;
	
	/**
	 * 构造器私有化
	 */
	private ToolsIp(){
		_cacheIpInfos = new ArrayList<IP>(1000000);
	}
	
	/**
	 * 获取单例实例
	 * @return
	 */
	public static ToolsIp getInstance(){
		return _cache;
	}
	
	/**
	 * 初始化ip信息
	 */
	public void init(){
		loadConfig(_cacheIpInfos);
		
		// 启动一个刷新ip文件的数据到内存中的线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(3*60*1000);// 每3分钟加载一次
					} catch (Exception e2) {
					}
					
					try {
						reloadConfig();
					} catch (Exception e) {
						logger.error("reoad load ip config error.", e);
					} 
				}
			}
		}).start();
	}
	
	/**
	 * 刷新ip配置信息
	 */
	public void reloadConfig(){
		List<IP> cacheIpInfos = null;
		loadConfig(cacheIpInfos);
		setCacheIpInfos(cacheIpInfos);
	}

	private void setCacheIpInfos(List<IP> cacheIpInfos) {
		if(cacheIpInfos != null && !cacheIpInfos.isEmpty()){// 有更新的文件才刷新到内存
			_cacheIpInfos = cacheIpInfos;
		}
	}
	
	/**
	 * 加载ip信息
	 */
	private void loadConfig(List<IP> cacheIpInfos){
		try {
			File file = new File(PropertiesReader.getInstance().getProperty("ip_config_path"));
			if(file.exists() && file.isDirectory()){
				// 找到最大的索引文件，即为最新的ip配置文件
				long maxIndex = -1l;
				File lastFile = null;
				for(File tempFile : file.listFiles()){
					long currIndex = ToolsNumber.parseLong(tempFile.getName().replace(".txt", ""));
					if(maxIndex < currIndex){
						maxIndex = currIndex;
						lastFile = tempFile;
					}
				}
				if(maxIndex > -1l){
					List<String> result = ToolsFile.readFileToList(lastFile, "utf-8");
					if(result != null && !result.isEmpty()){
						if(cacheIpInfos == null) cacheIpInfos = new ArrayList<IP>(1000000);
						for(int i = 1, l = result.size(); i < l; i++){
							if(i == l - 1) continue;// 第一行和最后一行不要
							String line = result.get(i);
							if(!ToolsString.isEmpty(line)){
								String[] values = line.split("\t");
								if(values.length != 15) continue;
								int startIp = ipToInt(values[START_IP]);
								int endIp = ipToInt(values[END_IP]);
								cacheIpInfos.add(new IP(startIp, endIp, values[COUNTRY], values[PROVINCE], values[CITY], values[ISP], values[X], values[Y], values[CITY_CODE]));
							}
						}
						
						// 按照升序排序
						Collections.sort(cacheIpInfos, new Comparator<IP>() {
							/**
							 * 比较类实现
							 */
							@Override
							public int compare(IP ip1, IP ip2) {
								// 返回结果
								int result = 0;
								if(ip1.getStartIp() > ip2.getStartIp()){
									result = 1;
								}else if(ip1.getStartIp() < ip2.getStartIp()){
									result = -1;
								}else{
									result = 0;
								}
								return result;
							}
						});
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private int ipToInt(String ip){
		if(!ToolsString.isEmpty(ip)){
			String[] values = ip.split("[.]");
			if(values.length == 4){
				int one = ToolsNumber.parseInt(values[0]);
				int two = ToolsNumber.parseInt(values[1]);
				int three = ToolsNumber.parseInt(values[2]);
				int four = ToolsNumber.parseInt(values[3]);
				if(one > 0 && one < 256 && two >= 0 && two < 256 && 
						three >= 0 && three < 256 && four >= 0 && four < 256){
					return (one << 24) | (two << 16) | (three << 8) | four;
				}
			}
		}
		return -1;
	}
	
	/**
	 * 根据ip获取信息
	 * @param ip
	 * @return
	 */
	public IP info(String ip){
		int ipInt = ipToInt(ip);
		if(_cacheIpInfos != null && !_cacheIpInfos.isEmpty()){
			return binarySearch(_cacheIpInfos, ipInt);
		}
		return null;
	}
	
	private IP binarySearch(List<IP> list, int key){
		int index = list.size() / 2;
		IP ip = list.get(index);
		if(ip.getStartIp() <= key && key <= ip.getEndIp()){
			return ip;
		}
		
		int start = 0;
		int end = list.size() - 1;
		while(start <= end){
			index = (end - start) / 2 + start;
			ip = list.get(index);
			if(ip.getStartIp() <= key && key <= ip.getEndIp()){
				return ip;
			}else if(key < ip.getStartIp()){
				end = index - 1;
			}else if(key > ip.getEndIp()){
				start = index + 1;
			}
		}
		return null;
	}
	
	public class IP{
		/**
		 * ip开始地址
		 */
		private int startIp;
		
		/**
		 * ip结束地址
		 */
		private int endIp;
		
		/**
		 * 国家
		 */
		private String country;
		
		/**
		 * 省份
		 */
		private String province;
		
		/**
		 * 城市
		 */
		private String city;
		
		/**
		 * 网络
		 */
		private String isp;
		
		/**
		 * x坐标
		 */
		private String x;
		
		/**
		 * y坐标
		 */
		private String y;
		
		/**
		 * 城市码
		 */
		private String cityCode;

		/**
		 * 构造器
		 * @param startIp
		 * @param endIp
		 * @param country
		 * @param province
		 * @param city
		 * @param isp
		 * @param x
		 * @param y
		 * @param cityCode
		 */
		public IP(int startIp, int endIp, String country, String province,
				String city, String isp, String x, String y, String cityCode) {
			this.startIp = startIp;
			this.endIp = endIp;
			this.country = country;
			this.province = province;
			this.city = city;
			this.isp = isp;
			this.x = x;
			this.y = y;
			this.cityCode = cityCode;
		}

		public int getStartIp() {
			return startIp;
		}

		public void setStartIp(int startIp) {
			this.startIp = startIp;
		}

		public int getEndIp() {
			return endIp;
		}

		public void setEndIp(int endIp) {
			this.endIp = endIp;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getIsp() {
			return isp;
		}

		public void setIsp(String isp) {
			this.isp = isp;
		}

		public String getX() {
			return x;
		}

		public void setX(String x) {
			this.x = x;
		}

		public String getY() {
			return y;
		}

		public void setY(String y) {
			this.y = y;
		}

		public String getCityCode() {
			return cityCode;
		}

		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
		}

		@Override
		public String toString() {
			return startIp + "|" + endIp + "|"	+ country + "|" + province + "|" + city
					+ "|" + isp + "|" + x + "|" + y + "|" + cityCode;
		}
	}
}
