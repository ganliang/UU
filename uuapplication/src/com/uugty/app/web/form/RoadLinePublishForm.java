package com.uugty.app.web.form;

import java.util.List;

public class RoadLinePublishForm {

	private int roadlineId;

	private String roadlineTitle;// 发布路线的标题
	private double roadlinePrice;// 路线需要的价格
	private String roadlineContent;// 路线 内容 摘要

	private String roadlineGoalArea;// 目的地
	private String roadlineDays;// 路线的天数
	private String roadlineBackground;
	private String roadlineStatus;
	private List<RoadLineDescribeForm> roadlineDescribes;

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

	public double getRoadlinePrice() {
		return roadlinePrice;
	}

	public void setRoadlinePrice(double roadlinePrice) {
		this.roadlinePrice = roadlinePrice;
	}

	public String getRoadlineContent() {
		return roadlineContent;
	}

	public void setRoadlineContent(String roadlineContent) {
		this.roadlineContent = roadlineContent;
	}

	public String getRoadlineGoalArea() {
		return roadlineGoalArea;
	}

	public void setRoadlineGoalArea(String roadlineGoalArea) {
		this.roadlineGoalArea = roadlineGoalArea;
	}

	public List<RoadLineDescribeForm> getRoadlineDescribes() {
		return roadlineDescribes;
	}

	public void setRoadlineDescribes(
			List<RoadLineDescribeForm> roadlineDescribes) {
		this.roadlineDescribes = roadlineDescribes;
	}

	public String getRoadlineDays() {
		return roadlineDays;
	}

	public void setRoadlineDays(String roadlineDays) {
		this.roadlineDays = roadlineDays;
	}

	public String getRoadlineBackground() {
		return roadlineBackground;
	}

	public void setRoadlineBackground(String roadlineBackground) {
		this.roadlineBackground = roadlineBackground;
	}

	public String getRoadlineStatus() {
		return roadlineStatus;
	}

	public void setRoadlineStatus(String roadlineStatus) {
		this.roadlineStatus = roadlineStatus;
	}

}
