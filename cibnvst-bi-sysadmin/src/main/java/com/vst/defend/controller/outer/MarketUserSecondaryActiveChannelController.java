package com.vst.defend.controller.outer;

import com.vst.common.tools.string.ToolsString;
import com.vst.defend.communal.bean.ReportBean;
import com.vst.defend.communal.controller.BaseController;
import com.vst.defend.communal.exception.ErrorCode;
import com.vst.defend.communal.exception.ErrorInfo;
import com.vst.defend.communal.exception.SystemException;
import com.vst.defend.communal.service.BaseService;
import com.vst.defend.communal.util.CutPage;
import com.vst.defend.communal.util.ExportUtil;
import com.vst.defend.communal.util.PropertiesReader;
import com.vst.defend.communal.util.VstTools;
import com.vst.defend.service.outer.MarketUserSecondaryActiveChannelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二次活跃渠道用户(市场数据)
 * @author wangjie
 * @date 2019/11/28 11:41
 */
@Scope("session")
@Controller
@RequestMapping("/marketUserSecondaryActiveChannel")
public class MarketUserSecondaryActiveChannelController extends BaseController {
    /**
     * 类名
     */
    private static final String _className = MarketUserSecondaryActiveChannelController.class.getName();

    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(_className);

    /**
     * 二次活跃渠道用户(市场数据)Service接口
     */
    @Resource
    private MarketUserSecondaryActiveChannelService _marketUserSecondaryActiveChannelService;

    /**
     * 基本操作接口
     */
    @Resource
    private BaseService _baseService;

    /**
     * 查询二次活跃渠道用户统计(市场数据)
     * @return
     */
    @RequestMapping("/findMarketUserSecondaryActiveChannel.action")
    public String findMarketUserSecondaryActiveChannel(){
        try {
            // 初始化
            this.initializeAction(_className);

            Object obj = session.getAttribute("cutPage_marketUserSecondaryActiveChannel");
            if(obj != null){
                cutPage = (CutPage) obj;
            }else{
                cutPage = new CutPage();
            }

            // 获取模块ID
            String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
            // 获取包名
            String recordPkg = ToolsString.checkEmpty(request.getParameter("recordPkg"));
            if(ToolsString.isEmpty(recordPkg)){
                if(session.getAttribute("pkgName") != null){
                    recordPkg = session.getAttribute("pkgName")+"";
                }else{
                    recordPkg = "net.myvst.v2";
                }
            }

            // 获取页面按钮
            cutPage.set_buttonList(getPageButtons(moduleId));
            // 转发查询到的数据到页面
            request.setAttribute("cutPage", cutPage);
            // 转发当前模块ID
            request.setAttribute("moduleId", moduleId);
            // 转发包名
            request.setAttribute("recordPkg", recordPkg);

            // 获取表描述
            request.setAttribute("tableDesc", _baseService.getTableDesc("vst_user_secondary_active_channel"));
        } catch (Exception e) {
            logger.error("findMarketUserSecondaryActiveChannel error." + SystemException.getExceptionInfo(e));
            // 转向失败页面
            return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo
                    .getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");
        } finally {
            // 记得移除session中的分页信息
            session.removeAttribute("cutPage_marketUserSecondaryActiveChannel");
        }

        return "outer/marketUserSecondaryActiveChannel_list";
    }

    /**
     * 导出数据
     * @return
     */
    @RequestMapping("/exportData.action")
    public String exportData(){
        try{
            this.initializeAction(_className);

            Map<String, Object> queryParam = new HashMap<String, Object>();
            queryParam.put("startDay", ToolsString.checkEmpty(request.getParameter("startDay")));
            queryParam.put("endDay", ToolsString.checkEmpty(request.getParameter("endDay")));
            queryParam.put("pkgName", ToolsString.checkEmpty(request.getParameter("pkgName")));
            queryParam.put("vst_usac_channel", ToolsString.checkEmpty(request.getParameter("vst_usac_channel")));
            queryParam.put("vst_coefficient", ToolsString.checkEmpty(request.getParameter("vst_coefficient")));

            // 获取模块ID
            String moduleId = ToolsString.checkEmpty(request.getParameter("moduleId"));
            ReportBean bean = _marketUserSecondaryActiveChannelService.getExportData(queryParam, getUserSession(moduleId));
            List<Map<String, Object>> data = bean.getMapData();
            File file = ExportUtil.ExportExcel(data, PropertiesReader.getProperty("export_dir"), bean.getTitle().trim());
            if(file.exists()){
                VstTools.fileDownLoad(response, file);
                file.delete();
            }
        }catch(Exception e){
            logger.error("exportData error." + SystemException.getExceptionInfo(e));
            return this.moveToError(ErrorCode.SYS_OPERATOR_FAILURE, ErrorInfo.getErrorInfo(ErrorCode.SYS_OPERATOR_FAILURE), "sysMain/mainPage.action");// 转向失败页面
        }
        return null;
    }
}
