package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TOrder;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.PayPasswordUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: OrderTourPursePaymentServet
 * @Description:旅游订单钱包支付
 * @author ganliang
 * @date 2015年6月13日 上午10:52:37
 */
@WebServlet(urlPatterns = { "/security/orderTourPursePayment.do" }, asyncSupported = true)
public class OrderTourPursePaymentServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		IOrderService orderService = new OrderServiceImpl();
		IUserService userSerice = new UserServiceImpl();

		String msg = PayPasswordUtil.validatePayPassword(
				order.getUserPayPassword(), sessionUser);
		// 比对支付密码
		if (msg != null) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS, msg);
			return;
		}

		TOrder orderById = orderService.getOrderById(order.getOrderId());
		TUser user = userSerice.getUserById(sessionUser);
		if (orderById.getOrderPrice() > user.getUserPurse()) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"你账户的余额不足！");
			return;
		}
		orderService.saveTourPursePayment(orderById);

		ResponseEntity.println(response);
	}
}