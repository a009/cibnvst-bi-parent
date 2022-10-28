package com.vst.core.communal.bean;

import java.io.Serializable;

/**
 * @author weiwei(joslyn)
 * @date 2017-12-6 上午11:39:52
 * @decription
 * @version 
 */
public class VideoTopicLink implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1900058699509985106L;

	/**
	 * 主键id
	 */
	private String linkId;

	/**
	 * 专题id
	 */
	private String topicId;
	
	/**
	 * 专题分类id
	 */
	private int cid;
	
	/**
	 * 专题下影片uuid
	 */
	private String uuid;
	
	/**
	 * 专题标题
	 */
	private String topicTitle;
	
	/**
	 * 专区类型
	 */
	private int specialType;
	
	/**
	 * 专题下单影片标题
	 */
	private String linkTitle;
	
	/**
	 * 专题下影片排序
	 */
	private String linkIndex;

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public int getSpecialType() {
		return specialType;
	}

	public void setSpecialType(int specialType) {
		this.specialType = specialType;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getLinkIndex() {
		return linkIndex;
	}

	public void setLinkIndex(String linkIndex) {
		this.linkIndex = linkIndex;
	}

	@Override
	public String toString() {
		return "VideoTopicLink [cid=" + cid + ", linkId=" + linkId
				+ ", linkIndex=" + linkIndex + ", linkTitle=" + linkTitle
				+ ", specialType=" + specialType + ", topicId=" + topicId
				+ ", topicTitle=" + topicTitle + ", uuid=" + uuid + "]";
	}
}
