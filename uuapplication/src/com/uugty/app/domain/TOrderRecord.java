package com.uugty.app.domain;

import java.io.Serializable;

/**
 * @ClassName: TOrderRecord
 * @Description: 订单记录 只要有钱的交易 就产生订单的记录
 * @author ganliang
 * @date 2015年7月6日 上午11:42:57
 */
public class TOrderRecord implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 4300046591659493731L;

	private int recordId;// 订单记录id
	private String outTradeNo;// 订单编号
	private String userId;// 操作用户的用户id

	public static final String RECORD_TYPE_ORDER_WX_SEND = "order_wx_send";
	public static final String RECORD_TYPE_ORDER_PURSE_SEND = "order_purse_send";
	public static final String RECORD_TYPE_ORDER_RECEIVE = "order_receive";// 订单收益
	public static final String RECORD_TYPE_GRATUITY_WX_SEND = "gratuity_wx_send";// 小费发送
	public static final String RECORD_TYPE_GRATUITY_PURSE_SEND = "gratuity_purse_send";// 小费发送
	public static final String RECORD_TYPE_GRATUITY_RECEIVE = "gratuity_receive";// 小费接受
	public static final String RECORD_TYPE_WIDTHDRAW = "widthdraw";// 提现
	public static final String RECORD_TYPE_RECHARGE = "recharge";// 充值
	public static final String RECORD_TYPE_DRAWBACK_OUTCOME = "drawback_outcome";//
	public static final String RECORD_TYPE_DRAWBACK_INCOME = "drawback_income";//
	public static final String RECORD_TYPE_PENALTY = "penalty";// 违约金
	private String recordType;

	public static final String RECORD_STATUS_RUNING = "1";
	public static final String RECORD_STATUS_SUCCESS = "2";
	public static final String RECORD_STATUS_CLOSE = "3";
	public static final String RECORD_STATUS_CANCEL = "4";
	private String recordStatus;// 交易状态 1 进行中 ，2 成功完成 ，3 关闭 ，4 取消

	private float recordTradeMoney;// 交易金额
	private String recordTradeDate;// 交易时间

	public static final String DRAWBACK_TOUR = "tour";
	public static final String DRAWBACK_GUIDE = "guide";

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

	public float getRecordTradeMoney() {
		return recordTradeMoney;
	}

	public void setRecordTradeMoney(float recordTradeMoney) {
		this.recordTradeMoney = recordTradeMoney;
	}

	public String getRecordTradeDate() {
		return recordTradeDate;
	}

	public void setRecordTradeDate(String recordTradeDate) {
		this.recordTradeDate = recordTradeDate;
	}

	/**
	 * 非持久化字段
	 */
	public int currentPage = 1;
	public int pageSize = 8;// 一页的数量

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	private int month;// 订单的月份

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

}
