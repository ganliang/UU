package com.uugty.app.web.listener;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.uugty.app.log.LogPropertiesTask;
import com.uugty.app.log.LogTimerTask;
import com.uugty.app.log.LogWriter;
import com.uugty.app.log.TokenTimerTask;

/**
 * @ClassName: LogContext
 * @Description: 记录用户操作的日志记录
 * @author ganliang
 * @date 2015年6月12日 上午10:42:30
 */
@WebListener
public class LogContext implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(LogContext.class);

	/**
	 * 当系统关闭的时候,将数据全部写入到文件中去
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		for (int i = 0; i < 24; i++) {
			Object obj = servletContext.getAttribute(String.valueOf(i));
			if (obj != null) {
				ConcurrentLinkedQueue<String> logs = (ConcurrentLinkedQueue<String>) obj;
				if (logs.size() > 0) {
					new LogWriter(logs).start();
				}
				try {
					Thread.sleep(100);// 等待保存文件线程100ms
				} catch (InterruptedException e) {
					LOG.error("线程休眠出现异常");
				}
			}
		}
		LOG.info("系统销毁 contextDestroyed........");
		LOG.info("系统资源清理........................");
	}

	/**
	 * 当系统初始化的时候
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {

		// ConcurrentLinkedQueue<String> logs = null;
		// Collections.synchronizedList(new ArrayList<String>());
		// 注册24个，每个小时一个
		LOG.info("系统初始化 contextInitialized........");
		ServletContext servletContext = event.getServletContext();
		// for (int i = 0; i < 24; i++) {
		// LOG.info("servletContext{" + i + "},values{}初始化........");
		// servletContext.setAttribute(String.valueOf(i), logs);
		// }
		/**
		 * 启动一个定时器
		 */
		new LogTimerTask(servletContext).start();

		/**
		 * 初始化配置文件
		 */
		new LogPropertiesTask().start();

		/**
		 * 启动一个定时器,定时清理服务器保存的验证码验证次数
		 */
		new TokenTimerTask(servletContext).start();

		/**
		 * 初始化敏感词库
		 */
		// KeywordFilter filter = new KeywordFilter();
		// filter.initfiltercode();

		// 等待连接池初始化
//		new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				IUserService userService = new UserServiceImpl();
//				// 将所有的用户的状态都设置为未登录
//				userService.clearUser();
//				LOG.info("将所有的用户状态都设置为未登录.............");
//			}
//		}, 2000);

	}
}
