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
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.utils.GratuityUnRecivedThread;

/**
 * @ClassName: OrderGratuityPursePaymentServlet
 * @Description: 小费 钱包支付 保存红包的记录,并且将发送人的账户余额扣除发送额 。超时发送后，将未接受的账户的钱打到发送者的账户中去
 * @author ganliang
 * @date 2015年6月13日 上午11:32:23
 */
@WebServlet(urlPatterns = { "/security/orderGratuityPursePayment.do" }, asyncSupported = false)
public class OrderGratuityPursePaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 800828469122812854L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrderGratuity gratuity = (TOrderGratuity) BeanUtil.populate(request,
				TOrderGratuity.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);

		// 比对支付密码
		if (gratuity.getUserPayPassword() == null
				|| !gratuity.getUserPayPassword().equals(
						sessionUser.getUserPayPassword())) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"支付密码不正确");
			return;
		}

		IOrderService orderService = new OrderServiceImpl();
		IUserService userSerice = new UserServiceImpl();

		float totalMoney = gratuity.getGratuityEveryMoney()
				* gratuity.getGratuityCount();
		gratuity.setGratuityTotalMoney(totalMoney);
		gratuity.setGratuitySenderUserId(sessionUser.getUserId());
		// 查看当前用户的钱包是否够用
		TUser user = userSerice.getUserById(sessionUser);
		float userPurse = user.getUserPurse();

		if (totalMoney > userPurse) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"你账户的余额不足！");
		} else {
			// 保存红包
			String outTradeNo = StringUtil.generateOrdrNo();
			gratuity.setOutTradeNo(outTradeNo);

			int gratuityId = orderService.savePursePaymentGratuity(gratuity);

			// 一天之后 红包没有被领取 则返回红包到发送者账户
			new GratuityUnRecivedThread(outTradeNo).start();

			ResponseEntity.println(response, new GratuityEntity(gratuityId));
		}
	}

	public static class GratuityEntity {
		private int gratuityId;

		public GratuityEntity(int gratuityId) {
			super();
			this.gratuityId = gratuityId;
		}

		public int getGratuityId() {
			return gratuityId;
		}

		public void setGratuityId(int gratuityId) {
			this.gratuityId = gratuityId;
		}

	}
}
