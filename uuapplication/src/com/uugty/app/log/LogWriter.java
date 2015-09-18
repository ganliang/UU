package com.uugty.app.log;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.uugty.app.constant.EncodeConstant;
import com.uugty.app.utils.DateUtil;
import com.uugty.app.utils.PropertiesUtil;

/**
 * @ClassName: LogWriter
 * @Description: 日志记录,每隔一段时间运行一次日志线程
 * @author ganliang
 * @date 2015年6月12日 下午12:05:51
 */
public class LogWriter extends Thread {

	private static final Logger LOG = Logger.getLogger(LogWriter.class);

	private ConcurrentLinkedQueue<String> logs;

	public LogWriter() {
	}

	public LogWriter(ConcurrentLinkedQueue<String> logs) {
		super();
		this.logs = logs;
	}

	public ConcurrentLinkedQueue<String> getLogs() {
		return logs;
	}

	public void setLogs(ConcurrentLinkedQueue<String> logs) {
		this.logs = logs;
	}

	@Override
	public void run() {
		// 获取log的文件目录
		Properties properties = PropertiesUtil.getProperties("file.properties");
		String dirName = (String) properties.get("log_file_dist");
		File file = new File(dirName);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file, DateUtil.currentDate() + ".log");
		StringBuffer buffer = new StringBuffer();
		for (String log : logs) {
			buffer.append(log + "\r\n");
		}
		try {
			if (buffer.length() > 1) {
				FileUtils.write(file, buffer.toString(), EncodeConstant.UTF8);
				LOG.info("日志保存到---->>>>" + file.getAbsolutePath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
