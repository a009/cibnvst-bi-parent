package com.vst.defend.dao.sys;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstDictionary;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2017-6-14 上午11:48:39
 * @description
 * @version
 */
@Repository
@Transactional
public interface VstDictionaryDao extends BaseDao<VstDictionary> {

}
