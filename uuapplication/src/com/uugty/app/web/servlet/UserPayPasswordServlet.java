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
 * @ClassName: UserSetPayPassword
 * @Description: 设置支付密码
 * @author ganliang
 * @date 2015年7月23日 上午9:28:23
 */
@WebServlet(urlPatterns = { "/security/userPayPassword.do" }, asyncSupported = false)
public class UserPayPasswordServlet extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 859433303528099684L;

	private static final String USER_PAY_PASSWORD_SET = "1";// 设置支付密码
	private static final String USER_PAY_PASSWORD_CHANGE = "2";// 修改支付密码
	private static final String USER_PAY_PASSWORD_FORGET = "3";// 忘记支付密码

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TUser user = new TUser();
		String type = request.getParameter("type");
		IUserService userService = new UserServiceImpl();

		TUser sessionUser = WebUtil.getUserFromSession(request);
		user.setUserId(sessionUser.getUserId());

		switch (type) {
		// 设置支付密码
		case USER_PAY_PASSWORD_SET:
			String userPayPassword = request.getParameter("userPayPassword");
			user.setUserPayPassword(userPayPassword);

			sessionUser.setUserPayPassword(userPayPassword);
			WebUtil.putUserToSession(request, sessionUser);

			userService.updateUser(user);
			ResponseEntity.println(response);
			break;
		// 修改支付密码
		case USER_PAY_PASSWORD_CHANGE:
			String oldPayPassword = request.getParameter("oldPayPassword");
			String newPayPassword = request.getParameter("newPayPassword");
			if (sessionUser.getUserPayPassword().equals(oldPayPassword)) {
				user.setUserPayPassword(newPayPassword);

				sessionUser.setUserPayPassword(newPayPassword);
				WebUtil.putUserToSession(request, sessionUser);

				userService.updateUser(user);
				ResponseEntity.println(response);
			} else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"支付密码不正确!");
			}
			break;
		// 忘记支付密码
		case USER_PAY_PASSWORD_FORGET:
			String veryCode = request.getParameter("veryCode");
			userPayPassword = request.getParameter("userPayPassword");
			String verycodeFromSession = WebUtil
					.getVerycodeFromSession(request);
			if (veryCode != null && veryCode.equals(verycodeFromSession)) {
				user.setUserPayPassword(userPayPassword);

				sessionUser.setUserPayPassword(userPayPassword);
				WebUtil.putUserToSession(request, sessionUser);

				userService.updateUser(user);
				ResponseEntity.println(response);
			} else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"验证码不正确!");
			}
			break;
		default:
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"参数设置不正确!");
			break;
		}
	}
}
