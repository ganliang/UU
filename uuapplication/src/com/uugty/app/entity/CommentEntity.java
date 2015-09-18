package com.uugty.app.entity;

/**
 * @ClassName: CommentEntity
 * @Description: 评论的实体对象
 * @author ganliang
 * @date 2015年6月25日 下午12:06:26
 */
public class CommentEntity {

	private String commentId;// 评论id
	private String totalIndex;// 总评分
	private String commentContent;// 评论的内容
	private String commentImages;// 评论的图片
	private String commentDate;// 评论的时间

	private String replyContent;
	private String replyImages;

	private String userId;// 评论的用户id
	private String userName;// 用户姓名
	private String userAvatar;// 用户的头像

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

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
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
}
