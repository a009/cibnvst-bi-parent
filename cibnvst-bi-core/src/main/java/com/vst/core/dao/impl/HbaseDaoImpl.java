package com.vst.core.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vst.common.tools.encrypt.ToolsEncrypt;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.json.simple.JSONObject;

import com.vst.common.tools.io.ToolsIO;
import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.util.PropertiesReader;
import com.vst.core.dao.HbaseDao;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-27 下午04:52:22
 * @decription
 * @version
 */
@SuppressWarnings("unchecked")
public class HbaseDaoImpl implements HbaseDao {

	/**
	 * hbase配置管理类
	 */
	private Configuration _configuration;

	/**
	 * hbase连接管理类
	 */
	private Connection _connection;

	{
		try {
			_configuration = HBaseConfiguration.create();
			_configuration.set("hbase.zookeeper.property.clientPort", PropertiesReader.getInstance().getProperty("client_port"));
			_configuration.set("hbase.zookeeper.quorum", PropertiesReader.getInstance().getProperty("zookeeper"));
			User user = User.create(UserGroupInformation.createRemoteUser(PropertiesReader.getInstance().getProperty("hbase_user")));
			_connection = ConnectionFactory.createConnection(_configuration, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param isReplaceExistTable 是否替换已经存在的表
	 * @param timeToLive 设置最大存活时间，单位是秒
	 * @param openCache 是否使用缓存
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName, String[] columns, boolean isReplaceExistTable, int timeToLive, boolean openCache) {
		Admin admin = null;
		try {
			admin = _connection.getAdmin();
			// 创建命名空间
			admin.createNamespace(NamespaceDescriptor.create(namespace).build());
			TableName table = TableName.valueOf(namespace + ":" + tableName);
			if(admin.tableExists(table)){
				if(!isReplaceExistTable) {
					return true;
				}
				// 如果不替换，先要禁用，才能删除
				admin.disableTable(table);
				admin.deleteTable(table);
			}

			// 创建列族
			HTableDescriptor hTableDescriptor = new HTableDescriptor(table);
			for(String column : columns){
				// 创建列
				HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(column);
				hColumnDescriptor.setMaxVersions(3);// 最多保留三个版本
				hColumnDescriptor.setMinVersions(1);// 最少保留一个版本
				if(timeToLive > 0){
					hColumnDescriptor.setTimeToLive(timeToLive);
				}
				if(openCache) hColumnDescriptor.setInMemory(true);
				hTableDescriptor.addFamily(hColumnDescriptor);
			}
			// 创建表
			admin.createTable(hTableDescriptor);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			// 关流，释放资源
			ToolsIO.closeStream(admin);
		}
		return true;
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param isReplaceExistTable 是否替换已经存在的表
	 * @param timeToLive 设置最大存活时间，单位是秒
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName,
							   String[] columns, boolean isReplaceExistTable, int timeToLive) {
		return createTable(namespace, tableName, columns, isReplaceExistTable, timeToLive, false);
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param timeToLive 设置最大存活时间，单位是秒
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName,
							   String[] columns, int timeToLive) {
		return createTable(namespace, tableName, columns, false, timeToLive);
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param isReplaceExistTable 是否替换已经存在的表
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName,
							   String[] columns, boolean isReplaceExistTable) {
		return createTable(namespace, tableName, columns, isReplaceExistTable, -1);
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName, String[] columns) {
		return createTable(namespace, tableName, columns, false);
	}

	/**
	 * 创建表方法
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param isReplaceExistTable 是否替换已经存在的表
	 * @param timeToLive 设置最大存活时间，单位是秒
	 * @return
	 */
	@Override
	public boolean createTable(String tableName, String[] columns, boolean isReplaceExistTable, int timeToLive) {
		return createTable(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, columns, isReplaceExistTable, timeToLive);
	}

	/**
	 * 创建表方法
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param timeToLive 设置最大存活时间，单位是秒
	 * @return
	 */
	@Override
	public boolean createTable(String tableName, String[] columns, int timeToLive) {
		return createTable(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, columns, timeToLive);
	}

	/**
	 * 创建表方法
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param isReplaceExistTable 是否替换已经存在的表
	 * @return
	 */
	@Override
	public boolean createTable(String tableName, String[] columns, boolean isReplaceExistTable) {
		return createTable(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, columns, isReplaceExistTable);
	}

	/**
	 * 创建表方法
	 * @param tableName 表名
	 * @param columns 元素名
	 * @return
	 */
	@Override
	public boolean createTable(String tableName, String[] columns) {
		return createTable(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, columns);
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param isReplaceExistTable 是否替换已经存在的表
	 * @param timeToLive 设置最大存活时间，单位是秒
	 * @param openCache 是否使用缓存
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName, boolean isReplaceExistTable, int timeToLive, boolean openCache) {
		return createTable(namespace, tableName, new String[]{PropertiesReader.getInstance().getProperty("hbase_default_family")}, isReplaceExistTable, timeToLive, openCache);
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param isReplaceExistTable 是否替换已经存在的表
	 * @param timeToLive 设置最大存活时间，单位是秒
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName, boolean isReplaceExistTable, int timeToLive) {
		return createTable(namespace, tableName, isReplaceExistTable, timeToLive, false);
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param isReplaceExistTable 是否替换已经存在的表
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName, boolean isReplaceExistTable) {
		return createTable(namespace, tableName, isReplaceExistTable, -1);
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param columns 元素名
	 * @param timeToLive 设置最大存活时间，单位是秒
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName, int timeToLive) {
		return createTable(namespace, tableName, false, timeToLive);
	}

	/**
	 * 创建表方法
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @return
	 */
	@Override
	public boolean createTable(String namespace, String tableName) {
		return createTable(namespace, tableName, new String[]{PropertiesReader.getInstance().getProperty("hbase_default_family")});
	}

	/**
	 * 创建表方法
	 * @param tableName 表名
	 * @return
	 */
	@Override
	public boolean createTable(String tableName) {
		return createTable(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, new String[]{PropertiesReader.getInstance().getProperty("hbase_default_family")});
	}

	/**
	 * 删除一张表
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @return
	 */
	@Override
	public boolean dropTable(String namespace, String tableName) {
		Admin admin = null;
		try {
			admin = _connection.getAdmin();
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			if(admin.tableExists(tableNameBean)){
				admin.disableTable(tableNameBean);
				admin.deleteTable(tableNameBean);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(admin);
		}
		return false;
	}

	/**
	 * 删除一张表
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @return
	 */
	@Override
	public boolean dropTable(String tableName) {
		return dropTable(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName);
	}

	/**
	 * 删除一张表
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @return
	 */
	@Override
	public boolean dropTables(String namespace, String... tableNames) {
		Admin admin = null;
		try {
			admin = _connection.getAdmin();
			for(String tableName : tableNames){
				TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
				if(admin.tableExists(tableNameBean)){
					admin.disableTable(tableNameBean);
					admin.deleteTable(tableNameBean);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(admin);
		}
		return false;
	}

	/**
	 * 删除一张表
	 * @param tableName 表名
	 * @return
	 */
	@Override
	public boolean dropTables(String... tableNames) {
		Admin admin = null;
		try {
			admin = _connection.getAdmin();
			for(String tableName : tableNames){
				TableName tableNameBean = TableName.valueOf(PropertiesReader.getInstance().getProperty("hbase_default_namespace") + ":" + tableName);
				if(admin.tableExists(tableNameBean)){
					admin.disableTable(tableNameBean);
					admin.deleteTable(tableNameBean);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(admin);
		}
		return false;
	}

	/**
	 * 插入一个列族的多个列数据
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param rowKey key值
	 * @param paramMap 多个列族下面的所有列键值对
	 * @return
	 */
	@Override
	public boolean saveRow(String namespace, String tableName, String rowKey, Map<String, Map<String, Object>> paramMap) {
		Table table = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			Put put = new Put(Bytes.toBytes(rowKey));
			for(Map.Entry<String, Map<String, Object>> entry : paramMap.entrySet()){
				String column = entry.getKey();
				for(Map.Entry<String, Object> entry2 : entry.getValue().entrySet()){
					String key = ToolsString.checkEmpty(entry2.getKey());
					Object value = entry2.getValue();
					put.addColumn(Bytes.toBytes(column), Bytes.toBytes(key), Bytes.toBytes(ToolsString.checkEmpty(value)));
				}
			}
			table.put(put);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return false;
	}

	/**
	 * 插入一个列族的多个列数据
	 * @param tableName 表名
	 * @param rowKey key值
	 * @param paramMap 多个列族下面的所有列键值对
	 * @return
	 */
	@Override
	public boolean saveRow(String tableName, String rowKey,
						   Map<String, Map<String, Object>> paramMap) {
		return saveRow(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, rowKey, paramMap);
	}

	/**
	 * 插入一个列族的多个列数据
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param rowKey key值
	 * @param column 列族
	 * @param paramMap 列族下面的所有列键值对
	 * @return
	 */
	@Override
	public boolean saveRow(String namespace, String tableName, String rowKey, String column, Map<String, Object> paramMap) {
		Table table = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			Put put = new Put(Bytes.toBytes(rowKey));
			for(Map.Entry<String, Object> entry : paramMap.entrySet()){
				put.addColumn(Bytes.toBytes(column), Bytes.toBytes(ToolsString.checkEmpty(entry.getKey())), Bytes.toBytes(ToolsString.checkEmpty(entry.getValue())));
			}
			table.put(put);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return false;
	}

	/**
	 * 插入一个列族的多个列数据
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param rowKey key值
	 * @param column 列族
	 * @param paramMap 列族下面的所有列键值对
	 * @return
	 */
	@Override
	public boolean saveOrUpdate(String tableName, String rowKey, String column, Map<String, Object> paramMap) {
		return saveRow(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, rowKey, column, paramMap);
	}

	/**
	 * 插入多行数据
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param paramMap 多个列族下面的所有列键值对
	 * @return
	 */
	@Override
	public boolean saveRows(String namespace, String tableName, Map<String, Map<String, Map<String, Object>>> paramMap) {
		Table table = null;
		try {
			if(paramMap != null && !paramMap.isEmpty()){
				TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
				table = _connection.getTable(tableNameBean);
				List<Put> puts = new ArrayList<Put>();
				for(Map.Entry<String, Map<String, Map<String, Object>>> rowEntry : paramMap.entrySet()){
					String rowKey = rowEntry.getKey();
					Put put = new Put(Bytes.toBytes(rowKey));
					for(Map.Entry<String, Map<String, Object>> entry : rowEntry.getValue().entrySet()){
						String column = entry.getKey();
						for(Map.Entry<String, Object> entry2 : entry.getValue().entrySet()){
							String key = ToolsString.checkEmpty(entry2.getKey());
							Object value = entry2.getValue();
							put.addColumn(Bytes.toBytes(column), Bytes.toBytes(key), Bytes.toBytes(ToolsString.checkEmpty(value)));
						}
					}
					puts.add(put);
				}
				table.put(puts);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return false;
	}

	/**
	 * 插入多行数据
	 * @param tableName 表名
	 * @param paramMap 多个列族下面的所有列键值对
	 * @return
	 */
	@Override
	public boolean saveRows(String tableName, Map<String, Map<String, Map<String, Object>>> paramMap) {
		return saveRows(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, paramMap);
	}

	/**
	 * 删除某一行
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param rowKey 行key
	 * @return
	 */
	@Override
	public boolean deleteRow(String namespace, String tableName, String rowKey) {
		Table table = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			Delete delete = new Delete(Bytes.toBytes(rowKey));
			table.delete(delete);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return false;
	}

	/**
	 * 删除某一行
	 * @param tableName 表名
	 * @param rowKey 行key
	 * @return
	 */
	@Override
	public boolean deleteRow(String tableName, String rowKey) {
		return deleteRow(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, rowKey);
	}

	/**
	 * 删除多行
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param rowKeys 行key
	 * @return
	 */
	@Override
	public boolean deleteRows(String namespace, String tableName, String... rowKeys) {
		Table table = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			List<Delete> list = new ArrayList<Delete>();
			for(String rowKey : rowKeys){
				Delete delete = new Delete(Bytes.toBytes(rowKey));
				list.add(delete);
			}
			table.delete(list);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return false;
	}

	/**
	 * 删除多行
	 * @param tableName 表名
	 * @param rowKeys 行key
	 * @return
	 */
	@Override
	public boolean deleteRows(String tableName, String... rowKeys) {
		return deleteRows(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, rowKeys);
	}

	/**
	 * 删除以某前缀开头的rowKey记录
	 * @param tableName
	 * @param rowKeyLike
	 * @return
	 */
	@Override
	public int deleteRowsByRowKeyLike(String tableName, String rowKeyLike) {
		Table table = null;
		ResultScanner rs = null;
		try {
			String namespace = PropertiesReader.getInstance().getProperty("hbase_default_namespace");
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			Scan scan = new Scan();
			RowFilter filter = new RowFilter(CompareOp.EQUAL, new SubstringComparator(rowKeyLike));
			scan.setFilter(filter);
			rs = table.getScanner(scan);
			if(rs != null){
				List<Delete> list = new ArrayList<Delete>();
				for(Result r : rs){
					Delete delete = new Delete(r.getRow());
					list.add(delete);
				}
				table.delete(list);
				return list.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return 0;
	}

	/**
	 * 查询某个表的所有记录
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryForList(String namespace, String tableName) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Table table = null;
		ResultScanner rs = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			rs = table.getScanner(new Scan());
			if(rs != null){
				for(Result r : rs){
					Map<String, Object> rowMap = new HashMap<String, Object>(2);
					for(Cell cell : r.listCells()){
						String rowKey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength(), "utf-8");
						String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength(), "utf-8");
						String value = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength(), "utf-8");
						rowMap.put("rowKey", rowKey);
						rowMap.put(qualifier, value);
					}
					result.add(rowMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(rs, table);
		}
		return result;
	}

	/**
	 * 查询某个表的所有记录
	 * @param tableName 表名
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryForList(String tableName) {
		return queryForList(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName);
	}

	/**
	 * 根据rowKey查找某一行记录
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param rowKey 行主键值
	 * @return
	 */
	@Override
	public Map<String, Object> queryByRowKey(String namespace, String tableName, String rowKey) {
		Table table = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			Get get = new Get(Bytes.toBytes(rowKey));
			Result r = table.get(get);
			if(r != null && r.listCells() != null){
				Map<String, Object> rowMap = new HashMap<String, Object>(2);
				for(Cell cell : r.listCells()){
					String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength(), "utf-8");
					String value = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength(), "utf-8");
					rowMap.put("rowKey", rowKey);
					rowMap.put(qualifier, value);
				}
				return rowMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return Collections.EMPTY_MAP;
	}

	/**
	 * 根据rowKey查找某一行记录
	 * @param tableName 表名
	 * @param rowKey 行主键值
	 * @return
	 */
	@Override
	public Map<String, Object> queryByRowKey(String tableName, String rowKey) {
		return queryByRowKey(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, rowKey);
	}

	/**
	 * 批量根据rowKey查找记录
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param family 列族
	 * @param rowKeys 行主键值集合
	 * @return
	 */
	@Override
	public Map<String, Map<String, Object>> queryByRowKeys(String namespace, String tableName, String family, List<String> rowKeys) {
		Table table = null;
		try {
			if(rowKeys == null || rowKeys.isEmpty()){
				return Collections.EMPTY_MAP;
			}
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			List<Get> gets = new ArrayList<Get>();
			for(String rowKey : rowKeys){
				Get get = new Get(Bytes.toBytes(rowKey));
				gets.add(get);
			}

			Result[] rs = table.get(gets);
			if(rs != null && rs.length > 0){
				Map<String, Map<String, Object>> result = new HashMap<String, Map<String,Object>>();
				Map<String, Object> rowMap = null;
				for(Result r : rs){
					if(r != null){
						String rowKey = Bytes.toString(r.getRow());
						List<Cell> rows = r.listCells();
						if(rows != null){
							rowMap = new HashMap<String, Object>(rows.size());
							for(Cell cell : rows){
								String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength(), "utf-8");
								String value = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength(), "utf-8");
								rowMap.put(qualifier, value);
							}
							rowMap.put("rowKey", rowKey);
							result.put(rowKey, rowMap);
						}
					}
				}
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return Collections.EMPTY_MAP;
	}

	/**
	 * 批量根据rowKey查找记录
	 * @param tableName 表名
	 * @param rowKeys 行主键值集合
	 * @return
	 */
	@Override
	public Map<String, Map<String, Object>> queryByRowKeys(String tableName, List<String> rowKeys) {
		return queryByRowKeys(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName,
				PropertiesReader.getInstance().getProperty("hbase_default_family"), rowKeys);
	}

	/**
	 * 根据某一列的值查询多行数据
	 * @param namespace
	 * @param tableName
	 * @param column
	 * @param value
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumnValue(String namespace,
															String tableName, String family, String column, String value, CompareOp compareOp) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Table table = null;
		ResultScanner rs = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(column), compareOp, Bytes.toBytes(value));
			Scan scan = new Scan();
			scan.setFilter(filter);
			rs = table.getScanner(scan);
			if(rs != null){
				for(Result r : rs){
					Map<String, Object> rowMap = new HashMap<String, Object>(2);
					for(Cell cell : r.listCells()){
						String rowKey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength(), "utf-8");
						String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength(), "utf-8");
						String currValue = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength(), "utf-8");
						rowMap.put("rowKey", rowKey);
						rowMap.put(qualifier, currValue);
					}
					result.add(rowMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(rs, table);
		}
		return result;
	}

	/**
	 * 根据某一列的值查询多行数据
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param column 列
	 * @param value 列值
	 * @param compareOp 操作类型
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumnValue(String namespace,
															String tableName, String column, String value, CompareOp compareOp) {
		return queryRowsByColumnValue(namespace, tableName, PropertiesReader.getInstance().getProperty("hbase_default_family"), column, value, compareOp);
	}

	/**
	 * 根据某一列的值查询多行数据
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param family 列族
	 * @param column 列
	 * @param value 列值
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumnValue(String namespace,
															String tableName, String family, String column, String value) {
		return queryRowsByColumnValue(namespace, tableName, family, column, value, CompareOp.EQUAL);
	}

	/**
	 * 根据某一列的值查询多行数据
	 * @param tableName 表名
	 * @param column 列
	 * @param value 列值
	 * @param compareOp 操作类型
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumnValue(String tableName,
															String column, String value, CompareOp compareOp) {
		return queryRowsByColumnValue(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, PropertiesReader.getInstance().getProperty("hbase_default_family"), column, value, compareOp);
	}

	/**
	 * 根据某一列的值查询多行数据
	 * @param tableName 表名
	 * @param family 列族
	 * @param column 列
	 * @param value 列值
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumnValue(String tableName,
															String family, String column, String value) {
		return queryRowsByColumnValue(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, family, column, value, CompareOp.EQUAL);
	}

	/**
	 * 根据某一列的值查询多行数据
	 * @param tableName 表名
	 * @param column 列
	 * @param value 列值
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumnValue(String tableName,
															String column, String value) {
		return queryRowsByColumnValue(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, PropertiesReader.getInstance().getProperty("hbase_default_family"), column, value, CompareOp.EQUAL);
	}

	/**
	 * 根据多列值查询多行数据
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param family 列族
	 * @param paramMap 查询列字段和列值
	 * @param compareOp 操作类型
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumns(String namespace,
														String tableName, String family, Map<String, Object> paramMap,
														CompareOp compareOp) {
		if(paramMap == null || paramMap.isEmpty()){
			return Collections.EMPTY_LIST;
		}

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Table table = null;
		ResultScanner rs = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			if(table != null){
				// 设置filter
				List<Filter> filters = new ArrayList<Filter>();
				for(Map.Entry<String, Object> entry : paramMap.entrySet()){
					Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(entry.getKey()), compareOp, Bytes.toBytes(ToolsString.checkEmpty(entry.getValue())));
					filters.add(filter);
				}
				FilterList filterList = new FilterList(filters);
				Scan scan = new Scan();
				scan.setFilter(filterList);
				rs = table.getScanner(scan);
				if(rs != null){
					for(Result r : rs){
						Map<String, Object> rowMap = new HashMap<String, Object>(2);
						for(Cell cell : r.listCells()){
							String rowKey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength(), "utf-8");
							String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength(), "utf-8");
							String currValue = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength(), "utf-8");
							rowMap.put("rowKey", rowKey);
							rowMap.put(qualifier, currValue);
						}
						result.add(rowMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(rs, table);
		}
		return result;
	}

	/**
	 * 根据多列值查询多行数据
	 * @param tableName 表名
	 * @param family 列族
	 * @param paramMap 查询列字段和列值
	 * @param compareOp 操作类型
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumns(String tableName, String family, Map<String, Object> paramMap,
														CompareOp compareOp) {
		return queryRowsByColumns(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, family, paramMap, compareOp);
	}

	/**
	 * 根据多列值查询多行数据
	 * @param tableName 表名
	 * @param paramMap 查询列字段和列值
	 * @param compareOp 操作类型
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumns(String tableName, Map<String, Object> paramMap,
														CompareOp compareOp) {
		return queryRowsByColumns(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, PropertiesReader.getInstance().getProperty("hbase_default_family"), paramMap, compareOp);
	}

	/**
	 * 根据多列值查询多行数据
	 * @param tableName 表名
	 * @param paramMap 查询列字段和列值
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryRowsByColumns(String tableName, Map<String, Object> paramMap) {
		return queryRowsByColumns(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, PropertiesReader.getInstance().getProperty("hbase_default_family"), paramMap, CompareOp.EQUAL);
	}

	/**
	 * 根据rowKey来模糊匹配记录
	 * @param namespace
	 * @param tableName
	 * @param rowKeyLike
	 * @return
	 */
	@Override
	public List<JSONObject> queryRowsByRowKeyLike(String namespace, String tableName, String rowKeyLike) {
		List<JSONObject> result = null;
		Table table = null;
		ResultScanner rs = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			Scan scan = new Scan();
			RowFilter filter = new RowFilter(CompareOp.EQUAL, new SubstringComparator(rowKeyLike));
			scan.setFilter(filter);
			rs = table.getScanner(scan);
			if(rs != null){
				result = new ArrayList<JSONObject>();
				for(Result r : rs){
					JSONObject rowMap = new JSONObject();
					for(Cell cell : r.listCells()){
						String rowKey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength(), "utf-8");
						String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength(), "utf-8");
						String currValue = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength(), "utf-8");
						rowMap.put("rowKey", rowKey);
						rowMap.put(qualifier, currValue);
					}
					result.add(rowMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return result;
	}

	/**
	 * 根据rowKey来模糊匹配记录
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param rowKeyLikes 多个RowKey
	 * @return listJson
	 */
	public List<JSONObject> queryRowsByRowKeyLike(String namespace, String tableName, List<String> rowKeyLikes) {
		return queryRowsByRowKeyLike(namespace,tableName,rowKeyLikes,false);
	}

	/**
	 * 根据rowKey来模糊匹配记录
	 * @param tableName 表名
	 * @param rowKeyLikes 多个RowKey
	 * @return listJson
	 */
	@Override
	public List<JSONObject> queryRowsByRowKeyLike(String tableName, List<String> rowKeyLikes) {
		return queryRowsByRowKeyLike(PropertiesReader.getInstance().getProperty("hbase_default_namespace"),tableName,rowKeyLikes,false);
	}

	/**
	 * 根据rowKey来模糊匹配记录
	 * @param tableName 表名
	 * @param rowKeyLikes 多个RowKey
	 * @param isRowKeyEncrypy 是否对查询的RowKey加密
	 * @return listJson
	 */
	@Override
	public List<JSONObject> queryRowsByRowKeyLike(String tableName, List<String> rowKeyLikes,Boolean isRowKeyEncrypy) {
		return queryRowsByRowKeyLike(PropertiesReader.getInstance().getProperty("hbase_default_namespace"),tableName,rowKeyLikes,isRowKeyEncrypy);
	}

	/**
	 * 根据rowKey来模糊匹配记录
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param rowKeyLikes 多个RowKey
	 * @param isRowKeyEncrypy 是否对查询的RowKey加密
	 * @return listJson
	 */
	@Override
	public List<JSONObject> queryRowsByRowKeyLike(String namespace, String tableName, List<String> rowKeyLikes,Boolean isRowKeyEncrypy) {
		List<JSONObject> result = null;
		Table table = null;
		try {
			if(rowKeyLikes == null || rowKeyLikes.isEmpty()){
				return Collections.EMPTY_LIST;
			}
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			List<Get> gets = new ArrayList<Get>();
			for(String rowKey : rowKeyLikes){
				Get get;
				if(isRowKeyEncrypy){
					get = new Get(Bytes.toBytes(ToolsEncrypt.getMD5Encrypt(rowKey)));
				}else{
					get = new Get(Bytes.toBytes(rowKey));
				}
				gets.add(get);
			}

			Result[] rs = table.get(gets);
			if(rs != null && rs.length > 0){
				result = new ArrayList<JSONObject>();
				for(Result r : rs){
					List<Cell> cells = r.listCells();
					if(cells == null || cells.isEmpty()) continue;
					JSONObject rowMap = new JSONObject();
					for(Cell cell : cells){
						String rowKey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength(), "utf-8");
						String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength(), "utf-8");
						String currValue = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength(), "utf-8");
						rowMap.put("rowKey", rowKey);
						rowMap.put(qualifier, currValue);
					}
					result.add(rowMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return result;
	}

	/**
	 * 根据rowKey来模糊匹配记录
	 * @param tableName
	 * @param rowKeyLike
	 * @return
	 */
	@Override
	public List<JSONObject> queryRowsByRowKeyLike(String tableName, String rowKeyLike) {
		return queryRowsByRowKeyLike(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName, rowKeyLike);
	}

	/**
	 * 根据rowKey来匹配记录
	 * @param namespace 命名空间
	 * @param tableName 表名
	 * @param family 列族
	 * @param startRow 开始索引
	 * @param endRow 结束索引
	 * @return
	 */
	public Map<String, Object> queryRows(String namespace, String tableName, String family, byte[] startRow, byte[] endRow){
		Map<String, Object> result = null;
		Table table = null;
		ResultScanner rs = null;
		try {
			TableName tableNameBean = TableName.valueOf(namespace + ":" + tableName);
			table = _connection.getTable(tableNameBean);
			endRow[endRow.length - 1]++;
			Scan scan = new Scan(startRow, endRow);
			rs = table.getScanner(scan);
			if(rs != null){
				for(Result r : rs){
					List<Cell> list = r.listCells();
					if(list == null || list.isEmpty()) continue;
					result = new HashMap<String, Object>();
					for(Cell cell : list){
						String rowKey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength(), "utf-8");
						String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength(), "utf-8");
						String currValue = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength(), "utf-8");
						result.put("rowKey", rowKey);
						result.put(qualifier, currValue);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ToolsIO.closeStream(table);
		}
		return result;
	}

	/**
	 * 根据rowKey来匹配记录
	 * @param tableName 表名
	 * @param startRow 开始索引
	 * @param endRow 结束索引
	 * @return
	 */
	@Override
	public Map<String, Object> queryRows(String tableName, byte[] startRow, byte[] endRow){
		return queryRows(PropertiesReader.getInstance().getProperty("hbase_default_namespace"), tableName,
				PropertiesReader.getInstance().getProperty("hbase_default_family"), startRow, endRow);
	}


}
