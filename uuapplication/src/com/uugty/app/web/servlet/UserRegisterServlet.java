package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.LoginEntity;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.utils.EasemobUtil;

/**
 * @ClassName: UserUURegisterServlet
 * @Description: 用户注册接口
 * @author ganliang
 * @date 2015年6月11日 下午5:46:09
 */
@WebServlet(urlPatterns = { "/security/userRegister.do" }, asyncSupported = true)
public class UserRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 8828694073290915336L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装数据
		final TUser user = (TUser) BeanUtil.populate(request, TUser.class);
		final IUserService userService = new UserServiceImpl();
		// 获取服务端的验证码
		String tokenFromSession = WebUtil.getVerycodeFromSession(request);
		// tokenFromSession = "123456";// 测试使用
		if (tokenFromSession != null) {
			// 验证客户端获取的验证码和服务器的验证码相等
			if (tokenFromSession.equals(user.getVeryCode())) {
				// 判断该手机号码是否已经被注册了
				TUser userByTelphone = userService.getUserByTelphone(user);
				// 该手机号码未被注册
				if (userByTelphone == null) {
					// 异步注册用户
					user.setUserId(StringUtil.getUUID());
					user.setUserRegisterDate(new Date());
					user.setUserLastLoginDate(new Date());
					user.setUserTelValidate(1);
					user.setUserTelValidateDate(new Date());
					user.setUserLoginType(1);
					user.setUserIsLogin(1);
					user.setUserLoginCount(1);
					user.setUserStatus("1");
					final String easemobPassword = EasemobUtil
							.getEasemobPassword(user.getUserId());
					user.setUserEasemobPassword(easemobPassword);

					WebUtil.putUserToSession(request, user);

					new Thread(new Runnable() {
						@Override
						public void run() {
							TUser saveUser = userService.saveUser(user);
							// 注册环信
							EasemobUtil.createIMUser(saveUser.getUserId(),
									easemobPassword);
							// 环信添加系统用户
							EasemobUtil.addFriendSingle(saveUser.getUserId());
						}
					}).start();

					LoginEntity entity = (LoginEntity) BeanUtil.setPropertys(
							user, LoginEntity.class);

					ResponseEntity.println(response, entity, null);
				}
				// 该手机号码已经被注册了
				else {
					ResponseEntity.println(response,
							ResponseEntity.WARN_STATUS,
							"手机号码{" + user.getUserTel() + "}已经被注册了");
				}
			} else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"验证码不相等");
			}
		}
		// 验证码过期
		else {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"验证码过期!");
		}
	}
}
