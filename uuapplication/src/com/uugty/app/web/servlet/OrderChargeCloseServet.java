package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TOrderRecharge;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: OrderChargeServet
 * @Description: 订单充值功能[从微信客户端充值]
 * @author ganliang
 * @date 2015年6月13日 上午11:13:42
 */
@WebServlet(urlPatterns = { "/orderChargeClose.do" }, asyncSupported = true)
public class OrderChargeCloseServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrderRecharge recharge = (TOrderRecharge) BeanUtil.populate(request,
				TOrderRecharge.class);

		IOrderService orderService = new OrderServiceImpl();

		// 取消支付
		orderService.CloseCharge(recharge.getOutTradeNo());

		ResponseEntity.println(response);
	}
}