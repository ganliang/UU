package com.uugty.app.entity;

public class OrderRecordEntity {

	private int recordId;// 订单记录id
	private String outTradeNo;// 订单id
	private String userId;// 操作用户的用户id
	private String recordType;
	private String recordStatus;// 交易状态 1 进行中 ，2 成功完成 ，3 关闭

	private String recordTradeMoney;// 交易金额
	private int month;// 订单的月份
	private String roadlineTitle;
	private String recordTradeDate;

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getRecordTradeMoney() {
		return recordTradeMoney;
	}

	public void setRecordTradeMoney(String recordTradeMoney) {
		this.recordTradeMoney = recordTradeMoney;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getRoadlineTitle() {
		return roadlineTitle;
	}

	public void setRoadlineTitle(String roadlineTitle) {
		this.roadlineTitle = roadlineTitle;
	}

	public String getRecordTradeDate() {
		return recordTradeDate;
	}

	public void setRecordTradeDate(String recordTradeDate) {
		this.recordTradeDate = recordTradeDate;
	}

}
