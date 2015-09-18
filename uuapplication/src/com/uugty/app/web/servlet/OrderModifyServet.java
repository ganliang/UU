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
 * @ClassName: OrderModifyServet
 * @Description: 订单编辑 修改
 * @author ganliang
 * @date 2015年7月18日 下午5:17:40
 */
@WebServlet(urlPatterns = { "/orderModify.do" }, asyncSupported = true)
public class OrderModifyServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);
		IOrderService orderService = new OrderServiceImpl();

		TOrder orderById = orderService.getOrderById(order.getOrderId());
		// 订单只有在下单的状态下才可以修改
		if (TOrder.ORDER_CREATE.equals(orderById.getOrderStatus())) {
			// 修改订单
			orderService.orderModify(orderById);
			ResponseEntity.println(response);
		} else {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"该订单不可以修改");
		}
	}
}