package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TOrder;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: OrderInvitationServet
 * @Description: 导游拒绝游客的订单 或者 导游同意游客的邀请
 * @author ganliang
 * @date 2015年6月13日 上午11:00:44
 */
@WebServlet(urlPatterns = { "/orderInvitation.do" }, asyncSupported = true)
public class OrderInvitationServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 导游拒绝之后 修改订单状态 为决绝状态 而且 该订单结束
		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);

		IOrderService orderService = new OrderServiceImpl();

		switch (order.getOrderStatus()) {
		// 导游同意
		case TOrder.ORDER_AGREE:
			// 更新订单状态
			orderService.updateOrderStatus(order);
			break;
		// 导游拒绝
		case TOrder.ORDER_DENY:
			// 退款 ; 更新订单状态
			orderService.orderTourDeny(order);
			break;
		default:
			break;
		}

		ResponseEntity.println(response);
	}
}