package com.uugty.app.log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.uugty.app.constant.EncodeConstant;
import com.uugty.app.constant.StringConstant;
import com.uugty.app.utils.DateUtil;

/**
 * @ClassName: LogUtil
 * @Description: 调用日志工具类,将每次请求的日志记录保存到servletContext中去,
 *               然后定时的将servletContext中的值保存到文件中去。
 * @author ganliang
 * @date 2015年6月15日 上午8:40:12
 */
public class LogUtil {

	private static final Logger LOG = Logger.getLogger(LogUtil.class);

	@SuppressWarnings("unchecked")
	@Deprecated
	public static void log(HttpServletRequest request, String log) {

		LOG.info("写入日志:" + log);

		ServletContext servletContext = request.getServletContext();

		Object object = servletContext.getAttribute("LOG_CONTEXT");

		ConcurrentLinkedQueue<String> logs = (ConcurrentLinkedQueue<String>) object;

		logs.add(log);
	}

	/**
	 * @Title: log
	 * @Description: 将servlet的请求都存入到内存中去
	 * @param @param request
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static void log(HttpServletRequest request) {

		String requestURL = request.getRequestURL().toString();

		StringBuilder builder = new StringBuilder();
		String parameters = getParameters(request);
		builder.append(requestURL + parameters);
		LOG.info("");
		LOG.info(builder);

		StringBuilder log = new StringBuilder();
		log.append(DateUtil.longDateFormat(new Date()) + "  ");
		log.append(requestURL + parameters);
		ServletContext servletContext = request.getServletContext();

		Object object = servletContext.getAttribute("LOG_CONTEXT");
		ConcurrentLinkedQueue<String> logs = (ConcurrentLinkedQueue<String>) object;
		logs.add(log.toString());
	}

	/**
	 * @Title: splitQueryString
	 * @Description: 将queryString 里面编码的数据解码
	 * @param @param queryString
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String splitQueryString(String queryString) {
		StringBuilder builder = new StringBuilder();
		if (queryString != null) {
			builder.append(StringConstant.QUESTION);
			String[] parameters = queryString.split(StringConstant.AMP);
			for (String str : parameters) {
				try {
					String parameter = URLDecoder.decode(str,
							EncodeConstant.UTF8);
					builder.append(parameter + StringConstant.AMP);
				} catch (UnsupportedEncodingException e) {
					LOG.error("参数{" + str + "}解码错误");
					throw new RuntimeException(e);
				}
			}
			builder.deleteCharAt(builder.lastIndexOf(StringConstant.AMP));
		}
		return builder.toString();
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
		builder.append("?");
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