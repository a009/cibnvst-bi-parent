package com.vst.defend.dao.task;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vst.common.pojo.VstExportTask;
import com.vst.defend.communal.dao.BaseDao;

/**
 * 
 * @author lhp
 * @date 2018-4-13 上午10:38:12
 * @version
 */
@Repository
@Transactional
public interface VstExportTaskDao extends BaseDao<VstExportTask> {

}
