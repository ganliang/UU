package com.uugty.app.entity;

import java.util.Date;

/**
 * @ClassName: UserEntity
 * @Description: 获取用户详情的用户实体
 * @author ganliang
 * @date 2015年6月24日 上午9:11:16
 */
public class UserDetailEntity {

	private String userId;// 用户主键id【32长度的uuid】
	private String userName;// 用户姓名
	private int userAge;// 用户年龄
	private String userPassword;// 用户密码

	private String userPost;// 用户的年代
	private String userConstellation;// 用户的星座
	private String userBirthday;// 用户的生日
	private int userSex;// 用户的性别 1 男 ,2 女

	private String userCar;// 用户的车
	private String userCarValidate;// 用户的车是否验证 0 未认证 1认证
	private Date userCarValidateDate;// 用户的车验证日期

	private String userCity;// 用户的城市
	private String userArea;// 用户的地点
	private String userWork;// 用户的工作
	private String userSchool;// 用户的学校
	private String userLanguage;// 用户的语言

	private String userRealname;// 用户的真实姓名
	private String userAvatar;// 用户的头像
	private String userPrivacyModel;// 用户隐身的模式(对所有人可见,对微信好友隐身,对qq好友隐身)

	private String userTel;// 用户的手机号码
	private int userTelValidate;// 手机号码是否验证 0 未验证,1 已验证

	private String userCertificate;// 用户的学位证
	private int userCertificateValidate;// 用户的学位证是否认证 0 未认证,1已认证

	private String userIdentity;// 用户的身份 
	private int userIdValidate;// 用户是否身份证验证 0 未认证,1已认证

	private String userTourCard;// 用户的旅游证
	private int userTourValidate;// 用户的旅游证是否认证0未认证,1已认证

	private int userCreditScore;// 用户信誉积分

	private String userLifePhoto;// 用户的生活照

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

	public String getUserPost() {
		return userPost;
	}

	public void setUserPost(String userPost) {
		this.userPost = userPost;
	}

	public String getUserConstellation() {
		return userConstellation;
	}

	public void setUserConstellation(String userConstellation) {
		this.userConstellation = userConstellation;
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

	public String getUserCarValidate() {
		return userCarValidate;
	}

	public void setUserCarValidate(String userCarValidate) {
		this.userCarValidate = userCarValidate;
	}

	public Date getUserCarValidateDate() {
		return userCarValidateDate;
	}

	public void setUserCarValidateDate(Date userCarValidateDate) {
		this.userCarValidateDate = userCarValidateDate;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getUserWork() {
		return userWork;
	}

	public void setUserWork(String userWork) {
		this.userWork = userWork;
	}

	public String getUserSchool() {
		return userSchool;
	}

	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}

	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserPrivacyModel() {
		return userPrivacyModel;
	}

	public void setUserPrivacyModel(String userPrivacyModel) {
		this.userPrivacyModel = userPrivacyModel;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
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

	public int getUserTourValidate() {
		return userTourValidate;
	}

	public void setUserTourValidate(int userTourValidate) {
		this.userTourValidate = userTourValidate;
	}

	public int getUserCreditScore() {
		return userCreditScore;
	}

	public void setUserCreditScore(int userCreditScore) {
		this.userCreditScore = userCreditScore;
	}

	public String getUserLifePhoto() {
		return userLifePhoto;
	}

	public void setUserLifePhoto(String userLifePhoto) {
		this.userLifePhoto = userLifePhoto;
	}

}
