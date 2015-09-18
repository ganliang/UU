package com.uugty.app.utils;

import org.junit.Test;

import com.uugty.app.domain.TUser;

public class SQLUtilTest {

	@Test
	public void generateTableTest() {
		System.out.println(SQLUtil.generateTableName("TUserLoginLog"));
	}

	@Test
	public void updateSQL() {
		TUser user = new TUser();
		user.setUserAge(123);
		System.out.println(SQLUtil.updateSQL(user));
	}

	@Test
	public void insertSQL() {
		TUser user = new TUser();
		user.setUserAge(123);
		System.out.println(SQLUtil.insertSQL(user));
	}
}
