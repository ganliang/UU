package com.uugty.validate.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uugty.validate.domain.User;
import com.uugty.validate.utils.Page;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
public interface IUserService {

	public static final String SERVER_NAME = "com.uugty.validate.service.impl.UserServiceImpl";

	public boolean validatePerson(User person);

	/**
	 * @param page
	 * @Title: findAllUser
	 * @Description: 找到所有的用户
	 * @param @param user
	 * @param @return
	 * @return List<User> 返回类型
	 * @throws
	 */
	public List<User> findAllUser(User user, Page page);

	/**
	 * @Title: findAllUserCount
	 * @Description: 当前用户的数量
	 * @param @param user
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	public int findAllUserCount(User user);

	/**
	 * @Title: validateReject
	 * @Description:
	 * @param @param user
	 * @return void 返回类型
	 * @throws
	 */
	public void validateReject(User user);

	/**
	 * @Title: validateAdopt
	 * @Description:
	 * @param @param user
	 * @return void 返回类型
	 * @throws
	 */
	public void validateAdopt(User user);

	public void validateIdentifyAdopt(User user);

}
