package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

public class TCommentReply implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -5218624131109912379L;

	private int replyId; //主键id

	private String replyContent; //回复内容

	private String replyImages; //回复的图片

	private int commentId; //回复的评论id

	private Date replyDate; //回复日期

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
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

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

}
