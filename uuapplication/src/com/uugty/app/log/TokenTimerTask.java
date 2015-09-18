package com.uugty.app.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.uugty.app.utils.DateUtil;

public class TokenTimerTask extends Thread {

	private static final Logger LOG = Logger.getLogger(LogTimerTask.class);

	public static final String TOKEN_VALIDATE = "TOKEN_VALIDATE";

	private static final Date CURRENT_DATE = DateUtil.nextDate();

	@Override
	public void run() {

		LOG.info("启动令牌验证的线程.................");
		servletContext.setAttribute(TOKEN_VALIDATE,
				new HashMap<String, Integer>());
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				LOG.info("清理令牌验证数据...........");
				servletContext.setAttribute(TOKEN_VALIDATE,
						new HashMap<String, Integer>());
			}
		}, CURRENT_DATE, 1000 * 60 * 60 * 24);
	}

	private ServletContext servletContext;

	public TokenTimerTask(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
