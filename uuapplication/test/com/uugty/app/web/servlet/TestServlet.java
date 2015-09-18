package com.uugty.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.utils.MD5;

/**
 * 和客户端连接测试
 * 
 * @author uu
 *
 */
@WebServlet(urlPatterns = { "/test.do" }, asyncSupported = true)
public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8167695848641043403L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String name1 = request.getParameter("name1");
		System.out.println(name1);
		System.out.println("测试代码连通性");

		ResponseEntity.println(response, ResponseEntity.SUCCESS_STATUS,
				"服务器连通性测试");

	}
}
