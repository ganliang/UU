package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TMark;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.service.impl.RoadlineServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: RoadlineSearchServet
 * @Description: 搜索路线
 * @author ganliang
 * @date 2015年7月27日 下午5:34:14
 */
@WebServlet(urlPatterns = { "/roadlineSearch.do" }, asyncSupported = true)
public class RoadlineSearchServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TMark mark = (TMark) BeanUtil.populate(request, TMark.class);

		IRoadlineService roadlineService = new RoadlineServiceImpl();

		// 根据标签搜索路线
		List<Object> roadLinePublishs = roadlineService
				.searchRoadlineList(mark);

		ResponseEntity.printlns(response, roadLinePublishs);
	}
}