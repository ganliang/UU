package com.tencent.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tencent.WXPay;
import com.tencent.WXPayUtil;
import com.tencent.protocol.pay_protocol.ScanPayReqData;

public class PayServiceTest {

	@SuppressWarnings("deprecation")
	public static void test() {

		String authCode = "120061098828009406";
		String body = "微信支付";
		String attach = "附加数据";
		String outTradeNo = "123456789";
		int totalFee = 1;
		String deviceInfo = "111111111";
		String spBillCreateIP = "192.168.1.123";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date startDate = new Date();
		String timeStart = sdf.format(startDate);
		startDate.setMinutes(startDate.getMinutes() + 6);
		String timeExpire = sdf.format(startDate);
		String goodsTag = "uu";
		ScanPayReqData scanPayReqData = new ScanPayReqData(authCode, body,
				attach, outTradeNo, totalFee, deviceInfo, spBillCreateIP,
				timeStart, timeExpire, goodsTag);
		try {
			String sign = WXPay.requestScanPayService(scanPayReqData);
			System.out.println(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unifiedorderTest() {
		WXPayUtil.requestUnifiedorder("weixin", "123456789", 1, "name=123",
				"192.168.1.123");
	}

	public static void getAccessToken() {
		WXPayUtil.requestAccessToken("011aa677b57cd27c162e316d60f8304O");
	}

	public static void getUserInfo() {

		String access_token = "OezXcEiiBSKSxW0eoylIeKOtXheCVF2cc2o4ekpWjjfYPmmBF3YuGQI289QM7yTnessZYQx6H5YX9m8bxj4M_dc4nsKPJi1xYX1iImKtzAUps2Q-UKfS4NWsa3KpSzRMUYhoqL7j9l-oawdTGbvT5g";
		String oepnid = "oKkCvs4QJb6so7aBZ3ckJGUkP61w";

		WXPayUtil.requestUserInfo(access_token, oepnid);
	}
}
