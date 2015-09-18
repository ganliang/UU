package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: UserBoundBankCardListServlet
 * @Description: 获取用户绑定的银行卡集合
 * @author ganliang
 * @date 2015年7月15日 上午10:40:11
 */
@WebServlet(urlPatterns = { "/userBoundBankCardList.do" }, asyncSupported = false)
public class UserBoundBankCardListServlet extends HttpServlet {

	private static final long serialVersionUID = -516654987820116309L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		IUserService userService = new UserServiceImpl();

		// 查询该用户绑定的银行卡号的集合
		List<Object> userBankList = userService
				.getUserBankListByUserId(sessionUser.getUserId());

		ResponseEntity.printlns(response, userBankList);
	}
}
