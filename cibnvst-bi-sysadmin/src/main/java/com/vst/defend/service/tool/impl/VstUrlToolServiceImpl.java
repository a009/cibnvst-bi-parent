package com.vst.defend.service.tool.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.VstUrl;
import com.vst.defend.service.tool.VstUrlToolService;

/**
 * 
 * @author lhp
 * @date 2017-6-21 下午03:48:31
 * @description
 * @version
 */
@Service
public class VstUrlToolServiceImpl implements VstUrlToolService {

	/**
	 * 获取基本网址
	 */
	@Override
	public List<VstUrl> getBasicUrls() {
		List<VstUrl> urlList = new ArrayList<VstUrl>();
		urlList.add(new VstUrl("百度", "https://www.baidu.com/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/G2PXRB9QGR4DUWZXHWGM.jpg"));
		urlList.add(new VstUrl("hao123", "https://www.hao123.com/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/H2D62XWNVP2YM6D4D27C.jpg"));
		urlList.add(new VstUrl("腾讯企业邮箱", "http://exmail.qq.com/login", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/W8BTXA8G383EKTPTMFJB.jpg"));
		urlList.add(new VstUrl("JSON编辑器", "https://admin.cibnvst.com/cibnvst-mobile-sysadmin/jsonTool/json", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/LGFQA34RZ8GE256QUG7W.jpg"));
		urlList.add(new VstUrl("绩效系统", "http://admin.cibnvst.com/vst_api/login", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/PKXV6ZG43FZCRXK2ZHTT.jpg"));
		urlList.add(new VstUrl("微视听论坛", "http://bbs.91vst.com/forum.php", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/MQP628JCBQASR48CCSGZ.jpg"));
		return urlList;
	}

	/**
	 * 获取VST网址
	 */
	@Override
	public List<VstUrl> getVstUrls() {
		List<VstUrl> urlList = new ArrayList<VstUrl>();
		urlList.add(new VstUrl("CIBN3.0平台", "http://admin.cibnvst.com:82/cibnsysadmin/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/F79JM67CTQRV2SAAF4ET.jpg"));
		urlList.add(new VstUrl("V单管理平台", "http://121.201.7.252:8888/vstsysadmin/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/N5Q8WL48VUDQKNXQ3DNQ.jpg"));
		urlList.add(new VstUrl("石榴管理平台", "http://139.198.5.208/shiliu_sysadmin/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/game/gameClassify/img/NAMFRES5NWAAG7DTA43Z.jpg"));
		urlList.add(new VstUrl("CIBN4.0平台", "https://admin.cibnvst.com/cibnvst-sysadmin/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/game/gameClassify/img/RZRV7H9JZDUPSWV79SYV.jpg"));
		urlList.add(new VstUrl("新报表管理平台", "https://bi.cibnvst.com/cibnvst-bi-sysadmin/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/game/gameClassify/img/PVPKYF36YJAT32W2R7V7.jpg"));
		urlList.add(new VstUrl("积分管理平台", "https://admin.user.cibnvst.com/cibnvst-user-sysadmin/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/K4DS3RNB4BJRZSW3YRSY.jpg"));
		urlList.add(new VstUrl("小薇影视后台", "https://admin.cibnvst.com/cibnvst-mobile-sysadmin/", "https://img.cp33.ott.cibntv.net/pic/cibnvst/sport/classify/img/N22ARTVL3TLE85KENPEE.jpg"));
		return urlList;
	}
}
