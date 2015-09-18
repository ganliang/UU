package com.uugty.app.entity;

/**
 * @ClassName: LoginEntity
 * @Description: 用户登录的时候返回的实体对象
 * @author ganliang
 * @date 2015年6月24日 下午4:07:58
 */
public class LoginEntity {
	private String userId;
	private String userName;
	private int userAge;
	private String userPassword;

	private String userPost;
	private String userConstellation;
	private String userBirthday;
	private int userSex;

	private String userCar;// 用户的汽车
	private int userCarValidate;// 用户的车是否验证

	private String userCity;// 用户的城市
	private String userArea;
	private String userWork;
	private String userSchool;
	private String userLanguage;

	private String userRealname;

	private String userAvatar;
	private String userPrivacyModel;

	private String userTel;
	private int userTelValidate;

	private String userCertificate;
	private int userCertificateValidate;

	private String userIdentity;
	private int userIdValidate;

	private String userTourCard;
	private int userTourValidate;

	private String userEmail;// 用户邮箱
	private int userEmailValidate;// 用户是否邮箱验证

	private int userCreditScore;

	private String userDescription;

	private String userPayPassword;// 路线id

	private String userLifePhoto;// 用户的生活照

	private String roadlineId;// 路线id

	private int commentsCount;

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

	public int getUserCarValidate() {
		return userCarValidate;
	}

	public void setUserCarValidate(int userCarValidate) {
		this.userCarValidate = userCarValidate;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
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

	public String getUserArea() {
		return userArea;
	}

	public String getUserCar() {
		return userCar;
	}

	public void setUserCar(String userCar) {
		this.userCar = userCar;
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

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getUserLifePhoto() {
		if (userLifePhoto != null && !"".equals(userLifePhoto)) {
			return userLifePhoto.trim();
		}
		return userLifePhoto;
	}

	public void setUserLifePhoto(String userLifePhoto) {
		this.userLifePhoto = userLifePhoto;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getUserEmailValidate() {
		return userEmailValidate;
	}

	public void setUserEmailValidate(int userEmailValidate) {
		this.userEmailValidate = userEmailValidate;
	}

	public String getRoadlineId() {
		return roadlineId;
	}

	public void setRoadlineId(String roadlineId) {
		this.roadlineId = roadlineId;
	}

	public String getUserPayPassword() {
		return userPayPassword;
	}

	public void setUserPayPassword(String userPayPassword) {
		this.userPayPassword = userPayPassword;
	}

	/*
	 * 环信的密码
	 */
	private String userEasemobPassword;// 环信密码

	public String getUserEasemobPassword() {
		return userEasemobPassword;
	}

	public void setUserEasemobPassword(String userEasemobPassword) {
		this.userEasemobPassword = userEasemobPassword;
	}
}
