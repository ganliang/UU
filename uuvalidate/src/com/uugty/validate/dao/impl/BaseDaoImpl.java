package com.uugty.validate.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.uugty.validate.dao.IBaseDao;

/** 基类的实现类，dao层基类 **/
@SuppressWarnings({ "unchecked" })
public class BaseDaoImpl<T> extends SqlSessionDaoSupport implements IBaseDao<T> {

	@Override
	public List<T> findAllObject(String paremt) {
		return getSqlSession().selectList(paremt);
	}

	@Override
	public T findObjectByparemt(String paremt, Object t) {
		if (t != null) {
			T t1 = (T) getSqlSession().selectOne(paremt, t);
			return t1;
		}
		return null;
	}

	@Override
	public List<T> findAllByStatement(String paremt, Object t) {
		if (t != null && paremt != null)
			return getSqlSession().selectList(paremt, t);
		return null;
	}

	@Override
	public boolean addObject(String paremt, Object t) {
		int insert = getSqlSession().insert(paremt, t);
		if (insert > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delObject(String paremt, Object t) {
		int delete = getSqlSession().delete(paremt, t);
		if (delete > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateObject(String paremt, Object t) {
		int update = getSqlSession().update(paremt, t);
		if (update > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int count(String parent, T t) {
		return (int) getSqlSession().selectOne(parent, t);
	}
}
