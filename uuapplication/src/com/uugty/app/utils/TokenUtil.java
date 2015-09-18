package com.uugty.app.utils;

import javax.servlet.http.HttpServletRequest;

import com.uugty.app.constant.StringConstant;

/**
 * 
 * @ClassName: TokenUtil
 * @Description: 验证客户端的token
 * @author ganliang
 * @date 2015年6月8日 下午5:04:23
 */
public class TokenUtil {

	private static String INITTOKEN = "123456";// 服务端设置的一个初始令牌密码
	private static String TOKEN = "token";

	/**
	 * 
	 * @Title: validateToken
	 * @Description: 验证客户端传递过来的token是否有效
	 * @param @param request
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean validateToken(HttpServletRequest request) {
		// 获取客户端请求的servlet 和queryString
		String servletPath = request.getServletPath();
		// 获取servlet的urlPartten
		String servletName = servletPath.substring(
				servletPath.indexOf(StringConstant.FORWARDSLASH) + 1,
				servletPath.indexOf(StringConstant.DOT));
		// 获取参数的值
		String parameterValues = getParameterValues(request.getQueryString());
		// 通过md5加密来获取服务端的token
		String serverToken = MD5.MD5Encode(servletName + parameterValues
				+ INITTOKEN);
		String clientToken = request.getParameter(TOKEN);
		if (!serverToken.equals(clientToken)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: getParameterValues
	 * @Description: 获取到参数的值组成的字符串
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private static String getParameterValues(String queryString) {
		StringBuffer buffer = new StringBuffer();
		if (queryString != null) {
			String[] querys = queryString.split(StringConstant.AMP);
			for (String string : querys) {
				String[] split = string.split(StringConstant.EQUAL);
				if (!TOKEN.equals(split[0])) {
					buffer.append(split[1]);
				}
			}
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		// System.out
		// .println(removeToken("token=12345&id=1&username=%E7%94%98%E4%BA%AE&password=123456&password=123456"));
		System.out
				.println(getParameterValues("id=1&username=%E7%94%98%E4%BA%AE&token=123456&password=123456"));
	}
}
