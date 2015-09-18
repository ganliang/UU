package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TComment;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: OrderEvaluationServet
 * @Description: 订单评价详情
 * @author ganliang
 * @date 2015年7月20日 下午3:01:09
 */
@WebServlet(urlPatterns = { "/orderEvaluationDetail.do" }, asyncSupported = true)
public class OrderEvaluationDetailServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TComment evaluation = (TComment) BeanUtil.populate(request,
				TComment.class);
		IOrderService orderService = new OrderServiceImpl();

		// 获取订单评价详情
		evaluation = orderService.getOrderCommentByOrderId(evaluation
				.getOrderId());

		ResponseEntity.println(response, evaluation);
	}
}
