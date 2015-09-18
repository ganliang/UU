package com.uugty.validate.dao;

import java.util.List;

import com.uugty.validate.domain.Roadline;
import com.uugty.validate.utils.Page;

public interface IRoadlineDao {

	public static final String SERVER_NAME = "com.uugty.validate.dao.impl.RoadlineDaoImpl";

	public int findAllRoadlineCount(Roadline roadline);

	public List<Roadline> findAllRoadline(Roadline roadline, Page page);

	public void dropRoadline(Roadline roadline);

	public Roadline findRoadlineById(Roadline roadline);

}
