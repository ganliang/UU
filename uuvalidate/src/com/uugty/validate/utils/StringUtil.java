package com.uugty.validate.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtil
 * @Description: 字符串工具类
 */
public class StringUtil {

	/** 生成订单编号 */
	public static String generateOrdrNo() {
		String cc = (int) (Math.random() * 1000)
				+ DateUtil.dateFormat(new Date(), "yyyyMMddHHmmssss");
		BigInteger bi = new BigInteger(cc);
		return baseString(bi, 62);
	}

	// 将一个字符串按照分隔符
	public static String[] StringToArray(String str, String seperator) {
		return str.split(seperator);
	}

	public static String encodeUtf8(String code) {
		try {
			return new String(code.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 文件服务器的文件名称获取方式
	public static String getFileName() {
		String cc = (int) (Math.random() * 1000)
				+ DateUtil.dateFormat(new Date(), "yyyyMMddHHmmssss");
		BigInteger bi = new BigInteger(cc);
		return baseString(bi, 62);
	}

	// 转码
	public static String baseString(BigInteger num, int base) {
		String str = "", digit = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		if (num.shortValue() == 0) {
			return "";
		} else {
			BigInteger valueOf = BigInteger.valueOf(base);
			str = baseString(num.divide(valueOf), base);
			return str + digit.charAt(num.mod(valueOf).shortValue());
		}
	}

	public static String parseDate(Date date) {
		StringBuffer buffer = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH) + 1;
		int DATE = cal.get(Calendar.DATE);
		int AM_PM = cal.get(Calendar.AM_PM);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		buffer.append(YEAR + "年" + MONTH + "月" + DATE + "日");
		if (AM_PM == 0) {
			buffer.append("上午");
		} else {
			buffer.append("下午");
		}
		if (HOUR_OF_DAY != 0 && MINUTE != 0) {
			buffer.append(HOUR_OF_DAY + ":" + MINUTE + "分");
		}
		return buffer.toString();
	}

	public static String parseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer buffer = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			throw new RuntimeException("", e);
		}
		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH) + 1;
		int DATE = cal.get(Calendar.DATE);
		int AM_PM = cal.get(Calendar.AM_PM);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		buffer.append(YEAR + "年" + MONTH + "月" + DATE + "日");
		if (AM_PM == 0) {
			buffer.append("上午");
		} else {
			buffer.append("下午");
		}
		if (HOUR_OF_DAY != 0 && MINUTE != 0) {
			buffer.append(HOUR_OF_DAY + ":" + MINUTE + "分");
		}
		return buffer.toString();
	}

	/**
	 * 获取固定长度的随机数字
	 * 
	 * @param n
	 * @return
	 */
	public static String getRandom(int n) {
		if (n == 0) {
			return null;
		}
		Random random = new Random();
		StringBuffer buffer = new StringBuffer(n);
		for (int i = 0; i < n; i++) {
			buffer.append(String.valueOf(random.nextInt(10)));
		}
		return buffer.toString();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(String obj) {
		if (obj == null) {
			return false;
		}
		if (obj.trim().length() == 0) {
			return false;
		}
		if (String.valueOf(obj).equals("null")
				|| String.valueOf(obj).equals("NULL")) {
			return false;
		}
		return true;
	}

	/**
	 * 判断对象是否为空或者int 为0 double为0.0
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Integer) {
			int parseInt = (int) obj;
			if (parseInt == 0) {
				return false;
			}
		}
		if (obj instanceof Double) {
			double parseDouble = (double) obj;
			if (parseDouble == 0.0) {
				return false;
			}
		}
		if (obj instanceof Float) {
			float parseDouble = (float) obj;
			if (parseDouble == 0.0) {
				return false;
			}
		}
		if (obj instanceof String) {
			String str = (String) obj;
			if ("".equals(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检测手机号码是否规范
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean validatePhone(String mobile) {
		if (mobile == null) {
			return false;
		}
		if (mobile.length() != 11) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: transform
	 * @Description: 将大写字母转换为下环线和小写的 userName--->user_name
	 * @param @param name
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String transform(String name) {
		String transform = null;
		if (name != null && name.length() > 0) {
			char[] chars = new char[name.length() * 2];
			int j = 0;
			for (int i = 0; i < name.length(); i++) {
				char charAt = name.charAt(i);
				// 如果是大写字符
				if (Character.isUpperCase(charAt)) {
					// 将大写字符转化为小写字符
					chars[j] = '_';
					chars[j + 1] = Character.toLowerCase(charAt);
					j += 2;
				} else {
					chars[j] = charAt;
					j++;
				}
			}
			transform = new String(chars).trim();
		}
		return transform;
	}

	/**
	 * 获取一个32位字符串的uuid
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取一定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * @Title: getUserBirthdayPost
	 * @Description: 获取到用户所属的年代
	 * @param @param date
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getUserBirthdayPost(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String year = String.valueOf(cal.get(Calendar.YEAR));
		return year.charAt(2) + "0后";
	}

	public static void main(String[] args) {
		// System.out.println(parseDate(new Date()));
		// System.out.println(parseDate(new Date().toLocaleString()));
		// System.out.println(isNotEmpty("123"));
		// System.out.println(checkMobile("15330061450a"));
		// System.out.println(getRandomMessage());
		// System.out.println(baseString(
		// new BigInteger(StringUtil.generateOrdrNo()), 16));
		// String fileName = getFileName();
		// System.out.println(fileName);
		// System.out.println(isNotEmpty(0.0));
		System.out.println(transform("AbcD"));
		System.out.println(getFileName());
	}
}
