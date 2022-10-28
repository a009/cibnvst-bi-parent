package com.vst.common.pojo;

/**
 * spark任务表
 * @author lhp
 * @date 2017-11-20 上午11:44:19
 * @version
 */
public class VstSql {
	/**
	 * 主键id，随机5位数，唯一
	 */
	private String vst_sql_id;
	
	/**
	 * sql父id，默认0表示是父级
	 */
	private String vst_sql_pid;
	
	/**
	 * 任务名称
	 */
	private String vst_sql_name;
	
	/**
	 * 任务类型，1：实时任务，2：离线任务，3：补充数据任务
	 */
	private Integer vst_sql_type;
	
	/**
	 * topic
	 */
	private String vst_sql_topic;
	
	/**
	 * 任务间隔，单位：秒
	 */
	private Integer vst_sql_interval;
	
	/**
	 * sql数据来源
	 */
	private String vst_sql_data_path;
	
	/**
	 * spark临时表名
	 */
	private String vst_sql_temp_table;
	
	/**
	 * 计算结果保存数据源，1：hbase，2：mysql，3：本地文件，4：hdfs文件
	 */
	private Integer vst_sql_db;
	
	/**
	 * 计算结果表名
	 */
	private String vst_sql_table;
	
	/**
	 * rowkey的组装元素,只有当保存数据源是hbase的时候才有意义，如果有多个，用英文逗号隔开
	 */
	private String vst_sql_rowkeyColumn;
	
	/**
	 * 任务描述
	 */
	private String vst_sql_desc;
	
	/**
	 * 上一次执行时间，13位毫秒数
	 */
	private Long vst_sql_runtime;
	
	/**
	 * 上一次执行位置，格式：yyyyMMdd|HH|mm
	 */
	private String vst_sql_run_position;
	
	/**
	 * sql运行模式，1：并行，2：串行，默认1
	 */
	private Integer vst_sql_run_model;
	
	/**
	 * 是否格式化属性，1：是，2：否，默认2
	 */
	private Integer vst_sql_is_format;
	
	/**
	 * sparksql查询关联key，若有多个，用|隔开
	 */
	private String vst_sql_joinKeys;
	
	/**
	 * 优先级：默认为-1，表示普通任务没有优先级可言，数值越大优先级越高
	 */
	private Integer vst_sql_priority;
	
	/**
	 * 排序值，越大越靠前，默认0
	 */
	private Integer vst_sql_index;
	
	/**
	 * 状态，1：正常，2：禁用
	 */
	private Integer vst_sql_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_sql_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_sql_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_sql_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_sql_updator;
	
	/**
	 * 备注
	 */
	private String vst_sql_summary;

	public String getVst_sql_id() {
		return vst_sql_id;
	}

	public void setVst_sql_id(String vstSqlId) {
		vst_sql_id = vstSqlId;
	}

	public String getVst_sql_pid() {
		return vst_sql_pid;
	}

	public void setVst_sql_pid(String vstSqlPid) {
		vst_sql_pid = vstSqlPid;
	}

	public String getVst_sql_name() {
		return vst_sql_name;
	}

	public void setVst_sql_name(String vstSqlName) {
		vst_sql_name = vstSqlName;
	}

	public Integer getVst_sql_type() {
		return vst_sql_type;
	}

	public void setVst_sql_type(Integer vstSqlType) {
		vst_sql_type = vstSqlType;
	}

	public String getVst_sql_topic() {
		return vst_sql_topic;
	}

	public void setVst_sql_topic(String vstSqlTopic) {
		vst_sql_topic = vstSqlTopic;
	}

	public Integer getVst_sql_interval() {
		return vst_sql_interval;
	}

	public void setVst_sql_interval(Integer vstSqlInterval) {
		vst_sql_interval = vstSqlInterval;
	}

	public String getVst_sql_data_path() {
		return vst_sql_data_path;
	}

	public void setVst_sql_data_path(String vstSqlDataPath) {
		vst_sql_data_path = vstSqlDataPath;
	}

	public String getVst_sql_temp_table() {
		return vst_sql_temp_table;
	}

	public void setVst_sql_temp_table(String vstSqlTempTable) {
		vst_sql_temp_table = vstSqlTempTable;
	}

	public Integer getVst_sql_db() {
		return vst_sql_db;
	}

	public void setVst_sql_db(Integer vstSqlDb) {
		vst_sql_db = vstSqlDb;
	}

	public String getVst_sql_table() {
		return vst_sql_table;
	}

	public void setVst_sql_table(String vstSqlTable) {
		vst_sql_table = vstSqlTable;
	}

	public String getVst_sql_rowkeyColumn() {
		return vst_sql_rowkeyColumn;
	}

	public void setVst_sql_rowkeyColumn(String vstSqlRowkeyColumn) {
		vst_sql_rowkeyColumn = vstSqlRowkeyColumn;
	}

	public String getVst_sql_desc() {
		return vst_sql_desc;
	}

	public void setVst_sql_desc(String vstSqlDesc) {
		vst_sql_desc = vstSqlDesc;
	}

	public Long getVst_sql_runtime() {
		return vst_sql_runtime;
	}

	public void setVst_sql_runtime(Long vstSqlRuntime) {
		vst_sql_runtime = vstSqlRuntime;
	}

	public String getVst_sql_run_position() {
		return vst_sql_run_position;
	}

	public void setVst_sql_run_position(String vstSqlRunPosition) {
		vst_sql_run_position = vstSqlRunPosition;
	}

	public Integer getVst_sql_run_model() {
		return vst_sql_run_model;
	}

	public void setVst_sql_run_model(Integer vstSqlRunModel) {
		vst_sql_run_model = vstSqlRunModel;
	}

	public Integer getVst_sql_is_format() {
		return vst_sql_is_format;
	}

	public void setVst_sql_is_format(Integer vstSqlIsFormat) {
		vst_sql_is_format = vstSqlIsFormat;
	}

	public String getVst_sql_joinKeys() {
		return vst_sql_joinKeys;
	}

	public void setVst_sql_joinKeys(String vstSqlJoinKeys) {
		vst_sql_joinKeys = vstSqlJoinKeys;
	}

	public Integer getVst_sql_priority() {
		return vst_sql_priority;
	}

	public void setVst_sql_priority(Integer vstSqlPriority) {
		vst_sql_priority = vstSqlPriority;
	}

	public Integer getVst_sql_index() {
		return vst_sql_index;
	}

	public void setVst_sql_index(Integer vstSqlIndex) {
		vst_sql_index = vstSqlIndex;
	}

	public Integer getVst_sql_state() {
		return vst_sql_state;
	}

	public void setVst_sql_state(Integer vstSqlState) {
		vst_sql_state = vstSqlState;
	}

	public Long getVst_sql_addtime() {
		return vst_sql_addtime;
	}

	public void setVst_sql_addtime(Long vstSqlAddtime) {
		vst_sql_addtime = vstSqlAddtime;
	}

	public String getVst_sql_creator() {
		return vst_sql_creator;
	}

	public void setVst_sql_creator(String vstSqlCreator) {
		vst_sql_creator = vstSqlCreator;
	}

	public Long getVst_sql_uptime() {
		return vst_sql_uptime;
	}

	public void setVst_sql_uptime(Long vstSqlUptime) {
		vst_sql_uptime = vstSqlUptime;
	}

	public String getVst_sql_updator() {
		return vst_sql_updator;
	}

	public void setVst_sql_updator(String vstSqlUpdator) {
		vst_sql_updator = vstSqlUpdator;
	}

	public String getVst_sql_summary() {
		return vst_sql_summary;
	}

	public void setVst_sql_summary(String vstSqlSummary) {
		vst_sql_summary = vstSqlSummary;
	}

	@Override
	public String toString() {
		return "VstSql [vst_sql_addtime=" + vst_sql_addtime
				+ ", vst_sql_creator=" + vst_sql_creator
				+ ", vst_sql_data_path=" + vst_sql_data_path + ", vst_sql_db="
				+ vst_sql_db + ", vst_sql_desc=" + vst_sql_desc
				+ ", vst_sql_id=" + vst_sql_id + ", vst_sql_index="
				+ vst_sql_index + ", vst_sql_interval=" + vst_sql_interval
				+ ", vst_sql_is_format=" + vst_sql_is_format
				+ ", vst_sql_joinKeys=" + vst_sql_joinKeys + ", vst_sql_name="
				+ vst_sql_name + ", vst_sql_pid=" + vst_sql_pid
				+ ", vst_sql_priority=" + vst_sql_priority
				+ ", vst_sql_rowkeyColumn=" + vst_sql_rowkeyColumn
				+ ", vst_sql_run_model=" + vst_sql_run_model
				+ ", vst_sql_run_position=" + vst_sql_run_position
				+ ", vst_sql_runtime=" + vst_sql_runtime + ", vst_sql_state="
				+ vst_sql_state + ", vst_sql_summary=" + vst_sql_summary
				+ ", vst_sql_table=" + vst_sql_table + ", vst_sql_temp_table="
				+ vst_sql_temp_table + ", vst_sql_topic=" + vst_sql_topic
				+ ", vst_sql_type=" + vst_sql_type + ", vst_sql_updator="
				+ vst_sql_updator + ", vst_sql_uptime=" + vst_sql_uptime + "]";
	}
}
