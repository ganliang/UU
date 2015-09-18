package com.uugty.validate.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uugty.validate.domain.Mark;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
public interface IMarkService {
	public static final String SERVER_NAME = "com.uugty.validate.service.impl.MarkServiceImpl";

	public List<Mark> getAllMark();

	public void addMarkContent(List<Integer> args);

}
