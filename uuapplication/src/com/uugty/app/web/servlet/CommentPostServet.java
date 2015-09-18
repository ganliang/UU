package com.uugty.app.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TComment;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.ICommentService;
import com.uugty.app.service.impl.CommentServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: CommentPostServet
 * @Description: 发布评论 游客对导游的评论
 * @author ganliang
 * @date 2015年6月13日 上午11:45:43
 */
@WebServlet(urlPatterns = { "/commentPost.do" }, asyncSupported = true)
public class CommentPostServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TComment comment = (TComment) BeanUtil
				.populate(request, TComment.class);
		String commentContent = comment.getCommentContent();
		if (commentContent != null && !"".equals(commentContent)) {
			comment.setCommentContent(URLEncoder
					.encode(commentContent, "UTF-8"));
		}
		ICommentService commentService = new CommentServiceImpl();
		// 保存评论
		int commentId = commentService.saveComment(comment);

		ResponseEntity.println(response, new CommentEntity(commentId));
	}

	public static class CommentEntity {
		private int commentId;

		public CommentEntity(int commentId) {
			super();
			this.commentId = commentId;
		}

		public int getCommentId() {
			return commentId;
		}

		public void setCommentId(int commentId) {
			this.commentId = commentId;
		}
	}
}