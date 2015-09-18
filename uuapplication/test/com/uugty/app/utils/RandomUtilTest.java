package com.uugty.app.utils;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class RandomUtilTest {

	@Test
	public void test() {
		float nextFloat = 0;
		for (int i = 0; i < 100; i++) {
			nextFloat = RandomUtils.nextFloat(0, 10);
			System.out.println("---->" + nextFloat);
		}
	}
}
