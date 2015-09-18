package com.uugty.app.log;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.uugty.app.utils.PropertiesUtil;

/**
 * @ClassName: LogTimerTask
 * @Description: 启动一个记录日志的定时器
 * @author ganliang
 * @date 2015年6月13日 下午5:33:21
 */
public class LogTimerTask extends Thread {

	private static final Logger LOG = Logger.getLogger(LogTimerTask.class);

	private static int log_flush_time = 30;
	static {
		Properties properties = PropertiesUtil.getProperties("file.properties");
		Object object = properties.get("log_flush_time");
		if (object != null) {
			try {
				log_flush_time = Integer.parseInt(String.valueOf(object));
			} catch (Exception e) {
				LOG.error("{file.properties}配置文件参数{log_flush_time}设置错误!");
			}
		}
	}

	@Override
	public void run() {
		// 启动一个定时器
		new Timer().schedule(new TimerTask() {

			ConcurrentLinkedQueue<String> queue = null;

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				LOG.info("启动定时器...................................");
				Object object = null;
				synchronized (servletContext) {
					object = servletContext.getAttribute("LOG_CONTEXT");
					servletContext.setAttribute("LOG_CONTEXT",
							new ConcurrentLinkedQueue<String>());
				}
				if (object != null) {
					LOG.info("更换servletContext..........................");
					queue = (ConcurrentLinkedQueue<String>) object;
					if (queue.size() > 0) {
						new LogWriter(queue).start();
					}
				}
			}
		}, 0, 1000 * 60 * log_flush_time);// 每隔一小时
	}

	private ServletContext servletContext;

	public LogTimerTask() {
		super();
	}

	public LogTimerTask(ServletContext servletContext) {
		super();
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
