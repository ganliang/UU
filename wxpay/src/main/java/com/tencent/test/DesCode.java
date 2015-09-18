package com.tencent.test;

import com.tencent.common.MD5;

public class DesCode {

	public static void main(String[] args) {

		String code = "uuclient815*!%";
		String md5Encode = MD5.MD5Encode(code);
		System.out.println(md5Encode);
	}

}
