package com.uugty.app.entity;

import java.util.List;

import com.uugty.app.web.form.RoadLineDescribeForm;

public class RoadlineDetailEntity {

	private String userId;// 用户主键id【32长度的uuid】
	private String userName;// 用户姓名
	private int userAge;// 用户年龄
	private String userPost;// 用户的年代
	private String userConstellation;// 用户的星座
	private int userSex;// 用户的性别 1 男 ,2 女
	private String userCity;// 用户的城市
	private String userArea;// 用户的地点
	private String userWork;// 用户的工作
	private String userDescription;// 用户的工作
	private String tourAvatar;// 导游的头像

	private String avageRatioIndex;//
	private String avageFreshIndex;//
	private String avageServiceIndex;//
	private String avageTotalIndex;// 总评分指数
	private int commentCount;// 总评论数

	private String commentId;// 评论id
	private String totalIndex;// 总评分
	private String commentUserAvatar;// 评论的内容
	private String commentContent;// 评论的内容
	private String commentImages;// 评论的图片
	private String commentDate;// 评论的时间
	private String replyContent;
	private String replyImages;

	private int roadlineId;
	private String roadlineTitle;// 发布路线的标题
	private double roadlinePrice;// 路线需要的价格
	private String roadlineContent;// 路线 内容 摘要
	private String roadlineGoalArea;// 目的地
	private String roadlineDays;// 路线的天数
	private String roadlineBackground;
	private List<RoadLineDescribeForm> roadlineDescribes;

	private int collectId;// 收藏id

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserPost() {
		return userPost;
	}

	public void setUserPost(String userPost) {
		this.userPost = userPost;
	}

	public String getUserConstellation() {
		return userConstellation;
	}

	public void setUserConstellation(String userConstellation) {
		this.userConstellation = userConstellation;
	}

	public int getUserSex() {
		return userSex;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getUserWork() {
		return userWork;
	}

	public void setUserWork(String userWork) {
		this.userWork = userWork;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getTotalIndex() {
		return totalIndex;
	}

	public void setTotalIndex(String totalIndex) {
		this.totalIndex = totalIndex;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentImages() {
		return commentImages;
	}

	public void setCommentImages(String commentImages) {
		this.commentImages = commentImages;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public String getTourAvatar() {
		return tourAvatar;
	}

	public void setTourAvatar(String tourAvatar) {
		this.tourAvatar = tourAvatar;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyImages() {
		return replyImages;
	}

	public void setReplyImages(String replyImages) {
		this.replyImages = replyImages;
	}

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

	public String getRoadlineDays() {
		return roadlineDays;
	}

	public void setRoadlineDays(String roadlineDays) {
		this.roadlineDays = roadlineDays;
	}

	public List<RoadLineDescribeForm> getRoadlineDescribes() {
		return roadlineDescribes;
	}

	public void setRoadlineDescribes(
			List<RoadLineDescribeForm> roadlineDescribes) {
		this.roadlineDescribes = roadlineDescribes;
	}

	public String getCommentUserAvatar() {
		return commentUserAvatar;
	}

	public void setCommentUserAvatar(String commentUserAvatar) {
		this.commentUserAvatar = commentUserAvatar;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public int getCollectId() {
		return collectId;
	}

	public void setCollectId(int collectId) {
		this.collectId = collectId;
	}

	public String getAvageRatioIndex() {
		return avageRatioIndex;
	}

	public void setAvageRatioIndex(String avageRatioIndex) {
		this.avageRatioIndex = avageRatioIndex;
	}

	public String getAvageFreshIndex() {
		return avageFreshIndex;
	}

	public void setAvageFreshIndex(String avageFreshIndex) {
		this.avageFreshIndex = avageFreshIndex;
	}

	public String getAvageServiceIndex() {
		return avageServiceIndex;
	}

	public void setAvageServiceIndex(String avageServiceIndex) {
		this.avageServiceIndex = avageServiceIndex;
	}

	public String getAvageTotalIndex() {
		return avageTotalIndex;
	}

	public void setAvageTotalIndex(String avageTotalIndex) {
		this.avageTotalIndex = avageTotalIndex;
	}

	public String getRoadlineBackground() {
		return roadlineBackground;
	}

	public void setRoadlineBackground(String roadlineBackground) {
		this.roadlineBackground = roadlineBackground;
	}

}
