package com.uugty.app.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dongsheng.nightclub.alipay.sign.RSA;
import com.uugty.app.config.AlipayConfig;

/**
 * 
 * @ClassName: AlipayServlet
 * @Description: 订单alipay支付
 * @author ganliang
 * @date 2015年6月6日 下午3:48:30
 */
@WebServlet("/alipay.do")
public class AlipayServlet extends HttpServlet {

	private static final long serialVersionUID = 546118829079018850L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 查询获取订单的主题，订单的内容，订单的实付价格
		String subject = "";
		String body = "";
		float total_fee = 0;
		// 生成订单编号
		String out_trade_no = null;
		// 待签名的字符串
		String orderinfo = "partner=\"" + AlipayConfig.partner
				+ "\"&seller_id=\"" + AlipayConfig.seller_id
				+ "\"&out_trade_no=\"" + out_trade_no + "\"&subject=\""
				+ subject + "\"&body=\"" + body + "\"&total_fee=\"" + total_fee
				+ "\"&notify_url=\"" + AlipayConfig.notify_url
				+ "\"&service=\"" + AlipayConfig.service + "\"&payment_type=\""
				+ AlipayConfig.payment_type + "\"&_input_charset=\""
				+ AlipayConfig.input_charset + "\"&it_b_pay=\""
				+ AlipayConfig.it_b_pay + "\"&show_url=\""
				+ AlipayConfig.show_url + "\"";
		// 签名"\"&return_url=\""+ AlipayConfig.return_url +
		String sign = RSA.sign(orderinfo, AlipayConfig.private_key,
				AlipayConfig.input_charset);
		sign = URLEncoder.encode(sign, "UTF-8");
		String linkString = orderinfo + "&sign=\"" + sign
				+ "\"&sign_type=\"RSA\"";
		// System.out.println(linkString);
		response.getWriter().write(linkString);
		response.setStatus(200);
	}
}
