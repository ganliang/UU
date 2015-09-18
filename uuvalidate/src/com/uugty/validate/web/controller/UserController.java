package com.uugty.validate.web.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uugty.validate.domain.User;
import com.uugty.validate.service.IUserService;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
	@Resource(name = IUserService.SERVER_NAME)
	private IUserService service;

	@RequestMapping("/login.do")
	public ModelAndView login(User user, Map<String, String> model,
			HttpServletRequest request) {
		String userName = user.getUserName();
		String userPassword = user.getUserPassword();
		HttpSession session = request.getSession();
		if ("adminUU".equals(userName)) {
			if ("uugty123456".equals(userPassword)) {
				session.setAttribute("login_user", user);
				return new ModelAndView("login/main");
			}
		}
		session.setAttribute("login_error", "你输入的用户名或者密码错误!");
		return new ModelAndView("redirect:/index.jsp", model);
	}
}
