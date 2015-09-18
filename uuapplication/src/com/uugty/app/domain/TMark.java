package com.uugty.app.domain;

import java.io.Serializable;

public class TMark implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 7767851585017009613L;

	private int markId;// 标签id

	private String markTitle;// 标签内容

	private String markContent;// 标签内容

	private String markImages;// 标签图片

	public static final String MARK_SEARCH_GOAL = "goal";
	public static final String MARK_SEARCH_CONTENT = "content";
	public static final String MARK_SEARCH_TITLE = "title";
	public static final String MARK_SEARCH_MIX = "mix";
	private String markSearchType;// 标签搜索方式 goal content

	private int markWeight;// 标签的权重

	private String markDate;// 标签日期生成

	public int getMarkId() {
		return markId;
	}

	public void setMarkId(int markId) {
		this.markId = markId;
	}

	public String getMarkTitle() {
		return markTitle;
	}

	public void setMarkTitle(String markTitle) {
		this.markTitle = markTitle;
	}

	public String getMarkImages() {
		return markImages;
	}

	public void setMarkImages(String markImages) {
		this.markImages = markImages;
	}

	public String getMarkSearchType() {
		return markSearchType;
	}

	public void setMarkSearchType(String markSearchType) {
		this.markSearchType = markSearchType;
	}

	public String getMarkDate() {
		return markDate;
	}

	public void setMarkDate(String markDate) {
		this.markDate = markDate;
	}

	public int getMarkWeight() {
		return markWeight;
	}

	public void setMarkWeight(int markWeight) {
		this.markWeight = markWeight;
	}

	public String getMarkContent() {
		return markContent;
	}

	public void setMarkContent(String markContent) {
		this.markContent = markContent;
	}

	/**
	 * 非持久化字段
	 */
	public int currentPage = 1;
	public int pageSize = 9;// 一页的数量
	public int userSex;// 用户的性别 1 男 ,2 女
	public int userCarValidate;// 用户的车是否验证 0未认证,1已认证
	public int userTourValidate;// 用户的旅游证是否认证0未认证,1已认证
	public String sort;// 排序 totalIndex 综合评分，orderCount 成交单量

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getUserSex() {
		return userSex;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public int getUserTourValidate() {
		return userTourValidate;
	}

	public void setUserTourValidate(int userTourValidate) {
		this.userTourValidate = userTourValidate;
	}

	public String getSort() {
		return sort != null ? sort : "";
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getUserCarValidate() {
		return userCarValidate;
	}

	public void setUserCarValidate(int userCarValidate) {
		this.userCarValidate = userCarValidate;
	}

	public String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
