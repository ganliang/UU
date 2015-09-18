package com.uugty.validate.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uugty.validate.domain.Roadline;
import com.uugty.validate.utils.Page;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
public interface IRoadlineService {
	public static final String SERVER_NAME = "com.uugty.validate.service.impl.RoadlineServiceImpl";

	public int findAllRoadlineCount(Roadline roadline);

	public List<Roadline> findAllRoadline(Roadline roadline, Page page);

	public void dropRoadline(Roadline roadline);

	public Roadline findRoadlineById(Roadline roadline);

}
