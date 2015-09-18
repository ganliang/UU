package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TCollectRoadline;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.service.impl.RoadlineServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: CollectRoadlineServet
 * @Description: 收藏一条路线
 * @author ganliang
 * @date 2015年7月29日 下午5:46:36
 */
@WebServlet(urlPatterns = { "/collectRoadline.do" }, asyncSupported = true)
public class CollectRoadlineServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        //封装
		TCollectRoadline collectRoadline = (TCollectRoadline) BeanUtil
				.populate(request, TCollectRoadline.class);

		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);

		collectRoadline.setCollectUserId(sessionUser.getUserId());

		IRoadlineService roadlineService = new RoadlineServiceImpl();

		// 保存收藏
		int collectId = roadlineService.saveCollectRoadline(collectRoadline);

		ResponseEntity.println(response, new CollectRoadlineEntity(collectId));
	}

	public static class CollectRoadlineEntity {
		private int collectId;

		public CollectRoadlineEntity(int collectId) {
			super();
			this.collectId = collectId;
		}

		public int getCollectId() {
			return collectId;
		}

		public void setCollectId(int collectId) {
			this.collectId = collectId;
		}
	}
}