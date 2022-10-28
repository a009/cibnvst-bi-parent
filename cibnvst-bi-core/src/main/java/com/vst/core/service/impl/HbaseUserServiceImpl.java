package com.vst.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.vst.common.tools.date.ToolsDate;
import com.vst.common.tools.encrypt.ToolsEncrypt;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.dao.HbaseDao;
import com.vst.core.dao.impl.HbaseDaoImpl;
import com.vst.core.service.DBMgrService;

/**
 * @author weiwei(joslyn)
 * @date 2017-11-1 下午05:52:34
 * @decription
 * @version
 */
public class HbaseUserServiceImpl extends DBMgrService {
    /**
     * 写日志
     */
    private static final Log logger = LogFactory.getLog(HbaseUserServiceImpl.class);

    /**
     * 单例类
     */
    private static final HbaseUserServiceImpl _cache = new HbaseUserServiceImpl();

    /**
     * hbase数据层管理类
     */
    private HbaseDao _hbaseDao;

    /**
     * 构造器
     */
    private HbaseUserServiceImpl() {
        _hbaseDao = new HbaseDaoImpl();
    }

    /**
     * 获取单例对象
     * @return
     */
    public static HbaseUserServiceImpl getInstance(){
        return _cache;
    }

    @Override
    public int save(SqlFactory sqlFactory, int date, List<Map<String, Object>> list) throws Exception {
        // 保存用户数据逻辑
        // 1、首先判断用户是否存在，如果不存在，插入操作，
        // 2、如果用户存在，需要合并以前的活跃天字段，用以前的注册时间字段，而后再插入
        if(list != null && !list.isEmpty()){
            int size = list.size();
            List<String> rowKeys = checkRepeat(list);// 过滤掉重复的
            logger.info("===>>> before check repeat size is (" + size + "), after checked size is (" + list.size() + ")");
            // 查询已经存在的用户信息
            long now = System.currentTimeMillis();
            Map<String, Map<String, Object>> users = _hbaseDao.queryByRowKeys(sqlFactory.getSqlTableName(), rowKeys);
            logger.info("===>>> query hbase user data takes (" + (System.currentTimeMillis() - now) + ") ms");
            now = System.currentTimeMillis();
            String[] rowKeyColumn = sqlFactory.getSqlRowKeyColumn();
            String defaultFamily = PropertiesReader.getInstance().getProperty("hbase_default_family");
            if(rowKeyColumn != null && rowKeyColumn.length > 0){
                Map<String, Map<String, Map<String, Object>>> paramMap = new HashMap<String, Map<String, Map<String, Object>>>();
                int insertCount = 0;
                int updateCount = 0;
                for(Map<String, Object> map : list){
                    // 拼接rowKey主键
                    String rowKey = ToolsEncrypt.getMD5Encrypt(ToolsString.checkEmpty(map.get("vst_user_uuid")));
                    if(rowKey == null){
                        logger.error("rowKey is null according to " + map);
                        continue;
                    }
                    map.put("vst_user_last_active_date", map.get("vst_user_register_date"));
                    String activeDates = ToolsDate.formatDate(ToolsDate.yyyy_MM_dd4, ToolsString.checkEmpty(map.get("vst_user_register_date")));
                    // 根据用户的uuid到hbase里面去查询记录
                    Map<String, Object> user = users.get(rowKey);
                    if(user != null && !user.isEmpty()){// 如果用户已经存在，需要更新活跃天字段，并且维持注册日期和rowKey不变
                        String oldActiveDates = ToolsString.checkEmpty(user.get("vst_user_active_dates"));
                        // 如果活跃天字段已经包含当前天，就不用更新此字段；如果没有，则需要拼接上
                        if(ToolsString.isEmpty(oldActiveDates)){
                            oldActiveDates = activeDates;
                        }else if(!oldActiveDates.contains(activeDates)){
                            oldActiveDates += "," + activeDates;
                        }
                        map.put("vst_user_active_dates", oldActiveDates);
                        map.put("vst_user_register_date", user.get("vst_user_register_date"));// 注册时间不变
                        updateCount++;
                    }else{
                        map.put("vst_user_active_dates", activeDates);
                        insertCount++;
                    }

                    Map<String, Map<String, Object>> columnMap = new HashMap<String, Map<String, Object>>(1);
                    columnMap.put(defaultFamily, map);
                    paramMap.put(rowKey, columnMap);
                }
                logger.info("===>>> wrap data takes (" + (System.currentTimeMillis() - now) + ") ms");
                now = System.currentTimeMillis();
                // 保存数据到hbase
                boolean flag = _hbaseDao.saveRows(sqlFactory.getSqlTableName(), paramMap);
                if(flag){
                    long time = System.currentTimeMillis() - now;
                    logger.info("===>>> insert count is (" + insertCount + "), update count is (" + updateCount + "), total affects (" + (insertCount + updateCount) + "), takes (" + time + ") ms");
                    return paramMap.size();
                }
                return -1;
            }
        }
        return 0;
    }

    private List<String> checkRepeat(List<Map<String, Object>> list){
        if(list != null && !list.isEmpty()){
            Map<String, String> filter = new HashMap<String, String>();
            List<String> rowKeys = new ArrayList<String>();
            for(Iterator<Map<String, Object>> it = list.iterator(); it.hasNext();){
                Map<String, Object> map = it.next();
                String uuid = ToolsString.checkEmpty(map.get("vst_user_uuid"));
                if(!filter.containsKey(uuid)){
                    filter.put(uuid, null);
                    rowKeys.add(ToolsEncrypt.getMD5Encrypt(uuid));
                }else{
                    it.remove();
                }
            }
            return rowKeys;
        }
        return null;
    }

    @Override
    public boolean delete(SqlFactory sqlFactory, String date) {
        return true;
    }

    @Override
    public List<JSONObject> queryFromDB(SqlFactory sqlFactory, String date) {
        return null;
    }
}
