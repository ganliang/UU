package com.uugty.app.web.servlet;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.uugty.app.web.servlet.UserPurseServlet.UserPurse;

public class UserServletTest {

	@Test
	public void test() {
		UserPurse userPurse = new UserPurse("2");
		JSONObject fromObject = JSONObject.fromObject(userPurse);
		System.out.println(fromObject);
	}

	@Test
	public void test2() {
		int a = 2;
		String valueOf = String.valueOf(a);
		System.out.println(valueOf);
	}
}
