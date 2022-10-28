package com.vst.common.pojo;

/**
 * 弹窗统计
 * @author lhp
 * @date 2018-6-14 上午10:10:28
 * @version
 */
public class VstBulletData {
	/**
	 * 主键，10位随机字符串
	 */
	private String vst_bd_id;
	
	/**
	 * 统计日期
	 */
	private Integer vst_bd_date;
	
	/**
	 * 包名
	 */
	private String vst_bd_pkg;
	
	/**
	 * 类型，1：留存用户，2：激活用户
	 */
	private Integer vst_bd_type;
	
	/**
	 * 弹窗点击用户
	 */
	private Integer vst_bd_total;
	
	/**
	 * 1日用户数
	 */
	private Integer vst_bd_one;
	
	/**
	 * 1日留存率
	 */
	private String vst_bd_one_ratio;
	
	/**
	 * 2日用户数
	 */
	private Integer vst_bd_two;
	
	/**
	 * 2日留存率
	 */
	private String vst_bd_two_ratio;
	
	/**
	 * 3日用户数
	 */
	private Integer vst_bd_three;
	
	/**
	 * 3日留存率
	 */
	private String vst_bd_three_ratio;
	
	/**
	 * 4日用户数
	 */
	private Integer vst_bd_four;
	
	/**
	 * 4日留存率
	 */
	private String vst_bd_four_ratio;
	
	/**
	 * 5日用户数
	 */
	private Integer vst_bd_five;
	
	/**
	 * 5日留存率
	 */
	private String vst_bd_five_ratio;
	
	/**
	 * 6日用户数
	 */
	private Integer vst_bd_six;
	
	/**
	 * 6日留存率
	 */
	private String vst_bd_six_ratio;
	
	/**
	 * 7日用户数
	 */
	private Integer vst_bd_seven;
	
	/**
	 * 7日留存率
	 */
	private String vst_bd_seven_ratio;
	
	/**
	 * 1日连续用户数
	 */
	private Integer vst_bd_con_one;
	
	/**
	 * 1日连续留存率
	 */
	private String vst_bd_con_one_ratio;
	
	/**
	 * 2日连续用户数
	 */
	private Integer vst_bd_con_two;
	
	/**
	 * 2日连续留存率
	 */
	private String vst_bd_con_two_ratio;
	
	/**
	 * 3日连续用户数
	 */
	private Integer vst_bd_con_three;
	
	/**
	 * 3日连续留存率
	 */
	private String vst_bd_con_three_ratio;
	
	/**
	 * 4日连续用户数
	 */
	private Integer vst_bd_con_four;
	
	/**
	 * 4日连续留存率
	 */
	private String vst_bd_con_four_ratio;
	
	/**
	 * 5日连续用户数
	 */
	private Integer vst_bd_con_five;
	
	/**
	 * 5日连续留存率
	 */
	private String vst_bd_con_five_ratio;
	
	/**
	 * 6日连续用户数
	 */
	private Integer vst_bd_con_six;
	
	/**
	 * 6日连续留存率
	 */
	private String vst_bd_con_six_ratio;
	
	/**
	 * 7日连续用户数
	 */
	private Integer vst_bd_con_seven;
	
	/**
	 * 7日连续留存率
	 */
	private String vst_bd_con_seven_ratio;
	
	/**
	 * 新增时间，13位毫秒数
	 */
	private Long vst_bd_addtime;
	
	/**
	 * 创建人
	 */
	private String vst_bd_creator;
	
	/**
	 * 修改时间，13位毫秒数
	 */
	private Long vst_bd_uptime;
	
	/**
	 * 修改人
	 */
	private String vst_bd_updator;
	
	/**
	 * 备注
	 */
	private String vst_bd_summary;

	public String getVst_bd_id() {
		return vst_bd_id;
	}

	public void setVst_bd_id(String vstBdId) {
		vst_bd_id = vstBdId;
	}

	public Integer getVst_bd_date() {
		return vst_bd_date;
	}

	public void setVst_bd_date(Integer vstBdDate) {
		vst_bd_date = vstBdDate;
	}

	public String getVst_bd_pkg() {
		return vst_bd_pkg;
	}

	public void setVst_bd_pkg(String vstBdPkg) {
		vst_bd_pkg = vstBdPkg;
	}

	public Integer getVst_bd_type() {
		return vst_bd_type;
	}

	public void setVst_bd_type(Integer vstBdType) {
		vst_bd_type = vstBdType;
	}

	public Integer getVst_bd_total() {
		return vst_bd_total;
	}

	public void setVst_bd_total(Integer vstBdTotal) {
		vst_bd_total = vstBdTotal;
	}

	public Integer getVst_bd_one() {
		return vst_bd_one;
	}

	public void setVst_bd_one(Integer vstBdOne) {
		vst_bd_one = vstBdOne;
	}

	public String getVst_bd_one_ratio() {
		return vst_bd_one_ratio;
	}

	public void setVst_bd_one_ratio(String vstBdOneRatio) {
		vst_bd_one_ratio = vstBdOneRatio;
	}

	public Integer getVst_bd_two() {
		return vst_bd_two;
	}

	public void setVst_bd_two(Integer vstBdTwo) {
		vst_bd_two = vstBdTwo;
	}

	public String getVst_bd_two_ratio() {
		return vst_bd_two_ratio;
	}

	public void setVst_bd_two_ratio(String vstBdTwoRatio) {
		vst_bd_two_ratio = vstBdTwoRatio;
	}

	public Integer getVst_bd_three() {
		return vst_bd_three;
	}

	public void setVst_bd_three(Integer vstBdThree) {
		vst_bd_three = vstBdThree;
	}

	public String getVst_bd_three_ratio() {
		return vst_bd_three_ratio;
	}

	public void setVst_bd_three_ratio(String vstBdThreeRatio) {
		vst_bd_three_ratio = vstBdThreeRatio;
	}

	public Integer getVst_bd_four() {
		return vst_bd_four;
	}

	public void setVst_bd_four(Integer vstBdFour) {
		vst_bd_four = vstBdFour;
	}

	public String getVst_bd_four_ratio() {
		return vst_bd_four_ratio;
	}

	public void setVst_bd_four_ratio(String vstBdFourRatio) {
		vst_bd_four_ratio = vstBdFourRatio;
	}

	public Integer getVst_bd_five() {
		return vst_bd_five;
	}

	public void setVst_bd_five(Integer vstBdFive) {
		vst_bd_five = vstBdFive;
	}

	public String getVst_bd_five_ratio() {
		return vst_bd_five_ratio;
	}

	public void setVst_bd_five_ratio(String vstBdFiveRatio) {
		vst_bd_five_ratio = vstBdFiveRatio;
	}

	public Integer getVst_bd_six() {
		return vst_bd_six;
	}

	public void setVst_bd_six(Integer vstBdSix) {
		vst_bd_six = vstBdSix;
	}

	public String getVst_bd_six_ratio() {
		return vst_bd_six_ratio;
	}

	public void setVst_bd_six_ratio(String vstBdSixRatio) {
		vst_bd_six_ratio = vstBdSixRatio;
	}

	public Integer getVst_bd_seven() {
		return vst_bd_seven;
	}

	public void setVst_bd_seven(Integer vstBdSeven) {
		vst_bd_seven = vstBdSeven;
	}

	public String getVst_bd_seven_ratio() {
		return vst_bd_seven_ratio;
	}

	public void setVst_bd_seven_ratio(String vstBdSevenRatio) {
		vst_bd_seven_ratio = vstBdSevenRatio;
	}

	public Integer getVst_bd_con_one() {
		return vst_bd_con_one;
	}

	public void setVst_bd_con_one(Integer vstBdConOne) {
		vst_bd_con_one = vstBdConOne;
	}

	public String getVst_bd_con_one_ratio() {
		return vst_bd_con_one_ratio;
	}

	public void setVst_bd_con_one_ratio(String vstBdConOneRatio) {
		vst_bd_con_one_ratio = vstBdConOneRatio;
	}

	public Integer getVst_bd_con_two() {
		return vst_bd_con_two;
	}

	public void setVst_bd_con_two(Integer vstBdConTwo) {
		vst_bd_con_two = vstBdConTwo;
	}

	public String getVst_bd_con_two_ratio() {
		return vst_bd_con_two_ratio;
	}

	public void setVst_bd_con_two_ratio(String vstBdConTwoRatio) {
		vst_bd_con_two_ratio = vstBdConTwoRatio;
	}

	public Integer getVst_bd_con_three() {
		return vst_bd_con_three;
	}

	public void setVst_bd_con_three(Integer vstBdConThree) {
		vst_bd_con_three = vstBdConThree;
	}

	public String getVst_bd_con_three_ratio() {
		return vst_bd_con_three_ratio;
	}

	public void setVst_bd_con_three_ratio(String vstBdConThreeRatio) {
		vst_bd_con_three_ratio = vstBdConThreeRatio;
	}

	public Integer getVst_bd_con_four() {
		return vst_bd_con_four;
	}

	public void setVst_bd_con_four(Integer vstBdConFour) {
		vst_bd_con_four = vstBdConFour;
	}

	public String getVst_bd_con_four_ratio() {
		return vst_bd_con_four_ratio;
	}

	public void setVst_bd_con_four_ratio(String vstBdConFourRatio) {
		vst_bd_con_four_ratio = vstBdConFourRatio;
	}

	public Integer getVst_bd_con_five() {
		return vst_bd_con_five;
	}

	public void setVst_bd_con_five(Integer vstBdConFive) {
		vst_bd_con_five = vstBdConFive;
	}

	public String getVst_bd_con_five_ratio() {
		return vst_bd_con_five_ratio;
	}

	public void setVst_bd_con_five_ratio(String vstBdConFiveRatio) {
		vst_bd_con_five_ratio = vstBdConFiveRatio;
	}

	public Integer getVst_bd_con_six() {
		return vst_bd_con_six;
	}

	public void setVst_bd_con_six(Integer vstBdConSix) {
		vst_bd_con_six = vstBdConSix;
	}

	public String getVst_bd_con_six_ratio() {
		return vst_bd_con_six_ratio;
	}

	public void setVst_bd_con_six_ratio(String vstBdConSixRatio) {
		vst_bd_con_six_ratio = vstBdConSixRatio;
	}

	public Integer getVst_bd_con_seven() {
		return vst_bd_con_seven;
	}

	public void setVst_bd_con_seven(Integer vstBdConSeven) {
		vst_bd_con_seven = vstBdConSeven;
	}

	public String getVst_bd_con_seven_ratio() {
		return vst_bd_con_seven_ratio;
	}

	public void setVst_bd_con_seven_ratio(String vstBdConSevenRatio) {
		vst_bd_con_seven_ratio = vstBdConSevenRatio;
	}

	public Long getVst_bd_addtime() {
		return vst_bd_addtime;
	}

	public void setVst_bd_addtime(Long vstBdAddtime) {
		vst_bd_addtime = vstBdAddtime;
	}

	public String getVst_bd_creator() {
		return vst_bd_creator;
	}

	public void setVst_bd_creator(String vstBdCreator) {
		vst_bd_creator = vstBdCreator;
	}

	public Long getVst_bd_uptime() {
		return vst_bd_uptime;
	}

	public void setVst_bd_uptime(Long vstBdUptime) {
		vst_bd_uptime = vstBdUptime;
	}

	public String getVst_bd_updator() {
		return vst_bd_updator;
	}

	public void setVst_bd_updator(String vstBdUpdator) {
		vst_bd_updator = vstBdUpdator;
	}

	public String getVst_bd_summary() {
		return vst_bd_summary;
	}

	public void setVst_bd_summary(String vstBdSummary) {
		vst_bd_summary = vstBdSummary;
	}

	@Override
	public String toString() {
		return "VstBulletData [vst_bd_addtime=" + vst_bd_addtime
				+ ", vst_bd_con_five=" + vst_bd_con_five
				+ ", vst_bd_con_five_ratio=" + vst_bd_con_five_ratio
				+ ", vst_bd_con_four=" + vst_bd_con_four
				+ ", vst_bd_con_four_ratio=" + vst_bd_con_four_ratio
				+ ", vst_bd_con_one=" + vst_bd_con_one
				+ ", vst_bd_con_one_ratio=" + vst_bd_con_one_ratio
				+ ", vst_bd_con_seven=" + vst_bd_con_seven
				+ ", vst_bd_con_seven_ratio=" + vst_bd_con_seven_ratio
				+ ", vst_bd_con_six=" + vst_bd_con_six
				+ ", vst_bd_con_six_ratio=" + vst_bd_con_six_ratio
				+ ", vst_bd_con_three=" + vst_bd_con_three
				+ ", vst_bd_con_three_ratio=" + vst_bd_con_three_ratio
				+ ", vst_bd_con_two=" + vst_bd_con_two
				+ ", vst_bd_con_two_ratio=" + vst_bd_con_two_ratio
				+ ", vst_bd_creator=" + vst_bd_creator + ", vst_bd_date="
				+ vst_bd_date + ", vst_bd_five=" + vst_bd_five
				+ ", vst_bd_five_ratio=" + vst_bd_five_ratio + ", vst_bd_four="
				+ vst_bd_four + ", vst_bd_four_ratio=" + vst_bd_four_ratio
				+ ", vst_bd_id=" + vst_bd_id + ", vst_bd_one=" + vst_bd_one
				+ ", vst_bd_one_ratio=" + vst_bd_one_ratio + ", vst_bd_pkg="
				+ vst_bd_pkg + ", vst_bd_seven=" + vst_bd_seven
				+ ", vst_bd_seven_ratio=" + vst_bd_seven_ratio
				+ ", vst_bd_six=" + vst_bd_six + ", vst_bd_six_ratio="
				+ vst_bd_six_ratio + ", vst_bd_summary=" + vst_bd_summary
				+ ", vst_bd_three=" + vst_bd_three + ", vst_bd_three_ratio="
				+ vst_bd_three_ratio + ", vst_bd_total=" + vst_bd_total
				+ ", vst_bd_two=" + vst_bd_two + ", vst_bd_two_ratio="
				+ vst_bd_two_ratio + ", vst_bd_type=" + vst_bd_type
				+ ", vst_bd_updator=" + vst_bd_updator + ", vst_bd_uptime="
				+ vst_bd_uptime + "]";
	}
}
