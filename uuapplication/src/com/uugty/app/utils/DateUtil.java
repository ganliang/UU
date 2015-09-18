package com.uugty.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @ClassName: DateUtil
 * @Description: 格式化日期信息的对象
 * @author ganliang
 * @date 2015年6月6日 下午3:53:12
 */
public final class DateUtil {
	public static final long MILLONS = 1000l;
	public static final long SECOND = MILLONS;
	public static final long MINITE = SECOND * 60;
	public static final long HOUR = MINITE * 60;
	public static final long DAY = HOUR * 24;
	static Calendar calendar = null;

	static {
		calendar = Calendar.getInstance();
		calendar.set(1970, 0, 1, 0, 0, 0);
	}

	/**
	 * 将日期对象格式化字符串的形式，并返回只包含年月日形式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String shortDateFormat(Date date) {
		return dateFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 返回当期日期的格式化字符串
	 * 
	 * @return
	 */
	public static String shortDateFormatCurrentDate() {
		return dateFormat(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 将日期格式化并以长格式的形式返回，长格式包括时分秒
	 * 
	 * @param date
	 *            日期对象
	 * @return
	 */
	public static String longDateFormat(Date date) {
		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将当前日期格式化并以长格式的形式进行返回
	 * 
	 * @return
	 */
	public static String longDateFormatCurrentDate() {
		return dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @Title: currentDate
	 * @Description: 获取当前时间的字符串
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String currentDate() {
		return dateFormat(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * @Title: longCurrentDate
	 * @Description:
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String longCurrentDate() {
		return dateFormat(new Date(), "yyyyMMddHHmmssSSSS");
	}

	/***
	 * 将String转化为Date
	 */
	public static Date shortDateFormat(String date) {
		return dateFormat(date, "yyyy-MM-dd");
	}

	public static Date longDateFormat(String date) {
		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取从1970-01-01开始的时间毫秒表示
	 * 
	 * @return
	 */
	public static long getTimesFromStard() {
		return calendar.getTimeInMillis();
	}

	public static String formatStandartSecond(Integer seconds) {
		Date date = new Date(calendar.getTimeInMillis() + seconds * MILLONS);
		return longDateFormat(date);
	}

	/**
	 * 根据指定的格式格式化日期对象
	 * 
	 * @param date
	 *            日期对象
	 * @param foramt
	 *            要格式的格式
	 * @return
	 */
	public static String dateFormat(Date date, String foramt) {
		String result = StringUtils.EMPTY;
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(foramt);
			result = df.format(date);
		}
		return result;
	}

	public static Date dateFormat(String date, String format) {
		Date result = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			result = df.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式出现错误!", e);
		}
		return result;
	}

	/**
	 * 返回起始日期和结束日期之间的天数之差
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 开始时间与结束时间之间的天数
	 */
	public static int calculateDays(Date startDate, Date endDate) {
		int result = 0;
		if (startDate != null && endDate != null) {
			result = (int) ((endDate.getTime() - startDate.getTime()) / DAY);
		}
		return result;
	}

	/**
	 * 返回起始日期和结束日期之间的分钟数
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 开始时间与结束时间之间的天数
	 */
	public static int calculateMinutes(Date startDate, Date endDate) {
		int result = 0;
		if (startDate != null && endDate != null) {
			result = Math
					.abs((int) ((endDate.getTime() - startDate.getTime()) / MINITE));
		}
		return result;
	}

	public static int calculateMinutes(Date startDate, String orderCreateDate) {
		Date endDate = longDateFormat(orderCreateDate);
		return calculateDays(startDate, endDate);
	}

	/**
	 * 当前时间戳
	 * 
	 * @return
	 */
	public static long currentTime() {
		return new Date().getTime();
	}

	/**
	 * 当前时间戳转为日期
	 * 
	 * @param time
	 * @return 2012-11-11 格式
	 */
	public static String currentTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(time));
	}

	/**
	 * 
	 * @param time
	 * @param patten
	 * @return
	 */
	public static String currentTime(long time, String patten) {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(new Date(time));
	}

	/**
	 * <pre>
	 * 当前时间转为时间戳  
	 * 转化失败返回-1
	 * @param time 标准的日期格式
	 * @return long
	 * </pre>
	 */
	public static long currentTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long date = -1;
		try {
			date = sdf.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static long currentTime(String time, String paten) {
		SimpleDateFormat sdf = new SimpleDateFormat(paten);
		long date = -1;
		try {
			date = sdf.parse(time).getTime();
		} catch (ParseException e) {
			throw new RuntimeException("日期格式出现错误!", e);
		}
		return date;
	}

	/**
	 * 
	 * @Title: getCurrentHour
	 * @Description: 获取到当前的小时
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentHour() {
		Date date = new Date();
		int hours = date.getHours();
		return hours;
	}

	/**
	 * @Title: minuteNum
	 * @Description: 获取两个时间相减的分钟数
	 * @param @param expir
	 * @param @return
	 * @return int 返回类型 【分钟】
	 * @throws
	 */
	public static int subMinute(long expir) {
		long time = new Date().getTime();
		long m = time - expir;
		return (int) m / 1000 / 60;
	}

	public static Date nextDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static void main(String[] args) {
		// System.out.println(getCurrentHour());
		// long expir = new Date().getTime();
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// System.out.println(subMinute(expir));

		System.out.println(nextDate());
		System.out.println(longCurrentDate());
	}
}