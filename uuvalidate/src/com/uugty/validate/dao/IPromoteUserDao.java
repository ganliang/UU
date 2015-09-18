package com.uugty.validate.dao;

import java.util.List;

import com.uugty.validate.domain.PromoteUser;
import com.uugty.validate.utils.Page;

public interface IPromoteUserDao {
	public static final String SERVER_NAME = "com.uugty.validate.dao.impl.PromoteUserDaoImpl";

	public int findAllPromoteUserCount(PromoteUser promoteUser);

	public List<PromoteUser> findAllPromoteUser(PromoteUser promoteUser,
			Page page);

	public void savePromoteUser(PromoteUser promoteUser);

	public void updatePromoteUser(PromoteUser promoteUser);

}
