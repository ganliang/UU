package com.uugty.app.utils;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void test() {

		String currentProjectPath = FileUtil.currentProjectPath();
		System.out.println(currentProjectPath);
	}

}
