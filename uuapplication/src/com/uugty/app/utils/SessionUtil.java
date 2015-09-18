package com.uugty.app.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.uugty.app.domain.TUser;

/**
 * @ClassName: SessionValidateUtil
 * @Description: 设置session失效
 * @author ganliang
 * @date 2015年8月19日 上午9:48:11
 */
public class SessionUtil {

	/**
	 * session 过期时间
	 */
	private static final int SESSION_TIME = 120;

	/**
	 * @Title: isExpire
	 * @Description: 检测当前的session是否过期
	 * @param @param session
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isExpire(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			return true;
		}

		long lastAccessedTime = session.getLastAccessedTime();
		int subMinute = DateUtil.subMinute(lastAccessedTime);

		if (subMinute > SESSION_TIME) {
			session.removeAttribute(TUser.SESSION_USER);
			return true;
		}

		return false;
	}
}
