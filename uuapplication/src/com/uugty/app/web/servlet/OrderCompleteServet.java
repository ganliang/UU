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
 * @ClassName: OrderCompleteServet
 * @Description: 订单完成 游客旅游完毕 点击完成旅游
 * @author ganliang
 * @date 2015年7月17日 上午11:31:38
 */
@WebServlet(urlPatterns = { "/orderComplete.do" }, asyncSupported = true)
public class OrderCompleteServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);
		IOrderService orderService = new OrderServiceImpl();
		// 订单完成【根据订单id 来获取订单信息 ; 修改订单状态; 将旅游费用给导游】
		orderService.orderCompleteByOrderId(order.getOrderId());
		ResponseEntity.println(response);
	}
}