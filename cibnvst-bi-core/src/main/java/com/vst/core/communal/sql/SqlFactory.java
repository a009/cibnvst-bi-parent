package com.vst.core.communal.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.json.simple.JSONObject;

import com.vst.common.tools.string.ToolsString;
import com.vst.core.communal.sql.sqlFilter.FilterChain;
import com.vst.core.communal.type.ColumnType;
import com.vst.core.communal.type.OperateType;
import com.vst.core.communal.type.SqlType;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-25 上午10:25:03
 * @decription
 * @version 
 */
public class SqlFactory implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8614403301701466409L;

	/**
	 * 空格
	 */
	private static final String BLANK = " ";
	
	/**
	 * 主键id，随机5位数，唯一
	 */
	private String sqlId;
	
	/**
	 * sql父级id
	 */
	private String sqlPid;
	
	/**
	 * 任务名称
	 */
	private String sqlName;
	
	/**
	 * 任务类型，1：实时任务，2：离线任务，3：补充数据任务
	 */
	private int sqlType;
	
	/**
	 * sql相关的topic
	 */
	private String sqlTopic;
	
	/**
	 * 任务间隔，单位：秒
	 */
	private int sqlInterval;
	
	/**
	 * sql任务的数据来源路径
	 */
	private String sqlDataPath;
	
	/**
	 * sparkSql的临时表名
	 */
	private String sqlTempTableName;
	
	/**
	 * sql计算结果保存的数据源，1：hbase，2：mysql，3：本地文件，4：hdfs文件
	 */
	private int sqlDB;
	
	/**
	 * 计算结果表名
	 */
	private String sqlTableName;
	
	/**
	 * rowkey的组装元素,只有当保存数据源是hbase的时候才有意义
	 */
	private String[] sqlRowKeyColumn;
	
	/**
	 * 任务描述
	 */
	private String sqlDesc;
	
	/**
	 * 上一次执行时间，13位毫秒数
	 */
	private long sqlRuntime;
	
	/**
	 * 上一次的执行位置,格式：yyyyMMdd|HH|mm
	 */
	private String[] sqlRunPosition;
	
	/**
	 * sql关联key组合，如果有多个，用|隔开
	 */
	private String sqlJoinKeys;

    /**
     * 优先级，-1普通任务，非-1的任务数值越大越先执行
     */
    private int priority;
	
	/**
	 * sql运行模式，1：并行，2：串行，默认1
	 */
	private int sqlRunModel;
	
	/**
	 * 是否修复数据
	 */
	private boolean sqlIsFormat;

	/**
	 * sql列集合
	 */
	private List<Column> columns;
	
	/**
	 * 分组集合
	 */
	private List<GroupBy> groupBys;
	
	/**
	 * 排序集合
	 */
	private List<OrderBy> orderBys;
	
	/**
	 * 过滤条件集合
	 */
	private List<FilterChain> filterList;
	
	/**
	 * 子sql集合
	 */
	private List<SqlFactory> childrenSqlList;
	
	/**
	 * sql保存语句集合
	 */
	private SaveSqlInfo saveSqlInfo;
	
	/**
	 * 无参构造器
	 */
	public SqlFactory() {
		
	}

	/**
	 * 获取spark列元素
	 * @return
	 */
	public StructType getSchema(){
		List<StructField> fields = new ArrayList<StructField>();
		if(columns != null){
			for(Column c : columns){
				String name = c.getName();
				// 如果是自定义列，需要格式如下：max(cid)[@!@]cid，其中第一列是函数列，第二列是真正的列属性
				if(c.getOperateType() == OperateType.CUSTOM.getType()){
					String[] values = name.split("\\[@!@]");
					if(values.length != 2) continue;
					name = values[1];
				}else if(c.getOperateType() == OperateType.COUNT.getType()){
					continue;// count列不是meta属性
				}
				if(c.getDataType() == ColumnType.String.getType()){
					fields.add(DataTypes.createStructField(name, DataTypes.StringType, true));
				}else if(c.getDataType() == ColumnType.Int.getType()){
					fields.add(DataTypes.createStructField(name, DataTypes.LongType, true));
				}else if(c.getDataType() == ColumnType.Float.getType()){
					fields.add(DataTypes.createStructField(name, DataTypes.DoubleType, true));
				}else if(c.getDataType() == ColumnType.BOOLEAN.getType()){
					fields.add(DataTypes.createStructField(name, DataTypes.BooleanType, true));
				}
			}
		}
		return DataTypes.createStructType(fields);
	}
	
	/**
	 * 生成sparkSQL
	 * @return
	 */
	public String toSql(){
		if(sqlType == SqlType.P_PULLDATA.getType()){
			return "";
		}
		StringBuilder sb = new StringBuilder("select").append(BLANK);
		// 先拼接列元素
		if(columns != null && !columns.isEmpty()){
			for(int i = 0, l = columns.size(); i < l; i++){
				Column c = columns.get(i);
				if(c.getOperateType() == OperateType.SIMPLE.getType()){
					if(c.getDataType() == ColumnType.Int.getType()){
						sb.append("castLong(").append(c.getName()).append(")").append(BLANK);
					}else if(c.getDataType() == ColumnType.Float.getType()){
						sb.append("cast(").append(c.getName()).append(" as double)").append(BLANK);
					}else{
						sb.append(c.getName()).append(BLANK);
					}
				}else if(c.getOperateType() == OperateType.SUM.getType()){
					if(c.getDataType() == ColumnType.Int.getType()){
						sb.append("sum(castLong(").append(c.getName()).append("))").append(BLANK);
					}else if(c.getDataType() == ColumnType.Float.getType()){
						sb.append("sum(cast(").append(c.getName()).append("as double))").append(BLANK);
					}else{
						sb.append("sum(").append(c.getName()).append(")").append(BLANK);
					}
				}else if(c.getOperateType() == OperateType.DISTINCT_COUNT.getType()){
					sb.append("count(distinct(").append(c.getName()).append("))").append(BLANK);
				}else if(c.getOperateType() == OperateType.FIRST.getType()){
					sb.append("first(").append(c.getName()).append(")").append(BLANK);
				}else if(c.getOperateType() == OperateType.CUSTOM.getType()){
					String name = c.getName();
					// 如果是自定义列，需要格式如下：max(cid)[@!@]cid，其中第一列是函数列，第二列是真正的列属性
					String[] values = name.split("\\[@!@]");
//					if(values.length != 2) {
//						throw new RuntimeException("custom column format is error! columnName = [" + name + "]");
//					}
					sb.append(values[0]).append(BLANK);
				}else if(c.getOperateType() == OperateType.COUNT.getType()){
					sb.append("castLong(").append(c.getName()).append(")").append(BLANK);
				}
				
				if(!ToolsString.isEmpty(c.getAlias())){
					sb.append("as ").append(c.getAlias()).append(",").append(BLANK);
				}else{
					sb.append(",").append(BLANK);
				}
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append("from ").append(sqlTempTableName).append(BLANK);
		
		// 然后拼接分组元素
		if(groupBys != null && !groupBys.isEmpty()){
			sb.append("group by").append(BLANK);
			for(int i = 0, l = groupBys.size(); i < l; i++){
				GroupBy g = groupBys.get(i);
				if(i != l - 1){
					sb.append(g.getName()).append(",").append(BLANK);
				}else{
					sb.append(g.getName()).append(BLANK);
				}
			}
		}
		
		// 最后拼接排序元素
		if(orderBys != null && !orderBys.isEmpty()){
			sb.append("order by").append(BLANK);
			for(int i = 0, l = orderBys.size(); i < l; i++){
				OrderBy o = orderBys.get(i);
				if(i != l - 1){
					sb.append(o.getName()).append(BLANK).append(o.getSort()).append(",").append(BLANK);
				}else{
					sb.append(o.getName()).append(BLANK).append(o.getSort());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 拦截方法
	 * @param json
	 * @return 通过拦截器校验返回true，否则返回false
	 */
	public boolean doChain(JSONObject json){
		// 默认通过
		boolean flag = true;
		// 筛选器筛选，不通过，直接返回false
		if(filterList != null && !filterList.isEmpty()){
			for(FilterChain filter : filterList){
				if(!filter.doChain(json)){// 只要有一个校验失败的，就停止校验
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}
	
	public String getSqlTypeName() {
		String name = null;
		if(sqlType == SqlType.P_ONLINE.getType()){
			name = "实时计算任务";
		}else if(sqlType == SqlType.P_OFFLINE.getType()){
			name = "离线计算任务";
		}else if(sqlType == SqlType.P_COMPLEMENT.getType()){
			name = "补充数据任务";
		}else if(sqlType == SqlType.P_PULLDATA.getType()){
			name = "拉取数据任务";
		}else if(sqlType == SqlType.C_SUM.getType()){
			name = "累加任务";
		}else if(sqlType == SqlType.C_RATIO.getType()){
			name = "环比任务";
		}
		return name;
	}
	
	public String getSqlTopic() {
		return sqlTopic;
	}

	public void setSqlTopic(String sqlTopic) {
		this.sqlTopic = sqlTopic;
	}

	public int getSqlInterval() {
		return sqlInterval;
	}

	public void setSqlInterval(int sqlInterval) {
		this.sqlInterval = sqlInterval;
	}

	public String getSqlDataPath() {
		return sqlDataPath;
	}

	public void setSqlDataPath(String sqlDataPath) {
		this.sqlDataPath = sqlDataPath;
	}

	public String getSqlTempTableName() {
		return sqlTempTableName;
	}

	public void setSqlTempTableName(String sqlTempTableName) {
		this.sqlTempTableName = sqlTempTableName;
	}
	
	public String getSqlTableName() {
		return sqlTableName;
	}

	public void setSqlTableName(String sqlTableName) {
		this.sqlTableName = sqlTableName;
	}
	
	public String[] getSqlRowKeyColumn() {
		return sqlRowKeyColumn;
	}

	public void setSqlRowKeyColumn(String[] sqlRowKeyColumn) {
		this.sqlRowKeyColumn = sqlRowKeyColumn;
	}

	public String getSqlDesc() {
		return sqlDesc;
	}

	public void setSqlDesc(String sqlDesc) {
		this.sqlDesc = sqlDesc;
	}

	public long getSqlRuntime() {
		return sqlRuntime;
	}

	public void setSqlRuntime(long sqlRuntime) {
		this.sqlRuntime = sqlRuntime;
	}
	
	public String[] getSqlRunPosition() {
		return sqlRunPosition;
	}

	public void setSqlRunPosition(String[] sqlRunPosition) {
		this.sqlRunPosition = sqlRunPosition;
	}
	
	public String getSqlJoinKeys() {
		return sqlJoinKeys;
	}

    public int getPriority() {  return priority; }

    public void setPriority(int priority) { this.priority = priority; }

	public void setSqlJoinKeys(String sqlJoinKeys) {
		this.sqlJoinKeys = sqlJoinKeys;
	}

	public List<Column> getColumns() {
		return columns;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<GroupBy> getGroupBys() {
		return groupBys;
	}

	public void setGroupBys(List<GroupBy> groupBys) {
		this.groupBys = groupBys;
	}

	public List<OrderBy> getOrderBys() {
		return orderBys;
	}

	public void setOrderBys(List<OrderBy> orderBys) {
		this.orderBys = orderBys;
	}

	public List<FilterChain> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<FilterChain> filterList) {
		this.filterList = filterList;
	}

	public String getSqlPid() {
		return sqlPid;
	}

	public void setSqlPid(String sqlPid) {
		this.sqlPid = sqlPid;
	}

	public int getSqlDB() {
		return sqlDB;
	}

	public void setSqlDB(int sqlDB) {
		this.sqlDB = sqlDB;
	}
	
	public List<SqlFactory> getChildrenSqlList() {
		return childrenSqlList;
	}

	public void setChildrenSqlList(List<SqlFactory> childrenSqlList) {
		this.childrenSqlList = childrenSqlList;
	}
	
	public void addSqlFactory(SqlFactory sqlFactory) {
		if(childrenSqlList == null){
			childrenSqlList = new ArrayList<SqlFactory>();
		}
		childrenSqlList.add(sqlFactory);
	}
	
	public SaveSqlInfo getSaveSqlInfo() {
		return saveSqlInfo;
	}

	public void setSaveSqlInfo(SaveSqlInfo saveSqlInfo) {
		this.saveSqlInfo = saveSqlInfo;
	}

	public boolean isSqlIsFormat() {
		return sqlIsFormat;
	}

	public void setSqlIsFormat(boolean sqlIsFormat) {
		this.sqlIsFormat = sqlIsFormat;
	}
	
	public int getSqlRunModel() {
		return sqlRunModel;
	}

	public void setSqlRunModel(int sqlRunModel) {
		this.sqlRunModel = sqlRunModel;
	}

	@Override
	public String toString() {
		return "SqlFactory [childrenSqlList=" + childrenSqlList + ", columns="
				+ columns + ", filterList=" + filterList + ", groupBys="
				+ groupBys + ", orderBys=" + orderBys + ", saveSqlInfo="
				+ saveSqlInfo + ", sqlDB=" + sqlDB + ", sqlDataPath="
				+ sqlDataPath + ", sqlDesc=" + sqlDesc + ", sqlId=" + sqlId
				+ ", sqlInterval=" + sqlInterval + ", sqlIsFormat="
				+ sqlIsFormat + ", sqlJoinKeys=" + sqlJoinKeys + ", sqlPriority="
				+ priority + ", sqlName="
				+ sqlName + ", sqlPid=" + sqlPid + ", sqlRowKeyColumn="
				+ Arrays.toString(sqlRowKeyColumn) + ", sqlRunPosition="
				+ Arrays.toString(sqlRunPosition) + ", sqlRuntime="
				+ sqlRuntime + ", sqlTableName=" + sqlTableName
				+ ", sqlTempTableName=" + sqlTempTableName + ", sqlTopic="
				+ sqlTopic + ", sqlType=" + sqlType + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		
		if(obj == null){
			return false;
		}
		
		if(obj instanceof SqlFactory){
			SqlFactory temp = (SqlFactory) obj;
			if(temp.getSqlId().equals(this.sqlId) && temp.getSqlPid().equals(this.sqlPid)){
				return true;
			}
		}
		return super.equals(obj);
	}
}
