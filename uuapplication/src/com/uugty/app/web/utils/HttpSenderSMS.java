package com.uugty.app.web.utils;

import org.apache.log4j.Logger;

import com.bcloud.msg.http.HttpSender;
import com.uugty.app.constant.StringConstant;

@Deprecated
public class HttpSenderSMS {

	private static final String uri = "http://222.73.117.158/msg/index.jsp";// 应用地址
	private static final String account = "hkyouyou";// 账号
	private static final String pswd = "Tch123456";// 密码
	private static final boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
	private static final String product = null;// 产品ID
	private static final String extno = null;// 扩展码

	private static Logger log = Logger.getLogger(HttpSenderSMS.class);

	public static boolean sendSMS(String mobile, String content) {
		content = "你的验证码是 " + content + " ,5分钟内有效。北京午饭科技有限公司。";// 短信内容
		try {
			String returnString = HttpSender.batchSend(uri, account, pswd,
					mobile, content, needstatus, product, extno);

			int beginIndex = returnString.indexOf(StringConstant.QUOTA);
			String status = returnString.substring(beginIndex + 1,
					beginIndex + 2);
			if ("0".equals(status)) {
				log.info("短信发送成功!" + mobile + " 发送内容:[" + content + "]");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("短信发送失败!" + mobile + " 发送内容:[" + content + "]");
		return false;
	}

	public static void main(String[] args) {
		String mobiles = "13718937148";// 手机号码，多个号码使用","分割
		String content = "你的验证码是 123456  。5分钟内有效。北京午饭科技有限公司。";// 短信内容
		try {
			String returnString = HttpSender.batchSend(uri, account, pswd,
					mobiles, content, needstatus, product, extno);
			int beginIndex = returnString.indexOf(StringConstant.QUOTA);
			String status = returnString.substring(beginIndex + 1,
					beginIndex + 2);
			if ("0".equals(status)) {
				log.info("短信发送成功!" + mobiles + " 发送内容:[" + content + "]");
			} else {
				System.out.println(returnString);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
