package com.uugty.app.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @ClassName: BeanUtil
 * @Description: 封装客户端传递过来的参数
 * @author ganliang
 * @date 2015年6月6日 下午3:49:47
 */
@SuppressWarnings("rawtypes")
public class BeanUtil {

	private static final Logger log = Logger.getLogger(BeanUtil.class);

	/**
	 * 
	 * @Title: populate
	 * @Description:从request的parameterMap中获取参数，然后通过反射封装对象
	 * @param @param request
	 * @param @param clazz
	 * @param @return 封装的对象
	 * @return Object 返回类型
	 * @throws
	 */
	public static Object populate(HttpServletRequest request, Class clazz) {
		Object object = null;
		try {
			// class对象
			object = clazz.newInstance();
			// 获取对象的所有字段
			Field[] fields = getFileds(clazz);
			// 获取request请求的所有的参数
			Map<String, String> parameterMap = getParameterMap(request);
			if (parameterMap != null && parameterMap.size() > 0) {
				for (String key : parameterMap.keySet()) {
					for (Field field : fields) {
						field.setAccessible(true);
						String name = field.getName();
						// 如果参数和字段的名字不区分大小写的相等
						if (name.equalsIgnoreCase(key)) {
							setProperty(field, object,
									String.valueOf(parameterMap.get(key)));
						}
					}
				}
			}
		} catch (InstantiationException e) {
			log.error("InstantiationException", e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException", e);
			throw new RuntimeException(e);
		}
		return object;
	}

	/**
	 * 
	 * @Title: getFileds
	 * @Description: 根据字节码对象来获取该对象的所有的字段
	 * @param @param clazz
	 * @param @return 对象的所有字段
	 * @return Field[] 返回类型
	 * @throws
	 */
	public static Field[] getFileds(Class clazz) {
		return clazz.getDeclaredFields();
	}

	/**
	 * @Title: getParameterMap
	 * @Description: 获取请求的参数 封装到一个map集合中，并且过滤掉空值的参数
	 * @param @param request
	 * @param @return 参数集合
	 * @return Map<String,String> 返回类型
	 * @throws
	 */
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> prop = new HashMap<String, String>();
		Map<String, String[]> map = request.getParameterMap();
		Set<String> keySet = map.keySet();
		try {
			for (String name : keySet) {
				String value = request.getParameter(name);
				//if (value != null) {
					// value = new String(value.getBytes("iso-8859-1"),
					// "UTF-8");
				//}
				prop.put(name, value);
			}
		} catch (Exception e) {
			log.error("获取requestMap的参数", e);
			throw new RuntimeException(e);
		}
		return prop;
	}

	/**
	 * 
	 * @Title: setProperty
	 * @Description: 将一个字段的值通过反射封装到对象中去
	 * @param @param field
	 * @param @param bean
	 * @param @param value
	 * @return void 返回类型
	 * @throws
	 */
	public static void setProperty(Field field, Object bean, String value) {
		try {
			field.setAccessible(true);
			String typeName = field.getType().getName();
			// typeName = field.getType().getTypeName(); jdk1.8
			// System.out.println(typeName);
			// 如果字段是int类型
			if (typeName.equals("int") || typeName.equals("java.lang.Integer")) {
				// 如果value不为空，但是非int类型，会保存
				field.setInt(bean,
						StringUtil.isNotEmpty(value) ? Integer.parseInt(value)
								: 0);
			}
			// 如果字段是Date类型
			else if (typeName.equals("java.util.Date")) {
				if (value.length() == 10) {
					field.set(
							bean,
							StringUtil.isNotEmpty(value) ? DateUtil
									.shortDateFormat(value) : null);
				} else {
					field.set(
							bean,
							StringUtil.isNotEmpty(value) ? DateUtil
									.longDateFormat(value) : null);
				}
			}
			// 如果是float类型
			else if (typeName.equals("float")
					|| typeName.equals("java.lang.Float")) {
				field.setFloat(bean,
						StringUtil.isNotEmpty(value) ? Float.parseFloat(value)
								: 0);
			}
			// 如果是double类型
			else if (typeName.equals("double")
					|| typeName.equals("java.lang.Double")) {
				field.setDouble(
						bean,
						StringUtil.isNotEmpty(value) ? Double
								.parseDouble(value) : 0);
			} else {
				field.set(bean, StringUtil.isNotEmpty(value) ? value : "");
			}
		} catch (Exception e) {
			log.error("数据封装出现异常", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: setPropertys
	 * @Description: 将一个对象里面的值附到另一个对象中去
	 * @param @param telUser
	 * @param @param class1
	 * @param @return
	 * @return UserEntity 返回类型
	 * @throws
	 */
	public static Object setPropertys(Object object, Class clazz) {
		Object obj = null;
		try {
			obj = clazz.newInstance();
			Field[] fileds1 = getFileds(clazz);
			Field[] fields2 = getFileds(object.getClass());
			for (Field f1 : fileds1) {
				f1.setAccessible(true);
				if (f1.getModifiers() == 2) {
					for (Field f2 : fields2) {
						f2.setAccessible(true);
						// 如果字段相同
						if (f1.getName().equalsIgnoreCase(f2.getName())) {
							if (f2.getType().getName().equals("java.util.Date")) {
								Date date = (Date) f2.get(object);
								f1.set(obj,
										date != null ? DateUtil.dateFormat(
												date, "yyyy-MM-dd") : "");
							} else {
								f1.set(obj, f2.get(object));
							}
						}
					}
				}
			}
		} catch (InstantiationException e) {
			log.error("UserEntity对象实例化异常", e);
			throw new RuntimeException("UserEntity对象实例化异常", e);
		} catch (IllegalAccessException e) {
			log.error("非法的权限访问", e);
			throw new RuntimeException("UserEntity对象实例化异常", e);
		}
		return obj;
	}

	/**
	 * @Title: setPropertys
	 * @Description: 将obj1对象中不为空的值赋给obj2
	 * @param @param obj1
	 * @param @param obj2
	 * @return void 返回类型
	 * @throws
	 */
	public static void setPropertys(Object obj1, Object obj2) {
		Field[] fileds = getFileds(obj1.getClass());
		Field[] otherFields = getFileds(obj2.getClass());
		try {
			for (Field f1 : fileds) {
				f1.setAccessible(true);
				Object value = f1.get(obj1);

				if (StringUtil.isNotEmpty(value) && f1.getModifiers() == 2) {
					for (Field f2 : otherFields) {
						f2.setAccessible(true);
						// 如果字段相同
						if (f1.getName().equalsIgnoreCase(f2.getName())) {
							if (f1.getType().getName().equals("java.util.Date")) {
								Date date = (Date) value;
								if (f2.getType().getName()
										.equals("java.util.Date")) {
									f2.set(obj2, value);
								} else {
									f2.set(obj2,
											date != null ? DateUtil
													.shortDateFormat(date) : "");
								}
							} else {
								f2.set(obj2, value);
							}
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			log.error("非法的权限访问", e);
			throw new RuntimeException("非法的权限访问", e);
		}
	}
}
