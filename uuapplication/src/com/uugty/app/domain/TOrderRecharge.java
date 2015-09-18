package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TOrderRecharge
 * @Description: 订单充值
 * @author ganliang
 * @date 2015年7月13日 上午10:59:21
 */
public class TOrderRecharge implements Serializable {

	private static final long serialVersionUID = 8841398344679582116L;

	private int rechargeId;// 充值id
	private String rechargeUserId;// 充值的用户id

	private String outTradeNo;// 充值订单号

	private float rechargeMoney;// 充值的钱数

	/**
	 * 充值中
	 */
	public static final String RECHARGE_STATUS_RUNING = "1";

	/**
	 * 充值成功
	 */
	public static final String RECHARGE_STATUS_SUCCESS = "2";

	/**
	 * 充值失败
	 */
	public static final String RECHARGE_STATUS_FAIL = "3";
	/**
	 * 充值取消
	 */
	public static final String RECHARGE_STATUS_CANCEL = "4";

	private String rechargeStatus;// 充值的状态 1 充值中 2 充值成功 3 充值失败
	private Date rechargeDate;// 充值时间

	public int getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(int rechargeId) {
		this.rechargeId = rechargeId;
	}

	public String getRechargeUserId() {
		return rechargeUserId;
	}

	public void setRechargeUserId(String rechargeUserId) {
		this.rechargeUserId = rechargeUserId;
	}

	public float getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(float rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public String getRechargeStatus() {
		return rechargeStatus;
	}

	public void setRechargeStatus(String rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public Date getRechargeDate() {
		return rechargeDate;
	}

	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String authCode;

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

}
