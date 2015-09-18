package com.uugty.validate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uugty.validate.dao.IRoadlineDao;
import com.uugty.validate.domain.Roadline;
import com.uugty.validate.utils.Page;

@Repository(IRoadlineDao.SERVER_NAME)
public class RoadlineDaoImpl extends BaseDaoImpl<Roadline> implements
		IRoadlineDao {

	@Override
	public int findAllRoadlineCount(Roadline roadline) {
		return this.count("findAllRoadlineCount", roadline);
	}

	@Override
	public List<Roadline> findAllRoadline(Roadline roadline, Page page) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginIndex", page.getBeginIndex());
		map.put("pageSize", page.getPageSize());
		map.put("roadlineStatus", roadline.getRoadlineStatus());
		return findAllByStatement("findAllRoadline", map);
	}

	@Override
	public void dropRoadline(Roadline roadline) {
		this.updateObject("dropRoadline", roadline);
	}
	@Override
	public Roadline findRoadlineById(Roadline roadline) {
		return this.findObjectByparemt("findRoadlineById", roadline);
	}
}
