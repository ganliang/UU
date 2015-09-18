package com.uugty.app.entity;

public class NeighborUserEntity {

	private String userId;// 用户主键id【32长度的uuid】
	private String userName;// 用户姓名
	private String userAvatar;// 用户的头像

	private double userLastLoginLat;// 用户最后一次登录的维度
	private double userLastLoginLng;// 用户最后一次登录的经度
	private String userStatus;// 用户身份 , 1.游客 ; 2.导游
	private String tempShout;
	private String uuid;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public double getUserLastLoginLat() {
		return userLastLoginLat;
	}

	public void setUserLastLoginLat(double userLastLoginLat) {
		this.userLastLoginLat = userLastLoginLat;
	}

	public double getUserLastLoginLng() {
		return userLastLoginLng;
	}

	public void setUserLastLoginLng(double userLastLoginLng) {
		this.userLastLoginLng = userLastLoginLng;
	}

	public String getTempShout() {
		return tempShout;
	}

	public void setTempShout(String tempShout) {
		this.tempShout = tempShout;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}
