package com.uugty.app.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName: TokenUtil
 * @Description: 验证客户端的token
 * @author ganliang
 * @date 2015年6月8日 下午5:04:23
 */
public class Token2Util {

	private static String SUFFIX = "uuk";
	private static String token = "token";

	/**
	 * @Title: validateToken
	 * @Description: 判断客户端和服务端的令牌是否一致
	 * @param @param request
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean validateToken(HttpServletRequest request) {
		String clientToken = request.getParameter(token);
		// String key = request.getParameter("key");
		// System.out.println("client key--->>>" + key);
		String serverToken = token(request);
		return serverToken.equals(clientToken);
	}

	/**
	 * @Title: token
	 * @Description: 获取到服务端的令牌
	 * @param @param request
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	private static String token(HttpServletRequest request) {
		String path = request.getServletPath();
		path = path.replace("/", "");
		path = path.replace(".do", "");
		path = path.substring(path.length() - 3);
		String parameters = getParameters(request);
		String initToken = path + parameters + SUFFIX;
		// System.out.println("server key--->>>" + initToken);
		return MD5.MD5Encode(initToken);
	}

	/**
	 * @Title: getParameters
	 * @Description: 获取到请求的参数
	 * @param @param request
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	private static String getParameters(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		// for (Entry<String, String[]> entry : parameterMap.entrySet()) {
		// System.out.println(entry.getKey() + "---->>>>"
		// + entry.getValue()[0]);
		// }

		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (String key : parameterMap.keySet()) {
			if (!token.equals(key)) {
				i++;
				String value = request.getParameter(key);
				if (value != null && !"".equals(value)) {
					if (value.length() > 3) {
						builder.append(value.substring(value.length() - 3));
					} else {
						builder.append(value);
					}
				}
			}
			if (i == 5) {
				break;
			}
		}
		return builder.toString();
	}
}
