package com.uugty.app.utils;

import java.io.Serializable;

/**
 * @ClassName: Page
 * @Description: 分页实体
 * @author ganliang
 * @date 2015年6月25日 下午3:33:44
 */
public class Page implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -5984949605138431838L;
	private int pageSize = 10;// 一页的数量
	private int currentPage = 1;// 当前的页数

	private int beginIndex = 0;// 起始
	private int endIndex = 10;// 起始

	private int totalSize;// 总条数
	private int totalPage;// 总页数

	public Page() {
		this.beginIndex = (currentPage - 1) * pageSize;
		this.endIndex = beginIndex + pageSize;
	}

	public Page(int currentPage) {
		super();
		this.currentPage = currentPage;
		this.beginIndex = ((currentPage == 0 ? 1 : currentPage) - 1) * pageSize;
		this.endIndex = beginIndex + pageSize;
	}

	public Page(int totalSize, int currentPage) {
		super();
		this.totalSize = totalSize;
		this.currentPage = currentPage;

		this.endIndex = beginIndex + pageSize;

		if (totalSize % pageSize == 0) {
			this.totalPage = totalSize < pageSize ? 1 : (totalSize / pageSize);
		} else {
			this.totalPage = totalSize / pageSize + 1;
		}

		this.beginIndex = ((currentPage == 0 ? 1 : currentPage) - 1) * pageSize;

		if (beginIndex == totalSize && beginIndex != 0) {
			beginIndex = beginIndex - pageSize;
			this.currentPage = currentPage - 1;
		}
	}

	public Page(int pageSize, int currentPage, int totalSize) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalSize = totalSize;

		this.beginIndex = ((currentPage == 0 ? 1 : currentPage) - 1) * pageSize;

		this.endIndex = beginIndex + pageSize;

	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
