package com.uugty.validate.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uugty.validate.domain.PromoteUser;
import com.uugty.validate.utils.Page;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
public interface IPromoteUserService {
	public static final String SERVER_NAME = "com.uugty.validate.service.impl.PromoteUserServiceImpl";

	public int findAllPromoteUserCount(PromoteUser promoteUser);

	public List<PromoteUser> findAllPromoteUser(PromoteUser promoteUser,
			Page page);

	public void savePromoteUser(PromoteUser promoteUser);

	public void updatePromoteUser(PromoteUser promoteUser);
}
