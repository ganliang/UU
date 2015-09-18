package com.uugty.app.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.dao.BaseDao;
import com.uugty.app.entity.ResponseEntity;

/**
 * @ClassName: RechargeServlet
 * @Description: 测试接口 给账户充钱
 * @author ganliang
 * @date 2015年8月6日 下午4:44:48
 */
@WebServlet(urlPatterns = { "/recharge.do" }, asyncSupported = false)
public class RechargeServlet extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 8114169607880035889L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userTel = request.getParameter("userTel");
		String userMoney = request.getParameter("money");
		float money = 0;
		try {
			money = Float.parseFloat(userMoney);
		} catch (Exception e) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"冲入钱数不正确【money=】" + userMoney);
		}

		BaseDao dao = new BaseDao();
		List<Object> args = new ArrayList<Object>();
		String sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_tel=?";
		args.add(money);
		args.add(userTel);
		try {
			dao.update(sql, args);
		} catch (SQLException e) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"充钱出现错误！！！");
		}
		ResponseEntity.println(response);
	}
}
