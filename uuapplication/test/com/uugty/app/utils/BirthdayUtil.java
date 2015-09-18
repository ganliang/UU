package com.uugty.app.utils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class BirthdayUtil {

	@Test
	public void test() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String year = String.valueOf(cal.get(Calendar.YEAR));
		char post = year.charAt(2);
		String userPost = post + "0后";
		System.out.println(userPost);
	}

	public static void main(String[] args) {

	}
}
