package com.vst.defend.controller.tool;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.ftp.ToolsFTp;
import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.util.EncryptUtils;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.service.tool.VstConvertToolService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * 转换工具
 * @author lhp
 * @date 2017-6-21 下午04:32:28
 * @description
 * @version
 */
@Controller
@RequestMapping("/convertTool")
public class ConvertToolController extends BaseController {
	/**
	 * 类名
	 */
	private static final String _className = ConvertToolController.class.getName();
	
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(_className);
	
	/**
	 * 转换工具Service接口
	 */
	@Resource
	private VstConvertToolService _vstConvertToolService;
	
	/**
	 * 跳转到页面
	 * @return
	 */
	@RequestMapping("/goToPage.action")
	public String goToPage(){
		try{
			// 初始化
			this.initializeAction(_className);
			
			// 获取模块ID
			String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
			// 转发当前模块ID
			request.setAttribute("moduleId", moduleId);
		} catch (Exception e) {
			logger.error("goToPage error." + SystemException.getExceptionInfo(e));
		}
		return "tool/convertTool_list";
	}
	
	/**
	 * 转换直播pid
	 * @return
	 */
	@RequestMapping("/ajaxConvertPid.action")
	public String ajaxConvertPid(){
		// 输出流
		PrintWriter out = null;
		String pid = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			pid = request.getParameter("live_pid");
			//转换
			String streamId = _vstConvertToolService.convertLivePid(pid);
			JSONObject json = new JSONObject();
			json.put("streamId", streamId);
			out.print(json.toString());
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxConvertPid error. live_pid=" + pid + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 加密UUID
	 * @return
	 */
	@RequestMapping("/ajaxEncryptUUID.action")
	public String ajaxEncryptUUID(){
		// 输出流
		PrintWriter out = null;
		String uuid = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			uuid = request.getParameter("uuid");
			//加密
			String result = EncryptUtils.encryptUUID(uuid);
			JSONObject json = new JSONObject();
			json.put("result", result);
			out.print(json.toString());
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxEncryptUUID error. uuid=" + uuid + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 解密UUID
	 * @return
	 */
	@RequestMapping("/ajaxDecodeUUID.action")
	public String ajaxDecodeUUID(){
		// 输出流
		PrintWriter out = null;
		String result = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			result = request.getParameter("result");
			//解密
			String uuid = EncryptUtils.decodeUUID(result);
			JSONObject json = new JSONObject();
			json.put("uuid", uuid);
			out.print(json.toString());
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxDecodeUUID error. result=" + result + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 时间戳转北京时间
	 * @return
	 */
	@RequestMapping("/ajaxGetBjTime.action")
	public String ajaxGetBjTime(){
		// 输出流
		PrintWriter out = null;
		String timeStamp = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			timeStamp = request.getParameter("timeStamp");
			//获取北京时间
			String bjTime = _vstConvertToolService.getBjTime(timeStamp);
			JSONObject json = new JSONObject();
			json.put("bjTime", bjTime);
			out.print(json.toString());
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxGetBjTime error. timeStamp=" + timeStamp + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 北京时间转时间戳
	 * @return
	 */
	@RequestMapping("/ajaxGetTimeStamp.action")
	public String ajaxGetTimeStamp(){
		// 输出流
		PrintWriter out = null;
		String bjTime = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			bjTime = request.getParameter("bjTime");
			//获取时间戳
			long timeStamp = _vstConvertToolService.getTimeStamp(bjTime);
			JSONObject json = new JSONObject();
			json.put("timeStamp", timeStamp);
			out.print(json.toString());
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxGetTimeStamp error. bjTime=" + bjTime + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
	
	/**
	 * 上传图片
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/ajaxUploadPicFile.action")
    public String ajaxUploadPicFile(@RequestParam("upload") CommonsMultipartFile myfile) {
		// 输出流
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			// 初始化
			this.initializeAction(_className); 
			out = response.getWriter();
			if(myfile != null){
//				// 上传图片不能大于350KB=358400K
//				int pic_max_size = ToolsNumber.parseInt(PropertiesReader.getProperty("pic_max_size"));
//				if(myfile.getSize() > pic_max_size){
//					json.put("code", 5);
//					json.put("maxSize", pic_max_size/1024+"KB");
//				}else{
					// 远程ftp服务器host
					String remoteHost = PropertiesReader.getProperty("[img]");
					// 创建ftp客户端链接
					ToolsFTp ftp = new ToolsFTp();
					InputStream fileStream = myfile.getInputStream();
					
					String url = ftp.uploadFile2("/pic/cibnvst/activity", remoteHost, fileStream);
					
					if(url != null){
						json.put("url", "[img]" + url);
						json.put("code", 0);
					}else{
						// 返回失败信息
						json.put("code", 1);
					}
//				}
			}else{
				json.put("code", 3);
			}
			out.print(json);
			out.flush();
		} catch (Exception e) {
			json.put("code", 2);
			out.print(json);
			logger.error("ajaxUploadPicFile error. ERROR:" + SystemException.getExceptionInfo(e));
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
    }
	
	/**
	 * 获取腾讯视频的图片
	 * @return
	 */
	@RequestMapping("/ajaxGetTencentPic.action")
	public String ajaxGetTencentPic(){
		// 输出流
		PrintWriter out = null;
		String videoUrl = null;
		try {
			// 初始化Action类
			this.initializeAction(_className);
			out = response.getWriter();
			videoUrl = request.getParameter("videoUrl");
			//获取图片
			String imgUrl = _vstConvertToolService.getTencentPic(videoUrl);
			JSONObject json = new JSONObject();
			json.put("imgUrl", imgUrl);
			out.print(json.toString());
			out.flush();
		} catch (Exception e) {
			logger.error("ajaxGetTencentPic error. videoUrl=" + videoUrl + ", ERROR:" + e.getMessage());
		} finally {
			// 关流操作
			ToolsIO.closeStream(out);
		}
		return null;
	}
}
