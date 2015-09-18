package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.log.TokenTimerTask;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.utils.Telecom;

/**
 * @ClassName: UserVerificationCodeServlet
 * @Description: 获取用户的验证码
 * @author ganliang
 * @date 2015年6月11日 下午5:53:14
 */
@WebServlet(urlPatterns = { "/userVerificationCode.do" }, asyncSupported = true)
public class UserVerificationCodeServlet extends HttpServlet {

	private static final long serialVersionUID = 2053347619779362626L;

	private static final String REGISTER_VERIFY_CODE = "0";// 获取注册的时候获取的验证码
	private static final String FORGET_PASSWORD_VERIFY_CODE = "1";// 用户找回密码时候获取的验证码
	private static final String VALIDATE_USER_TELPHONE = "2";// 验证手机号码的验证码
	private static final String VALIDATE_USER_PAY_PASSWORD = "3";// 验证支付密码的验证码

	private static final int MAX_COUNT = 5;

	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获取封装数据
		TUser user = (TUser) BeanUtil.populate(request, TUser.class);

		String uuid = user.getUuid();
		HashMap<String, Integer> tokenMap = (HashMap<String, Integer>) request
				.getServletContext()
				.getAttribute(TokenTimerTask.TOKEN_VALIDATE);
		if (tokenMap != null && tokenMap.size() > 0) {
			Integer count = tokenMap.get(uuid);
			if (count != null) {
				if (count == MAX_COUNT) {
					ResponseEntity.println(response,
							ResponseEntity.WARN_STATUS, "获取验证码次数过多！");
					return;
				} else {
					tokenMap.put(uuid, count + 1);
				}
			} else {
				tokenMap.put(uuid, 1);
			}
		} else {
			tokenMap.put(uuid, 1);
		}
		// 获取6位验证码
		String randomNum = StringUtil.getRandom(6);
		IUserService userService = new UserServiceImpl();
		TUser userByTelphone = null;
		switch (user.getType()) {
		// 注册的时候,获取验证码
		case REGISTER_VERIFY_CODE:
			userByTelphone = userService.getUserByTelphone(user);
			// 手机号码已经被注册
			if (userByTelphone != null) {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"手机号码{" + user.getUserTel() + "}已经被注册了");
				return;
			}
			// 保存到session中
			WebUtil.putVerycodeToSession(request, randomNum);
			// 发送短信通知,验证码
			boolean isSuccess = Telecom.sendSMS(user.getUserTel(), randomNum);
			if (isSuccess) {
				ResponseEntity.println(response);
			} else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"短信发送失败");
			}
			break;
		// 忘记密码的时候,获取验证码
		case FORGET_PASSWORD_VERIFY_CODE:
			userByTelphone = userService.getUserByTelphone(user);
			// 手机号码未注册
			if (userByTelphone == null) {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"手机号码{" + user.getUserTel() + "}未注册");
				return;
			}
			WebUtil.putVerycodeToSession(request, randomNum);
			isSuccess = Telecom.sendSMS(user.getUserTel(), randomNum);
			if (isSuccess) {
				ResponseEntity.println(response);
			} else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"短信发送失败");
			}
			break;
		// 验证手机号码的验证码
		case VALIDATE_USER_TELPHONE:
			userByTelphone = userService.getUserByTelphone(user);
			// 手机号码未注册
			if (userByTelphone != null) {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"手机号码{" + user.getUserTel() + "}已经被验证了");
				return;
			}
			// 保存到session中
			WebUtil.putVerycodeToSession(request, randomNum);
			// 发送短信通知,验证码
			isSuccess = Telecom.sendSMS(user.getUserTel(), randomNum);
			if (isSuccess) {
				ResponseEntity.println(response);
			} else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"短信发送失败");
			}
			break;
		// 验证支付密码的验证码
		case VALIDATE_USER_PAY_PASSWORD:
			// 保存到session中
			WebUtil.putVerycodeToSession(request, randomNum);
			// 发送短信通知,验证码
			isSuccess = Telecom.sendSMS(user.getUserTel(), randomNum);
			if (isSuccess) {
				ResponseEntity.println(response);
			} else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"短信发送失败");
			}
			break;
		default:
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"客户端类型设置错误");
			break;
		}
	}
}