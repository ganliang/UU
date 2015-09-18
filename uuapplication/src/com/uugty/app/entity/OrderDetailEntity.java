package com.uugty.app.entity;

public class OrderDetailEntity {

	private String userId;// 用户主键id【32长度的uuid】
	private String userAvatar;// 用户的头像
	private String userRealname;// 用户的真实姓名
	private int userSex;// 用户的性别 1 男 ,2 女
	private String userConstellation;// 用户的星座
	private String userWork;// 用户的工作
	private String userPost;// 年代
	private int userCreditScore;// 用户信誉积分

	private String roadlineGoalArea;// 目的地
	private String roadlineDays;// 路线的天数

	private String orderNo;// 订单号
	private String orderMark;// 导游的标签
	private String orderTime;// 订单的时间
	private String orderPrice;// 订单的价格
	private String orderStatus;// 订单的状态 1 已下单 未付款，2 已失效 ，3 已支付，4 未支付取消 ，5 已拒绝 ，
								// 6 已接受，7 付款取消，8 订单完成 付款 ，9 退款中 ，10 退款完成
	private String orderCreateDate;// 订单的生成日期

	private String orderDrawbackReason;// 退款原因
	private String orderDrawbackMoney;// 退款金额
	private String orderDrawbackDate;// 退款日期

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

	public int getUserSex() {
		return userSex;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public String getUserConstellation() {
		return userConstellation;
	}

	public void setUserConstellation(String userConstellation) {
		this.userConstellation = userConstellation;
	}

	public String getUserWork() {
		return userWork;
	}

	public void setUserWork(String userWork) {
		this.userWork = userWork;
	}

	public int getUserCreditScore() {
		return userCreditScore;
	}

	public void setUserCreditScore(int userCreditScore) {
		this.userCreditScore = userCreditScore;
	}

	public String getRoadlineGoalArea() {
		return roadlineGoalArea;
	}

	public void setRoadlineGoalArea(String roadlineGoalArea) {
		this.roadlineGoalArea = roadlineGoalArea;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderMark() {
		return orderMark;
	}

	public void setOrderMark(String orderMark) {
		this.orderMark = orderMark;
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

	public String getOrderCreateDate() {
		return orderCreateDate;
	}

	public void setOrderCreateDate(String orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getOrderDrawbackReason() {
		return orderDrawbackReason;
	}

	public void setOrderDrawbackReason(String orderDrawbackReason) {
		this.orderDrawbackReason = orderDrawbackReason;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderDrawbackMoney() {
		return orderDrawbackMoney;
	}

	public void setOrderDrawbackMoney(String orderDrawbackMoney) {
		this.orderDrawbackMoney = orderDrawbackMoney;
	}

	public String getOrderDrawbackDate() {
		return orderDrawbackDate;
	}

	public void setOrderDrawbackDate(String orderDrawbackDate) {
		this.orderDrawbackDate = orderDrawbackDate;
	}

	public String getUserPost() {
		return userPost;
	}

	public void setUserPost(String userPost) {
		this.userPost = userPost;
	}

	public String getRoadlineDays() {
		return roadlineDays;
	}

	public void setRoadlineDays(String roadlineDays) {
		this.roadlineDays = roadlineDays;
	}

}
