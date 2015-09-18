package com.uugty.app.utils;

import net.sf.json.JSONObject;

import com.uugty.app.domain.TUser;

public class AssertTest {

	public static void main(String[] args) {
		assert (10 > 12) : "error";
		System.out.println("assert.....");

		TUser user = new TUser();
		user.setUserName("");
		JSONObject jsonObject = JSONObject.fromObject(user);
		Object object = jsonObject.get("userName");

		System.out.println(object);
		if (object != null) {
			System.out.println(object.toString());
		}
	}
}
