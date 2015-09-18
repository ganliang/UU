package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;

/**
 * @ClassName: UserLoginServlet
 * @Description: 登出系统
 * @author ganliang
 * @date 2015年6月26日 下午5:54:47
 */
@WebServlet(urlPatterns = { "/userLogout.do" }, asyncSupported = true)
public class UserLogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -3137740832150185249L;

	private static final Logger log = Logger.getLogger(UserLogoutServlet.class);

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			log.info("用户注销 sessionID--->>>" + session.getId());
			session.removeAttribute(TUser.SESSION_USER);
		}
		ResponseEntity.println(response);
	}
}
