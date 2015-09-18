package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TOrder;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.OrderDetailEntity;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.DateUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: OrderDetailMessageServet
 * @Description: 获取订单的详细信息
 * @author ganliang
 * @date 2015年6月13日 上午11:06:31
 */
@WebServlet(urlPatterns = { "/orderDetailMessage.do" }, asyncSupported = true)
public class OrderDetailMessageServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrder parameterOrder = (TOrder) BeanUtil.populate(request,
				TOrder.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);

		IOrderService orderService = new OrderServiceImpl();

		OrderDetailEntity orderDetail = orderService.getOrderDetailMessage(
				parameterOrder.getOrderId(), sessionUser.getUserId());

		if (orderDetail != null) {
			switch (orderDetail.getOrderStatus()) {
			/*
			 * 订单是未付款订单 则检查该订单是否已经过去了 30分钟如果过了 就将订单设置为失效的订单
			 */
			case TOrder.ORDER_CREATE:
				if (DateUtil.calculateMinutes(new Date(),
						orderDetail.getOrderCreateDate()) > 30) {
					orderDetail.setOrderStatus(TOrder.ORDER_INVALID);// 订单失效
					parameterOrder.setOrderStatus(TOrder.ORDER_INVALID);// 订单失效
					asyncUpdateOrderStatus(parameterOrder, orderService);
				}
				break;

			/*
			 * 如果这个订单已经付款完毕，但是 导游过了三个小时还没有结束 就将订单状态设置为 【导游未接受】的状态
			 */
			case TOrder.ORDER_PAYMENT:
				if (DateUtil.calculateMinutes(new Date(),
						orderDetail.getOrderCreateDate()) > 3 * 60) {
					orderDetail.setOrderStatus(TOrder.ORDER_NOT_AGREE_CANCEL);// 导游未接受
					parameterOrder
							.setOrderStatus(TOrder.ORDER_NOT_AGREE_CANCEL);// 导游未接受
					asyncUpdateOrderStatus(parameterOrder, orderService);
				}
				break;
			default:
				break;
			}
		}
		ResponseEntity.println(response, orderDetail);
	}

	/**
	 * @Title: asyncUpdateOrderStatus
	 * @Description: 异步更新订单状态
	 * @param @param order
	 * @param @param orderService
	 * @return void 返回类型
	 * @throws
	 */
	private void asyncUpdateOrderStatus(final TOrder order,
			final IOrderService orderService) {
		// 异步更新订单状态
		new Thread(new Runnable() {
			@Override
			public void run() {
				orderService.updateOrderStatus(order);
			}
		}).start();
	}
}