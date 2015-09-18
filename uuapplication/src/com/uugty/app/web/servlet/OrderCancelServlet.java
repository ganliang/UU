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
 * @ClassName: OrderCancelServlet
 * @Description: 订单取消
 * @author ganliang
 * @date 2015年7月16日 上午10:06:57
 */
@WebServlet(urlPatterns = { "/orderCancel.do" }, asyncSupported = false)
public class OrderCancelServlet extends HttpServlet {

	private static final long serialVersionUID = 800828469122812854L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);
		IOrderService orderService = new OrderServiceImpl();
		boolean isSuccess = true;
		switch (order.getOrderStatus()) {
		// 取消未付款订单
		case TOrder.ORDER_NO_PAY_CANCEL:
			orderService.updateOrderStatus(order);
			break;
		// 取消付款之后 导游未同意的订单
		// 取消付款之后 导游同意之后的订单
		case TOrder.ORDER_NOT_AGREE_CANCEL:
		case TOrder.ORDER_AGREE_CANCEL:
			orderService.cancelOrder(order);
			break;
		default:
			isSuccess = false;
			break;
		}

		if (isSuccess) {
			ResponseEntity.println(response);
		} else {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"取消订单的状态设置错误");
		}
	}
}
