package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: UserPurseServlet
 * @Description: 用户钱包
 * @author ganliang
 * @date 2015年7月22日 上午9:17:25
 */
@WebServlet(urlPatterns = "/userPurse.do", asyncSupported = false)
public class UserPurseServlet extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -1945248997311451399L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TUser sessionUser = WebUtil.getUserFromSession(request);
		IUserService userService = new UserServiceImpl();
 
		/** 获取用户钱包  */
		TUser user = userService.getUserById(sessionUser);

		ResponseEntity.println(response,
				new UserPurse(String.valueOf(user.getUserPurse())));
	}

	public static class UserPurse {

		private String userPurse;

		public UserPurse(String userPurse) {
			super();
			this.userPurse = userPurse;
		}

		public String getUserPurse() {
			return userPurse;
		}

		public void setUserPurse(String userPurse) {
			this.userPurse = userPurse;
		}
	}
}
