package com.uugty.validate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uugty.validate.dao.IRoadlineDao;
import com.uugty.validate.domain.Roadline;
import com.uugty.validate.service.IRoadlineService;
import com.uugty.validate.utils.Page;

@Service(IRoadlineService.SERVER_NAME)
public class RoadlineServiceImpl implements IRoadlineService {
	// 注入dao
	@Resource(name = IRoadlineDao.SERVER_NAME)
	private IRoadlineDao roadlineDao;

	@Override
	public int findAllRoadlineCount(Roadline roadline) {
		return roadlineDao.findAllRoadlineCount(roadline);
	}

	@Override
	public List<Roadline> findAllRoadline(Roadline roadline, Page page) {
		return roadlineDao.findAllRoadline(roadline, page);
	}

	@Override
	public void dropRoadline(Roadline roadline) {
		roadlineDao.dropRoadline(roadline);
	}

	@Override
	public Roadline findRoadlineById(Roadline roadline) {
		return roadlineDao.findRoadlineById(roadline);
	}

}
