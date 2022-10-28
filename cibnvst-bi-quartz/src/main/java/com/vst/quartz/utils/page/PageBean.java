package com.vst.quartz.utils.page;

/**
 * @author kai
 * 分页类
 * TODO: 2018/4/23
 */
public class PageBean {
	/**
	 * 每页显示的记录数
	 */
	private Integer totalCount = 1;
	/**
	 * 当前页码
	 */
	private Integer currentPage = 1;
	/**
	 * 总页数
	 */
	private Integer pageNum = 1;

	public PageBean() {
		super();
	}

	public PageBean(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	@Override
	public String toString() {
		String rn = "\r\n";
		String sp = ":";
		String sp1 = " ";
		// 打印结果
		String info = "记录如下" + sp + rn;
		info += "总记录数：" + this.getTotalCount() + sp1;
		info += "总页数：" + this.getPageNum() + rn;
		info += "当前页：" + this.getCurrentPage() + sp1;
		return info;
	}

	public static class Builder {
		/**
		 * 总记录数
		 */
		private Integer totalCount = 0;
		/**
		 * 当前页码
		 */
		private Integer currentPage = 0;
		/**
		 * 总页数
		 */
		private Integer pageNum = 0;

		public Builder totalCount(int val) {
			totalCount = val;
			return this;
		}

		public Builder currentPage(int val) {
			currentPage = val;
			return this;
		}

		public Builder pageNum(int val) {
			pageNum = val;
			return this;
		}

		public PageBean build() {
			return new PageBean(this);
		}
	}

	private PageBean(Builder builder) {
		totalCount = builder.totalCount;
		currentPage = builder.currentPage;
		pageNum = builder.pageNum;
	}
}