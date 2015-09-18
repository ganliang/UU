package com.uugty.validate.dao;

import java.util.List;

import com.uugty.validate.domain.User;
import com.uugty.validate.utils.Page;

public interface IUserDao {

	public static final String SERVER_NAME = "com.uugty.validate.dao.impl.UserDaoImpl";

	public User logon(User person);

	public List<User> findAllUser(User user, Page page);

	public int findAllUserCount(User user);

	public void validateReject(User user);

	public void validateAdopt(User user);

	public void validateIdentifyAdopt(User user);

}
