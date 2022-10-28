package com.vst.defend.service.index;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2017-12-5 下午05:40:05
 * @version
 */
@Service
public interface IndexService {
	/**
	 * 获取首页数据
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getIndexData(Map<String, Object> params) throws SystemException;
	
	/**
	 * 导出首页数据文件
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	File exportIndexFile(Map<String, Object> params) throws SystemException;
	
	
	/**
	 * 获取邮件内容
	 * @param params
	 * @param path
	 * @return
	 * @throws SystemException
	 */
	String getEmailContent(Map<String, Object> params, String path) throws SystemException;
	
	/**
	 * 获取邮件内容(对外)
	 * @param params
	 * @param ratio
	 * @return
	 * @throws SystemException
	 */
	String getEmailByOuter(Map<String, Object> params, double ratio) throws SystemException;
	
	/**
	 * 获取应用指标数据
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getApplicationIndex(Map<String, Object> params) throws SystemException;
	
	/**
	 * 获取分类播放数据
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	JSONObject getClassifyPlay(Map<String, Object> params) throws SystemException;
}
