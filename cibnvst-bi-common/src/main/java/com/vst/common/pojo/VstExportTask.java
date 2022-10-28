package com.vst.common.pojo;

/**
 * 导出任务
 * @author lhp
 * @date 2018-4-13 上午10:31:25
 * @version
 */
public class VstExportTask {
	/**
	 * 主键id，随机10位字符，唯一
	 */
	private String vst_task_id;
	
	/**
	 * 模块ID
	 */
	private String vst_module_id;
	
	/**
	 * 导出条件
	 */
	private String vst_task_condition;
	
	/**
	 * 导出表名
	 */
	private String vst_task_table;
	
	/**
	 * 导出字段
	 */
	private String vst_task_columns;
	
	/**
	 * 文件路径
	 */
	private String vst_task_file_path;
	
	/**
	 * 文件名
	 */
	private String vst_task_file_name;
	
	/**
	 * 文件大小，单位：byte
	 */
	private Long vst_task_file_size;
	
	/**
	 * 导出状态：0-进行中，1-已完成，2-异常
	 */
	private Integer vst_task_state;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_task_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_task_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_task_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_task_updator;
	
	/**
	 * 备注
	 */
	private String vst_task_summary;

	public String getVst_task_id() {
		return vst_task_id;
	}

	public void setVst_task_id(String vstTaskId) {
		vst_task_id = vstTaskId;
	}

	public String getVst_module_id() {
		return vst_module_id;
	}

	public void setVst_module_id(String vstModuleId) {
		vst_module_id = vstModuleId;
	}

	public String getVst_task_condition() {
		return vst_task_condition;
	}

	public void setVst_task_condition(String vstTaskCondition) {
		vst_task_condition = vstTaskCondition;
	}

	public String getVst_task_table() {
		return vst_task_table;
	}

	public void setVst_task_table(String vstTaskTable) {
		vst_task_table = vstTaskTable;
	}

	public String getVst_task_columns() {
		return vst_task_columns;
	}

	public void setVst_task_columns(String vstTaskColumns) {
		vst_task_columns = vstTaskColumns;
	}

	public String getVst_task_file_path() {
		return vst_task_file_path;
	}

	public void setVst_task_file_path(String vstTaskFilePath) {
		vst_task_file_path = vstTaskFilePath;
	}

	public String getVst_task_file_name() {
		return vst_task_file_name;
	}

	public void setVst_task_file_name(String vstTaskFileName) {
		vst_task_file_name = vstTaskFileName;
	}

	public Long getVst_task_file_size() {
		return vst_task_file_size;
	}

	public void setVst_task_file_size(Long vstTaskFileSize) {
		vst_task_file_size = vstTaskFileSize;
	}

	public Integer getVst_task_state() {
		return vst_task_state;
	}

	public void setVst_task_state(Integer vstTaskState) {
		vst_task_state = vstTaskState;
	}

	public Long getVst_task_addtime() {
		return vst_task_addtime;
	}

	public void setVst_task_addtime(Long vstTaskAddtime) {
		vst_task_addtime = vstTaskAddtime;
	}

	public String getVst_task_creator() {
		return vst_task_creator;
	}

	public void setVst_task_creator(String vstTaskCreator) {
		vst_task_creator = vstTaskCreator;
	}

	public Long getVst_task_uptime() {
		return vst_task_uptime;
	}

	public void setVst_task_uptime(Long vstTaskUptime) {
		vst_task_uptime = vstTaskUptime;
	}

	public String getVst_task_updator() {
		return vst_task_updator;
	}

	public void setVst_task_updator(String vstTaskUpdator) {
		vst_task_updator = vstTaskUpdator;
	}

	public String getVst_task_summary() {
		return vst_task_summary;
	}

	public void setVst_task_summary(String vstTaskSummary) {
		vst_task_summary = vstTaskSummary;
	}

	@Override
	public String toString() {
		return "VstExportTask [vst_module_id=" + vst_module_id
				+ ", vst_task_addtime=" + vst_task_addtime
				+ ", vst_task_columns=" + vst_task_columns
				+ ", vst_task_condition=" + vst_task_condition
				+ ", vst_task_creator=" + vst_task_creator
				+ ", vst_task_file_name=" + vst_task_file_name
				+ ", vst_task_file_path=" + vst_task_file_path
				+ ", vst_task_file_size=" + vst_task_file_size
				+ ", vst_task_id=" + vst_task_id + ", vst_task_state="
				+ vst_task_state + ", vst_task_summary=" + vst_task_summary
				+ ", vst_task_table=" + vst_task_table + ", vst_task_updator="
				+ vst_task_updator + ", vst_task_uptime=" + vst_task_uptime
				+ "]";
	}
}
