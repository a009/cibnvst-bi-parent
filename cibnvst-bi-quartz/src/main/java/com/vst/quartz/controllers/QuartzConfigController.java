package com.vst.quartz.controllers;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.quartz.constant.ApiMagic;
import com.vst.quartz.utils.page.PageBean;
import com.vst.quartz.service.quartz.QuartzService;
import com.vst.quartz.utils.properties.PropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @author kai 
 * TODO: 2018/1/12
 * 配置信息控制器
 */
@Controller
@RequestMapping("/config")
public class QuartzConfigController {


    private QuartzService quartzService;

    @Autowired
    public QuartzConfigController(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    /**
     * @author LiuKai
     * 跳转到列表页面
     * @return String
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "quartz/job/list";
    }
    /**
     * @author LiuKai
     * 跳转到添加页面
     * @return 返回值String
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(Model model){
        String data = PropertiesReader.getProperty("data_source");
        String[] result;
        if (data.contains(ApiMagic.API_MAGIC_COMMA)){
            result = data.split(ApiMagic.API_MAGIC_COMMA);
        }else {
            result=new String[]{data};
        }

        model.addAttribute("result",result);

        return "quartz/job/add";
    }



    /**
     * @author LiuKai
     * 跳转到编辑页面
     * @return 返回值string
     */
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(String quartzId, Model model){
        VstQuartzConfig config = quartzService.queryByIdQuartz(quartzId);
        model.addAttribute("config",config);

        String data = PropertiesReader.getProperty("data_source");
        String[] result;
        if (data.contains(ApiMagic.API_MAGIC_COMMA)){
            result = data.split(ApiMagic.API_MAGIC_COMMA);
        }else {
            result=new String[]{data};
        }
        model.addAttribute("result",result);


        String sourceName = config.getVst_qc_tarbet_table_name();
        model.addAttribute("source_name",sourceName.replace("{","").replace("}",""));
        return "quartz/job/edit";
    }

    /**
     * @author LiuKai
     * 查看详细信息
     * @return 返回值String
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(String quartzId, Model model){
        VstQuartzConfig config = quartzService.queryByIdQuartz(quartzId);
        model.addAttribute("config",config);
        return "quartz/job/detail";
    }

    /**
     * kai
     * 备份表弹出框页面
     * @param db 数据源
     * @param table 表名
     * @param sourceName 选择的备份表细腻下
     * @return 返回值String
     */
    @RequestMapping(value = "/table")
    public String table(String db,String table,String sourceName,Model model) throws UnsupportedEncodingException{
        System.out.println(db);

        sourceName = URLDecoder.decode(sourceName,"utf-8");

        String[] source;
        if (db.contains(ApiMagic.API_MAGIC_COMMA)){
            source = db.split(",");
        }else {
            source = new String[]{db};
        }

        model.addAttribute("db",source);
        model.addAttribute("table",table);
        JSONObject object =  quartzService.queryTable(source[0],table);

        List<Map<String,Object>> maps = (List<Map<String,Object>>)object.get("data");
        model.addAttribute("data",maps);

        model.addAttribute("sourceName",sourceName);

        return "quartz/module/table";
    }





    /**
     * @author kai
     * TODO:2017-1-12 15：53：29
     * 查询所有配置信息
     * @return 返回值 json
     */
    @RequestMapping("list")
    @ResponseBody
    public JSONObject list(String jobName,@RequestParam(value = "page",required = false,defaultValue = "1" ) Integer page
            ,@RequestParam(value = "rows",required = false,defaultValue = "10") Integer rows){

        //封装分页信息
        PageBean bean =new PageBean();
        bean.setCurrentPage(page);
        bean.setTotalCount(rows);

        return quartzService.queryQuartzConfig(jobName,bean);
    }

    /**
     * kai
     * 根据数据源和条件查询备份表
     * @param db 数据源
     * @param table 数据源备份表模糊查询条件
     * @return 返回值Json
     */
    @RequestMapping(value = "query",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryTable(String db,String table){
        return quartzService.queryTable(db,table);
    }





    /**
     * @author LiuKai
     * 保存配置信息
     * TODO: 2018/1/9
     * @param config 需要保存的配置信息
     * @return 返回值 Json
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject save(VstQuartzConfig config){
        return quartzService.batchQuartzConfig(config);
    }

    /**
     * @author LiuKai
     * 保存配置信息
     * TODO: 2018/1/9
     * @param config 需要保存的配置信息
     * @return 返回值 Json
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(VstQuartzConfig config){
        return quartzService.updateQuartzConfig(config);
    }

    /**
     * kai
     * 删除任务配置
     * @param quartzId 配置id
     * @return 返回值Json
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(name = "quartzId") String quartzId){
        return quartzService.deleteQuartzConfig(quartzId );
    }

}
