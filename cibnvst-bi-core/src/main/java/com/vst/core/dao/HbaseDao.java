package com.vst.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.json.simple.JSONObject;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-27 下午04:51:56
 * @decription
 * @version
 */
public interface HbaseDao {

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
    boolean createTable(String namespace, String tableName, String[] columns, boolean isReplaceExistTable, int timeToLive, boolean openCache);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @param columns 元素名
     * @param isReplaceExistTable 是否替换已经存在的表
     * @param timeToLive 设置最大存活时间，单位是秒
     * @return
     */
    boolean createTable(String namespace, String tableName, String[] columns, boolean isReplaceExistTable, int timeToLive);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @param columns 元素名
     * @param timeToLive 设置最大存活时间，单位是秒
     * @return
     */
    boolean createTable(String namespace, String tableName, String[] columns, int timeToLive);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @param columns 元素名
     * @param isReplaceExistTable 是否替换已经存在的表
     * @return
     */
    boolean createTable(String namespace, String tableName, String[] columns, boolean isReplaceExistTable);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @param columns 元素名
     * @return
     */
    boolean createTable(String namespace, String tableName, String[] columns);

    /**
     * 创建表方法
     * @param tableName 表名
     * @param columns 元素名
     * @param isReplaceExistTable 是否替换已经存在的表
     * @param timeToLive 设置最大存活时间，单位是秒
     * @return
     */
    boolean createTable(String tableName, String[] columns, boolean isReplaceExistTable, int timeToLive);

    /**
     * 创建表方法
     * @param tableName 表名
     * @param columns 元素名
     * @param timeToLive 设置最大存活时间，单位是秒
     * @return
     */
    boolean createTable(String tableName, String[] columns, int timeToLive);

    /**
     * 创建表方法
     * @param tableName 表名
     * @param columns 元素名
     * @param isReplaceExistTable 是否替换已经存在的表
     * @return
     */
    boolean createTable(String tableName, String[] columns, boolean isReplaceExistTable);

    /**
     * 创建表方法
     * @param tableName 表名
     * @param columns 元素名
     * @return
     */
    boolean createTable(String tableName, String[] columns);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @param isReplaceExistTable 是否替换已经存在的表
     * @param timeToLive 设置最大存活时间，单位是秒
     * @param openCache 是否使用缓存
     * @return
     */
    boolean createTable(String namespace, String tableName, boolean isReplaceExistTable, int timeToLive, boolean openCache);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @param isReplaceExistTable 是否替换已经存在的表
     * @param timeToLive 设置最大存活时间，单位是秒
     * @return
     */
    boolean createTable(String namespace, String tableName, boolean isReplaceExistTable, int timeToLive);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @return
     */
    boolean createTable(String namespace, String tableName, boolean isReplaceExistTable);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @param timeToLive 设置最大存活时间，单位是秒
     * @return
     */
    boolean createTable(String namespace, String tableName, int timeToLive);

    /**
     * 创建表方法
     * @param namespace 命名空间
     * @param tableName 表名
     * @return
     */
    boolean createTable(String namespace, String tableName);

    /**
     * 创建表方法
     * @param tableName 表名
     * @return
     */
    boolean createTable(String tableName);

    /**
     * 删除一张表
     * @param namespace 命名空间
     * @param tableName 表名
     * @return
     */
    boolean dropTable(String namespace, String tableName);

    /**
     * 删除一张表
     * @param tableName 表名
     * @return
     */
    boolean dropTable(String tableName);

    /**
     * 删除多张表
     * @param namespace
     * @param tableName
     * @return
     */
    boolean dropTables(String namespace, String... tableName);

    /**
     * 删除多张表
     * @param tableName
     * @return
     */
    boolean dropTables(String... tableName);

    /**
     * 插入一个列族的多个列数据
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKey key值
     * @param paramMap 多个列族下面的所有列键值对
     * @return
     */
    boolean saveRow(String namespace, String tableName, String rowKey, Map<String, Map<String, Object>> paramMap);

    /**
     * 插入一个列族的多个列数据
     * @param tableName 表名
     * @param rowKey key值
     * @param paramMap 多个列族下面的所有列键值对
     * @return
     */
    boolean saveRow(String tableName, String rowKey, Map<String, Map<String, Object>> paramMap);

    /**
     * 插入一个列族的多个列数据
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKey key值
     * @param column 列族
     * @param paramMap 列族下面的所有列键值对
     * @return
     */
    boolean saveRow(String namespace, String tableName, String rowKey, String column, Map<String, Object> paramMap);

    /**
     * 插入一个列族的多个列数据
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKey key值
     * @param column 列族
     * @param paramMap 列族下面的所有列键值对
     * @return
     */
    boolean saveOrUpdate(String tableName, String rowKey, String column, Map<String, Object> paramMap);

    /**
     * 插入多行数据
     * @param namespace 命名空间
     * @param tableName 表名
     * @param paramMap 多个列族下面的所有列键值对
     * @return
     */
    boolean saveRows(String namespace, String tableName, Map<String, Map<String, Map<String, Object>>> paramMap);

    /**
     * 插入多行数据
     * @param tableName 表名
     * @param paramMap 多个列族下面的所有列键值对
     * @return
     */
    boolean saveRows(String tableName, Map<String, Map<String, Map<String, Object>>> paramMap);

    /**
     * 删除某一行
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKey 行key
     * @return
     */
    boolean deleteRow(String namespace, String tableName, String rowKey);

    /**
     * 删除某一行
     * @param tableName 表名
     * @param rowKey 行key
     * @return
     */
    boolean deleteRow(String tableName, String rowKey);

    /**
     * 删除多行
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKeys 行key
     * @return
     */
    boolean deleteRows(String namespace, String tableName, String... rowKeys);

    /**
     * 删除多行
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKeys 行key
     * @return
     */
    boolean deleteRows(String tableName, String... rowKeys);

    /**
     * 删除以某前缀开头的rowKey记录
     * @param tableName
     * @param rowKeyLike
     * @return
     */
    int deleteRowsByRowKeyLike(String tableName, String rowKeyLike);

    /**
     * 查询某个表的所有记录
     * @param namespace 命名空间
     * @param tableName 表名
     * @return
     */
    List<Map<String, Object>> queryForList(String namespace, String tableName);

    /**
     * 查询某个表的所有记录
     * @param tableName 表名
     * @return
     */
    List<Map<String, Object>> queryForList(String tableName);

    /**
     * 根据rowKey查找某一行记录
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKey 行主键值
     * @return
     */
    Map<String, Object> queryByRowKey(String namespace, String tableName, String rowKey);

    /**
     * 根据rowKey查找某一行记录
     * @param tableName 表名
     * @param rowKey 行主键值
     * @return
     */
    Map<String, Object> queryByRowKey(String tableName, String rowKey);

    /**
     * 批量根据rowKey查找记录
     * @param namespace 命名空间
     * @param tableName 表名
     * @param family 列族
     * @param rowKeys 行主键值集合
     * @return
     */
    Map<String, Map<String, Object>> queryByRowKeys(String namespace, String tableName, String family, List<String> rowKeys);

    /**
     * 批量根据rowKey查找记录
     * @param tableName 表名
     * @param rowKeys 行主键值集合
     * @return
     */
    Map<String, Map<String, Object>> queryByRowKeys(String tableName, List<String> rowKeys);

    /**
     * 根据某一列的值查询多行数据
     * @param namespace 命名空间
     * @param tableName 表名
     * @param family 列族
     * @param column 列
     * @param value 列值
     * @param compareOp 操作类型
     * @return
     */
    List<Map<String, Object>> queryRowsByColumnValue(String namespace, String tableName, String family, String column, String value, CompareOp compareOp);

    /**
     * 根据某一列的值查询多行数据
     * @param namespace 命名空间
     * @param tableName 表名
     * @param column 列
     * @param value 列值
     * @param compareOp 操作类型
     * @return
     */
    List<Map<String, Object>> queryRowsByColumnValue(String namespace, String tableName, String column, String value, CompareOp compareOp);

    /**
     * 根据某一列的值查询多行数据
     * @param namespace 命名空间
     * @param tableName 表名
     * @param family 列族
     * @param column 列
     * @param value 列值
     * @return
     */
    List<Map<String, Object>> queryRowsByColumnValue(String namespace, String tableName, String family, String column, String value);

    /**
     * 根据某一列的值查询多行数据
     * @param tableName 表名
     * @param column 列
     * @param value 列值
     * @param compareOp 操作类型
     * @return
     */
    List<Map<String, Object>> queryRowsByColumnValue(String tableName, String column, String value, CompareOp compareOp);

    /**
     * 根据某一列的值查询多行数据
     * @param tableName 表名
     * @param family 列族
     * @param column 列
     * @param value 列值
     * @return
     */
    List<Map<String, Object>> queryRowsByColumnValue(String tableName, String family, String column, String value);

    /**
     * 根据某一列的值查询多行数据
     * @param tableName 表名
     * @param column 列
     * @param value 列值
     * @return
     */
    List<Map<String, Object>> queryRowsByColumnValue(String tableName, String column, String value);

    /**
     * 根据多列值查询多行数据
     * @param namespace 命名空间
     * @param tableName 表名
     * @param family 列族
     * @param paramMap 查询列字段和列值
     * @param compareOp 操作类型
     * @return
     */
    List<Map<String, Object>> queryRowsByColumns(String namespace, String tableName, String family, Map<String, Object> paramMap, CompareOp compareOp);

    /**
     * 根据多列值查询多行数据
     * @param tableName 表名
     * @param family 列族
     * @param paramMap 查询列字段和列值
     * @param compareOp 操作类型
     * @return
     */
    List<Map<String, Object>> queryRowsByColumns(String tableName, String family, Map<String, Object> paramMap, CompareOp compareOp);

    /**
     * 根据多列值查询多行数据
     * @param tableName 表名
     * @param paramMap 查询列字段和列值
     * @param compareOp 操作类型
     * @return
     */
    List<Map<String, Object>> queryRowsByColumns(String tableName, Map<String, Object> paramMap, CompareOp compareOp);

    /**
     * 根据多列值查询多行数据
     * @param tableName 表名
     * @param paramMap 查询列字段和列值
     * @return
     */
    List<Map<String, Object>> queryRowsByColumns(String tableName, Map<String, Object> paramMap);

    /**
     * 根据rowKey来模糊匹配记录
     * @param namespace
     * @param tableName
     * @param rowKeyLike
     * @return
     */
    List<JSONObject> queryRowsByRowKeyLike(String namespace, String tableName, String rowKeyLike);

    /**
     * 根据rowKey来查找数据返回ListJSON
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKeyLikes 多个RowKey
     * @return
     */
    List<JSONObject> queryRowsByRowKeyLike(String namespace, String tableName, List<String> rowKeyLikes);

    /**
     * 根据rowKey来模糊匹配记录
     * @param tableName 表名
     * @param rowKeyLikes 多个RowKey
     * @param isRowKeyEncrypy 是否对RowKey加密
     * @return listJson
     */
    List<JSONObject> queryRowsByRowKeyLike(String tableName, List<String> rowKeyLikes, Boolean isRowKeyEncrypy);

    /**
     * 根据rowKey来模糊匹配记录
     * @param namespace 命名空间
     * @param tableName 表名
     * @param rowKeyLikes 多个RowKey
     * @param isRowKeyEncrypy 是否对RowKey加密
     * @return listJson
     */
    List<JSONObject> queryRowsByRowKeyLike(String namespace, String tableName, List<String> rowKeyLikes, Boolean isRowKeyEncrypy);

    /**
     * 根据rowKey来模糊匹配记录
     * @param tableName
     * @param rowKeyLike
     * @return
     */
    List<JSONObject> queryRowsByRowKeyLike(String tableName, String rowKeyLike);


    /**
     * 批量根据rowKey查找记录
     * @param tableName 表名
     * @param rowKeyLikes 行主键值集合
     * @return JSONObject
     */
    List<JSONObject> queryRowsByRowKeyLike(String tableName, List<String> rowKeyLikes);

    /**
     * 根据rowKey来匹配记录
     * @param namespace 命名空间
     * @param tableName 表名
     * @param family 列族
     * @param startRow 开始索引
     * @param endRow 结束索引
     * @return
     */
    Map<String, Object> queryRows(String namespace, String tableName, String family, byte[] startRow, byte[] endRow);

    /**
     * 根据rowKey来匹配记录
     * @param tableName 表名
     * @param startRow 开始索引
     * @param endRow 结束索引
     * @return
     */
    Map<String, Object> queryRows(String tableName, byte[] startRow, byte[] endRow);
}
