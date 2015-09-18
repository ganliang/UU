package com.uugty.validate.dao;

import java.util.List;

import com.uugty.validate.domain.Mark;

public interface IMarkDao extends IBaseDao<Mark> {

	public static final String SERVER_NAME = "com.uugty.validate.dao.impl.MarkDaoImpl";

	public void addMarkContent(List<Integer> args);

	public List<Mark> getAllMark();

}
