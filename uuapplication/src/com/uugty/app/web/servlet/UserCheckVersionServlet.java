package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.entity.UserVersionEntity;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.PropertiesUtil;
import com.uugty.app.web.form.UserCheckVersionForm;

/**
 * @ClassName: UserCheckVersionServlet
 * @Description: 获取当前系统的版本号，检测是否更新系统
 * @author ganliang
 * @date 2015年6月16日 上午11:42:39
 */
@WebServlet(urlPatterns = { "/userCheckVersion.do" }, asyncSupported = false)
public class UserCheckVersionServlet extends HttpServlet {

	private static final long serialVersionUID = -516654987820116309L;

	public static final String NOT_VERSION_UPDATE_STRATEGY = "0";// 不需要更新系统
	public static final String WEAK_VERSION_UPDATE_STRATEGY = "1";// 弱类型更新系统
	public static final String STRONG_VERSION_UPDATE_STRATEGY = "2";// 必须要更新系统

	public static final String OPERATION_SYSTEM_ANDROID = "android";// android手机操作系统
	public static final String OPERATION_SYSTEM_IOS = "ios";// 苹果手机操作系统
	public static final String OPERATION_SYSTEM_WINPHONE = "winphone";// winphone手机操作系统

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UserCheckVersionForm versionForm = (UserCheckVersionForm) BeanUtil
				.populate(request, UserCheckVersionForm.class);

		String MSG = null;
		Properties properties = PropertiesUtil
				.getProperties("versionUpdate.properties");
		// 当前版本号
		String currVersion = properties.getProperty("current_system_version");
		// 获取策略
		String stratery = properties
				.getProperty(versionForm.getClientVersion());
		if (stratery != null) {
			// 获取跳转地址
			String redirectLocation = "";
			if (versionForm.getOsType() != null
					&& versionForm.getClientVersion() != null) {
				switch (versionForm.getOsType()) {
				case OPERATION_SYSTEM_ANDROID:
					redirectLocation = properties
							.getProperty("android_redirect_location");
					break;
				case OPERATION_SYSTEM_IOS:
					redirectLocation = properties
							.getProperty("ios_redirect_location");
					break;
				case OPERATION_SYSTEM_WINPHONE:
					redirectLocation = properties
							.getProperty("wp_redirect_location");
					break;
				default:
					MSG = "操作系统类型设置错误！";
				}
				if (MSG == null) {
					ResponseEntity.println(response, new UserVersionEntity(
							stratery, currVersion, redirectLocation));
				}
			} else {
				MSG = "客户端操作系统类型没有设置或者客户端传递的版本号不能为空！";
			}
		} else {
			MSG = "客户端传递的版本号没有对应的策略！";
		}
		if (MSG != null) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS, MSG);
		}
	}
}
