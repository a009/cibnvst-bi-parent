package com.vst.test;

import java.util.List;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.cache.CacheSparkSqls;
import com.vst.core.communal.sql.SqlFactory;
import com.vst.core.communal.type.SqlType;
import com.vst.core.service.SparkSQLMgrService;
import com.vst.core.service.impl.SparkSQLMgrServiceImpl;


public class Test {

	public static void main(String[] args) {
		SparkSQLMgrService s = new SparkSQLMgrServiceImpl();
		s.initSQLCache();
		
		final List<SqlFactory> sqls = CacheSparkSqls.getInstance().getAllSqlsByState(SqlType.P_ONLINE);
		System.out.println(sqls);
	}
	
	
}
