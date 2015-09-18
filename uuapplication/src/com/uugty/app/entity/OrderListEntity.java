package com.uugty.app.entity;

import java.util.Date;

public class OrderListEntity {

	private String userId;// 用户id
	private String userAvatar;// 头像
	private String userRealname;// 姓名

	private String roadlineGoalArea;// 路线地点
	private String roadlineDays;// 路线时间

	private String orderTime;// 订单开始日期
	private String orderStatus;// 订单状态
	private String orderPrice;// 订单价格

	private int orderId;
	private Date orderCreateDate;// 订单的生成日期

	private String isEval;// 0 未评价, 1 已评价

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getRoadlineGoalArea() {
		return roadlineGoalArea;
	}

	public void setRoadlineGoalArea(String roadlineGoalArea) {
		this.roadlineGoalArea = roadlineGoalArea;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderCreateDate() {
		return orderCreateDate;
	}

	public void setOrderCreateDate(Date orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}

	public String getIsEval() {
		return isEval;
	}

	public void setIsEval(String isEval) {
		this.isEval = isEval;
	}

	public String getRoadlineDays() {
		return roadlineDays;
	}

	public void setRoadlineDays(String roadlineDays) {
		this.roadlineDays = roadlineDays;
	}

}
