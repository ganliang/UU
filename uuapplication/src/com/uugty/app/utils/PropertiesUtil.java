package com.uugty.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.uugty.app.log.LogWriter;

/**
 * @ClassName: PropertiesUtil
 * @Description: 获取资源文件
 * @author ganliang
 * @date 2015年6月12日 下午8:17:00
 */
public class PropertiesUtil {

	private static Logger log = Logger.getLogger(LogWriter.class);

	public static Properties getProperties(String propertiesName) {

		Properties p = new Properties();

		try {
			InputStream inputStream = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(propertiesName);
			p.load(inputStream);

		} catch (IOException e) {
			log.error("读取配置文件{" + propertiesName + "}出现异常");
			throw new RuntimeException(e);
		}

		return p;
	}

}
