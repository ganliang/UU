package com.uugty.app.utils;

import java.util.Date;

import com.uugty.app.domain.TUser;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;

public class PayPasswordUtil {
	// 支付密码 最多输入错误次数
	public static final int PAY_PASSWORD_WRONG_CONT = 5;

	// 支付密码错误 最大次数后，多久解锁 单位 (m)
	public static final int PAY_PASSWORD_WRONG_LOCKING = 3 * 60;

	/**
	 * @Title: validatePayPassword
	 * @Description: 验证支付密码
	 * @param @param clientPayPassword
	 * @param @param serverPayPassword
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String validatePayPassword(String clientPayPassword,
			TUser sessionUser) {
		IUserService userService = new UserServiceImpl();
		String msg = null;
		Date currentDate = new Date();

		// 获取支付 错误次数,最后一次错误的时间
		TUser payUser = userService.getUserById(sessionUser);
		String payCountStr = payUser.getUserPayCount();
		int payCount = Integer.parseInt(payCountStr);

		Date payTime = payUser.getUserPayTime();
		int minutes = DateUtil.calculateMinutes(payTime, new Date());

		TUser user = new TUser();
		user.setUserId(sessionUser.getUserId());

		user.setUserPayCount("0");
		user.setUserPayTime(currentDate);

		String serverPayPassword = sessionUser.getUserPayPassword();
		if (minutes > PAY_PASSWORD_WRONG_LOCKING) {
			if (clientPayPassword == null
					|| !clientPayPassword.equals(serverPayPassword)) {
				msg = "支付密码不正确";
				user.setUserPayCount("1");
			}
		}
		//
		else {
			if (payCount >= PAY_PASSWORD_WRONG_CONT) {
				msg = "支付密码输入次数过多,请等待 "
						+ (PAY_PASSWORD_WRONG_LOCKING - minutes)
						+ " 分钟,或者修改密码!";
				user.setUserPayCount(String.valueOf(payCount + 1));
				user.setUserPayTime(null);
			} else {
				if (clientPayPassword == null
						|| !clientPayPassword.equals(serverPayPassword)) {
					msg = "支付密码不正确";
					user.setUserPayCount(String.valueOf(payCount + 1));
				}
			}
		}
		// 更新支付密码输入次数
		userService.updateUser(user);
		return msg;
	}
}
