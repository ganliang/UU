package com.uugty.app.web.form;

public class RoadLineMarkForm {

	private int markId;
	private String markContent;// 标签的内容
	private double markX;// 图标的x轴
	private double markY;// 图标的y轴

	public int getMarkId() {
		return markId;
	}

	public void setMarkId(int markId) {
		this.markId = markId;
	}

	public String getMarkContent() {
		return markContent;
	}

	public void setMarkContent(String markContent) {
		this.markContent = markContent;
	}

	public double getMarkX() {
		return markX;
	}

	public void setMarkX(double markX) {
		this.markX = markX;
	}

	public double getMarkY() {
		return markY;
	}

	public void setMarkY(double markY) {
		this.markY = markY;
	}

}
