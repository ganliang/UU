package com.uugty.app.utils;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * 文件的帮助类
 * 
 * @author uu
 *
 */
public class FileUtil {
	/**
	 * 获取系统的所有的属性
	 */
	@Test
	public void list() {
		Properties properties = System.getProperties();
		for (Object key : properties.keySet()) {
			System.out.println(key + "\t"
					+ properties.getProperty(String.valueOf(key)));
		}
	}

	/**
	 * 获取项目根目录
	 * 
	 * @return
	 */
	public static String currentProjectPath() {
		String path = System.getProperty("user.dir");
		URL xmlpath = FileUtils.class.getClassLoader().getResource("");
		String tempPath = xmlpath.getPath().toLowerCase();
		if (tempPath.contains("web-inf")) {
			path = tempPath.split("web-inf")[0];
		} else if (tempPath.contains("lib")) {
			path = tempPath.split("lib")[0];
		}
		path.replace("\\", File.separator).replace("/", File.separator);
		if (path.endsWith("\\") || path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		if (path.startsWith("\\") || path.startsWith("/")) {
			path = path.substring(1, path.length());
		}
		return path;
	}
}
