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
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.service.impl.RoadlineServiceImpl;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: CollectRoadlineListServet
 * @Description: 获取收藏路线的列表
 * @author ganliang
 * @date 2015年7月29日 下午5:46:10
 */
@WebServlet(urlPatterns = { "/collectRoadlineList.do" }, asyncSupported = true)
public class CollectRoadlineListServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);

		IRoadlineService roadlineService = new RoadlineServiceImpl();

		String userId = sessionUser.getUserId();

		// 获取登录用户收藏的一些路线信息
		List<Object> list = roadlineService.getCollectRoadlineByUserId(userId);

		ResponseEntity.printlns(response, list);

	}
}