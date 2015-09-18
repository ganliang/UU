package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import com.uugty.app.web.form.RoadLinePublishForm;

/**
 * @ClassName: RoadlinePublishServet
 * @Description: 获取自己发布的路线集合【第一期一个用户只能发布一条路线】
 * @author ganliang
 * @date 2015年6月13日 上午10:30:24
 */
@WebServlet(urlPatterns = { "/roadlineList.do" }, asyncSupported = true)
public class RoadlineListServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TUser sessionUser = WebUtil.getUserFromSession(request);
		IRoadlineService roadlineService = new RoadlineServiceImpl();

		// 获取当前用户发布的路线
		List<RoadLinePublishForm> roadLinePublishs = roadlineService
				.findAllRoadLine(sessionUser);

		List<Object> roadLinePublishForms = new ArrayList<Object>();
		for (RoadLinePublishForm roadLinePublish : roadLinePublishs) {
			roadLinePublishForms.add(roadLinePublish);
		}
		// 返回多条路线
		ResponseEntity.printlns(response, roadLinePublishForms);
	}
}