package com.vst.defend.service.bullet;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2018-6-12 下午04:27:52
 * @version
 */
@Service
public interface VstBulletDataService {
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
	
	/**
	 * 导入数据
	 * @param type
	 * @param way
	 * @param jsonArr
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int importData(int type, int way, JSONArray jsonArr, UserSession user) throws SystemException;
	
	/**
	 * 删除数据
	 * @param params
	 * @param user
	 * @return
	 * @throws SystemException
	 */
	int deleteData(Map<String, Object> params, UserSession user) throws SystemException;
}
