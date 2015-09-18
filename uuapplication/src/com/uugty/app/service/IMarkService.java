package com.uugty.app.service;

import java.util.List;

import com.uugty.app.domain.TMark;

public interface IMarkService {

	public List<Object> getMarkList(TMark mark);

	public void saveMarkList(List<TMark> marks);
	
	public List<Object> getMarkLineList(TMark mark);

}
