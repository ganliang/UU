package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
import com.uugty.app.domain.TOrderGratuity;
import com.uugty.app.domain.TOrderRecharge;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.utils.WXPayUtil;

/**
 * @ClassName: OrderGratuityWeChartPaymentServlet
 * @Description: 小费 微信支付
 * @author ganliang
 * @date 2015年6月13日 上午11:32:23
 */
@WebServlet(urlPatterns = { "/security/orderGratuityWeChartPayment.do" }, asyncSupported = false)
public class OrderGratuityWeChartPaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 800828469122812854L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrderGratuity gratuity = (TOrderGratuity) BeanUtil.populate(request,
				TOrderGratuity.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);

		IOrderService orderService = new OrderServiceImpl();
		// 服务后台生成小费订单记录
		float totalMoney = gratuity.getGratuityEveryMoney()
				* gratuity.getGratuityCount();
		gratuity.setGratuityTotalMoney(totalMoney);
		gratuity.setGratuitySenderUserId(sessionUser.getUserId());

		// 保存红包
		String outTradeNo = StringUtil.generateOrdrNo();
		gratuity.setOutTradeNo(outTradeNo);
		// 微信支付服务后台生成预支付交易单 返回交易码
		UnifiedorderResData requestUnifiedorder = WXPayUtil
				.requestUnifiedorder("小费支付成功", outTradeNo,
						(int) (totalMoney * 100), "outTradeNo=",
						WebUtil.getRemoteIP(request));

		if (requestUnifiedorder.getReturn_code().equals("SUCCESS")
				&& requestUnifiedorder.getResult_code().equals("SUCCESS")) {
			// 保存充值记录
			TOrderRecharge recharge = new TOrderRecharge();
			recharge.setOutTradeNo(outTradeNo);
			recharge.setRechargeMoney(totalMoney);
			recharge.setRechargeUserId(sessionUser.getUserId());

			int gratutiyId = orderService.saveWeChartPaymentGratuity(gratuity,
					recharge);

			requestUnifiedorder.setGratutiyId(gratutiyId);
			requestUnifiedorder.setOut_trade_no(outTradeNo);
			ResponseEntity.println(response, requestUnifiedorder);
		} else {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					requestUnifiedorder.getErr_code_des());
		}
	}
}
