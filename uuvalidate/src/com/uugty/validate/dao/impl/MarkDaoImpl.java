package com.uugty.validate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uugty.validate.dao.IMarkDao;
import com.uugty.validate.domain.Mark;

@Repository(IMarkDao.SERVER_NAME)
public class MarkDaoImpl extends BaseDaoImpl<Mark> implements IMarkDao {

	@Override
	public void addMarkContent(List<Integer> args) {
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		map.put("markIds", args);
		this.getSqlSession().update("addMarkContent", map);
	}

	@Override
	public List<Mark> getAllMark() {
		return findAllByStatement("getAllMark", new Mark());
	}
}
