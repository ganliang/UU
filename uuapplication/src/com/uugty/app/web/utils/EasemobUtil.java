package com.uugty.app.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easemob.server.uu.jersey.apidemo.EasemobIMUsers;
import com.easemob.server.uu.jersey.apidemo.EasemobMessages;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.uugty.app.utils.MD5;

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
	 * @param easemobPassword
	 * @Title: createIMUser
	 * @Description: 在环信上注册一个账号
	 * @param @param username
	 * @param @param password
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean createIMUser(String username, String easemobPassword) {
		boolean flag = false;
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		datanode.put("username", username);
		datanode.put("password", easemobPassword);
		ObjectNode createNewIMUserSingleNode = EasemobIMUsers
				.createNewIMUserSingle(datanode);
		if (null != createNewIMUserSingleNode) {
			LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
			flag = true;
		}
		return flag;
	}

	/**
	 * @Title: modifyIMUserPassword
	 * @Description: 修改环信密码
	 * @param @param username
	 * @param @param newpassword
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean modifyIMUserPassword(String username,
			String newpassword) {
		boolean flag = false;
		ObjectNode node = JsonNodeFactory.instance.objectNode();
		node.put("newpassword", newpassword);
		ObjectNode modifyIMUserPasswordWithAdminTokenNode = EasemobIMUsers
				.modifyIMUserPasswordWithAdminToken(username, node);
		if (null != modifyIMUserPasswordWithAdminTokenNode) {
			LOGGER.info("重置IM用户密码 提供管理员token: "
					+ modifyIMUserPasswordWithAdminTokenNode.toString());
			flag = true;
		}
		return flag;
	}

	/**
	 * @Title: deleteAllIMuser
	 * @Description: 删除easemob所有的用户
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	public static void deleteAllIMuser() {
		Long limit = 200l;
		ObjectNode deleteIMUserByUsernameBatchNode = EasemobIMUsers
				.deleteIMUserByUsernameBatch(limit, null);
		if (null != deleteIMUserByUsernameBatchNode) {
			LOGGER.info("删除IM用户[批量]: "
					+ deleteIMUserByUsernameBatchNode.toString());
		}
	}

	/**
	 * @Title: sendMessage
	 * @Description: 以管理员的身份给一个用户发送一条消息
	 * @param @param username
	 * @param @param message
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean sendMessage(String username, String message) {
		final JsonNodeFactory factory = new JsonNodeFactory(false);
		boolean flag = false;
		String from = "admin";
		String targetTypeus = "users";

		ObjectNode ext = factory.objectNode();
		ArrayNode targetusers = factory.arrayNode();

		targetusers.add(username);
		ObjectNode txtmsg = factory.objectNode();

		txtmsg.put("msg", message);
		txtmsg.put("type", "txt");

		ObjectNode sendTxtMessageusernode = EasemobMessages.sendMessages(
				targetTypeus, targetusers, txtmsg, from, ext);
		if (null != sendTxtMessageusernode) {
			LOGGER.info("给用户发一条文本消息: " + sendTxtMessageusernode.toString());
		}
		return flag;
	}

	/**
	 * @Title: getFriends
	 * @Description: 获取好友列表
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getFriends(String userName) {
		ObjectNode friends = EasemobIMUsers.getFriends(userName);
		LOGGER.info("获取好友列表" + friends);
		return friends.toString();
	}

	/**
	 * @Title: getIMUsersByUserName
	 * @Description: 获取用户的详细信息
	 * @param @param userName
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getIMUsersByUserName(String userName) {
		ObjectNode imUsersByUserName = EasemobIMUsers
				.getIMUsersByUserName(userName);
		LOGGER.info("用户信息" + imUsersByUserName.toString());
		return imUsersByUserName.toString();
	}

	/**
	 * @Title: getEasemobPassword
	 * @Description: 得到环信的密码[获取UUID的后五位 ; 然后md5加密]
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getEasemobPassword(String uuid) {
		String lastFiveDigest = uuid.substring(27);
		return MD5.MD5Encode(lastFiveDigest);
	}

	/**
	 * @Title: addFriendSingle
	 * @Description: 添加系统好友
	 * @param @param friendUserName
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String addFriendSingle(String friendUserName) {
		String ownerUserName = "sys";
		ObjectNode friendSingle = EasemobIMUsers.addFriendSingle(ownerUserName,
				friendUserName);
		LOGGER.info("好友信息" + friendSingle.toString());
		return friendSingle.toString();
	}
}
