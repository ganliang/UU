package com.uugty.app.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.constant.StringConstant;
import com.uugty.app.entity.SQLEntity;

/**
 * @ClassName: SQLUtil
 * @Description: sql工具类
 * @author ganliang
 * @date 2015年6月9日 下午6:35:39
 */
public class SQLUtil {

	private static final Logger LOG = Logger.getLogger(SQLUtil.class);

	/**
	 * @Title: generateSQL
	 * @Description: 根据对象获取到要更新的数据属性
	 * @param @param obj
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static SQLEntity updateSQL(Object obj) {
		StringBuffer parameters = new StringBuffer();
		List<Object> list = new ArrayList<Object>();

		Field[] fileds = BeanUtil.getFileds(obj.getClass());
		try {
			for (Field field : fileds) {
				field.setAccessible(true);
				Object value = field.get(obj);
				if (StringUtil.isNotEmpty(value) && field.getModifiers() == 2) {
					parameters.append(StringUtil.transform(field.getName())
							+ StringConstant.EQUAL + StringConstant.QUESTION
							+ StringConstant.QUOTA);
					if (field.getType().getName().equals("java.util.Date")) {
						Date date = (Date) value;
						list.add(DateUtil.longDateFormat(date));
					} else {
						list.add(value);
					}
				}
			}
		} catch (SecurityException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		// delete last ,
		if (list.size() > 0) {
			parameters.deleteCharAt(parameters
					.lastIndexOf(StringConstant.QUOTA));
			return new SQLEntity(parameters.toString(), list);
		}
		return null;
	}

	/**
	 * insert into t_user(user_id,user_name) values(?,?)
	 * 
	 * @Title: insertSQL
	 * @Description:生成插入的sql语句
	 * @param @param obj
	 * @param @return
	 * @return SQLEntity 返回类型
	 * @throws
	 */
	public static SQLEntity insertSQL(Object obj) {
		StringBuffer parameters = new StringBuffer();
		StringBuffer quotas = new StringBuffer();
		List<Object> list = new ArrayList<Object>();

		Field[] fileds = BeanUtil.getFileds(obj.getClass());
		try {
			for (Field field : fileds) {
				field.setAccessible(true);
				// System.out.println(field.getModifiers());
				Object value = field.get(obj);
				if (StringUtil.isNotEmpty(value) && field.getModifiers() == 2) {
					parameters.append(StringUtil.transform(field.getName())
							+ StringConstant.QUOTA);
					quotas.append(StringConstant.QUESTION
							+ StringConstant.QUOTA);
					if (value instanceof Date) {
						list.add(DateUtil.longDateFormat((Date) value));
					} else {
						list.add(value);
					}
				}
			}
		} catch (SecurityException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		// delete last ,
		if (list.size() > 0) {
			parameters.deleteCharAt(parameters
					.lastIndexOf(StringConstant.QUOTA));
			quotas.deleteCharAt(quotas.lastIndexOf(StringConstant.QUOTA));
			String entityName = obj.getClass().getName();
			String tableName = generateTableName(entityName);
			String sql = "INSERT INTO " + tableName + "("
					+ parameters.toString() + ") VALUES(" + quotas.toString()
					+ ")";
			return new SQLEntity(sql, list);
		}
		return null;
	}

	/**
	 * @Title: generateTableName
	 * @Description: 根据实体对象的名称来获取到表的名称
	 * @param @param entityName 对象的全限定路径
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String generateTableName(String entityName) {
		String tableName = entityName
				.substring(entityName.lastIndexOf(".") + 1);
		// TUser-->User-->t_user
		String left = tableName.substring(0, 1);
		String right = tableName.substring(1);
		right = StringUtil.transform(right);
		return (left + right).toUpperCase();
	}
}
