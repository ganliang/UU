package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TRoadline;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.service.impl.RoadlineServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: RoadlinePublishServet
 * @Description: 删除路线
 * @author ganliang
 * @date 2015年6月13日 上午10:30:24
 */
@WebServlet(urlPatterns = { "/roadlineDelete.do" }, asyncSupported = true)
public class RoadlineDeleteServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TRoadline roadline = (TRoadline) BeanUtil.populate(request,
				TRoadline.class);
		IRoadlineService roadlineService = new RoadlineServiceImpl();
		// 判断这条路线是否已经被预定 如果已经被预定了 就不允许修改了
		boolean isReview = roadlineService.getRoadlineReviewedById(roadline
				.getRoadlineId());
		if (isReview) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"该路线已经被预定了！");
			return;
		}
		// 删除路线
		roadlineService.roadlineDelete(roadline);
		ResponseEntity.println(response);
	}
}