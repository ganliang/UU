package com.uugty.app.web.form;

import java.util.List;

public class RoadLineDescribeForm {

	private int describeId;//

	private String describeImage;// 路线描述的图片地址
	private String describeTime;// 路线描述的图片时间
	private String describeArea;// 图片的目的地
	private List<RoadLineMarkForm> describeMarks;

	public int getDescribeId() {
		return describeId;
	}

	public void setDescribeId(int describeId) {
		this.describeId = describeId;
	}

	public String getDescribeImage() {
		return describeImage;
	}

	public void setDescribeImage(String describeImage) {
		this.describeImage = describeImage;
	}

	public String getDescribeTime() {
		return describeTime;
	}

	public void setDescribeTime(String describeTime) {
		this.describeTime = describeTime;
	}

	public String getDescribeArea() {
		return describeArea;
	}

	public void setDescribeArea(String describeArea) {
		this.describeArea = describeArea;
	}

	public List<RoadLineMarkForm> getDescribeMarks() {
		return describeMarks;
	}

	public void setDescribeMarks(List<RoadLineMarkForm> describeMarks) {
		this.describeMarks = describeMarks;
	}

}
