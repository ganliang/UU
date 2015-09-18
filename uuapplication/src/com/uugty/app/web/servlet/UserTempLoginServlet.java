package com.uugty.app.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TTempUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: UserTempLoginServlet
 * @Description:临客登录
 * @author ganliang
 * @date 2015年7月9日 上午9:38:16
 */
@WebServlet(urlPatterns = { "/userTempLogin.do" }, asyncSupported = false)
public class UserTempLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -516654987820116309L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		final TTempUser tempUser = (TTempUser) BeanUtil.populate(request,
				TTempUser.class);
		tempUser.setTempShout(URLEncoder.encode(tempUser.getTempShout(),
				"UTF-8"));
		final IUserService userService = new UserServiceImpl();
		// 查询该临客是否曾经登录
		TTempUser temp = userService.getTempUserByUUID(tempUser);
		if (temp != null) {
			// 临客两次登录的大叫一声不一样
			if (!temp.getTempShout().equals(tempUser.getTempShout())) {
				// 异步更新临客记录
				new Thread(new Runnable() {
					@Override
					public void run() {
						userService.updateTempUser(tempUser);
					}
				}).start();
			}
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"该临客已经登录过");
		}
		// 异步插入 临客登录数据
		else {
			new Thread(new Runnable() {
				@Override
				public void run() {
					userService.saveTempUser(tempUser);
				}
			}).start();
			ResponseEntity.println(response);
		}
	}
}
