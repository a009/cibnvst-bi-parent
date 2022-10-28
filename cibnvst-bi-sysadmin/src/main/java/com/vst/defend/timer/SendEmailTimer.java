package com.vst.defend.timer;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vst.common.tools.date.ToolsDate;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.MailUtils;
import com.vst.defend.service.index.IndexService;

/**
 * 定时发送 <微视听日报>
 * @author lhp
 * @date 2018-2-2 下午02:59:16
 * @version
 */
public class SendEmailTimer extends TimerTask {
	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(SendEmailTimer.class);
	
	/**
	 * 首页Service接口
	 */
	private IndexService _indexService;
	
	/**
	 * 图片路径
	 */
	private String _path;
	
	public SendEmailTimer(){}
	
	public SendEmailTimer(IndexService indexService, String path){
		this._indexService = indexService;
		this._path = path;
	}
	
	@Override
	public void run() {
		try {
			String date = ToolsDate.getCurrDate();
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("date", date);
			params.put("pkgName", "net.myvst.v2");
			
			String table = _indexService.getEmailContent(params, _path);
			
			String smtp = "smtp.exmail.qq.com";
			String from = "jie.wang@91vst.com";
			String to = "jie.wang@91vst.com";
			String copyto = "1793208105@qq.com";//"kai.li@91vst.com,hao.chen@91vst.com,yao.zhang@91vst.com,guanji.zhou@91vst.com,cheng.fu@91vst.com";
			String subject = "微视听日报";
			String username = "jie.wang@91vst.com";
			String password = "Wj201230210205wJ";
			
			MailUtils.sendImageAndCc(smtp, from, to, copyto, subject, table, username, password, null);
		} catch(Exception e) {
			logger.error("发送邮件失败: " + SystemException.getExceptionInfo(e));
			throw new SystemException(ErrorCode.SERVICE_OPERATOR_FAILURE, e);
		}
	}
}
