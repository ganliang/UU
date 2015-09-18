package com.uugty.app.entity;

public class MarkEntity {

	private int markId;// 标签id

	private String markTitle;// 标签内容

	private String markContent;// 标签内容

	private String markImages;// 标签图片

	public static final String MARK_SEARCH_GOAL = "goal";
	public static final String MARK_SEARCH_CONTENT = "content";
	private String markSearchType;// 标签搜索方式 goal content

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

	public String getMarkContent() {
		return markContent;
	}

	public void setMarkContent(String markContent) {
		this.markContent = markContent;
	}

}