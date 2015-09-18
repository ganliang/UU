package com.uugty.validate.dao;

import java.util.List;

/** 基类接口 **/
public interface IBaseDao<T> {

	/** 查找所有对象 **/
	List<T> findAllObject(String paremt);

	/** 根据参数，查找所有对象 **/
	List<T> findAllByStatement(String paremt, T t);

	/** 根据参数,查找对象 **/
	T findObjectByparemt(String paremt, T t);

	/** 添加一个对象 **/
	boolean addObject(String paremt, T t);

	/** 删除一个对象 **/
	boolean delObject(String paremt, T t);

	/** 更新一个对象 **/
	boolean updateObject(String paremt, T t);

	/**
	 * @Title: count
	 * @Description: 查找数量
	 * @param @param parent
	 * @param @param t
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	int count(String parent, T t);

}
