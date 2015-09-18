package com.uugty.app.service;

import java.util.List;

import com.uugty.app.domain.TComment;
import com.uugty.app.domain.TCommentReply;
import com.uugty.app.entity.CommentReplyEntity;
import com.uugty.app.utils.Page;

public interface ICommentService {

	public List<Object> getCommentsByCommentedUserId(String userId, Page page);

	public List<Object> getCommentListByRoadlineId(int roadlineId);

	public CommentReplyEntity getCommentIndexByRoadlineId(int roadlineId);

	/*
	 * 评论的功能模块
	 */
	public int getCommentsCountByCommentedUserId(String userId);

	public int saveComment(TComment comment);

	public int saveCommentReply(TCommentReply reply);

	public List<Object> getCommentListByUserId(String userId);

	public Object getCommentIndexByUserId(String userId);

}
