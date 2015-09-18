package com.uugty.app.web.utils;

import org.junit.Test;

public class DownloadWexinHeadImgUtilTest {

	@Test
	public void test() {
		String url = "http://wx.qlogo.cn/mmopen/UaV4OgicLceKCVMibfL7x7XG7OWxP2jAibCGh1RbTGPlIZ9o39pp9nszTK5AjQR4gzia9kyXLge1z8WKzU84icLptI1Hia9HjZAhJh/0";
		String path = "D:/usr/local/uu/images/wexinHeadImg/111.jpg";
		new DownloadWexinHeadImgUtil(path, url).start();
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
