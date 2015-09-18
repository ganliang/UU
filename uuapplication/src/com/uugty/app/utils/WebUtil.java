package com.uugty.app.utils;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.uugty.app.domain.TUser;
import com.uugty.app.log.LogWriter;

/**
 * 
 * @ClassName: WebUtil
 * @Description:
 * @author ganliang
 * @date 2015年6月9日 下午5:51:15
 */
public class WebUtil {

	/**
	 * @Title: putUserToSession
	 * @Description: 将当前登录的用户保存到session中去
	 * @param @param request
	 * @param @param weChatUser
	 * @return void 返回类型
	 * @throws
	 */
	public static void putUserToSession(HttpServletRequest request,
			TUser weChatUser) {
		HttpSession session = request.getSession(true);
		session.setAttribute(TUser.SESSION_USER, weChatUser);
	}

	/**
	 * @Title: getUserFromSession
	 * @Description: 从服务器获取当前登录的用户
	 * @param @param request
	 * @param @return
	 * @return TUser 返回类型
	 * @throws
	 */
	public static TUser getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		Object obj = session.getAttribute(TUser.SESSION_USER);
		return obj != null ? (TUser) obj : null;
	}

	/**
	 * @Title: putLogToContext
	 * @Description: 将日志记录到文件中去
	 * @param @param log
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public static void putLogToContext(HttpServletRequest request, String log) {
		int currentHour = DateUtil.getCurrentHour();
		ServletContext servletContext = request.getServletContext();
		for (int i = 0; i < 24; i++) {
			Object obj = servletContext.getAttribute(String.valueOf(i));
			ConcurrentLinkedQueue<String> logs = null;
			// 非当前时刻，将日志记录到文件中去，并值空
			if (i != currentHour) {
				if (obj != null) {
					logs = (ConcurrentLinkedQueue<String>) obj;
					// 另开一个线程，将数据写入到文件中去
					new LogWriter(logs).start();
					servletContext.setAttribute(String.valueOf(i), null);
				}
			}
			// 将当前的日志记录到context中去
			else {
				synchronized (logs) {
					obj = servletContext.getAttribute(String.valueOf(i));
					if (obj != null) {
						logs = (ConcurrentLinkedQueue<String>) obj;
					} else {
						logs = new ConcurrentLinkedQueue<String>();
					}
					logs.add(log);
					servletContext.setAttribute(String.valueOf(i), logs);
				}
			}
		}
	}

	/**
	 * @Title: getTokenFromSession
	 * @Description: 从服务器获取令牌的session
	 * @param @param request
	 * @return void 返回类型
	 * @throws
	 */
	public static String getVerycodeFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		Object tokenObj = session.getAttribute(TUser.VERIFYCODE);
		Object expirObj = session.getAttribute(TUser.EXPIR);
		// session.removeAttribute(TUser.VERIFYCODE);
		// session.removeAttribute(TUser.EXPIR);
		if (tokenObj != null && expirObj != null) {
			String token = String.valueOf(tokenObj);
			long expir = Long.parseLong(String.valueOf(expirObj));
			// 验证码未过期
			if (DateUtil.subMinute(expir) < TUser.LENGTH) {
				return token;
			}
		}
		return null;
	}

	/**
	 * @Title: setTokenToSession
	 * @Description: 往服务端设置token值
	 * @param @param request
	 * @param @param token
	 * @return void 返回类型
	 * @throws
	 */
	public static void putVerycodeToSession(HttpServletRequest request,
			String token) {
		HttpSession session = request.getSession(false);
		session.setAttribute(TUser.VERIFYCODE, token);
		session.setAttribute(TUser.EXPIR, DateUtil.currentTime());
	}

	/**
	 * @Title: getRemoteIP
	 * @Description: 获取到远程客户端的ip
	 * @param @param request
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getRemoteIP(HttpServletRequest request) {
		String ip = request.getRemoteHost();
		return ip;
	}
}
