package com.uugty.app.web.utils;

import org.junit.Test;

public class EasemobUtilTest {

	@Test
	public void createIMUser() {
		EasemobUtil.createIMUser("sys", "123456");
	}

	@Test
	public void modifyIMUserPassword() {
		EasemobUtil.modifyIMUserPassword("ganliang", "123456789");
	}

	@Test
	public void deleteAllIMuser() {
		EasemobUtil.deleteAllIMuser();
	}

	@Test
	public void sendMessage() {
		EasemobUtil.sendMessage("4b07e8f7b3064b86bf2e3bc12e6531de", "4b07e8f7b3064b86bf2e3bc12e6531de");
	}

	@Test
	public void getEasemobUser() {
		EasemobUtil.getIMUsersByUserName("fcae891604e84721ad68436de3923cac");
	}

	@Test
	public void getFriends() {
		EasemobUtil.getFriends("fcae891604e84721ad68436de3923cac");
	}

	@Test
	public void addFriendSingle() {
		EasemobUtil.addFriendSingle("653e00baac474ee5a4d88a00e5360eaa");
	}

}
