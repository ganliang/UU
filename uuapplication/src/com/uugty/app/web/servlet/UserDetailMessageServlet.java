package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.LoginEntity;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.ICommentService;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.CommentServiceImpl;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.Page;

/**
 * 
 * @ClassName: UserDetailMessageServlet
 * @Description: 获取登录用户的个人信息
 * @author ganliang
 * @date 2015年6月11日 下午5:49:22
 */
@WebServlet(urlPatterns = { "/userDetailMessage.do" }, asyncSupported = true)
public class UserDetailMessageServlet extends HttpServlet {

	private static final long serialVersionUID = -3193012127070105599L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TUser telUser = (TUser) BeanUtil.populate(request, TUser.class);
		// 获取该用户被评论的信息
		ICommentService commentService = new CommentServiceImpl();

		IUserService userService = new UserServiceImpl();

		// 获取到登录时获取的信息
		LoginEntity entity = userService.getLoginUserMessage(telUser
				.getUserId());
		Page page = new Page(2, 1);
		if (entity == null) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"该用户未注册！");
			return;
		}
		// 获取到评论
		List<Object> comments = commentService.getCommentsByCommentedUserId(
				telUser.getUserId(), page);

		ResponseEntity.println(response, entity, comments);
	}
}
