package com.uugty.app.service;

import java.util.List;

import com.uugty.app.domain.TTempUser;
import com.uugty.app.domain.TUser;
import com.uugty.app.domain.TUserBank;
import com.uugty.app.domain.TWithdrawCash;
import com.uugty.app.entity.LoginEntity;
import com.uugty.app.entity.RoadlineDetailEntity;
import com.uugty.app.entity.UserDetailEntity;

/**
 * 
 * @ClassName: IUserService
 * @Description: 用户业务接口类
 * @author ganliang
 * @date 2015年6月6日 下午3:56:19
 */
public interface IUserService {

	public TUser weChartLogin(TUser user);

	public TUser saveUser(TUser user);

	public TUser updateUser(TUser user);

	public TUser getUserByTelphone(TUser user);

	public UserDetailEntity getUserDetailMessage(
			UserDetailEntity userDetailEntity);

	public List<Object> getNeighborUser(TUser user);

	public void clearUser();

	public TTempUser saveTempUser(TTempUser tempUser);

	public TUser getUserById(TUser sessionUser);

	public TTempUser getTempUserByUUID(TTempUser tempUser);

	public void updateTempUser(TTempUser tempUser);

	public int saveWithDraw(TWithdrawCash withdraw);

	public int saveUserBank(TUserBank userBank);

	public List<Object> getUserBankListByUserId(String userId);

	public List<Object> getWithdrawCashList(TWithdrawCash withdraw);

	public RoadlineDetailEntity getUserByRoadlineId(String userId,
			int roadlineId);

	public LoginEntity getLoginUserMessage(String userId);

	public List<Object> getEasemobUser(List<Object> userIds);

	public void modifyUserPassword(String userTel, String userPassword);

	public List<Object> searchNeighborUser(TUser user);

	public boolean checkRoadlinePublishPermission(String userId);
}
