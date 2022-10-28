package com.vst.core.communal.bean;

import java.io.Serializable;

/**
 * @author weiwei(joslyn)
 * @date 2017-12-6 上午11:15:21
 * @decription
 * @version 
 */
public class VideoTopic implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2252644629225298298L;

	/**
	 * 专题id
	 */
	private String topicId;
	
	/**
	 * 专题名称
	 */
	private String title;
	
	/**
	 * 专题分类id
	 */
	private int cid;
	
	/**
	 * 专区类型
	 */
	private int specialType;
	
	/**
	 * 专题模板类型
	 */
	private int templateType;
	
	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getSpecialType() {
		return specialType;
	}

	public void setSpecialType(int specialType) {
		this.specialType = specialType;
	}

	public int getTemplateType() {
		return templateType;
	}

	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}
	
	/**
	 * 【栏目模版】 1：影片列表模版，2：影片专题模版，3：新版少儿专题模版，4：事件专题模版；9：轮播列表【专题模板】 5：普通专题，6：排行专题，7：精选专题；【事件模板】 8：事件专题
	 * @return
	 */
	public String getTemplate() {
		String result = "未知";
		switch (templateType) {
		case 1:
			result = "影片列表模版";
			break;
		case 2:
			result = "影片专题模版";
			break;
		case 3:
			result = "新版少儿专题模版";
			break;
		case 4:
			result = "事件专题模版";
			break;
		case 5:
			result = "普通专题";
			break;
		case 6:
			result = "排行专题";
			break;
		case 7:
			result = "精选专题";
			break;
		case 8:
			result = "事件专题";
			break;
		case 9:
			result = "轮播列表";
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public String toString() {
		return "VideoTopic [cid=" + cid + ", specialType=" + specialType
				+ ", templateType=" + templateType + ", title=" + title
				+ ", topicId=" + topicId + "]";
	}
}
