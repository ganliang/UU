package com.uugty.app.entity;

public class RoadlineSearchEntity {

	private int roadlineId;// 路线id

	private String roadlineImages;// 发布路线的图片
	private String roadlineTitle;// 发布路线的标题
	private float roadlinePrice;// 路线需要的价格

	private String userAvatar;
	private String roadlineBackground;

	public int getRoadlineId() {
		return roadlineId;
	}

	public void setRoadlineId(int roadlineId) {
		this.roadlineId = roadlineId;
	}

	public String getRoadlineTitle() {
		return roadlineTitle;
	}

	public void setRoadlineTitle(String roadlineTitle) {
		this.roadlineTitle = roadlineTitle;
	}

	public String getRoadlineImages() {
		return roadlineImages;
	}

	public void setRoadlineImages(String roadlineImages) {
		this.roadlineImages = roadlineImages;
	}

	public float getRoadlinePrice() {
		return roadlinePrice;
	}

	public void setRoadlinePrice(float roadlinePrice) {
		this.roadlinePrice = roadlinePrice;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getRoadlineBackground() {
		return roadlineBackground;
	}

	public void setRoadlineBackground(String roadlineBackground) {
		this.roadlineBackground = roadlineBackground;
	}

}
