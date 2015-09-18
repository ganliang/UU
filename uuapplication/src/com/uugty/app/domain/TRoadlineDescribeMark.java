package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TMark
 * @Description: 标签实体
 * @author ganliang
 * @date 2015年6月13日 下午2:17:26
 */
public class TRoadlineDescribeMark implements Serializable {

	private static final long serialVersionUID = 3159458407704604901L;

	private int markId;// 标签的id
	private String markRoadlineId;// 标签的路线id
	private String markDescribeId;// 便签的描述id
	private String markContent;// 标签的内容
	private double markX;// 图标的x轴
	private double markY;// 图标的y轴
	private Date markCreateDate;// 标签的创建日期

	public int getMarkId() {
		return markId;
	}

	public void setMarkId(int markId) {
		this.markId = markId;
	}

	public String getMarkRoadlineId() {
		return markRoadlineId;
	}

	public void setMarkRoadlineId(String markRoadlineId) {
		this.markRoadlineId = markRoadlineId;
	}

	public String getMarkDescribeId() {
		return markDescribeId;
	}

	public void setMarkDescribeId(String markDescribeId) {
		this.markDescribeId = markDescribeId;
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

	public Date getMarkCreateDate() {
		return markCreateDate;
	}

	public void setMarkCreateDate(Date markCreateDate) {
		this.markCreateDate = markCreateDate;
	}

}
