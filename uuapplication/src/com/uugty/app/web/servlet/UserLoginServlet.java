package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.Date;
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
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: UserUULoginServlet
 * @Description: uu平台用户登录接口
 * @author ganliang
 * @date 2015年6月11日 下午5:41:18
 */
@WebServlet(urlPatterns = { "/security/userLogin.do" }, asyncSupported = true)
public class UserLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -3137740832150185249L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 参数封装
		TUser user = (TUser) BeanUtil.populate(request, TUser.class);
		IUserService userService = new UserServiceImpl();
		TUser telUser = userService.getUserByTelphone(user);
		// 判断密码是否相等
		if (telUser != null
				&& telUser.getUserPassword().equals(user.getUserPassword())) {
			// 密码相等 判断用户是否在线 ,防止异地登录
			switch (telUser.getUserIsLogin()) {
			// 当前用户未登录
			case -1:
				break;
			// 当前用户已经登录
			case 1:
				// // 对原来登录的用户发送一条通知下线的消息
				// EasemobUtil.sendMessage(telUser.getUserTel(),
				// "你的账号在另外一个地方登录,请重新登录！");
				break;
			}
			WebUtil.putUserToSession(request, telUser);

			user.setUserId(telUser.getUserId());
			user.setUserLoginCount(telUser.getUserLoginCount() + 1);
			user.setUserLoginType(1);
			user.setUserIsLogin(1);
			user.setUserStatus("1");
			user.setUserLastLoginDate(new Date());
			userService.updateUser(user);

			// 获取该用户被评论的信息
			ICommentService commentService = new CommentServiceImpl();
			Page page = new Page(2, 1);
			List<Object> comments = commentService
					.getCommentsByCommentedUserId(telUser.getUserId(), page);

			LoginEntity entity = userService.getLoginUserMessage(telUser
					.getUserId());

			ResponseEntity.println(response, entity, comments);
		}
		// 登陆失败
		else {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"用户登录失败,请查看你的用户名和密码是否匹配!");
		}
	}
}
