package com.uugty.app.service.impl;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.dao.UserDao;
import com.uugty.app.domain.TTempUser;
import com.uugty.app.domain.TUser;
import com.uugty.app.domain.TUserBank;
import com.uugty.app.domain.TWithdrawCash;
import com.uugty.app.entity.EasemobEntity;
import com.uugty.app.entity.LoginEntity;
import com.uugty.app.entity.NeighborUserEntity;
import com.uugty.app.entity.RoadlineDetailEntity;
import com.uugty.app.entity.SQLEntity;
import com.uugty.app.entity.UserDetailEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.utils.SQLUtil;

/**
 * 
 * @ClassName: UserServiceImpl
 * @Description: 用户业务实现类
 * @author ganliang
 * @date 2015年6月6日 下午3:55:55
 */
public class UserServiceImpl implements IUserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	private UserDao userDao = new UserDao();

	/**
	 * 
	 * @Title: weChartLogin
	 * @Description: 使用微信来登录
	 * @param @param user
	 * @param @return
	 * @return TUser 返回类型
	 * @throws
	 */
	@Override
	public TUser weChartLogin(TUser user) {
		List<Object> list = new ArrayList<Object>();
		list.add(user.getOpenid());
		List<Object> query = null;
		try {
			query = userDao.query("SELECT *FROM T_USER WHERE wx_openid=?",
					list, TUser.class);
		} catch (InstantiationException e) {
			log.error("微信登录出现异常", e);
			throw new RuntimeException("微信登录出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("微信登录出现异常", e);
			throw new RuntimeException("微信登录出现异常", e);
		} catch (SQLException e) {
			log.error("微信登录出现异常", e);
			throw new RuntimeException("微信登录出现异常", e);
		}
		return (query != null && query.size() > 0) ? (TUser) query.get(0)
				: null;
	}

	/**
	 * @Title: saveUser
	 * @Description: 保存用户
	 * @param @param user
	 * @param @return
	 * @return TUser 返回类型
	 * @throws
	 */
	@Override
	public TUser saveUser(TUser user) {
		// user.setUserId(StringUtil.getUUID());
		// user.setUserRegisterDate(new Date());
		// user.setUserLastLoginDate(new Date());
		// user.setUserTelValidate(1);
		// user.setUserTelValidateDate(new Date());
		SQLEntity sqlEntity = SQLUtil.insertSQL(user);
		try {
			userDao.Insert(sqlEntity.getParameter(), sqlEntity.getList());
		} catch (SQLException e) {
			log.error("保存用户信息出现异常", e);
			throw new RuntimeException("保存用户信息出现异常", e);
		}
		return user;
	}

	/**
	 * @Title: updateWeChatUser
	 * @Description: 更新微信用户
	 * @param @param weChartUser
	 * @param @return
	 * @return TUser 返回类型
	 * @throws
	 */
	@Override
	public TUser updateUser(TUser user) {
		// user.setUserRegisterDate(new Date());
		// user.setUserLastLoginDate(new Date());
		SQLEntity sqlEntity = SQLUtil.updateSQL(user);
		String sql = "UPDATE T_USER SET " + sqlEntity.getParameter()
				+ " WHERE USER_ID=?";
		List<Object> list = sqlEntity.getList();
		list.add(user.getUserId());
		try {
			userDao.update(sql, list);
		} catch (SQLException e) {
			log.error("更新用户信息出现异常", e);
			throw new RuntimeException("更新用户信息出现异常", e);
		}
		return user;
	}

	/**
	 * @Title: getUserByTelphone
	 * @Description: 根据用户的手机号码来获取用户信息
	 * @param @param user
	 * @param @return
	 * @return TUser 返回类型
	 * @throws
	 */
	@Override
	public TUser getUserByTelphone(TUser user) {
		List<Object> list = new ArrayList<Object>();
		list.add(user.getUserTel());
		String sql = "SELECT *FROM T_USER WHERE USER_TEL=?";
		List<Object> query = null;
		try {
			query = userDao.query(sql, list, TUser.class);
		} catch (InstantiationException e) {
			log.error("根据用户手机号获取用户详细信息出现异常", e);
			throw new RuntimeException("根据用户手机号获取用户详细信息出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据用户手机号获取用户详细信息出现异常", e);
			throw new RuntimeException("根据用户手机号获取用户详细信息出现异常", e);
		} catch (SQLException e) {
			log.error("根据用户手机号获取用户详细信息出现异常", e);
			throw new RuntimeException("根据用户手机号获取用户详细信息出现异常", e);
		}
		return query != null && query.size() > 0 ? (TUser) query.get(0) : null;
	}

	/**
	 * @Title: getUserDetailMessage
	 * @Description: 获取用户的详细信息
	 * @param @param UserDetailEntity
	 * @param @return
	 * @return UserDetailEntity 返回类型
	 * @throws
	 */
	@Override
	public UserDetailEntity getUserDetailMessage(UserDetailEntity user) {
		List<Object> list = new ArrayList<Object>();
		list.add(user.getUserId());
		String sql = "SELECT *FROM T_USER WHERE USER_ID=?";
		List<Object> query = null;
		try {
			query = userDao.query(sql, list, UserDetailEntity.class);
		} catch (InstantiationException e) {
			log.error("根据用户id获取用户详细信息出现异常", e);
			throw new RuntimeException("根据用户id获取用户详细信息出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据用户id获取用户详细信息出现异常", e);
			throw new RuntimeException("根据用户id获取用户详细信息出现异常", e);
		} catch (SQLException e) {
			log.error("根据用户id获取用户详细信息出现异常", e);
			throw new RuntimeException("根据用户id获取用户详细信息出现异常", e);
		}
		return query != null && query.size() > 0 ? (UserDetailEntity) query
				.get(0) : null;
	}

	/**
	 * @Title: getNeighborUser
	 * @Description: 找到附近的游客和导游
	 * @param @param user
	 * @param @return
	 * @return List<TUser> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getNeighborUser(TUser user) {
		List<Object> args = new ArrayList<Object>();
		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLng());
		args.add(10);// 距离小于10千米
		String sql = "SELECT u.user_id,u.user_name,u.user_avatar,u.user_last_login_lat,u.user_last_login_lng,u.uuid,u.user_status,t.temp_shout FROM t_user u LEFT JOIN T_TEMP_USER t ON(t.temp_uuid=u.uuid) WHERE u.user_is_login=1 AND 2 * 6378.137 * asin(sqrt(pow(sin(0.008726646259971648 * (u.user_last_login_lat - ?)), 2)+ cos(0.17453292519943296 * u.user_last_login_lat)* cos(0.17453292519943296 * ?)* pow(sin(0.008726646259971648 * (u.user_last_login_lng - ?)), 2)))<? ORDER BY u.user_last_login_lat,u.user_last_login_lng DESC";
		List<Object> query = null;
		List<Object> list = new ArrayList<Object>();
		try {
			query = userDao.query(sql, args, NeighborUserEntity.class);
			for (Object object : query) {
				NeighborUserEntity entity = (NeighborUserEntity) object;
				String tempShout = entity.getTempShout();
				if (tempShout != null && !"".equals(tempShout)) {
					entity.setTempShout(URLDecoder.decode(
							entity.getTempShout(), "UTF-8"));
				}
				if (entity.getUserId().equals(user.getUserId())) {
					list.add(0, entity);
				} else {
					list.add(entity);
				}
			}
		} catch (InstantiationException e) {
			log.error("获取附近的导游和游客出现异常", e);
			throw new RuntimeException("获取附近的导游和游客出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取附近的导游和游客出现异常", e);
			throw new RuntimeException("获取附近的导游和游客出现异常", e);
		} catch (Exception e) {
			log.error("获取附近的导游和游客出现异常", e);
			throw new RuntimeException("获取附近的导游和游客出现异常", e);
		}
		return list;
	}

	@Deprecated
	public List<Object> searchNeighborUser2(TUser user) {
		List<Object> args = new ArrayList<Object>();
		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLng());
		args.add(user.getDistance());
		String userSex = "";
		switch (user.getUserSex()) {
		case 0:
			userSex = " 1=1 ";
			break;
		case 1:
			userSex = " u.user_sex=2";
			break;
		case 2:
			userSex = " u.user_sex=1";
			break;
		}
		args.add(user.getSize());
		String sql = "SELECT u.user_id,u.user_name,u.user_avatar,u.user_last_login_lat,u.user_last_login_lng,u.uuid,u.user_status,t.temp_shout FROM t_user u LEFT JOIN T_TEMP_USER t ON(t.temp_uuid=u.uuid) WHERE u.user_is_login=1 AND 2 * 6378.137 * asin(sqrt(pow(sin(0.008726646259971648 * (u.user_last_login_lat - ?)), 2)+ cos(0.17453292519943296 * u.user_last_login_lat)* cos(0.17453292519943296 * ?)* pow(sin(0.008726646259971648 * (u.user_last_login_lng - ?)), 2)))<? AND "
				+ userSex + " LIMIT 0,?";
		List<Object> list = new ArrayList<Object>();
		try {
			List<Object> query = userDao.query(sql, args,
					NeighborUserEntity.class);
			if (query.size() < user.getSize()) {
				List<Object> args2 = new ArrayList<Object>();
				args2.add(user.getUserLastLoginLat());
				args2.add(user.getUserLastLoginLat());
				args2.add(user.getUserLastLoginLng());
				args2.add(user.getDistance());
				args2.add(user.getUserWork());
				args2.add(user.getSize() - query.size());
				sql = "SELECT u.user_id,u.user_name,u.user_avatar,u.user_last_login_lat,u.user_last_login_lng,u.uuid,u.user_status,t.temp_shout FROM t_user u LEFT JOIN T_TEMP_USER t ON(t.temp_uuid=u.uuid) WHERE u.user_is_login=1 AND 2 * 6378.137 * asin(sqrt(pow(sin(0.008726646259971648 * (u.user_last_login_lat - ?)), 2)+ cos(0.17453292519943296 * u.user_last_login_lat)* cos(0.17453292519943296 * ?)* pow(sin(0.008726646259971648 * (u.user_last_login_lng - ?)), 2)))<? AND u.user_work LIKE ? LIMIT 0,?";
				List<Object> query2 = userDao.query(sql, args2,
						NeighborUserEntity.class);
				query.addAll(query2);
			}
			if (query.size() < user.getSize()) {
				List<Object> args3 = new ArrayList<Object>();
				args3.add(user.getUserLastLoginLat());
				args3.add(user.getUserLastLoginLat());
				args3.add(user.getUserLastLoginLng());
				args3.add(user.getDistance());
				args3.add(user.getSize() - list.size());
				sql = "SELECT u.user_id,u.user_name,u.user_avatar,u.user_last_login_lat,u.user_last_login_lng,u.uuid,u.user_status,t.temp_shout FROM t_user u LEFT JOIN T_TEMP_USER t ON(t.temp_uuid=u.uuid) WHERE u.user_is_login=1 AND 2 * 6378.137 * asin(sqrt(pow(sin(0.008726646259971648 * (u.user_last_login_lat - ?)), 2)+ cos(0.17453292519943296 * u.user_last_login_lat)* cos(0.17453292519943296 * ?)* pow(sin(0.008726646259971648 * (u.user_last_login_lng - ?)), 2)))<?  LIMIT 0,?";
				List<Object> query3 = userDao.query(sql, args3,
						NeighborUserEntity.class);
				query.addAll(query3);
			}

			for (Object object : query) {
				if (list.contains(object)) {
					continue;
				}
				NeighborUserEntity entity = (NeighborUserEntity) object;
				String tempShout = entity.getTempShout();
				if (tempShout != null && !"".equals(tempShout)) {
					entity.setTempShout(URLDecoder.decode(
							entity.getTempShout(), "UTF-8"));
				}
				list.add(entity);
			}
		} catch (InstantiationException e) {
			log.error("获取附近的导游和游客出现异常", e);
			throw new RuntimeException("获取附近的导游和游客出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取附近的导游和游客出现异常", e);
			throw new RuntimeException("获取附近的导游和游客出现异常", e);
		} catch (Exception e) {
			log.error("获取附近的导游和游客出现异常", e);
			throw new RuntimeException("获取附近的导游和游客出现异常", e);
		}
		return list;
	}

	@Override
	public List<Object> searchNeighborUser(TUser user) {
		List<Object> args = new ArrayList<Object>();
		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLng());
		args.add(user.getDistance());
		String userSex = "";
		switch (user.getUserSex()) {
		case 1:
			userSex = "2";
			break;
		case 2:
			userSex = "1";
			break;
		}
		args.add(userSex);

		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLng());
		args.add(user.getDistance());
		args.add(user.getUserWork());

		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLat());
		args.add(user.getUserLastLoginLng());
		args.add(user.getDistance());
		args.add(user.getSize());

		String sql = "SELECT *FROM (SELECT 3 mix,u.user_id,u.user_name,u.user_avatar,u.user_last_login_lat,u.user_last_login_lng,u.uuid,u.user_status,t.temp_shout FROM t_user u LEFT JOIN T_TEMP_USER t ON(t.temp_uuid=u.uuid) WHERE u.user_is_login=1 AND 2 * 6378.137 * asin(sqrt(pow(sin(0.008726646259971648 * (u.user_last_login_lat - ?)), 2)+ cos(0.17453292519943296 * u.user_last_login_lat)* cos(0.17453292519943296 * ?)* pow(sin(0.008726646259971648 * (u.user_last_login_lng - ?)), 2)))<? AND u.user_sex=? UNION SELECT 2 mix,u.user_id,u.user_name,u.user_avatar,u.user_last_login_lat,u.user_last_login_lng,u.uuid,u.user_status,t.temp_shout FROM t_user u LEFT JOIN T_TEMP_USER t ON(t.temp_uuid=u.uuid) WHERE u.user_is_login=1 AND 2 * 6378.137 * asin(sqrt(pow(sin(0.008726646259971648 * (u.user_last_login_lat - ?)),2)+ cos(0.17453292519943296 * u.user_last_login_lat)* cos(0.17453292519943296 * ?)* pow(sin(0.008726646259971648 * (u.user_last_login_lng - ?)), 2)))<? AND u.user_work=? UNION SELECT 1 mix,u.user_id,u.user_name,u.user_avatar,u.user_last_login_lat,u.user_last_login_lng,u.uuid,u.user_status,t.temp_shout FROM t_user u LEFT JOIN T_TEMP_USER t ON(t.temp_uuid=u.uuid) WHERE u.user_is_login=1 AND 2 * 6378.137 * asin(sqrt(pow(sin(0.008726646259971648 * (u.user_last_login_lat - ?)), 2)+ cos(0.17453292519943296 * u.user_last_login_lat)* cos(0.17453292519943296 * ?)* pow(sin(0.008726646259971648 * (u.user_last_login_lng - ?)), 2)))<?) search ORDER BY search.mix DESC LIMIT 0,?";
		List<Object> list = new ArrayList<Object>();
		try {
			List<Object> query = userDao.query(sql, args,
					NeighborUserEntity.class);
			for (Object object : query) {
				NeighborUserEntity entity = (NeighborUserEntity) object;
				String tempShout = entity.getTempShout();
				if (tempShout != null && !"".equals(tempShout)) {
					entity.setTempShout(URLDecoder.decode(
							entity.getTempShout(), "UTF-8"));
				}
				list.add(entity);
			}
		} catch (Exception e) {
			log.error("获取附近的导游和游客出现异常", e);
			throw new RuntimeException("获取附近的导游和游客出现异常", e);
		}
		return list;
	}

	/**
	 * @Title: clearUser
	 * @Description:服务器关闭的时候，将所有的用户状态都设置为未登录
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void clearUser() {
		try {
			userDao.update("UPDATE T_USER SET user_is_login=-1 WHERE 1=1", null);
		} catch (SQLException e) {
			log.error("更新服务器登录状态出现异常", e);
			throw new RuntimeException("更新服务器登录状态出现异常", e);
		}
	}

	/**
	 * @Title: saveTempUser
	 * @Description: 保存临客登录的记录
	 * @param @param tempUser
	 * @param @return
	 * @return TTempUser 返回类型
	 * @throws
	 */
	@Override
	public TTempUser saveTempUser(TTempUser tempUser) {
		tempUser.setTempCreateDate(new Date());
		SQLEntity sqlEntity = SQLUtil.insertSQL(tempUser);
		int tempId = 0;
		try {
			tempId = userDao.executeInsert(sqlEntity.getParameter(),
					sqlEntity.getList());
			tempUser.setTempId(tempId);
		} catch (Exception e) {
			log.error("保存临客登录信息出现异常", e);
			throw new RuntimeException("保存临客登录信息出现异常", e);
		}
		return tempUser;
	}

	/**
	 * @Title: getUserById
	 * @Description: 根据用户的id 来找到用户的额信息
	 * @param @param sessionUser
	 * @param @return
	 * @return TUser 返回类型
	 * @throws
	 */
	@Override
	public TUser getUserById(TUser sessionUser) {
		String sql = "SELECT *FROM T_USER WHERE user_id=?";
		List<Object> list = new ArrayList<Object>();
		list.add(sessionUser.getUserId());
		List<Object> query = null;
		try {
			query = userDao.query(sql, list, TUser.class);
		} catch (InstantiationException e) {
			log.error("根据用户id来获取用户信息出现异常", e);
			throw new RuntimeException("根据用户id来获取用户信息出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据用户id来获取用户信息出现异常", e);
			throw new RuntimeException("根据用户id来获取用户信息出现异常", e);
		} catch (SQLException e) {
			log.error("根据用户id来获取用户信息出现异常", e);
			throw new RuntimeException("根据用户id来获取用户信息出现异常", e);
		}
		return query != null && query.size() > 0 ? (TUser) query.get(0) : null;
	}

	/**
	 * @Title: getTempUserByUUID
	 * @Description: 根据uuid 来查询 临客是否登录过
	 * @param @param tempUser
	 * @param @return
	 * @return TTempUser 返回类型
	 * @throws
	 */
	@Override
	public TTempUser getTempUserByUUID(TTempUser tempUser) {
		String sql = "SELECT temp_id,temp_uuid,temp_shout,temp_create_date FROM T_TEMP_USER WHERE temp_uuid=?";
		List<Object> args = new ArrayList<Object>();
		args.add(tempUser.getTempUuid());
		List<Object> query;
		try {
			query = userDao.query(sql, args, TTempUser.class);
		} catch (InstantiationException e) {
			log.error("根据UUID来获取临时表数据出现异常", e);
			throw new RuntimeException("根据UUID来获取临时表数据出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据UUID来获取临时表数据出现异常", e);
			throw new RuntimeException("更新临时表出现异常", e);
		} catch (SQLException e) {
			log.error("根据UUID来获取临时表数据出现异常", e);
			throw new RuntimeException("根据UUID来获取临时表数据出现异常", e);
		}
		return query != null && query.size() > 0 ? (TTempUser) query.get(0)
				: null;
	}

	/**
	 * @Title: updateTempUser
	 * @Description: 更新大叫一声
	 * @param @param tempUser
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void updateTempUser(TTempUser tempUser) {
		String sql = "UPDATE T_TEMP_USER SET temp_shout=? WHERE temp_uuid=?";
		List<Object> args = new ArrayList<Object>();
		args.add(tempUser.getTempShout());
		args.add(tempUser.getTempUuid());
		try {
			userDao.update(sql, args);
		} catch (SQLException e) {
			log.error("更新临时表出现异常", e);
			throw new RuntimeException("更新临时表出现异常", e);
		}
	}

	/**
	 * @Title: saveWithDraw
	 * @Description: 保存提现记录
	 * @param @param withdraw
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	public int saveWithDraw(TWithdrawCash withdraw) {
		try {
			return userDao.saveWithDraw(withdraw);
		} catch (Exception e) {
			log.error("保存提现记录出现异常", e);
			throw new RuntimeException("保存提现记录出现异常", e);
		}
	}

	/**
	 * @Title: saveUserBank
	 * @Description: 保存绑定的银行卡号
	 * @param @param userBank
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	public int saveUserBank(TUserBank userBank) {
		try {
			return userDao.saveUserBondBank(userBank);
		} catch (Exception e) {
			log.error("绑定银行卡号出现异常", e);
			throw new RuntimeException("绑定银行卡号出现异常", e);
		}
	}

	/**
	 * @Title: getUserBankListByUserId
	 * @Description: 找到该用户绑定的所有的银行卡
	 * @param @param userId
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getUserBankListByUserId(String userId) {
		String sql = "SELECT *FROM T_USER_BANK WHERE user_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		List<Object> list = null;
		try {
			list = userDao.query(sql, args, TUserBank.class);
		} catch (InstantiationException e) {
			log.error("获取所有绑定的银行卡号出现异常", e);
			throw new RuntimeException("获取所有绑定的银行卡号出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取所有绑定的银行卡号出现异常", e);
			throw new RuntimeException("获取所有绑定的银行卡号出现异常", e);
		} catch (SQLException e) {
			log.error("获取所有绑定的银行卡号出现异常", e);
			throw new RuntimeException("获取所有绑定的银行卡号出现异常", e);
		}
		return list;
	}

	/**
	 * @Title: getWithdrawCashList
	 * @Description: 根据用户id，来获取提现列表
	 * @param @param withdraw
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getWithdrawCashList(TWithdrawCash withdraw) {
		String append = "";
		List<Object> args = new ArrayList<Object>();
		args.add(withdraw.getUserId());
		List<Object> list = null;
		switch (withdraw.getWithdrawStatus()) {
		case 0:
			break;
		default:
			append = " AND withdraw_status=?";
			args.add(withdraw.getWithdrawStatus());
			break;
		}
		String sql = "SELECT *FROM T_WITHDRAW_CASH WHERE user_id=?  AND 1=1 "
				+ append;
		try {
			list = userDao.query(sql, args, TWithdrawCash.class);
		} catch (InstantiationException e) {
			log.error("根据用户id，来获取提现列表出现异常", e);
			throw new RuntimeException("根据用户id，来获取提现列表出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据用户id，来获取提现列表出现异常", e);
			throw new RuntimeException("根据用户id，来获取提现列表出现异常", e);
		} catch (SQLException e) {
			log.error("根据用户id，来获取提现列表出现异常", e);
			throw new RuntimeException("根据用户id，来获取提现列表出现异常", e);
		}
		return list;
	}

	/**
	 * @Title: getUserByRoadlineId
	 * @Description: 根据路线id 找到用户信息
	 * @param @param userId
	 * @param @param roadlineId
	 * @param @return
	 * @return RoadlineDetailEntity 返回类型
	 * @throws
	 */
	@Override
	public RoadlineDetailEntity getUserByRoadlineId(String userId,
			int roadlineId) {
		String sql = "SELECT u.user_id,u.user_name,u.user_avatar tour_avatar,u.user_age,u.user_post,u.user_constellation,u.user_sex,u.user_city,u.user_description,u.user_area,u.user_work,cr.collect_id FROM T_ROADLINE r  LEFT JOIN T_USER u ON(r.roadline_user_id=u.user_id) LEFT JOIN T_COLLECT_ROADLINE cr ON(cr.collect_roadline_id=r.roadline_id AND cr.collect_user_id=?) WHERE r.roadline_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		args.add(roadlineId);
		List<Object> list = null;
		try {
			list = userDao.query(sql, args, RoadlineDetailEntity.class);
		} catch (InstantiationException e) {
			log.error("获取用户详情出现异常", e);
			throw new RuntimeException("获取用户详情出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取用户详情出现异常", e);
			throw new RuntimeException("获取用户详情出现异常", e);
		} catch (SQLException e) {
			log.error("获取用户详情出现异常", e);
			throw new RuntimeException("获取用户详情出现异常", e);
		}
		return list != null && list.size() > 0 ? (RoadlineDetailEntity) list
				.get(0) : null;
	}

	/**
	 * 获取到登录用户的信息
	 */
	@Override
	public LoginEntity getLoginUserMessage(String userId) {
		String sql = "SELECT u.user_id,u.user_name,u.user_age,u.user_password,u.user_post,u.user_constellation,date_format(u.user_birthday,'%Y-%m-%d') user_birthday,u.user_sex,u.user_car,u.user_car_validate,u.user_city,u.user_area,u.user_work,u.user_school,u.user_language,u.user_realname,u.user_avatar,u.user_privacy_model,u.user_tel,u.user_tel_validate,u.user_certificate,u.user_certificate_validate,u.user_identity,u.user_id_validate,u.user_tour_card,u.user_tour_validate,u.user_email,u.user_email_validate,u.user_credit_score,u.user_description,u.user_pay_password,u.user_life_photo,u.user_easemob_password,(SELECT GROUP_CONCAT(r.roadline_id) FROM T_ROADLINE r WHERE r.roadline_user_id=u.user_id) roadlineId,(SELECT COUNT(c.comment_id) FROM T_COMMENT c  WHERE c.commented_user_id=u.user_id) commentsCount FROM T_USER u WHERE u.user_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		List<Object> list = null;
		try {
			list = userDao.query(sql, args, LoginEntity.class);
			return (list != null && list.size() > 0 ? (LoginEntity) list.get(0)
					: null);
		} catch (InstantiationException e) {
			log.error("获取登录用户详情出现异常", e);
			throw new RuntimeException("获取登录用户详情出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取登录用户详情出现异常", e);
			throw new RuntimeException("获取登录用户详情出现异常", e);
		} catch (SQLException e) {
			log.error("获取登录用户详情出现异常", e);
			throw new RuntimeException("获取登录用户详情出现异常", e);
		}
	}

	/**
	 * @Title: getEasemobUser
	 * @Description: 根据用户id 来获取到环信的好友信息
	 * @param @param string
	 * @param @return
	 * @return EasemobEntity 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getEasemobUser(List<Object> userIds) {
		StringBuilder question = new StringBuilder();
		for (int i = 0; i < userIds.size(); i++) {
			question.append("?,");
		}
		question.deleteCharAt(question.lastIndexOf(","));
		String sql = "SELECT user_id,user_name,user_avatar,user_city FROM T_USER WHERE user_id IN("
				+ question + ")";
		try {
			return userDao.query(sql, userIds, EasemobEntity.class);
		} catch (InstantiationException e) {
			log.error("获取环信用户详情出现异常", e);
			throw new RuntimeException("获取环信用户详情出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取环信用户详情出现异常", e);
			throw new RuntimeException("获取环信用户详情出现异常", e);
		} catch (SQLException e) {
			log.error("获取环信用户详情出现异常", e);
			throw new RuntimeException("获取环信用户详情出现异常", e);
		}
	}

	/**
	 * @Title: modifyUserPassword
	 * @Description: 修改密码
	 * @param @param userTel
	 * @param @param userPassword
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void modifyUserPassword(String userTel, String userPassword) {
		String sql = "UPDATE T_USER u SET u.user_password=? WHERE u.user_tel=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userPassword);
		args.add(userTel);
		try {
			userDao.update(sql, args);
		} catch (SQLException e) {
			log.error("修改密码出现异常", e);
			throw new RuntimeException("修改密码出现异常", e);
		}
	}
    
	/** 判断该用户是否还有权限再次发布路线  */
	@Override
	public boolean checkRoadlinePublishPermission(String userId) {
		/** 有路线正在审核的 不能继续发送 
		 *  路线审核完成 但是该路线没有成交量【order_status='order_success'】的 不能够发送
		 **/
		String sql="SELECT (SELECT COUNT(roadline_id) FROM t_roadline WHERE roadline_user_id=? AND roadline_status=?) reviewRoadlineCount,(SELECT COUNT(roadline_id) FROM t_roadline WHERE roadline_user_id=? AND roadline_status=?) successRoadlineCount,(SELECT COUNT(order_id) FROM t_order,(SELECT roadline_id FROM t_roadline WHERE roadline_user_id=? AND roadline_status=? ORDER BY roadline_create_date DESC LIMIT 1) roadline WHERE order_roadline_id=roadline.roadline_id  AND order_status IN('order_success')) latestRoadlineOrderCount FROM DUAL";
		List<Object> args=new ArrayList<Object>();
		args.add(userId);
		args.add("review");
		args.add(userId);
		args.add("success");
		args.add(userId);
		args.add("success");
		boolean isPermit=false;
		int reviewRoadlineCount=0;
		int successRoadlineCount=0;
		int latestRoadlineOrderCount=0;
		try {
			List<Object[]> query = userDao.query(sql, args);
			if(query!=null&&query.size()>0){
				Object[] obj = query.get(0);
				if(obj[0]!=null){
				  reviewRoadlineCount=Integer.parseInt(obj[0].toString());
				}
				if(obj[1]!=null){
					successRoadlineCount=Integer.parseInt(obj[1].toString());
				}
				if(obj[2]!=null){
				    latestRoadlineOrderCount=Integer.parseInt(obj[2].toString());
				}
				//发布的路线都没有审核过[或者未发布过路线的]--可以继续发布路线
				if(reviewRoadlineCount==0&&successRoadlineCount==0){
					isPermit=true;
				}
				//发布路线审核通过 而且 最近发布的路线有订单成交的时候 --可以继续发布路线
				if(reviewRoadlineCount==0&&latestRoadlineOrderCount>0){
					isPermit=true;
				}
			}
		} catch (SQLException e) {
			log.error("用户发布路线异常", e);
			throw new RuntimeException("用户发布路线异常", e);
		}
		return isPermit;
	}
}
