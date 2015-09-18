package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;

import com.uugty.app.service.IAccessLogService;
import com.uugty.app.service.ILogService;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.AccessLogServiceImpl;
import com.uugty.app.service.impl.LogServiceImpl;
import com.uugty.app.service.impl.UserServiceImpl;

/**
 * @ClassName: TUser
 * @Description: 用户实体对象
 * @author ganliang
 * @date 2015年6月13日 下午2:21:34
 */
public class TUser implements Serializable, HttpSessionBindingListener {

	/**
	 * @Fields serialVersionUID : 实体类的持久化
	 */
	private static final long serialVersionUID = 4636785020842391167L;

	private static final Logger log = Logger.getLogger(TUser.class);
	/**
	 * 持久化字段
	 */
	private String userId;// 用户主键id【32长度的uuid】
	private String userName;// 用户姓名
	private int userAge;// 用户年龄
	private String userPassword;// 用户密码

	private String userPost;// 用户的年代
	private String userConstellation;// 用户的星座
	private Date userBirthday;// 用户的生日
	private int userSex;// 用户的性别 1 男 ,2 女

	private String userCar;// 用户的车
	private int userCarValidate;// 用户的车是否验证
	private Date userCarValidateDate;// 用户的车验证日期

	private String userCity;// 用户的城市
	private String userArea;// 用户的地点
	private String userWork;// 用户的工作
	private String userSchool;// 用户的学校
	private String userLanguage;// 用户的语言

	private String userRealname;// 用户的真实姓名

	private String userAvatar;// 用户的头像
	private String userAvatarValidate;// 用户的头像
	private String userAvatarValidateDate;// 用户的头像

	private Date userRegisterDate;// 用户注册日期
	private String userPrivacyModel;// 用户隐身的模式(对所有人可见,对微信好友隐身,对qq好友隐身)

	public static final int VALIDATE_NO = 0;// 未验证
	public static final int VALIDATE_RUNING = 1;// 正在验证
	public static final int VALIDATE_FINSH = 2;// 已验证
	public static final int VALIDATE_FAILURE = 3;// 审核失败

	private String userTel;// 用户的手机号码
	private Date userTelValidateDate;// 手机号码验证日期
	private int userTelValidate;// 手机号码是否验证

	private String userCertificate;// 用户的学位证
	private Date userCertificateValidateDate;// 用户的学位证审核日期
	private int userCertificateValidate;// 用户的学位证是否认证

	private String userIdentityCard;// 用户的身份证号码
	private String userIdentity;// 用户的身份
	private Date userIdValidateDate;// 用户验证身份证的日期
	private int userIdValidate;// 用户是否身份证验证

	private String userTourCard;// 用户的旅游证
	private Date userTourValidateDate;// 用户旅游证审核日期
	private int userTourValidate;// 用户的旅游证是否认证

	private String userEmail;// 用户邮箱
	private int userEmailValidate;// 用户是否邮箱验证
	private Date userEmailValidateDate;// 用户验证邮箱的日期

	private Date userLastLoginDate;// 用户最后登录的时间
	private double userLastLoginLat;// 用户最后一次登录的维度
	private double userLastLoginLng;// 用户最后一次登录的经度

	private int userCreditScore;// 用户信誉积分

	/**
	 * 微信平台登录保存的字段
	 */
	private String wxOpenid;// 普通用户的标识，对当前开发者帐号唯一
	private String wxNickname;// 微信用户的昵称
	private String wxSex;// 微信用户的额性别
	private String wxProvince;// 微信用户的省份
	private String wxCity;// 微信用户的城市
	private String wxCountry;// 微信用户的国家
	private String wxHeadimgurl;// 微信用户的头像
	private String wxPrivilege;// 微信用户的微信权限
	private String wxUnionid;// 微信用户的额唯一标识

	private String userDescription;// 用户的标签，个性说明
	/**
	 * 保留字段
	 */
	private int userAttentionCount;// 用户的关注数量
	private int userReplayCount;// 用户的评论数量
	private String userSina;// 用户的新浪账号
	private String userQq;// 用户qq

	/**
	 * add
	 */
	private float userPurse;// 用户钱包

	private int userLoginCount;// 用户登录的次数
	private int userIsLogin;// 判断用户是否在登录,防止两个手机同时登录 -1 未登录 1 已登录
	private int userLoginType;// 登录的方式 1 账号登录,2 微信登录

	private String uuid;// 手机的唯一标示

	private String userLifePhoto;// 用户的生活照

	private String userStatus;// 用户身份 , 1.游客 ; 2.导游

	private String userPayPassword;// 支付密码

	private String userPayCount;//
	private Date userPayTime;//

	public String getUserId() {
		return userId;
	}

	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getUserAvatarValidate() {
		return userAvatarValidate;
	}

	public void setUserAvatarValidate(String userAvatarValidate) {
		this.userAvatarValidate = userAvatarValidate;
	}

	public String getUserAvatarValidateDate() {
		return userAvatarValidateDate;
	}

	public void setUserAvatarValidateDate(String userAvatarValidateDate) {
		this.userAvatarValidateDate = userAvatarValidateDate;
	}

	public void setUserPost(String userPost) {
		this.userPost = userPost;
	}

	public String getUserPost() {
		return userPost;
	}

	public void setUserConstellation(String userConstellation) {
		this.userConstellation = userConstellation;
	}

	public String getUserConstellation() {
		return userConstellation;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public String getUserIdentityCard() {
		return userIdentityCard;
	}

	public void setUserIdentityCard(String userIdentityCard) {
		this.userIdentityCard = userIdentityCard;
	}

	public int getUserSex() {
		return userSex;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getUserArea() {
		return userArea;
	}

	public String getUserLifePhoto() {
		return userLifePhoto;
	}

	public void setUserLifePhoto(String userLifePhoto) {
		this.userLifePhoto = userLifePhoto;
	}

	public void setUserWork(String userWork) {
		this.userWork = userWork;
	}

	public String getUserWork() {
		return userWork;
	}

	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}

	public String getUserSchool() {
		return userSchool;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserRegisterDate(Date userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}

	public Date getUserRegisterDate() {
		return userRegisterDate;
	}

	public void setUserPrivacyModel(String userPrivacyModel) {
		this.userPrivacyModel = userPrivacyModel;
	}

	public String getUserPrivacyModel() {
		return userPrivacyModel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTelValidateDate(Date userTelValidateDate) {
		this.userTelValidateDate = userTelValidateDate;
	}

	public Date getUserTelValidateDate() {
		return userTelValidateDate;
	}

	public void setUserTelValidate(int userTelValidate) {
		this.userTelValidate = userTelValidate;
	}

	public int getUserTelValidate() {
		return userTelValidate;
	}

	public void setUserCertificate(String userCertificate) {
		this.userCertificate = userCertificate;
	}

	public String getUserCertificate() {
		return userCertificate;
	}

	public int getUserCarValidate() {
		return userCarValidate;
	}

	public void setUserCarValidate(int userCarValidate) {
		this.userCarValidate = userCarValidate;
	}

	public Date getUserCarValidateDate() {
		return userCarValidateDate;
	}

	public void setUserCarValidateDate(Date userCarValidateDate) {
		this.userCarValidateDate = userCarValidateDate;
	}

	public void setUserCertificateValidateDate(Date userCertificateValidateDate) {
		this.userCertificateValidateDate = userCertificateValidateDate;
	}

	public Date getUserCertificateValidateDate() {
		return userCertificateValidateDate;
	}

	public void setUserCertificateValidate(int userCertificateValidate) {
		this.userCertificateValidate = userCertificateValidate;
	}

	public int getUserCertificateValidate() {
		return userCertificateValidate;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdValidateDate(Date userIdValidateDate) {
		this.userIdValidateDate = userIdValidateDate;
	}

	public Date getUserIdValidateDate() {
		return userIdValidateDate;
	}

	public void setUserIdValidate(int userIdValidate) {
		this.userIdValidate = userIdValidate;
	}

	public int getUserIdValidate() {
		return userIdValidate;
	}

	public void setUserTourCard(String userTourCard) {
		this.userTourCard = userTourCard;
	}

	public String getUserTourCard() {
		return userTourCard;
	}

	public void setUserTourValidateDate(Date userTourValidateDate) {
		this.userTourValidateDate = userTourValidateDate;
	}

	public Date getUserTourValidateDate() {
		return userTourValidateDate;
	}

	public void setUserTourValidate(int userTourValidate) {
		this.userTourValidate = userTourValidate;
	}

	public int getUserTourValidate() {
		return userTourValidate;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmailValidate(int userEmailValidate) {
		this.userEmailValidate = userEmailValidate;
	}

	public int getUserEmailValidate() {
		return userEmailValidate;
	}

	public void setUserEmailValidateDate(Date userEmailValidateDate) {
		this.userEmailValidateDate = userEmailValidateDate;
	}

	public Date getUserEmailValidateDate() {
		return userEmailValidateDate;
	}

	public void setUserLastLoginDate(Date userLastLoginDate) {
		this.userLastLoginDate = userLastLoginDate;
	}

	public Date getUserLastLoginDate() {
		return userLastLoginDate;
	}

	public void setUserLastLoginLat(double userLastLoginLat) {
		this.userLastLoginLat = userLastLoginLat;
	}

	public double getUserLastLoginLat() {
		return userLastLoginLat;
	}

	public void setUserLastLoginLng(double userLastLoginLng) {
		this.userLastLoginLng = userLastLoginLng;
	}

	public double getUserLastLoginLng() {
		return userLastLoginLng;
	}

	public void setUserCreditScore(int userCreditScore) {
		this.userCreditScore = userCreditScore;
	}

	public int getUserCreditScore() {
		return userCreditScore;
	}

	public void setWxNickname(String wxNickname) {
		this.wxNickname = wxNickname;
	}

	public String getWxNickname() {
		return wxNickname;
	}

	public void setWxSex(String wxSex) {
		this.wxSex = wxSex;
	}

	public String getWxSex() {
		return wxSex;
	}

	public void setWxProvince(String wxProvince) {
		this.wxProvince = wxProvince;
	}

	public String getWxProvince() {
		return wxProvince;
	}

	public void setWxCity(String wxCity) {
		this.wxCity = wxCity;
	}

	public String getWxCity() {
		return wxCity;
	}

	public void setWxCountry(String wxCountry) {
		this.wxCountry = wxCountry;
	}

	public String getWxCountry() {
		return wxCountry;
	}

	public void setWxHeadimgurl(String wxHeadimgurl) {
		this.wxHeadimgurl = wxHeadimgurl;
	}

	public String getWxHeadimgurl() {
		return wxHeadimgurl;
	}

	public void setWxPrivilege(String wxPrivilege) {
		this.wxPrivilege = wxPrivilege;
	}

	public String getWxPrivilege() {
		return wxPrivilege;
	}

	public void setWxUnionid(String wxUnionid) {
		this.wxUnionid = wxUnionid;
	}

	public String getWxUnionid() {
		return wxUnionid;
	}

	public String getUserCar() {
		return userCar;
	}

	public void setUserCar(String userCar) {
		this.userCar = userCar;
	}

	public void setUserAttentionCount(int userAttentionCount) {
		this.userAttentionCount = userAttentionCount;
	}

	public int getUserAttentionCount() {
		return userAttentionCount;
	}

	public void setUserReplayCount(int userReplayCount) {
		this.userReplayCount = userReplayCount;
	}

	public int getUserReplayCount() {
		return userReplayCount;
	}

	public void setUserSina(String userSina) {
		this.userSina = userSina;
	}

	public String getUserSina() {
		return userSina;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public String getUserQq() {
		return userQq;
	}

	public float getUserPurse() {
		return userPurse;
	}

	public void setUserPurse(float userPurse) {
		this.userPurse = userPurse;
	}

	public int getUserLoginCount() {
		return userLoginCount;
	}

	public void setUserLoginCount(int userLoginCount) {
		this.userLoginCount = userLoginCount;
	}

	public int getUserIsLogin() {
		return userIsLogin;
	}

	public void setUserIsLogin(int userIsLogin) {
		this.userIsLogin = userIsLogin;
	}

	public int getUserLoginType() {
		return userLoginType;
	}

	public void setUserLoginType(int userLoginType) {
		this.userLoginType = userLoginType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserPayPassword() {
		return userPayPassword;
	}

	public void setUserPayPassword(String userPayPassword) {
		this.userPayPassword = userPayPassword;
	}

	public String getUserPayCount() {
		return userPayCount;
	}

	public void setUserPayCount(String userPayCount) {
		this.userPayCount = userPayCount;
	}

	public Date getUserPayTime() {
		return userPayTime;
	}

	public void setUserPayTime(Date userPayTime) {
		this.userPayTime = userPayTime;
	}

	/**
	 * 非持久化字段
	 */
	public static final String SESSION_USER = "CURRENT_USER";// 当前登录用用户
	public static final String VERIFYCODE = "VERYCODE";// 验证码
	public static final String EXPIR = "EXPIR";// 验证码的过期时间
	public static final int LENGTH = 30;// 令牌过期的需要几分钟,单位是分钟
	public String veryCode;// 注册的时候短信的验证码

	public String getVeryCode() {
		return veryCode;
	}

	public void setVeryCode(String veryCode) {
		this.veryCode = veryCode;
	}

	public static final String CHANGE_USER_NAME = "0";// 改变用户名称
	public static final String CHANGE_USER_LANGUAGE = "1";// 改变用户的语言
	public static final String CHANGE_USER_SEX = "2";// 改变用户的性别
	public static final String CHANGE_USER_CONSTELLATIO = "3";// 改变用户的星座
	public static final String CHANGE_USER_BIRTHDAY = "4";// 改变用户的生日
	public static final String CHANGE_USER_EMAIL = "5";// 改变用户的额邮箱
	public static final String VALIDATE_USER_TEL = "6";// 改变用户的手机号码
	public static final String CHANGE_USER_DESCRIPTION = "7";// 改变用户的个人说明
	public static final String CHANGE_USER_CITY = "8";// 改变用户的城市
	public static final String CHANGE_USER_SCHOOL = "9";// 改变用户的学校
	public static final String CHANGE_USER_WORK = "10";// 改变用户的工作
	public static final String CHANGE_USER_LIFE_PHOTO = "11";// 改变生活照
	public static final String SWITCH_USER_STATUS = "12";// 切换用户身份
	public static final String MODIFY_LNG_LAT = "13";// 修改当前用户的经纬度

	public String type;// 用户编辑类型 是名称 性别...

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String LoginArea;// 登录的地址
	private String loginNetwork;// 登录网络状况
	private String loginMobilType;// 登录的手机型号

	public String getLoginArea() {
		return LoginArea;
	}

	public void setLoginArea(String loginArea) {
		LoginArea = loginArea;
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

	private static IUserService userService = new UserServiceImpl();
	private static IAccessLogService accessLogService = new AccessLogServiceImpl();

	/**
	 * 当把 Tuser对象绑定到session中去的时候,触发此事件
	 */
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		String sessionID = event.getSession().getId();
		log.info("");
		// 微信用户登录
		if (wxNickname != null && !"".equals(wxNickname)) {
			log.info("微信用户{" + wxNickname + "}登录系统..sessionID{" + sessionID
					+ "}");
		}
		// 账号用户登录
		else {
			log.info("账号用户{" + userTel + "}登录系统..sessionID{" + sessionID + "}");
		}

		// 将登录信息保存到t_user_login_log表中去
		TUserLoginLog loginLog = new TUserLoginLog();
		loginLog.setLoginUserId(userId);
		loginLog.setLoginDate(new Date());
		loginLog.setLoginArea("");
		loginLog.setLoginType(userLoginType == 1 ? "用户登录" : "微信登录");
		loginLog.setLoginNetwork(loginNetwork);
		loginLog.setLoginMobilType(loginMobilType);
		ILogService logService = new LogServiceImpl();
		logService.save(loginLog);
	}

	/**
	 * 当把TUser从session中解绑的时候,触发此事件
	 */
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		String sessionID = event.getSession().getId();
		log.info("");
		// 微信用户退出登录
		if (wxNickname != null && !"".equals(wxNickname)) {
			log.info("微信用户{" + wxNickname + "}退出系统..sessionID{" + sessionID
					+ "}");
		}
		// 账号用户退出登录
		else {
			log.info("账号用户{" + userTel + "}退出系统..sessionID{" + sessionID + "}");
		}

		TUser user = new TUser();
		user.setUserId(userId);
		user.setUserIsLogin(-1);
		userService.updateUser(user);

		TSessionUnbound bound = new TSessionUnbound();
		bound.setUserId(userId);
		bound.setSessionId(sessionID);
		accessLogService.saveSessionBound(bound);
	}

	public String content;// 编辑用户的时候传递过得信息

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 用户身份是游客
	 */
	public static final String USER_STATUS_TOUR = "tour";

	/**
	 * 用户身份是导游
	 */
	public static final String USER_STATUS_TOUR_GUIDE = "tour_guide";

	public String access_token;
	public String openid;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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

	public double distance;// 距离
	public int size;// 信息条数

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
