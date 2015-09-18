package com.uugty.validate.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TUser
 * @Description: 用户实体对象
 * @author ganliang
 * @date 2015年6月13日 下午2:21:34
 */
public class User implements Serializable {

	/**
	 * @Fields serialVersionUID : 实体类的持久化
	 */
	private static final long serialVersionUID = 4636785020842391167L;

	/**
	 * 持久化字段
	 */
	private String userId;// 用户主键id【32长度的uuid】
	private String userName;// 用户姓名
	private int userAge;// 用户年龄
	private String userPassword;// 用户密码

	private String userBirthday;// 用户的生日
	private int userSex;// 用户的性别 1 男 ,2 女

	private String userCar;// 用户的车
	private int userCarValidate;// 用户的车是否验证
	private Date userCarValidateDate;// 用户的车验证日期

	private String userAvatar;// 用户的头像
	private int userAvatarValidate;// 用户的头像是否审核 0 未审核 1 审核

	private String userTel;// 用户的手机号码
	private Date userTelValidateDate;// 手机号码验证日期
	private int userTelValidate;// 手机号码是否验证 0 未验证,1 已验证

	private String userCertificate;// 用户的学位证
	private Date userCertificateValidateDate;// 用户的我学位证审核日期
	private int userCertificateValidate;// 用户的学位证是否认证 0未认证,1正在认证 ,2已认证

	private String userIdentityCard;// 用户的身份证号码
	private String userIdentity;// 用户的身份
	private Date userIdValidateDate;// 用户验证身份证的日期
	private int userIdValidate;// 用户是否身份证验证

	private String userTourCard;// 用户的旅游证
	private Date userTourValidateDate;// 用户旅游证审核日期
	private int userTourValidate;// 用户的旅游证是否认证0未认证,1正在认证 ,2已认证

	private Date userLastLoginDate;// 用户最后登录的时间
	/**
	 * 微信平台登录保存的字段
	 */
	private String wxNickname;// 微信用户的昵称
	private String wxSex;// 微信用户的额性别
	private String wxHeadimgurl;// 微信用户的头像

	private String userDescription;// 用户的标签，个性说明

	private int userLoginType;// 登录的方式 1 账号登录,2 微信登录

	private String userLifePhoto;// 用户的生活照

	private Date userRegisterDate;// 用户注册日期

	private String userRealname;

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

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

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}

	public int getUserSex() {
		return userSex;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public String getUserCar() {
		return userCar;
	}

	public void setUserCar(String userCar) {
		this.userCar = userCar;
	}

	public int getUserCarValidate() {
		return userCarValidate;
	}

	public void setUserCarValidate(int userCarValidate) {
		this.userCarValidate = userCarValidate;
	}

	public String getUserIdentityCard() {
		return userIdentityCard;
	}

	public void setUserIdentityCard(String userIdentityCard) {
		this.userIdentityCard = userIdentityCard;
	}

	public Date getUserCarValidateDate() {
		return userCarValidateDate;
	}

	public void setUserCarValidateDate(Date userCarValidateDate) {
		this.userCarValidateDate = userCarValidateDate;
	}

	public int getUserAvatarValidate() {
		return userAvatarValidate;
	}

	public void setUserAvatarValidate(int userAvatarValidate) {
		this.userAvatarValidate = userAvatarValidate;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public Date getUserTelValidateDate() {
		return userTelValidateDate;
	}

	public void setUserTelValidateDate(Date userTelValidateDate) {
		this.userTelValidateDate = userTelValidateDate;
	}

	public int getUserTelValidate() {
		return userTelValidate;
	}

	public void setUserTelValidate(int userTelValidate) {
		this.userTelValidate = userTelValidate;
	}

	public String getUserCertificate() {
		return userCertificate;
	}

	public void setUserCertificate(String userCertificate) {
		this.userCertificate = userCertificate;
	}

	public Date getUserCertificateValidateDate() {
		return userCertificateValidateDate;
	}

	public void setUserCertificateValidateDate(Date userCertificateValidateDate) {
		this.userCertificateValidateDate = userCertificateValidateDate;
	}

	public int getUserCertificateValidate() {
		return userCertificateValidate;
	}

	public void setUserCertificateValidate(int userCertificateValidate) {
		this.userCertificateValidate = userCertificateValidate;
	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public Date getUserIdValidateDate() {
		return userIdValidateDate;
	}

	public void setUserIdValidateDate(Date userIdValidateDate) {
		this.userIdValidateDate = userIdValidateDate;
	}

	public int getUserIdValidate() {
		return userIdValidate;
	}

	public void setUserIdValidate(int userIdValidate) {
		this.userIdValidate = userIdValidate;
	}

	public String getUserTourCard() {
		return userTourCard;
	}

	public void setUserTourCard(String userTourCard) {
		this.userTourCard = userTourCard;
	}

	public Date getUserTourValidateDate() {
		return userTourValidateDate;
	}

	public void setUserTourValidateDate(Date userTourValidateDate) {
		this.userTourValidateDate = userTourValidateDate;
	}

	public int getUserTourValidate() {
		return userTourValidate;
	}

	public void setUserTourValidate(int userTourValidate) {
		this.userTourValidate = userTourValidate;
	}

	public Date getUserLastLoginDate() {
		return userLastLoginDate;
	}

	public void setUserLastLoginDate(Date userLastLoginDate) {
		this.userLastLoginDate = userLastLoginDate;
	}

	public String getWxNickname() {
		return wxNickname;
	}

	public void setWxNickname(String wxNickname) {
		this.wxNickname = wxNickname;
	}

	public String getWxSex() {
		return wxSex;
	}

	public void setWxSex(String wxSex) {
		this.wxSex = wxSex;
	}

	public String getWxHeadimgurl() {
		return wxHeadimgurl;
	}

	public void setWxHeadimgurl(String wxHeadimgurl) {
		this.wxHeadimgurl = wxHeadimgurl;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public int getUserLoginType() {
		return userLoginType;
	}

	public void setUserLoginType(int userLoginType) {
		this.userLoginType = userLoginType;
	}

	public String getUserLifePhoto() {
		return userLifePhoto;
	}

	public void setUserLifePhoto(String userLifePhoto) {
		this.userLifePhoto = userLifePhoto;
	}

	public Date getUserRegisterDate() {
		return userRegisterDate;
	}

	public void setUserRegisterDate(Date userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}

	/**
	 * 非持久化字段
	 */
	private int currentPage;//
	private String type;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
