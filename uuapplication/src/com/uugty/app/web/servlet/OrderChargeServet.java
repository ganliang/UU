package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
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
 * @ClassName: OrderChargeServet
 * @Description: 订单充值功能[从微信客户端充值]
 * @author ganliang
 * @date 2015年6月13日 上午11:13:42
 */
@WebServlet(urlPatterns = { "/security/orderCharge.do" }, asyncSupported = true)
public class OrderChargeServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrderRecharge recharge = (TOrderRecharge) BeanUtil.populate(request,
				TOrderRecharge.class);
		TUser sessionUser = WebUtil.getUserFromSession(request);

		IOrderService orderService = new OrderServiceImpl();

		String outTradeNo = StringUtil.generateOrdrNo();
		float rechargeMoney = recharge.getRechargeMoney();

		// 微信支付服务后台生成预支付交易单 返回交易码
		UnifiedorderResData requestUnifiedorder = WXPayUtil
				.requestUnifiedorder("充值成功", outTradeNo,
						(int) (rechargeMoney * 100), "outTradeNo=",
						WebUtil.getRemoteIP(request));
		if (requestUnifiedorder.getReturn_code().equals("SUCCESS")
				&& requestUnifiedorder.getResult_code().equals("SUCCESS")) {
			// 保存充值记录
			recharge.setOutTradeNo(outTradeNo);
			recharge.setRechargeMoney(rechargeMoney);
			recharge.setRechargeStatus(TOrderRecharge.RECHARGE_STATUS_RUNING);// 充值中
			recharge.setRechargeUserId(sessionUser.getUserId());
			orderService.saveOrderRechargeAndRecord(recharge);

			requestUnifiedorder.setOut_trade_no(outTradeNo);
			ResponseEntity.println(response, requestUnifiedorder);
		} else {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					requestUnifiedorder.getErr_code_des());
		}
	}
}