package com.uugty.app.web.utils;

import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;

public class WXPayUtilTest {

	public static void main(String[] args) {

		UnifiedorderResData requestUnifiedorder = WXPayUtil
				.requestUnifiedorder("订单支付成功", "123456789123456", 1,
						"outTradeNo=1", "192.168.1.123");
		System.out.println(requestUnifiedorder);

		String access_token = "OezXcEiiBSKSxW0eoylIeKOtXheCVF2cc2o4ekpWjjfYPmmBF3YuGQI289QM7yTnessZYQx6H5YX9m8bxj4M_dc4nsKPJi1xYX1iImKtzAUps2Q-UKfS4NWsa3KpSzRMUYhoqL7j9l-oawdTGbvT5g";
		String openid = "oKkCvs4QJb6so7aBZ3ckJGUkP61w";
		WXPayUtil.requestUserInfo(access_token, openid);

	}
}
