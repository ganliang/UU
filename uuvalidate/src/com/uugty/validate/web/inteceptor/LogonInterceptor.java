package com.uugty.validate.web.inteceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.uugty.validate.domain.User;

public class LogonInterceptor implements HandlerInterceptor {

	private static List<String> list = new ArrayList<String>();

	static {
		list.add("/index.jsp");
		list.add("/user/login.do");
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		// 处理乱码问题
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String servletPath = request.getServletPath();
		if (list.contains(servletPath)) {
			return true;
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("login_user");
			if (user != null) {
				return true;
			}
		}
		request.setAttribute("login_error", "用户未登录!");
		response.sendRedirect("../index.jsp");
		// request.getRequestDispatcher("/index.jsp").forward(request,
		// response);
		return false;
	}
}
