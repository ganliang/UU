package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TOrder;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;

/**
 * 
 * @ClassName: GetOrderListServlet
 * @Description:获取订单列表
 * @author ganliang
 * @date 2015年6月6日 下午3:47:25
 */
@WebServlet(urlPatterns = { "/orderList.do" }, asyncSupported = false)
public class OrderListServlet extends HttpServlet {

	private static final long serialVersionUID = 800828469122812854L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);

		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);

		order.setOrderUserId(sessionUser.getUserId());

		IOrderService orderService = new OrderServiceImpl();

		//获取当前用户的所有的订单
		List<Object> list = orderService.getAllOrderByUserId(order);

		ResponseEntity.printlns(response, list);
	}
}
