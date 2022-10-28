package com.vst.defend.service.rank;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.bean.UserSession;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.CutPage;

/**
 * 
 * @author lhp
 * @date 2018-2-1 上午11:16:46
 * @version
 */
@Service
public interface MovieClickRankService {
	/**
	 * 查询分页数据
	 * @param cutPage
	 * @return
	 * @throws SystemException
	 */
	ReportBean getCutPageData(CutPage cutPage) throws SystemException;
	
	/**
	 * 导出数据
	 * @param params
	 * @return
	 */
	ReportBean getExportData(Map<String, Object> params, UserSession user) throws SystemException;
}
