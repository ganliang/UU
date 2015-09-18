package com.uugty.app.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TCommentReply;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.ICommentService;
import com.uugty.app.service.impl.CommentServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: CommentReplyServet
 * @Description: 回复
 * @author ganliang
 * @date 2015年7月24日 下午8:27:55
 */
@WebServlet(urlPatterns = { "/commentReply.do" }, asyncSupported = true)
public class CommentReplyServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TCommentReply reply = (TCommentReply) BeanUtil.populate(request,
				TCommentReply.class);
		String replyContent = reply.getReplyContent();
		if (replyContent != null && !"".equals(replyContent)) {
			reply.setReplyContent(URLEncoder.encode(replyContent, "UTF-8"));
		}
		ICommentService commentService = new CommentServiceImpl();
		// 保存评论回复
		int replyCommentId = commentService.saveCommentReply(reply);

		ResponseEntity
				.println(response, new commentReplyEntity(replyCommentId));
	}

	public static class commentReplyEntity {
		private int replyId;

		public commentReplyEntity(int replyId) {
			super();
			this.replyId = replyId;
		}

		public int getReplyId() {
			return replyId;
		}

		public void setReplyId(int replyId) {
			this.replyId = replyId;
		}
	}
}