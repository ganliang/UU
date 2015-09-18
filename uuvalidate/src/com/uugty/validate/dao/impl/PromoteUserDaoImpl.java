package com.uugty.validate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uugty.validate.dao.IPromoteUserDao;
import com.uugty.validate.domain.PromoteUser;
import com.uugty.validate.utils.Page;

@Repository(IPromoteUserDao.SERVER_NAME)
public class PromoteUserDaoImpl extends BaseDaoImpl<PromoteUser> implements
		IPromoteUserDao {

	@Override
	public List<PromoteUser> findAllPromoteUser(PromoteUser promoteUser,
			Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginIndex", page.getBeginIndex());
		map.put("pageSize", page.getPageSize());
		return findAllByStatement("findAllPromoteUser", map);
	}

	@Override
	public int findAllPromoteUserCount(PromoteUser promoteUser) {
		return this.count("findAllPromoteUserCount", promoteUser);
	}

	@Override
	public void savePromoteUser(PromoteUser promoteUser) {
		this.addObject("savePromoteUser", promoteUser);
	}

	@Override
	public void updatePromoteUser(PromoteUser promoteUser) {
		this.updateObject("updatePromoteUser", promoteUser);
	}
}
