package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TCollectRoadline;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.service.impl.RoadlineServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: CollectCancelRoadlineServet
 * @Description: 取消收藏一条路线
 * @author ganliang
 * @date 2015年7月29日 下午5:45:45
 */
@WebServlet(urlPatterns = { "/collectCancelRoadline.do" }, asyncSupported = true)
public class CollectCancelRoadlineServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//封装实体对象
		TCollectRoadline collectRoadline = (TCollectRoadline) BeanUtil
				.populate(request, TCollectRoadline.class);

		IRoadlineService roadlineService = new RoadlineServiceImpl();

		//根据收藏id 来取消收藏
		roadlineService.deleteCollectRoadlineById(collectRoadline
				.getCollectId());

		ResponseEntity.println(response);
	}
}