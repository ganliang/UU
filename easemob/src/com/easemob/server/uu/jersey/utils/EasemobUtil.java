package com.easemob.server.uu.jersey.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easemob.server.uu.comm.Constants;
import com.easemob.server.uu.jersey.apidemo.EasemobIMUsers;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @ClassName: EasemobUtil
 * @Description: 环信接口
 * @author ganliang
 * @date 2015年6月15日 上午11:30:55
 */
public class EasemobUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EasemobUtil.class);

	/**
	 * @Title: createIMUser
	 * @Description: 在环信上注册一个账号
	 * @param @param username
	 * @param @param password
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean createIMUser(String username, String password) {
		boolean flag = false;
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		datanode.put("username", username);
		datanode.put("password", password);
		ObjectNode createNewIMUserSingleNode = EasemobIMUsers
				.createNewIMUserSingle(datanode);
		if (null != createNewIMUserSingleNode) {
			LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
			flag = true;
		}
		return flag;
	}

	public static String getFriends() {
		ObjectNode friends = EasemobIMUsers
				.getFriends("eca5cf2ce37c4a4094df3a3e99f1a916");
		LOGGER.info("获取好友列表" + friends);
		return friends.toString();
	}

	public static String getIMUsersByUserName(String userName) {
		ObjectNode imUsersByUserName = EasemobIMUsers
				.getIMUsersByUserName(userName);
		LOGGER.info("用户信息" + imUsersByUserName.toString());
		return imUsersByUserName.toString();
	}

	public static void main(String[] args) {
	}
}
