package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.domain.TWithdrawCash;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.PayPasswordUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: UserWithdrawServlet
 * @Description: 用户提现的接口 【用户提现请求,删除提现请求】 生成提现记录 后台手工操作 提现
 * @author ganliang
 * @date 2015年7月15日 上午11:12:36
 */
@WebServlet(urlPatterns = { "/security/userWithdraw.do" }, asyncSupported = false)
public class UserWithdrawServlet extends HttpServlet {

	private static final long serialVersionUID = -516654987820116309L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TWithdrawCash withdraw = (TWithdrawCash) BeanUtil.populate(request,
				TWithdrawCash.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		IUserService userService = new UserServiceImpl();
		IOrderService orderService = new OrderServiceImpl();
		// 验证支付密码
		String msg = PayPasswordUtil.validatePayPassword(
				withdraw.getUserPayPassword(), sessionUser);
		if (msg != null) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS, msg);
			return;
		}

		withdraw.setUserId(sessionUser.getUserId());
		// 保存提现记录
		withdraw.setOutTradeNo(StringUtil.generateOrdrNo());

		TUser userById = userService.getUserById(sessionUser);
		if (userById.getUserPurse() < withdraw.getWithdrawMoney()) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"提现金额不足!");
			return;
		}

		/*** 查看该用户是否在7天之内有交易记录 如果有交易记录, 则提现额度=现有额度-七天之内的交易额度 */
		/** 获取七天内的交易额度 */
		float tradeMoney = orderService.getOrderSevenDayTrade(sessionUser
				.getUserId());
		if (withdraw.getWithdrawMoney() > (userById.getUserPurse() - tradeMoney)) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"提现额度不足,你有最近的订单");
			return;
		}

		int withDrawId = userService.saveWithDraw(withdraw);
		ResponseEntity.println(response, new WithdrawEntity(withDrawId));
	}

	public static class WithdrawEntity {

		private int withDrawId;

		public WithdrawEntity(int withDrawId) {
			super();
			this.withDrawId = withDrawId;
		}

		public int getWithDrawId() {
			return withDrawId;
		}

		public void setWithDrawId(int withDrawId) {
			this.withDrawId = withDrawId;
		}

	}
}
