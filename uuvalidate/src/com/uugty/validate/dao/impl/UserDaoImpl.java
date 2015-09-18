package com.uugty.validate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uugty.validate.dao.IUserDao;
import com.uugty.validate.domain.User;
import com.uugty.validate.utils.Page;

@Repository(IUserDao.SERVER_NAME)
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@Override
	public User logon(User user) {
		List<User> list = findAllByStatement("selectPersonByNameAndPassword",
				user);
		return list != null ? list.get(0) : null;
	}

	@Override
	public List<User> findAllUser(User user, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginIndex", page.getBeginIndex());
		map.put("pageSize", page.getPageSize());
		map.put("userIdValidate", user.getUserIdValidate());
		map.put("userCertificateValidate", user.getUserCertificateValidate());
		map.put("userTourValidate", user.getUserTourValidate());
		map.put("userAvatarValidate", user.getUserAvatarValidate());
		map.put("userCarValidate", user.getUserCarValidate());
		map.put("type", user.getType());
		map.put("userTel", user.getUserTel());
		map.put("userName", user.getUserName());
		return findAllByStatement("findAllUser", map);
	}

	@Override
	public int findAllUserCount(User user) {
		return this.count("findAllUserCount", user);
	}

	@Override
	public void validateReject(User user) {
		this.updateObject("validateReject", user);
	}

	@Override
	public void validateAdopt(User user) {
		this.updateObject("validateAdopt", user);
	}

	@Override
	public void validateIdentifyAdopt(User user) {
		this.updateObject("validateIdentifyAdopt", user);
	}
}
