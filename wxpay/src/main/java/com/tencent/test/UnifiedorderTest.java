package com.tencent.test;

import com.tencent.WXPayUtil;

public class UnifiedorderTest {

	public static void main(String[] args) {

		WXPayUtil.requestUnifiedorder("weixin", "123456789101111", 1,
				"name=123", "192.168.1.123");
	}
}
