package com.uugty.app.web.servlet;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import org.junit.Test;

import com.uugty.app.utils.DateUtil;

public class RoadlineDetailMessageTest {

	@Test
	public void test() {
		Date shortDateFormat = DateUtil.shortDateFormat("2015-08-11 13:23:30");
		String shortDateFormat2 = DateUtil.shortDateFormat(shortDateFormat);
		System.out.println(shortDateFormat2);
	}

	@Test
	public void test2() {
		NumberFormat nf = new DecimalFormat("0.0 ");
		String format = nf.format(1.123456789);
		float parseFloat = Float.parseFloat(format);
		System.out.println(parseFloat);
	}
}
