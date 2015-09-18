package com.uugty.validate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uugty.validate.dao.IPromoteUserDao;
import com.uugty.validate.domain.PromoteUser;
import com.uugty.validate.service.IPromoteUserService;
import com.uugty.validate.utils.Page;

@Service(IPromoteUserService.SERVER_NAME)
public class PromoteUserServiceImpl implements IPromoteUserService {
	// 注入dao
	@Resource(name = IPromoteUserDao.SERVER_NAME)
	private IPromoteUserDao promoteUserDao;

	@Override
	public int findAllPromoteUserCount(PromoteUser promoteUser) {
		return promoteUserDao.findAllPromoteUserCount(promoteUser);
	}

	@Override
	public List<PromoteUser> findAllPromoteUser(PromoteUser promoteUser,
			Page page) {
		return promoteUserDao.findAllPromoteUser(promoteUser, page);
	}

	@Override
	public void savePromoteUser(PromoteUser promoteUser) {
		promoteUserDao.savePromoteUser(promoteUser);
	}

	@Override
	public void updatePromoteUser(PromoteUser promoteUser) {
		promoteUserDao.updatePromoteUser(promoteUser);
	}

}
