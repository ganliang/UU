package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TRoadline
 * @Description: 路线的实体对象
 * @author ganliang
 * @date 2015年6月13日 下午2:18:47
 */
public class TRoadline implements Serializable {

	private static final long serialVersionUID = -8568450768722437273L;

	private int roadlineId;// 路线id
	private String roadlineUserId;// 发布路线的用户id

	private String roadlineTitle;// 发布路线的标题
	private float roadlinePrice;// 路线需要的价格
	private String roadlineContent;// 路线 内容 摘要

	private String roadlineGoalArea;// 目的地

	private int roadlineType;// 发布路线的类型
	private Date roadlineCreateDate;// 路线的生成日期

	public static final String STATUS_REVIEW = "review";// 正在审核
	public static final String STATUS_SUCCESS = "success";// 审核通过
	public static final String STATUS_FAILURE = "failure";// 审核失败
	public static final String STATUS_DROP = "drop";// 删除
	private String roadlineStatus;

	private float roadlineDays;// 路线天数
	private String roadlineBackground;

	public static final String ROADLINE_IS_HOT_YES = "yes";
	public static final String ROADLINE_IS_HOT_NO = "no";
	private String roadlineIsHot;// 该路线是否是热门城市

	public int getRoadlineId() {
		return roadlineId;
	}

	public void setRoadlineId(int roadlineId) {
		this.roadlineId = roadlineId;
	}

	public String getRoadlineUserId() {
		return roadlineUserId;
	}

	public void setRoadlineUserId(String roadlineUserId) {
		this.roadlineUserId = roadlineUserId;
	}

	public String getRoadlineTitle() {
		return roadlineTitle;
	}

	public void setRoadlineTitle(String roadlineTitle) {
		this.roadlineTitle = roadlineTitle;
	}

	public float getRoadlinePrice() {
		return roadlinePrice;
	}

	public void setRoadlinePrice(float roadlinePrice) {
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

	public int getRoadlineType() {
		return roadlineType;
	}

	public void setRoadlineType(int roadlineType) {
		this.roadlineType = roadlineType;
	}

	public Date getRoadlineCreateDate() {
		return roadlineCreateDate;
	}

	public void setRoadlineCreateDate(Date roadlineCreateDate) {
		this.roadlineCreateDate = roadlineCreateDate;
	}

	public String getRoadlineStatus() {
		return roadlineStatus;
	}

	public void setRoadlineStatus(String roadlineStatus) {
		this.roadlineStatus = roadlineStatus;
	}

	public float getRoadlineDays() {
		return roadlineDays;
	}

	public void setRoadlineDays(float roadlineDays) {
		this.roadlineDays = roadlineDays;
	}

	public String getRoadlineBackground() {
		return roadlineBackground;
	}

	public void setRoadlineBackground(String roadlineBackground) {
		this.roadlineBackground = roadlineBackground;
	}

	public String getRoadlineIsHot() {
		return roadlineIsHot;
	}

	public void setRoadlineIsHot(String roadlineIsHot) {
		this.roadlineIsHot = roadlineIsHot;
	}

}
