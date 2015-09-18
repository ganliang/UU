package com.uugty.app.log;

import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.uugty.app.utils.PropertiesUtil;

/**
 * @ClassName: LogPropertiesTask
 * @Description: 启动一个线程验证配置文件的正确性
 * @author ganliang
 * @date 2015年6月16日 上午10:47:37
 */
public class LogPropertiesTask extends Thread {

	private  static final Logger log = Logger.getLogger(LogPropertiesTask.class);

	private static String propertiesName = "versionUpdate.properties";

	@Override
	public void run() {
		Properties properties = null;
		try {
			properties = PropertiesUtil.getProperties(propertiesName);
			for (Entry<Object, Object> prop : properties.entrySet()) {
				log.info(prop.getKey() + "-->>" + prop.getValue());
			}
		} catch (Exception e) {
			log.error("加载配置文件{" + propertiesName + "}失败");
			throw new RuntimeException(e);
		}
	}
}
