package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TRoadlineDescribe
 * @Description:
 * @author ganliang
 * @date 2015年6月13日 下午2:19:17
 */
public class TRoadlineDescribe implements Serializable {

	private static final long serialVersionUID = -4378787071624765488L;

	private int describeId;// 路线描述id
	private int describeRoadlineId;// 路线id

	private String describeImage;// 路线描述的图片地址
	private String describeTime;// 路线描述的图片时间
	private String describeArea;// 图片的目的地

	private Date describeCreateDate;

	public int getDescribeId() {
		return describeId;
	}

	public void setDescribeId(int describeId) {
		this.describeId = describeId;
	}

	public int getDescribeRoadlineId() {
		return describeRoadlineId;
	}

	public void setDescribeRoadlineId(int describeRoadlineId) {
		this.describeRoadlineId = describeRoadlineId;
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

	public Date getDescribeCreateDate() {
		return describeCreateDate;
	}

	public void setDescribeCreateDate(Date describeCreateDate) {
		this.describeCreateDate = describeCreateDate;
	}

}
