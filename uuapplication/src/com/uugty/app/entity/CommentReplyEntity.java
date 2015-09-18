package com.uugty.app.entity;

public class CommentReplyEntity {

	private int commentCount;// 总评论数
	private String avageRatioIndex;//
	private String avageFreshIndex;//
	private String avageServiceIndex;//
	private String avageTotalIndex;//

	public int getCommentCount() {
		return commentCount;
	}

	public String getAvageRatioIndex() {
		return avageRatioIndex;
	}

	public void setAvageRatioIndex(String avageRatioIndex) {
		this.avageRatioIndex = avageRatioIndex;
	}

	public String getAvageFreshIndex() {
		return avageFreshIndex;
	}

	public void setAvageFreshIndex(String avageFreshIndex) {
		this.avageFreshIndex = avageFreshIndex;
	}

	public String getAvageServiceIndex() {
		return avageServiceIndex;
	}

	public void setAvageServiceIndex(String avageServiceIndex) {
		this.avageServiceIndex = avageServiceIndex;
	}

	public String getAvageTotalIndex() {
		return avageTotalIndex;
	}

	public void setAvageTotalIndex(String avageTotalIndex) {
		this.avageTotalIndex = avageTotalIndex;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
}