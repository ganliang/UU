package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TOrderGratuity;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: OrderGratuityReceiveServlet
 * @Description: 小费领取
 * @author ganliang
 * @date 2015年7月10日 上午11:35:03
 */
@WebServlet(urlPatterns = { "/security/orderGratuityReceive.do" }, asyncSupported = false)
public class OrderGratuityReceiveServlet extends HttpServlet {

	private static final long serialVersionUID = 800828469122812854L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrderGratuity gratuity = (TOrderGratuity) BeanUtil.populate(request,
				TOrderGratuity.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);

		IOrderService orderService = new OrderServiceImpl();

		gratuity = orderService.getGratuityById(gratuity);

		if (gratuity == null) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"客户端获取参数报错");
			return;
		}
		String MSG = "";
		switch (gratuity.getGratuityStatus()) {
		// 发送中
		case TOrderGratuity.GRATUITY_STATUS_RUNING:
			// 领取红包
			if (gratuity.getGratuityCount() == 1) {
				int gratuityCount = orderService.getGratuityCountById(gratuity
						.getGratuityId());
				if (gratuityCount < gratuity.getGratuityCount()) {
					if (gratuity.getGratuityCount() - gratuityCount == 1) {
						gratuity.setGratuityStatus("2");// 红包标识结束
					}
					gratuity.setGratuityReceiverUserId(sessionUser.getUserId());

					// 接收红包
					orderService.receiveGratuity(gratuity);
				} else {
					MSG = "该红包已经被领取完了";
				}
			} else {
				synchronized (orderService) {

					int gratuityCount = orderService
							.getGratuityCountById(gratuity.getGratuityId());

					if (gratuityCount < gratuity.getGratuityCount()) {
						if (gratuity.getGratuityCount() - gratuityCount == 1) {
							gratuity.setGratuityStatus(TOrderGratuity.GRATUITY_STATUS_END);// 红包标识结束
						}
						gratuity.setGratuityReceiverUserId(sessionUser
								.getUserId());
						orderService.receiveGratuity(gratuity);
					} else {
						MSG = "该红包已经被领取完了";
					}
				}
			}
			break;
		// 发送结束
		case TOrderGratuity.GRATUITY_STATUS_END:
			MSG = "该红包已经被领取完了";
			break;
		// 未接受
		case TOrderGratuity.GRATUITY_STATUS_EXPIRE:
			MSG = "该红包已经过期";
			break;
		default:
			break;
		}
		// 获取接受到小费的用户个人信息
		// List<Object> list = orderService
		// .getReceivedGratuityUserMessageById(gratuity.getGratuityId());

		ResponseEntity.println(response, MSG, null, null);
	}
}
