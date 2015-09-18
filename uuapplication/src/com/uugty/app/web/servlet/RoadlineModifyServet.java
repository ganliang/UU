package com.uugty.app.web.servlet;

import java.io.IOException;

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
 * @ClassName: RoadlineModifyServet
 * @Description: 路线编辑
 * @author ganliang
 * @date 2015年7月4日 上午10:43:32
 */
@WebServlet(urlPatterns = { "/roadlineModify.do" }, asyncSupported = true)
public class RoadlineModifyServet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static RoadLinePublishForm roadLinePublishForm = null;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		final TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		final IRoadlineService roadlineService = new RoadlineServiceImpl();
		final String key = request.getParameter("key");
		// ModifyRoadlineEntity entity = roadlineService
		// .getRoadlineReviewedByUserId(sessionUser.getUserId());
		// if (entity == null) {
		// ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
		// "该用户未发布路线!");
		// return;
		// }

		// 修改路线
		new Thread(new Runnable() {
			@Override
			public void run() {

				roadLinePublishForm = RoadlinePublishServet
						.parseContentFromString(key);
				// 删除路线
				// roadlineService.deleteRoadlineByUserId(userId);
				// 将路线标识未已删除
				roadlineService.dropRoadlineByUserId(roadLinePublishForm
						.getRoadlineId());

				// 保存路线
				roadlineService.saveRoadLine(roadLinePublishForm, sessionUser);
			}
		}).start();
		ResponseEntity.println(response);
	}

	public static class ModifyRoadlineEntity {

		private int orderId;
		private int roadlineId;

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public int getRoadlineId() {
			return roadlineId;
		}

		public void setRoadlineId(int roadlineId) {
			this.roadlineId = roadlineId;
		}

	}
}