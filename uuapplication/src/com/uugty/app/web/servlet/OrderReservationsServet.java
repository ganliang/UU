package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TOrder;
import com.uugty.app.domain.TRoadline;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.service.impl.RoadlineServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: OrderReservationsServet
 * @Description: 订单预定【生成订单,并向客户端返回订单信息】
 * @author ganliang
 * @date 2015年6月13日 上午10:47:39
 */
@WebServlet(urlPatterns = { "/orderReservations.do" }, asyncSupported = true)
public class OrderReservationsServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		IOrderService orderService = new OrderServiceImpl();
		IRoadlineService roadlineService = new RoadlineServiceImpl();

		// 根据路线id来获取路线信息
		TRoadline roadline = roadlineService.getRoadlineById(order
				.getOrderRoadlineId());
		if (roadline == null) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"这条路线不存在！");
			return;
		}
		order.setOrderUserId(sessionUser.getUserId());
		order.setOrderTourUserId(roadline.getRoadlineUserId());
		order.setOrderNo(StringUtil.generateOrdrNo());

		order.setOrderPrice(roadline.getRoadlinePrice());
		order.setOrderStatus(TOrder.ORDER_CREATE);// 已下单的状态
		order.setOrderCreateDate(new Date());
		// 生成订单
		int orderId = orderService.saveOrder(order);
		// 返回订单信息
		ResponseEntity.println(response, new OrderEntity(orderId));
	}

	public static class OrderEntity {
		private int orderId;

		public OrderEntity(int orderId) {
			this.orderId = orderId;
		}

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}
	}
}
