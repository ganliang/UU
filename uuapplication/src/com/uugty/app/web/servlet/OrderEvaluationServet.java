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
 * @Description: 订单评价
 * @author ganliang
 * @date 2015年7月20日 下午3:01:09
 */
@WebServlet(urlPatterns = { "/orderEvaluation.do" }, asyncSupported = true)
public class OrderEvaluationServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TComment comment = (TComment) BeanUtil
				.populate(request, TComment.class);
		IOrderService orderService = new OrderServiceImpl();

		// 保存评价记录
		int evalId = orderService.saveOrderComment(comment);

		ResponseEntity.println(response, new EvaluationEntity(evalId));
	}

	public static class EvaluationEntity {

		private int evalId;

		public EvaluationEntity(int evalId) {
			super();
			this.evalId = evalId;
		}

		public int getEvalId() {
			return evalId;
		}

		public void setEvalId(int evalId) {
			this.evalId = evalId;
		}
	}
}
