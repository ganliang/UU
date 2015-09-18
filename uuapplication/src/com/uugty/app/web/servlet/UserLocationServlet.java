package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: UserNeighborServlet
 * @Description: 根据当前用户的位置，来搜索附近的游客和导游
 * @author ganliang
 * @date 2015年6月12日 下午4:54:35
 */
@WebServlet(urlPatterns = { "/userLocation.do" }, asyncSupported = false)
public class UserLocationServlet extends HttpServlet {

	private static final long serialVersionUID = 800828469122812854L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装数据
		TUser user = (TUser) BeanUtil.populate(request, TUser.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		IUserService userService = new UserServiceImpl();
		user.setUserId(sessionUser.getUserId());
		List<Object> userlist = userService.getNeighborUser(user);
		ResponseEntity.printlns(response, userlist);
	}
}
