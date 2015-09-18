package com.uugty.app.entity;

public class CollectRoadlineEntity {
	private int collectId;// 关注的主键
	private String collectTitle;// 被关注的用户id
	private String roadlineImages;
	private String collectCreateDate;// 关注创建的日期

	private int roadlineId;// 路线id

	public int getCollectId() {
		return collectId;
	}

	public void setCollectId(int collectId) {
		this.collectId = collectId;
	}

	public String getCollectTitle() {
		return collectTitle;
	}

	public void setCollectTitle(String collectTitle) {
		this.collectTitle = collectTitle;
	}

	public String getCollectCreateDate() {
		return collectCreateDate;
	}

	public void setCollectCreateDate(String collectCreateDate) {
		this.collectCreateDate = collectCreateDate;
	}

	public String getRoadlineImages() {
		return roadlineImages;
	}

	public void setRoadlineImages(String roadlineImages) {
		this.roadlineImages = roadlineImages;
	}

	public int getRoadlineId() {
		return roadlineId;
	}

	public void setRoadlineId(int roadlineId) {
		this.roadlineId = roadlineId;
	}

}
