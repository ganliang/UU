package com.uugty.app.domain;

import java.io.Serializable;

public class TComment implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -6249040136614871960L;

	private int commentId;   //主键
	private String commentUserId;  //评论的用户id
	private String commentedUserId;  //被评论的用户id

	private String commentType;// 评论的类型 1 个人评价 ;2 订单评价

	private String commentContent; //评论的内容
	private int orderId;  //订单id
	private float serviceIndex; //服务指数
	private float freshIndex;  //新鲜指数

	private float ratioIndex;  //信价比指数
	private float totalIndex;  //总评分指数
	private String commentImages;  //评论的图片集合
	private String commentDate;   //评论的时期

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}

	public String getCommentUserId() {
		return commentUserId;
	}

	public void setCommentedUserId(String commentedUserId) {
		this.commentedUserId = commentedUserId;
	}

	public String getCommentedUserId() {
		return commentedUserId;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setServiceIndex(float serviceIndex) {
		this.serviceIndex = serviceIndex;
	}

	public float getServiceIndex() {
		return serviceIndex;
	}

	public void setFreshIndex(float freshIndex) {
		this.freshIndex = freshIndex;
	}

	public float getFreshIndex() {
		return freshIndex;
	}

	public void setRatioIndex(float ratioIndex) {
		this.ratioIndex = ratioIndex;
	}

	public float getRatioIndex() {
		return ratioIndex;
	}

	public void setTotalIndex(float totalIndex) {
		this.totalIndex = totalIndex;
	}

	public float getTotalIndex() {
		return totalIndex;
	}

	public void setCommentImages(String commentImages) {
		this.commentImages = commentImages;
	}

	public String getCommentImages() {
		return commentImages;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommentDate() {
		return commentDate;
	}

	/**
	 * 非持久化字段
	 */
	public String subCommentContent;
	public String userName;// 用户姓名
	public String userAvatar;// 用户的头像

	public String getSubCommentContent() {
		return subCommentContent;
	}

	public void setSubCommentContent(String subCommentContent) {
		this.subCommentContent = subCommentContent;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

}
