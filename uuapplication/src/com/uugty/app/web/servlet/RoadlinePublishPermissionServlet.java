package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.WebUtil;

@WebServlet(urlPatterns = { "/roadlinePublishPermission.do" }, asyncSupported = false)
public class RoadlinePublishPermissionServlet extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 7708550580838128793L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TUser user = WebUtil.getUserFromSession(request);
		IUserService userService = new UserServiceImpl();
		/*
		 * 判断该用户是否有权限发布路线 
		 * 用户发布了一条路线，如果没有订单交易完成,则不允许发布路线
		 */
		boolean isPermit= userService.checkRoadlinePublishPermission(user.getUserId());
		if(isPermit){
			ResponseEntity.println(response);
		}else{
			ResponseEntity.println(response,ResponseEntity.WARN_STATUS,"你没有权限再次发布路线!");
		}
	}
}
