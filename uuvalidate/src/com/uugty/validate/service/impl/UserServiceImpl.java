package com.uugty.validate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uugty.validate.dao.IUserDao;
import com.uugty.validate.domain.User;
import com.uugty.validate.service.IUserService;
import com.uugty.validate.utils.Page;

@Service(IUserService.SERVER_NAME)
public class UserServiceImpl implements IUserService {
	// 注入dao
	@Resource(name = IUserDao.SERVER_NAME)
	private IUserDao userDao;

	@Override
	public boolean validatePerson(User person) {
		// 调用数据层获取到登陆的用户的信息
		User p = userDao.logon(person);
		return p != null ? true : false;
	}

	@Override
	public List<User> findAllUser(User user, Page page) {
		return userDao.findAllUser(user, page);
	}

	@Override
	public int findAllUserCount(User user) {
		return userDao.findAllUserCount(user);
	}

	@Override
	public void validateReject(User user) {
		userDao.validateReject(user);
	}

	@Override
	public void validateAdopt(User user) {
		userDao.validateAdopt(user);
	}

	@Override
	public void validateIdentifyAdopt(User user) {
		userDao.validateIdentifyAdopt(user);
	}
}
