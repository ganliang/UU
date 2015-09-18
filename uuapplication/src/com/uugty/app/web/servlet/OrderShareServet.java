package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: OrderShareServet
 * @Description: 订单分享
 * @author ganliang
 * @date 2015年6月13日 上午11:00:44
 */
@WebServlet(urlPatterns = { "/orderShare.do" }, asyncSupported = true)
public class OrderShareServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}