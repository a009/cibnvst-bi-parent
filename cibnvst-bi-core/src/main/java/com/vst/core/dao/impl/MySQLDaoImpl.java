package com.vst.core.dao.impl;

import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.Save;
import com.vst.core.dao.MySQLDao;

/**
 * @author joslyn
 * @date 2017年11月22日 下午12:05:55
 * @description
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class MySQLDaoImpl implements MySQLDao, Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4270867865031584780L;

	/**
	 * 写日志
	 */
	private static final Log logger = LogFactory.getLog(MySQLDaoImpl.class);

	/**
	 * 数据库连接对象
	 */
	private ComboPooledDataSource _cpds = null;

	/**
	 * 构造器
	 */
	public MySQLDaoImpl() {
		this("jdbc:mysql://localhost:3306/vst_bi", "javadmin", "VstVst520_");
	}

	/**
	 * 带参构造器
	 * @param url 数据库连接地址
	 * @param username 用户名
	 * @param passwd 密码
	 */
	public MySQLDaoImpl(String url, String username, String passwd) {
		try {
			_cpds = new ComboPooledDataSource("mysql");
			_cpds.setJdbcUrl(url);//设置url
			_cpds.setDriverClass("com.mysql.jdbc.Driver");//设置驱动
			_cpds.setUser(username);//mysql的账号
			_cpds.setPassword(passwd);//mysql的密码
			_cpds.setInitialPoolSize(5);//初始连接数，即初始化6个连接
			_cpds.setMaxPoolSize(50);//最大连接数，即最大的连接数是50
			_cpds.setMaxIdleTime(60);//最大空闲时间
		} catch (PropertyVetoException e) {
			logger.error("init mysql connection error. ERROR:", e);
		}
	}

	@Override
	public int save(List<String> sqls) {
		if(sqls != null && !sqls.isEmpty()){
			Connection conn = null;
			Statement st = null;
			try {
				conn = _cpds.getConnection();
				conn.setAutoCommit(false);
				st = conn.createStatement();
				int affectRows = 0;
				for(int i = 0, l = sqls.size(); i < l; i++){
					st.addBatch(sqls.get(i));
					if((i + 1) % 5000 == 0 || i == l - 1){
						int[] rows = st.executeBatch();
						if(rows != null && rows.length > 0){
							for(int row : rows){
								if(row > 0) affectRows += row;
							}
						}
						conn.commit();
						st.clearBatch();
					}
				}
				return affectRows;
			} catch (Exception e) {
				try {
					if(conn != null) conn.rollback();
				} catch (SQLException e1) {
					logger.error("rollback error. ERROR:", e);
				}
				e.printStackTrace();
			} finally {
				try {
					if(conn != null){
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					logger.error("set auto commit error. ERROR:", e);
				}
				close(st, conn);
			}
		}
		return 0;
	}

	@Override
	public int save(String sql, List<Save> columns, List<Map<String, Object>> dataList) throws Exception {
		if(columns != null && !columns.isEmpty() && dataList != null && !dataList.isEmpty()){
			Connection conn = null;
			PreparedStatement psmt = null;
			try {
				conn = _cpds.getConnection();
				conn.setAutoCommit(false);
				psmt = conn.prepareStatement(sql);
				int affectRows = 0;
				for(int i = 0, l = dataList.size(); i < l; i++){
					Map<String, Object> data = dataList.get(i);
					if(data == null || data.isEmpty()) continue;
					for(int index = 0; index < columns.size(); index++){
						Save save = columns.get(index);
						Object value = data.get(save.getName());
						psmt.setObject(index + 1, value);
					}
					psmt.addBatch();
				}
				int[] rows = psmt.executeBatch();
				if(rows != null && rows.length > 0){
					for(int row : rows){
						if(row > 0) affectRows += row;
					}
				}
				conn.commit();
				return affectRows;
			} catch (Exception e) {
				try {
					if(conn != null) conn.rollback();
				} catch (SQLException e1) {
					logger.error("rollback error. ERROR:", e);
				}
				e.printStackTrace();
				logger.error("mysql db error. ERROR:", e);
				throw new RuntimeException("mysql db error!");
			} finally {
				try {
					if(conn != null){
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					logger.error("set auto commit error. ERROR:", e);
				}
				close(psmt, conn);
			}
		}
		return 0;
	}

	private void close(Statement st, Connection conn){
		if(st != null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void close(ResultSet rs, Statement st, Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if(st != null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int delete(String sql) {
		if(!ToolsString.isEmpty(sql)){
			Connection conn = null;
			Statement st = null;
			try {
				conn = _cpds.getConnection();
				st = conn.createStatement();
				return st.executeUpdate(sql);
			} catch (Exception e) {
				logger.error("delete error. ERROR:", e);
			} finally {
				close(st, conn);
			}
		}
		return 0;
	}

	@Override
	public List<JSONObject> query(String sql) {
		if(!ToolsString.isEmpty(sql)){
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			try {
				conn = _cpds.getConnection();
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				JSONObject temp = null;
				List<JSONObject> result = new ArrayList<JSONObject>();
				while(rs.next()){
					temp = new JSONObject();
					ResultSetMetaData rsmd = rs.getMetaData();
					int size = rsmd.getColumnCount();
					for(int i = 1; i <= size; i++){
						String label = rsmd.getColumnName(i);
						temp.put(label, rs.getObject(i));
					}
					result.add(temp);
				}
				return result;
			} catch (Exception e) {
				logger.error("query error. ERROR:", e);
			} finally {
				close(rs, psmt, conn);
			}
		}
		return null;
	}
}
