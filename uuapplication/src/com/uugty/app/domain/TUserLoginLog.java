package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TUserLoginLog
 * @Description: 用户登录的日志记录
 * @author ganliang
 * @date 2015年6月15日 下午2:00:23
 */
public class TUserLoginLog implements Serializable {

	private static final long serialVersionUID = 4791505443528527354L;

	private int loginLogId;// 主键
	private String loginUserId;// 登陆者的用户id

	private Date loginDate;// 登录日期
	private String loginArea;// 登录地点
	private String loginType;// 登录方式
	private String loginNetwork;// 登录网络状况
	private String loginMobilType;// 登录的手机型号

	private long loginUserTime;// 登录的使用时长

	public int getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(int loginLogId) {
		this.loginLogId = loginLogId;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginArea() {
		return loginArea;
	}

	public void setLoginArea(String loginArea) {
		this.loginArea = loginArea;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLoginNetwork() {
		return loginNetwork;
	}

	public void setLoginNetwork(String loginNetwork) {
		this.loginNetwork = loginNetwork;
	}

	public String getLoginMobilType() {
		return loginMobilType;
	}

	public void setLoginMobilType(String loginMobilType) {
		this.loginMobilType = loginMobilType;
	}

	public long getLoginUserTime() {
		return loginUserTime;
	}

	public void setLoginUserTime(long loginUserTime) {
		this.loginUserTime = loginUserTime;
	}

}
