package com.vst.core.service.impl;

import com.vst.core.cache.CacheUserFeatures;
import com.vst.core.dao.MySQLDao;
import com.vst.core.dao.impl.MySQLDaoImpl;
import com.vst.core.service.OthersMgrService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import java.util.List;

public class FeatureConfigMgrServiceImpl implements OthersMgrService {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -197575425800090875L;

	/**
     * mysql管理类
     */
    private MySQLDao _mysqlDao;

    /**
     * 写日志
     */
    private static final Log logger = LogFactory.getLog(FeatureConfigMgrServiceImpl.class);


    /**
     * 构造器
     */
    public FeatureConfigMgrServiceImpl() {
        _mysqlDao = new MySQLDaoImpl("jdbc:mysql://bj-bi-operation-sysadmin:3306/vst_config", "javadmin", "VstVst520_");
    }

    @Override
    public void initCache() {
        try {
            List<JSONObject> featuresList = _mysqlDao.query("SELECT vst_features_pkg, vst_features_special_type, vst_features_cid, vst_features_type, vst_features_name, vst_features_value FROM vst_features_config WHERE vst_features_state = 1 ORDER BY vst_features_pkg DESC,vst_features_special_type,vst_features_cid,vst_features_index DESC");
            CacheUserFeatures.getInstance().loadFeatureConfig(featuresList);
        } catch (Exception e) {
            logger.error("initConfigCache error. ERROR:", e);
        }
    }

    @Override
    public void flushCache() {
        try {
            List<JSONObject> featuresList = _mysqlDao.query("SELECT vst_features_pkg, vst_features_special_type, vst_features_cid, vst_features_type, vst_features_name, vst_features_value FROM vst_features_config WHERE vst_features_state = 1 ORDER BY vst_features_pkg DESC,vst_features_special_type,vst_features_cid,vst_features_index DESC");
            CacheUserFeatures.getInstance().loadFeatureConfig(featuresList);
        } catch (Exception e) {
            logger.error("initConfigCache error. ERROR:", e);
        }
    }
}
