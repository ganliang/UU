<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.tencent.protocol.paycall_protocol.PayCallReqData"%>
<%@page import="com.tencent.protocol.paycall_protocol.PayCallResData"%>
<%@page import="com.uugty.app.service.IOrderService"%>
<%@page import="com.uugty.app.service.impl.OrderServiceImpl"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.uugty.app.web.utils.WXPayUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎光临UU旅游社交平台</title>
</head>
<body>
	<%
		IOrderService orderService = new OrderServiceImpl();
		String readline = null;
		StringBuilder xml = new StringBuilder();
		Logger log = Logger.getLogger("wexin_notify_url");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		while ((readline = in.readLine()) != null) {
			xml.append(readline);
		}
		log.info("微信回调参数----->>>>" + xml);
		PayCallResData payCallResData = WXPayUtil.requestPayCallResData(xml
				.toString());
		//调用后台
		String result = orderService.wexinPayCall(
				payCallResData.getResult_code(),
				payCallResData.getOut_trade_no());

		if (payCallResData.getReturn_code().equals("SUCCESS")
				&& payCallResData.getResult_code().equals("SUCCESS")
				&& result.equals("SUCCESS")) {
			/* String responseXML = WXPayUtil
					.requestPayCallReqData(new PayCallReqData("SUCCESS",
							"OK"));*/
			log.info("微信支付成功----->>>>");
			out.println("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
			out.flush();
			return;
		}
		log.info("微信支付失败----->>>>");
		out.println("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[支付出现错误,请退款！]]></return_msg></xml>");
		out.flush();
	%>
</body>
</html>