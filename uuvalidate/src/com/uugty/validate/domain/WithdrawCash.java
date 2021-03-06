package com.uugty.validate.domain;

import java.io.Serializable;

/**
 * @ClassName: TWithdrawCash
 * @Description: 用户提现的实体对象
 * @author ganliang
 * @date 2015年7月6日 下午12:03:29
 */
public class WithdrawCash implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -4687795700194612720L;

	private int withdrawId;// 提现id

	private String userId;// 提现的用户id

	private int withdrawType;// 提现方式 提现到哪里 1 英航卡 默认为银行卡
	private String withdrawBankCard;// 提现的银行卡号
	private String withdrawBankCardType;// 提现的银行卡号是那个银行
	private float withdrawMoney;// 提现金额

	private int withdrawStatus;// 提现的状态 1 提出提现请求，2 取消提现请求 ，3 提现请求完成 ,4 获取提现失败的提现

	private String withdrawDate;// 提现日期

	private String outTradeNo;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public int getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(int withdrawId) {
		this.withdrawId = withdrawId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(int withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getWithdrawBankCard() {
		return withdrawBankCard;
	}

	public void setWithdrawBankCard(String withdrawBankCard) {
		this.withdrawBankCard = withdrawBankCard;
	}

	public String getWithdrawBankCardType() {
		return withdrawBankCardType;
	}

	public void setWithdrawBankCardType(String withdrawBankCardType) {
		this.withdrawBankCardType = withdrawBankCardType;
	}

	public float getWithdrawMoney() {
		return withdrawMoney;
	}

	public void setWithdrawMoney(float withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}

	public int getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(int withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	public String getWithdrawDate() {
		return withdrawDate;
	}

	public void setWithdrawDate(String withdrawDate) {
		this.withdrawDate = withdrawDate;
	}

	private int currentPage;//

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	private String status;//

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
