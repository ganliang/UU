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
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: UserForgetPasswordServlet
 * @Description: 忘记密码
 * @author ganliang
 * @date 2015年7月15日 下午5:56:54
 */
@WebServlet(urlPatterns = { "/userForgetPassword.do" }, asyncSupported = false)
public class UserForgetPasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 800828469122812854L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装数据
		TUser user = (TUser) BeanUtil.populate(request, TUser.class);
		String tokenFromSession = WebUtil.getVerycodeFromSession(request);

		IUserService userService = new UserServiceImpl();
		if (tokenFromSession != null) {
			// 验证客户端获取的验证码和服务器的验证码相等
			if (tokenFromSession.equals(user.getVeryCode())) {
				TUser userByTelphone = userService.getUserByTelphone(user);
				// 手机号码未注册
				if (userByTelphone == null) {
					ResponseEntity.println(response,
							ResponseEntity.WARN_STATUS,
							"手机号码{" + user.getUserTel() + "}未注册");
					return;
				}
				userService.modifyUserPassword(user.getUserTel(),
						user.getUserPassword());
				ResponseEntity.println(response);
			} else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"验证码不相等!");
			}
		} else {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"验证码过期!");
		}
	}
}
