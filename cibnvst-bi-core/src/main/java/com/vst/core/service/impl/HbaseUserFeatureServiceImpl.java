package com.vst.core.service.impl;

import com.vst.common.tools.encrypt.ToolsEncrypt;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.dao.HbaseDao;
import com.vst.core.dao.impl.HbaseDaoImpl;
import com.vst.core.service.DBMgrService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * 用户指标业务处理类
 * */
public class HbaseUserFeatureServiceImpl extends DBMgrService {

    /**
     * 写日志
     */
    private static final Log logger = LogFactory.getLog(HbaseUserFeatureServiceImpl.class);

    /**
     * 单例类
     */
    private static final HbaseUserFeatureServiceImpl _cache = new HbaseUserFeatureServiceImpl();

    /**
     * hbase数据层管理类
     */
    protected HbaseDao _hbaseDao;

    /**
     * 构造器
     */
    protected HbaseUserFeatureServiceImpl() {
        _hbaseDao = new HbaseDaoImpl();
    }

    /**
     * 获取单例对象
     *
     * @return HbaseFeatureServiceImpl
     */
    public static HbaseUserFeatureServiceImpl getInstance() {
        return _cache;
    }

    /**
     * 数据保存，处理
     * */
    @Override
    public int save(SqlFactory sqlFactory, int date, List<Map<String, Object>> list) {
        Map<String, Map<String, Map<String, Object>>> paramMap = new HashMap<>();
        String[] sqlRowKeyColumn = sqlFactory.getSqlRowKeyColumn();  //获取拼接的RowKey列

        String family = PropertiesReader.getInstance().getProperty("hbase_default_family"); //列族

        if (list != null && !list.isEmpty()) {
            logger.info("===>>> source data size is (" + list.size() + ")");
            long now = System.currentTimeMillis();
            for (Map<String, Object> keyValue : list) { //将数据封装为存储格式
                StringBuilder key = new StringBuilder();
                for (String tempKey : sqlRowKeyColumn) {    //拼接RowKey
                    if (keyValue.containsKey(tempKey)) key.append(keyValue.get(tempKey)).append("[@!@]");
                }
                String keyStr = ToolsEncrypt.getMD5Encrypt(key.toString().replaceAll("\\[@!@]$", ""));

                Map<String, Map<String, Object>> familyMap = paramMap.get(keyStr);
                if (familyMap == null) {
                    familyMap = new HashMap<>();
                }

                Map<String, Object> qulifierValue = familyMap.get(family);
                if (qulifierValue == null) {
                    qulifierValue = new HashMap<>();
                }

                for (Map.Entry<String, Object> dataMap : keyValue.entrySet()) { //踢除为null的值
                    if(!ToolsString.isEmpty(dataMap.getKey()) && !ToolsString.isEmpty(dataMap.getValue()))
                        qulifierValue.put(dataMap.getKey(),dataMap.getValue());
                }

                if(qulifierValue.size() == sqlRowKeyColumn.length) continue;   //如果数据中只存在rowKey的数据则不存储

                familyMap.put(family, qulifierValue);
                paramMap.put(keyStr, familyMap);
            }
            logger.info("===>>> filter after data takes (" + (System.currentTimeMillis() - now) + ") ms");

            boolean flag = _hbaseDao.saveRows(sqlFactory.getSqlTableName(), paramMap);  //数据保存
            if (flag) {
                long time = System.currentTimeMillis() - now;
                logger.info("===>>> insert count is (" + paramMap.size() + "), takes (" + time + ") ms");
                return paramMap.size();
            }
            return -1;
        }
        return 0;
    }

    @Override
    public boolean delete(SqlFactory sqlFactory, String date) {
        return true;
    }

    @Override
    public List<JSONObject> queryFromDB(SqlFactory sqlFactory, String date) {
        return null;
    }

    /**
     * 查询历史数据
     * */
    @Override
    public List<JSONObject> queryFromDB(SqlFactory sqlFactory, List<String> data) {
        return _hbaseDao.queryRowsByRowKeyLike(sqlFactory.getSqlTableName(), data,false);
    }
}
