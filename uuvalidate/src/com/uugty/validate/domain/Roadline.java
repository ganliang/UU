package com.uugty.validate.domain;

import java.io.Serializable;

public class Roadline implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 8943711561435845131L;

	private int roadlineId;// 路线id
	private String userId;// 用户头像
	private String userAvatar;// 用户头像
	private String roadlineTitle;// 发布路线的标题
	private double roadlinePrice;// 路线需要的价格
	private String roadlineContent;// 路线 内容 摘要

	private String roadlineGoalArea;// 目的地

	private String describeImages;// 路线描述的图片地址

	private String roadlineBackground;// 背景图像

	private String roadlineCreateDate;// 路线的生成日期

	public static final String ROADLINE_IS_HOT_YES = "yes";
	public static final String ROADLINE_IS_HOT_NO = "no";
	private String roadlineIsHot;// 该路线是否是热门城市

	private int userTelValidate;// 电话验证
	private int userAvatarValidate;// 头像验证
	private int userCertificateValidate;// 学历证
	private int userIdValidate;// 身份证
	private int userTourValidate;// 导游证
	private int userCarValidate;// 用户车辆审核

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

	public String getDescribeImages() {
		return describeImages;
	}

	public void setDescribeImages(String describeImages) {
		this.describeImages = describeImages;
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

	public String getRoadlineCreateDate() {
		return roadlineCreateDate;
	}

	public void setRoadlineCreateDate(String roadlineCreateDate) {
		this.roadlineCreateDate = roadlineCreateDate;
	}

	private String roadlineStatus;
	private int currentPage;//

	public String getRoadlineStatus() {
		return roadlineStatus;
	}

	public void setRoadlineStatus(String roadlineStatus) {
		this.roadlineStatus = roadlineStatus;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	private String status;
	private String type;
	private String validateResaon;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidateResaon() {
		return validateResaon;
	}

	public void setValidateResaon(String validateResaon) {
		this.validateResaon = validateResaon;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoadlineIsHot() {
		return roadlineIsHot;
	}

	public void setRoadlineIsHot(String roadlineIsHot) {
		this.roadlineIsHot = roadlineIsHot;
	}

	public int getUserTelValidate() {
		return userTelValidate;
	}

	public void setUserTelValidate(int userTelValidate) {
		this.userTelValidate = userTelValidate;
	}

	public int getUserAvatarValidate() {
		return userAvatarValidate;
	}

	public void setUserAvatarValidate(int userAvatarValidate) {
		this.userAvatarValidate = userAvatarValidate;
	}

	public int getUserCertificateValidate() {
		return userCertificateValidate;
	}

	public void setUserCertificateValidate(int userCertificateValidate) {
		this.userCertificateValidate = userCertificateValidate;
	}

	public int getUserIdValidate() {
		return userIdValidate;
	}

	public void setUserIdValidate(int userIdValidate) {
		this.userIdValidate = userIdValidate;
	}

	public int getUserTourValidate() {
		return userTourValidate;
	}

	public void setUserTourValidate(int userTourValidate) {
		this.userTourValidate = userTourValidate;
	}

	public int getUserCarValidate() {
		return userCarValidate;
	}

	public void setUserCarValidate(int userCarValidate) {
		this.userCarValidate = userCarValidate;
	}

}
