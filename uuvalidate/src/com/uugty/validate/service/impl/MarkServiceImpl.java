package com.uugty.validate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uugty.validate.dao.IMarkDao;
import com.uugty.validate.domain.Mark;
import com.uugty.validate.service.IMarkService;

@Service(IMarkService.SERVER_NAME)
public class MarkServiceImpl implements IMarkService {

	// 注入dao
	@Resource(name = IMarkDao.SERVER_NAME)
	private IMarkDao markDao;

	@Override
	public List<Mark> getAllMark() {
		return markDao.getAllMark();
	}

	@Override
	public void addMarkContent(List<Integer> args) {
		markDao.addMarkContent(args);
	}
}
