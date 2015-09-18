package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.domain.TUserBank;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: UserBoundBankCardServlet
 * @Description: 用户可以设置绑定的银行卡，将银行卡绑定到平台。当提现的时候,将钱包的钱打到绑定的银行卡中去
 * @author ganliang
 * @date 2015年7月15日 上午10:20:43
 */
@WebServlet(urlPatterns = { "/security/userBoundBankCard.do" }, asyncSupported = false)
public class UserBoundBankCardServlet extends HttpServlet {

	private static final long serialVersionUID = -516654987820116309L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TUserBank userBank = (TUserBank) BeanUtil.populate(request,
				TUserBank.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		IUserService userService = new UserServiceImpl();

		userBank.setUserId(sessionUser.getUserId());

		// 保存绑定的银行卡
		int bankId = userService.saveUserBank(userBank);
		ResponseEntity.println(response, new BankEntity(bankId));
	}

	public static class BankEntity {

		private int bankId;

		public BankEntity(int bankId) {
			super();
			this.bankId = bankId;
		}

		public int getBankId() {
			return bankId;
		}

		public void setBankId(int bankId) {
			this.bankId = bankId;
		}

	}
}
