package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
import com.uugty.app.domain.TOrder;
import com.uugty.app.domain.TOrderRecharge;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.utils.WXPayUtil;

/**
 * @ClassName: OrderTourWeChartPaymentServet
 * @Description:旅游订单微信支付
 * @author ganliang
 * @date 2015年6月13日 上午10:51:09
 */
@WebServlet(urlPatterns = { "/security/orderTourWeChartPayment.do" }, asyncSupported = true)
public class OrderTourWeChartPaymentServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrder order = (TOrder) BeanUtil.populate(request, TOrder.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		IOrderService orderService = new OrderServiceImpl();
		IUserService userSerice = new UserServiceImpl();

		TOrder orderById = orderService.getOrderById(order.getOrderId());

		if (orderById == null) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"客户端传递订单号出现错误 ORDERID[" + order.getOrderId() + "]！");
			return;
		}
		TUser user = userSerice.getUserById(sessionUser);
		// 微信支付
		String outTradeNo = orderById.getOrderNo();
		float orderPrice = orderById.getOrderPrice();
		// 微信支付服务后台生成预支付交易单 返回交易码
		UnifiedorderResData requestUnifiedorder = WXPayUtil
				.requestUnifiedorder("订单支付成功", outTradeNo,
						(int) (orderPrice * 100), "outTradeNo=",
						WebUtil.getRemoteIP(request));
		// 下单成功
		if (requestUnifiedorder.getReturn_code().equals("SUCCESS")
				&& requestUnifiedorder.getResult_code().equals("SUCCESS")) {
			// 保存充值记录
			TOrderRecharge recharge = new TOrderRecharge();
			recharge.setOutTradeNo(outTradeNo);
			recharge.setRechargeMoney(orderPrice);
			recharge.setRechargeStatus(TOrderRecharge.RECHARGE_STATUS_RUNING);// 充值中
			recharge.setRechargeUserId(user.getUserId());
			orderService.saveTourWeChartPayment(orderById, recharge);

			requestUnifiedorder.setOut_trade_no(outTradeNo);
			ResponseEntity.println(response, requestUnifiedorder);
		} else {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					requestUnifiedorder.getErr_code_des());
		}
	}
}