package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TUser;
import com.uugty.app.domain.TWithdrawCash;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: UserWithdrawServlet
 * @Description: 获取用户所有的提现列表 包括提交请求的 完成的 失败的
 * @author ganliang
 * @date 2015年7月6日 下午2:51:31
 */
@WebServlet(urlPatterns = { "/userWithdrawList.do" }, asyncSupported = false)
public class UserWithdrawListServlet extends HttpServlet {

	private static final long serialVersionUID = -516654987820116309L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TWithdrawCash withdraw = (TWithdrawCash) BeanUtil.populate(request,
				TWithdrawCash.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		IUserService userService = new UserServiceImpl();
		// 根据用户id，来获取提现列表
		withdraw.setUserId(sessionUser.getUserId());
		
		/** 获取提现列表  */
		List<Object> withdrawList = userService.getWithdrawCashList(withdraw);
		
		ResponseEntity.printlns(response, withdrawList);
	}
}
