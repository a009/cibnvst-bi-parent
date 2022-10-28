package com.vst.quartz.service.clean.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vst.common.pojo.VstQuartzConfig;
import com.vst.common.tools.string.ToolsString;
import com.vst.quartz.constant.ApiCode;
import com.vst.quartz.utils.page.PageBean;
import com.vst.quartz.utils.table.TableTools;
import com.vst.quartz.config.dynamic.DataSourceHolder;
import com.vst.quartz.dao.clean.BackUpMapper;
import com.vst.quartz.dao.clean.CleanTablesMapper;
import com.vst.quartz.enumerate.QuartzEnum;
import com.vst.quartz.service.clean.BackUpService;
import com.vst.quartz.utils.mail.Email;
import com.vst.quartz.utils.mail.EmailModule;
import com.vst.quartz.utils.mail.EmailTools;
import com.vst.quartz.utils.thread.FileThread;
import com.vst.quartz.utils.ts.TscTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kai
 * 备份逻辑层
 * TODO: 2018/1/11
 */
@Service
public class BackUpServiceImpl implements BackUpService {

    private static Log logger= LogFactory.getLog(BackUpServiceImpl.class);


    /**
     * 用于保存需要备份成文件的数据
     *用于存储备份的sql，然后保存到文件中
     */
    private List<String> strings = new ArrayList<>();

    /**
     * 用于保存导出excel时需要的数据格式
     */
    private List<Map<String,Object>> excelMap = new ArrayList<>();

    /**
     * 回去每个字段列的名称
     */
    private Map<String,String> fileMaps = new HashMap<>();

    private BackUpMapper backUpMapper;

    private CleanTablesMapper cleanTablesMapper;

    @Autowired
    public BackUpServiceImpl(BackUpMapper backUpMapper,CleanTablesMapper cleanTablesMapper) {
        this.backUpMapper = backUpMapper;
        this.cleanTablesMapper = cleanTablesMapper;
    }

    /**
     * kai
     * 2018/1/11 11：45：58
     * 字段备份
     * @param config 任务对象
     * @return 返回值json
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject backUpField(VstQuartzConfig config, PageBean pageBean,String db) {
        JSONObject object=new JSONObject();

        try {
            //获取表名
            String tableName = config.getVst_qc_table();
            //获取需要备份的字段
            String field = config.getVst_qc_backup_condition();

            JSONObject resultFiled = publicFiled(db,config.getVst_qc_table());
            if (resultFiled == null){
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }

            //判断tableName和field是否为空
            if (ToolsString.isEmpty(tableName) || ToolsString.isEmpty(field)){
                logger.info("表名或者字段名为空。。。。");
                object.put("code",119);
                object.put("msg","表名或者字段名为空");
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }

            String condition = config.getVst_qc_condition();
            if (ToolsString.isEmpty(condition)){
                condition = "1=1";
            }

            //拼接Sql
            String sql="SELECT "+field+" FROM " +tableName+" where "+condition;
            PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getTotalCount());
            //查询需要备份的数据
            Page<Map<String,Object>> mapPage = (Page<Map<String,Object>>) backUpMapper.queryList(sql);
            List<Map<String,Object>> maps=mapPage.getResult();
            if (maps.size()<=0){
                logger.info("没有查询到备份数据。。。。");
                object.put("code",202);
                object.put("msg","没有查询到备份数据");
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }
            //获取字段file类型
            Map<String,String> map = (Map<String,String>)resultFiled.get("column_type");

            boolean flag = false;
            //判断是否删除原数据
            int after = config.getVst_qc_deal_after();
            if (after == QuartzEnum.VST_JOB_AFTER.getTowStatus()){
                flag = true;
            }
            //传入分页信息
            pageBean.setPageNum(mapPage.getPages());

            TscTools tools = new TscTools();

            //获取列对应的列描述
            fileMaps = (Map<String,String>)resultFiled.get("column_comment");

            //保存添加的数据
            excelMap.addAll(maps);

            object = tools.betweenSave(maps, field, map, flag, pageBean,config,db,strings);

            pageBean.setCurrentPage(pageBean.getCurrentPage()+1);

        }catch (Exception e){
            e.printStackTrace();

            object.put("code",ApiCode.API_CODE_110);
            object.put("msg","字段备份时出现异常错误；任务名:"+config.getVst_qc_name());


            //发送邮件提醒
            Email email = new Email();
            email.setTitle("报表任务出现异常：任务名："+config.getVst_qc_name());
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("字段备份","LiuKai",method,e));
            EmailTools.sendEmail(email);

            pageBean.setCurrentPage(pageBean.getCurrentPage()+1);

        }

        return object;
    }


    /**
     * kai
     * 2018-1-11 15：42：40
     * 全量备份
     * @param config 任务对象
     * @return 返回值 JSONObject
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject backUpAll(VstQuartzConfig config, PageBean pageBean,String db) {
        JSONObject object=new JSONObject();
        try {
            String tableName = config.getVst_qc_table();

            //判断tableName和field是否为空
            if (ToolsString.isEmpty(tableName)){
                logger.info("表名或者实体类名称为空。。。。");
                object.put("code",119);
                object.put("msg","表名或者实体类名称为空");



                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }

            JSONObject resultFiled = publicFiled(db,config.getVst_qc_table());
            if (resultFiled == null){
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }
            String filed= resultFiled.getString("column_name");

            //获取file字段名的类型
            Map<String,String> map = (Map<String,String>)resultFiled.get("column_type");

            if (ToolsString.isEmpty(filed)){
                logger.info("无法获取实体类字段属性。可能实体类有问题");
                object.put("code",119);
                object.put("msg","无法获取实体类字段属性。可能实体类有问题");
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }

            //拼接sql
            String sql="SELECT "+filed+" FROM "+tableName;

            PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getTotalCount(),true);
            //查询需要备份的数据
            Page<Map<String,Object>> pageInfo = (Page<Map<String,Object>>) backUpMapper.queryList(sql);
            List<Map<String,Object>> maps=pageInfo.getResult();
            //判断是否查询数据
            if (maps.size()<=0){
                logger.info("未查询到备份数据。。。。");
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","未查询到备份数据");
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }

            //获取列对应的列描述
            fileMaps = (Map<String,String>)resultFiled.get("column_comment");

            if (map == null || map.size()<=0){
                logger.info("字段类型获取失败");
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return  object;
            }

            boolean flag = false;
            //判断是否删除原数据
            int after = config.getVst_qc_deal_after();
            if (after == QuartzEnum.VST_JOB_AFTER.getTowStatus()){
                flag = true;
            }

            //保存添加的数据
            excelMap.addAll(maps);

            //传入分页信息
            pageBean.setPageNum(pageInfo.getPages());

            TscTools tools = new TscTools();

            //保存数据到备份表
            object = tools.betweenSave(maps, filed, map, flag, pageBean,config,db,strings);
            pageBean.setCurrentPage(pageBean.getCurrentPage()+1);

        }catch (Exception e){
            object.put("code",ApiCode.API_CODE_110);
            object.put("msg","全量备份时出现异常错误；任务名:"+config.getVst_qc_name());

            //发送邮件提醒
            Email email = new Email();
            email.setTitle("报表任务出现异常：任务名："+config.getVst_qc_name());
            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("全量备份","LiuKai",method,e));
            EmailTools.sendEmail(email);


            pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
            e.printStackTrace();
        }

        return object;
    }

    /**
     * @author kai
     * 根据条件来备份
     * TODO: 2018/1/11 16:00:20
     * @param config 任务配置对象
     * @return 返回值Json
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject backUpCondition(VstQuartzConfig config, PageBean pageBean,String db){

        JSONObject object = new JSONObject();
        try {
            String tableName = config.getVst_qc_table();
            String condition = config.getVst_qc_condition();

            //判断tableName和field是否为空
            if (ToolsString.isEmpty(tableName)){
                logger.info("表名或者实体类名称为空。。。。");
                object.put("code",ApiCode.API_CODE_119);
                object.put("msg","表名或者实体类名称为空");
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }
            //如果条件为空,直接可以条用全量的方法
            if (ToolsString.isEmpty(condition)){
                //发送邮件提醒
                Email email = new Email();

                email.setTitle("条件为空。。。,任务名："+config.getVst_qc_name());

                //获取当前方法
                String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
                //内容
                String content = "您选择了条件备份,却没有给相应的条件，因此备份驳回";
                email.setContent(EmailModule.creteTipsModule("条件备份","LiuKai",method,content));

                EmailTools.sendEmail(email);
                return object;
            }
            //获取实体类字段
            JSONObject resultFiled = publicFiled(db,config.getVst_qc_table());
            if (resultFiled == null){
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }
            //获取表字段
            String filed=resultFiled.getString("column_name");

            String sql="SELECT "+filed+" FROM "+tableName+" WHERE "+condition;

            PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getTotalCount());
            //查询需要备份的数据
            Page<Map<String,Object>> mapPage = (Page<Map<String,Object>>) backUpMapper.queryList(sql);
            List<Map<String,Object>> maps=mapPage.getResult();

            if (maps.size()<=0){

                logger.info("未找到根据条件来备份的数据");
                object.put("code",ApiCode.API_CODE_202);
                object.put("msg","未查询到备份数据");
                pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
                return object;
            }

            //获取列名对应的列类型
            Map<String,String> map = (Map<String,String>)resultFiled.get("column_type");

            //获取列对应的列描述
            fileMaps = (Map<String,String>)resultFiled.get("column_comment");

            boolean flag = false;
            //判断是否删除原数据
            int after = config.getVst_qc_deal_after();
            if (after == QuartzEnum.VST_JOB_AFTER.getTowStatus()){
                flag = true;
            }

            //保存添加的数据
            excelMap.addAll(maps);

            //传入分页信息
            pageBean.setPageNum(mapPage.getPages());

            TscTools tools = new TscTools();

            //保存备份表数据
            object= tools.betweenSave(maps, filed, map, flag, pageBean,config,db,strings);

            pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
        }catch (Exception e){
            object.put("code",ApiCode.API_CODE_110);
            object.put("msg","根据条件备份是出现异常错误；任务名:"+config.getVst_qc_name());

            //发送邮件提醒
            Email email = new Email();

            email.setTitle("报表任务出现异常：任务名："+config.getVst_qc_name());

            //获取当前方法
            String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
            email.setContent(EmailModule.createIpModule("条件备份","LiuKai",method,e));

            EmailTools.sendEmail(email);

            pageBean.setCurrentPage(pageBean.getCurrentPage()+1);
            e.printStackTrace();
        }

        return object;
    }



    /**
     * kai
     * 条件备份,表备份和清除必须要统一方法中，保证事务一致性
     * TODO: 2018/1/11 16:20:20
     * @param backUpTableName 备份表名称
     * @param maps 备份的数据
     * @param filed 备份的字段
     * @param map 根据字段获取字段的类型
     * @param flag 用户判断备份之后是否需要执行原表清理操作
     * @param config 任务配置对象
     * @param daSite 目标数据源
     * @param result 数据库目标表名
     * @param resultSql 备份到备份表的sql
     * @return 返回值 JsonObject
     */
    @Override
    public JSONObject saveBatch(String backUpTableName,List<Map<String,Object>> maps,String filed,Map<String,String> map
                                 ,Boolean flag,PageBean bean,VstQuartzConfig config,String[] daSite,String db
                                 ,JSONObject result,String resultSql){
            JSONObject object=new JSONObject();

            String condition = config.getVst_qc_condition();
            if (ToolsString.isEmpty(condition)) {
                condition = "1=1";
            }
            try {

                //判断该任务是否需要进行表备份,1进行表备份，2进行文件备份，3两者都备份
                int backup = config.getVst_qc_backup();
                if (backup == QuartzEnum.VST_BACKUP.getOneStatus() || backup == QuartzEnum.VST_BACKUP.getTreeStatus()){
                    //备份数据，遍历目标数据源
                    for (String dbSite:daSite) {
                        //获取目标数据源对应的表名
                        String tableName = result.getString(dbSite);
                        if (ToolsString.isEmpty(tableName)){
                            continue;
                        }
                        //如果获取的数据源表名中包含这张表，则备份,否则不备份
                        if (tableName.contains(backUpTableName)){
                            //切换数据源
                            DataSourceHolder.setDataSourceKey(dbSite);

                            int count = backUpMapper.batchTablesBackUp(resultSql);
                            if (count>0){
                                logger.info("数据成功备份"+count+"条");
                            }
                        }

                    }
                }

                //获取当前页码
                int currentPage = bean.getCurrentPage();
                int pageNum = bean.getPageNum();
                //如果数据已经备份，即所有页码都已经清除，则根据条件删除
                if (currentPage == pageNum) {
                    //判读是否需要执行清理操作
                     if (flag){
                         DataSourceHolder.setDataSourceKey(db);
                         String table = config.getVst_qc_table();
                         //获取封装的删除sql
                         String deleteSql = TableTools.deleteData(table,condition);
                         //删除数据
                         int clear = cleanTablesMapper.cleanTables(deleteSql);
                         if (clear > 0) {
                            logger.info("清除数据:" + clear + "条成功。。。");
                         } else {
                            logger.info("未找到符合条件的数据清除");
                         }
                    }

                    if (backup == QuartzEnum.VST_BACKUP.getTowStatus() || backup == QuartzEnum.VST_BACKUP.getTreeStatus()){
                        //文件备份
                        new Thread(new FileThread(strings,excelMap,fileMaps,config)).start();

                    }


                }

            }catch (Exception e){
                logger.info("条件备份数据是出现异常");
                e.printStackTrace();
                //发送邮件提醒
                Email email = new Email();
                email.setTitle("报表任务出现异常:任务名:"+ config.getVst_qc_name());
                //获取当前方法
                String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
                email.setContent(EmailModule.createIpModule("报表任务出现异常","LiuKai",method,e));
                EmailTools.sendEmail(email);


                throw new RuntimeException();
            }
        object.put("code",ApiCode.API_CODE_200);
        object.put("msg","");


        return object;
    }

    /**
     * @author kai
     * 获取表字段
     * @return 返回值Json
     */
    private JSONObject publicFiled(String db,String table){
        //拼接查询表字段的sql
        String filedSql = TableTools.getTable(db,table);
        //查询表字段属性
        List<Map<String,Object>> filedMap = backUpMapper.queryList(filedSql);
        if (filedMap == null || filedMap.size()<=0){
            return  null;
        }else {
            return TableTools.getMap(filedMap);

        }
    }
}
