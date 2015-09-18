package com.uugty.app.log;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;

/**
 * @ClassName: LogWriterTest
 * @Description: 测试logWriter类
 * @author ganliang
 * @date 2015年6月13日 上午9:20:26
 */
public class LogWriterTest {

	@Test
	public void test() {
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
		for (int i = 0; i < 1000000; i++) {
			queue.add("java is my love language!  " + String.valueOf(i + 1));
		}
		System.out.println("before...............");
		LogWriter logWriter = new LogWriter(queue);
		logWriter.setDaemon(true);// 设置此线程为后台线程
		logWriter.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("after...............");
	}
}
