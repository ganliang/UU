package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

public class TOrderGratuity implements Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -7896950008277436898L;

	private int gratuityId;
	private String gratuitySenderUserId;// 红包发送人的用户id
	private String gratuityReceiverUserId;// 收红包的用户id
	private String gratuityReceiverGroupId;// 群组id

	private int gratuityCount;// 红包的数量
	private float gratuityEveryMoney;// 每个红包的价钱
	private float gratuityTotalMoney;

	private String gratuityMark;// 红包留言

	public static final String GRATUITY_STATUS_RUNING = "1";
	public static final String GRATUITY_STATUS_END = "2";
	public static final String GRATUITY_STATUS_EXPIRE = "3";
	private String gratuityStatus;// 红包的状态 1 发送中 ,2 发送结束,3 未接受 过期

	private String gratuityType;// 红包的类型 1 普通红包 2 拼手气红包
	private Date gratuityStartDate;// 红包的开始时间
	private Date gratuityEndDate;// 红包的结束时间

	private String outTradeNo;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public int getGratuityId() {
		return gratuityId;
	}

	public void setGratuityId(int gratuityId) {
		this.gratuityId = gratuityId;
	}

	public String getGratuitySenderUserId() {
		return gratuitySenderUserId;
	}

	public void setGratuitySenderUserId(String gratuitySenderUserId) {
		this.gratuitySenderUserId = gratuitySenderUserId;
	}

	public String getGratuityReceiverUserId() {
		return gratuityReceiverUserId;
	}

	public void setGratuityReceiverUserId(String gratuityReceiverUserId) {
		this.gratuityReceiverUserId = gratuityReceiverUserId;
	}

	public String getGratuityReceiverGroupId() {
		return gratuityReceiverGroupId;
	}

	public void setGratuityReceiverGroupId(String gratuityReceiverGroupId) {
		this.gratuityReceiverGroupId = gratuityReceiverGroupId;
	}

	public int getGratuityCount() {
		return gratuityCount;
	}

	public void setGratuityCount(int gratuityCount) {
		this.gratuityCount = gratuityCount;
	}

	public float getGratuityEveryMoney() {
		return gratuityEveryMoney;
	}

	public void setGratuityEveryMoney(float gratuityEveryMoney) {
		this.gratuityEveryMoney = gratuityEveryMoney;
	}

	public String getGratuityMark() {
		return gratuityMark;
	}

	public void setGratuityMark(String gratuityMark) {
		this.gratuityMark = gratuityMark;
	}

	public String getGratuityStatus() {
		return gratuityStatus;
	}

	public void setGratuityStatus(String gratuityStatus) {
		this.gratuityStatus = gratuityStatus;
	}

	public String getGratuityType() {
		return gratuityType;
	}

	public void setGratuityType(String gratuityType) {
		this.gratuityType = gratuityType;
	}

	public Date getGratuityStartDate() {
		return gratuityStartDate;
	}

	public void setGratuityStartDate(Date gratuityStartDate) {
		this.gratuityStartDate = gratuityStartDate;
	}

	public Date getGratuityEndDate() {
		return gratuityEndDate;
	}

	public void setGratuityEndDate(Date gratuityEndDate) {
		this.gratuityEndDate = gratuityEndDate;
	}

	public float getGratuityTotalMoney() {
		return gratuityTotalMoney;
	}

	public void setGratuityTotalMoney(float gratuityTotalMoney) {
		this.gratuityTotalMoney = gratuityTotalMoney;
	}

	/**
	 * 非持久化字段
	 */
	public String userPayPassword;

	public String getUserPayPassword() {
		return userPayPassword;
	}

	public void setUserPayPassword(String userPayPassword) {
		this.userPayPassword = userPayPassword;
	}

	public String type;// 1 微信支付;2 还是钱包支付

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String authCode;

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

}
