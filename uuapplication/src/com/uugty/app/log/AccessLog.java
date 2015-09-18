package com.uugty.app.log;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.uugty.app.domain.TAccessLog;
import com.uugty.app.domain.TUser;
import com.uugty.app.service.IAccessLogService;
import com.uugty.app.service.impl.AccessLogServiceImpl;
import com.uugty.app.utils.WebUtil;

public class AccessLog {

	/**
	 * @Title: write
	 * @Description: 将访问的日志记录到数据库
	 * @param @param request
	 * @return void 返回类型
	 * @throws
	 */
	public static void write(HttpServletRequest request) {

		IAccessLogService accessLogService = new AccessLogServiceImpl();
		TAccessLog accessLog = new TAccessLog();

		String requestURL = request.getRequestURL().toString();
		accessLog.setAction(requestURL);

		String parameters = getParameters(request);
		accessLog.setParameter(parameters);

		TUser userFromSession = WebUtil.getUserFromSession(request);
		if (userFromSession != null) {
			accessLog.setUserId(userFromSession.getUserId());
		}
		HttpSession session = request.getSession(false);
		accessLog.setSessionId(session.getId());

		accessLogService.saveAccessLog(accessLog);
	}

	/**
	 * @Title: getParameters
	 * @Description: 获取到请求的参数
	 * @param @param request
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getParameters(HttpServletRequest request) {

		Map<String, String[]> parameterMap = request.getParameterMap();
		StringBuilder builder = new StringBuilder();
		for (String key : parameterMap.keySet()) {
			String value = request.getParameter(key);
			builder.append(key + "=" + value + "&");
		}
		int indexOf = builder.lastIndexOf("&");
		if (indexOf != -1) {
			builder.deleteCharAt(indexOf);
		}
		return builder.toString();
	}
}
