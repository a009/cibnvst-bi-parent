package com.vst.core.communal.bean;

import java.io.Serializable;

/**
 * @author weiwei(joslyn)
 * @date 2017-12-6 上午11:12:19
 * @decription
 * @version
 */
public class Video implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 750503839456416539L;

	/**
	 * 影片uuid
	 */
	private String uuid;

	/**
	 * 影片标题
	 */
	private String title;

	/**
	 * 影片分类id
	 */
	private int cid;

	/**
	 * 影片专区类型
	 */
	private int specialType;

	/**
	 * 影片类型
	 */
	private String cat;

	/**
	 * 演员
	 */
	private String actor;

	/**
	 * 导演
	 */
	private String director;

	/**
	 * 地区
	 */
	private String area;

	/**
	 * 评分
	 * */
	private String mark;

	/**
	 * 上映年份
	 * */
	private int year;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMark() {
		return mark;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return "Video [actor=" + actor + ", area=" + area + ", cat=" + cat
				+ ", cid=" + cid + ", director=" + director + ", specialType="
				+ specialType + ", title=" + title + ", uuid=" + uuid
				+ ", mark=" + mark + ", year=" + year + "]";
	}
}
