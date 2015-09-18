package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
import com.uugty.app.utils.DateUtil;

/**
 * @ClassName: OrderDrawbackServet
 * @Description: 订单申请退款,接受退款,拒绝退款
 * @author ganliang
 * @date 2015年6月13日 上午11:08:37
 */
@WebServlet(urlPatterns = { "/orderDrawback.do" }, asyncSupported = true)
public class OrderDrawbackServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);

		final IOrderService orderService = new OrderServiceImpl();
		final int orderId = order.getOrderId();
		switch (order.getOrderStatus()) {
		// 申请退款
		case TOrder.ORDER_DRAWBACK:
			order.setOrderDrawbackDate(DateUtil.longDateFormatCurrentDate());
			orderService.updateOrder(order);
			// 启动一个定时器 如果七天之后 该订单还是申请退款中，则自动退款
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					orderService.scheduleDrawbackSuccess(orderId,
							TOrder.ORDER_DRAWBACK_SUCCESS);
				}
			}, 1000 * 60 * 60 * 24 * 7);
			break;
		// 退款失败
		case TOrder.ORDER_DRAWBACK_FAILURE:
			orderService.updateOrderStatus(order);
			break;
		// 退款成功
		case TOrder.ORDER_DRAWBACK_SUCCESS:
			// 导游的钱包=导游的钱包-旅游费用 ;游客的钱包=游客的钱包+旅游的费用
			orderService
					.drawbackSuccess(orderId, TOrder.ORDER_DRAWBACK_SUCCESS);
			break;
		default:
			break;
		}
		ResponseEntity.println(response);
	}
}