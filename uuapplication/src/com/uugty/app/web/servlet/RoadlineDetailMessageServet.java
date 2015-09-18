package com.uugty.app.web.servlet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.CommentEntity;
import com.uugty.app.entity.CommentReplyEntity;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.entity.RoadlineDetailEntity;
import com.uugty.app.service.ICommentService;
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.CommentServiceImpl;
import com.uugty.app.service.impl.RoadlineServiceImpl;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.form.RoadLinePublishForm;

/**
 * @ClassName: RoadlineDetailMessageServet
 * @Description: 路线详情【获取导游的路线信息】
 * @author ganliang
 * @date 2015年7月7日 上午10:46:32
 */
@WebServlet(urlPatterns = { "/roadlineDetailMessage.do" }, asyncSupported = true)
public class RoadlineDetailMessageServet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static NumberFormat nf = new DecimalFormat("0.0 ");

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		String roadlineIdStr = request.getParameter("roadlineId");
		// 如果路线id不为空 根据路线id来获取路线详情
		if (roadlineIdStr == null || "".equals(roadlineIdStr)) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"客户端传递参数为空！！");
			return;
		}

		int roadlineId = Integer.parseInt(roadlineIdStr);
		ICommentService commentService = new CommentServiceImpl();
		IRoadlineService roadlineService = new RoadlineServiceImpl();
		IUserService userService = new UserServiceImpl();

		// 判断这条路线 用户是否收藏
		TUser sessionUser = WebUtil.getUserFromSession(request);
		String userId = "";
		if (sessionUser != null) {
			userId = sessionUser.getUserId();
		}
		// 根据路线id，获取用户信息
		RoadlineDetailEntity detailEntity = userService.getUserByRoadlineId(
				userId, roadlineId);
		if (detailEntity == null) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"该路线不存在 roadlineId=" + roadlineId);
			return;
		}

		// 获取评论
		List<Object> comments = commentService
				.getCommentListByRoadlineId(roadlineId);
		if (comments != null && comments.size() > 0) {
			CommentEntity comment = (CommentEntity) comments.get(0);
			detailEntity.setCommentId(comment.getCommentId());

			String totalIndex = comment.getTotalIndex();
			if (totalIndex != null && !"".equals(totalIndex)) {
				detailEntity.setTotalIndex(nf.format(Float
						.parseFloat(totalIndex)));
			}

			detailEntity.setCommentContent(comment.getCommentContent());
			detailEntity.setCommentUserAvatar(comment.getUserAvatar());
			detailEntity.setCommentImages(comment.getCommentImages());

			detailEntity.setReplyContent(comment.getReplyContent());
			detailEntity.setReplyImages(comment.getReplyImages());
		}
		// 获取指数
		CommentReplyEntity reply = commentService
				.getCommentIndexByRoadlineId(roadlineId);
		detailEntity.setAvageTotalIndex(nf.format(Float.parseFloat(reply
				.getAvageTotalIndex())));
		detailEntity.setAvageFreshIndex(nf.format(Float.parseFloat(reply
				.getAvageFreshIndex())));
		detailEntity.setAvageRatioIndex(nf.format(Float.parseFloat(reply
				.getAvageRatioIndex())));
		detailEntity.setAvageServiceIndex(nf.format(Float.parseFloat(reply
				.getAvageServiceIndex())));
		detailEntity.setCommentCount(reply.getCommentCount());

		// 获取路线的详细信息
		RoadLinePublishForm form = roadlineService
				.getRoadlineDetailById(roadlineId);
		if (form != null) {
			detailEntity.setRoadlineId(form.getRoadlineId());
			detailEntity.setRoadlineTitle(form.getRoadlineTitle());
			detailEntity.setRoadlinePrice(form.getRoadlinePrice());
			detailEntity.setRoadlineContent(form.getRoadlineContent());
			detailEntity.setRoadlineGoalArea(form.getRoadlineGoalArea());
			detailEntity.setRoadlineDays(form.getRoadlineDays());
			detailEntity.setRoadlineBackground(form.getRoadlineBackground());

			detailEntity.setRoadlineDescribes(form.getRoadlineDescribes());
		}
		// 返回客户端数据
		ResponseEntity.println(response, detailEntity, null);
	}
}