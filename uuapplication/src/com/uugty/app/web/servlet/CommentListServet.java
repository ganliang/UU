package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.ICommentService;
import com.uugty.app.service.impl.CommentServiceImpl;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: CommentPostServet
 * @Description: 获取评论列表的集合,获取到一个用户发布的评论的集合
 * @author ganliang
 * @date 2015年6月13日 上午11:45:43
 */
@WebServlet(urlPatterns = { "/commentList.do" }, asyncSupported = true)
public class CommentListServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TUser sessionUser = WebUtil.getUserFromSession(request);
		String userId = sessionUser.getUserId();

		ICommentService commentService = new CommentServiceImpl();

		// 获取评论
		List<Object> comment = commentService.getCommentListByUserId(userId);

		// 获取指数
		Object obj = commentService.getCommentIndexByUserId(userId);

		ResponseEntity.println(response, obj, comment);
	}
}