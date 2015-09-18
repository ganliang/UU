package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.entity.UserDetailEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;

/**
 * @ClassName: UserSimpleMessageServlet
 * @Description: 获取简单的用户详情
 * @author ganliang
 * @date 2015年8月4日 下午1:13:20
 */
@WebServlet(urlPatterns = { "/userSimpleMessage.do" }, asyncSupported = true)
public class UserSimpleMessageServlet extends HttpServlet {

	private static final long serialVersionUID = -3193012127070105599L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		UserDetailEntity simple = new UserDetailEntity();
		simple.setUserId(userId);
		IUserService userService = new UserServiceImpl();

		// 获取用户个人的信息
		UserDetailEntity entity = userService.getUserDetailMessage(simple);
		ResponseEntity.println(response, entity);
	}
}
