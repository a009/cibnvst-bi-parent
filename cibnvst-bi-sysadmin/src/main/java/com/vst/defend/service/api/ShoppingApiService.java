package com.vst.defend.service.api;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.exception.SystemException;

/**
 * 
 * @author lhp
 * @date 2018-1-30 上午11:41:06
 * @version
 */
@Service
public interface ShoppingApiService {

	/**
	 * 查询点播订单数据
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean findShoppingPlayData(Map<String, Object> params) throws SystemException;
	
	/**
	 * 查询直播订单数据
	 * @param params
	 * @return
	 * @throws SystemException
	 */
	ReportBean findShoppingLiveData(Map<String, Object> params) throws SystemException;
}
