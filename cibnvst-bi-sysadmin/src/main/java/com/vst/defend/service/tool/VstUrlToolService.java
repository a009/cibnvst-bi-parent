package com.vst.defend.service.tool;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.VstUrl;

/**
 * 
 * @author lhp
 * @date 2017-6-21 下午03:48:03
 * @description
 * @version
 */
@Service
public interface VstUrlToolService {
	/**
	 * 获取常用网址
	 * @return
	 */
	List<VstUrl> getBasicUrls();
	
	/**
	 * 获取VST网址
	 * @return
	 */
	List<VstUrl> getVstUrls();
}
