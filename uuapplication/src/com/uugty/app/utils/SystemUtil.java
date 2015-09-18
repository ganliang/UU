package com.uugty.app.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: SystemUtil
 * @Description: 操作系统相关的工具类
 * @author ganliang
 * @date 2015年6月6日 下午3:53:30
 */
public final class SystemUtil {
	/**
	 * 判断是否为Windows系统
	 * 
	 * @return
	 */
	public static boolean isWindows() {
		String osname = System.getProperty("os.name");
		return StringUtils.startsWithIgnoreCase(osname, "win");
	}

	/**
	 * 判断是否为Linux系统
	 * 
	 * @return
	 */
	public static boolean isLinux() {
		String osname = System.getProperty("os.name");
		return StringUtils.startsWithIgnoreCase(osname, "linux");
	}
}