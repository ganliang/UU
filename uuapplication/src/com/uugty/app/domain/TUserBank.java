package com.uugty.app.domain;

import java.io.Serializable;

/**
 * @ClassName: TUserBank
 * @Description: 用户添加的银行卡信息
 * @author ganliang
 * @date 2015年7月6日 下午2:41:07
 */
public class TUserBank implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 6115049035430015446L;

	private int bankId;// 银行卡id
	private String userId;// 用户的id

	private String bankCard;// 银行卡号
	/*
	 * 1 中国银行
	 * 
	 * 2 中国农业银行
	 * 
	 * 3 中国工商银行
	 * 
	 * 4 中国建设银行
	 * 
	 * 5 中国交通银行
	 * 
	 * 6 中国招商银行
	 */
	private String bankCardType;// 银行卡号的类型

	private int bankIsDefault;// 是默认支付银行卡号不

	private String bankBoundDate;// 银行绑定的日期

	private String bankOwner;// 银行卡的用户

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public int getBankIsDefault() {
		return bankIsDefault;
	}

	public void setBankIsDefault(int bankIsDefault) {
		this.bankIsDefault = bankIsDefault;
	}

	public String getBankBoundDate() {
		return bankBoundDate;
	}

	public void setBankBoundDate(String bankBoundDate) {
		this.bankBoundDate = bankBoundDate;
	}

	public String getBankOwner() {
		return bankOwner;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

}
