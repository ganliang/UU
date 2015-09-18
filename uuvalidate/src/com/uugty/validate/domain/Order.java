package com.uugty.validate.domain;

import java.io.Serializable;

public class Order implements Serializable {
	private static final long serialVersionUID = 7502367889887982633L;

	private int orderId;// 用户订单id
	private String orderUserId;// 生成订单的用户id
	private String orderTourUserId;// 导游的用户id
	private int orderRoadlineId;// 订单的路线id

	private String orderNo;// 订单号
	private String orderMark;// 导游的标签
	private String orderTime;// 订单的时间
	private float orderPrice;// 订单的价格

	/** bad_service 服务态度差 */
	public static final String DRAWBACK_REASON_BAD_SERVICE = "1";

	/** not_appointment 没有按时赴约 */
	public static final String DRAWBACK_REASON_NOT_APPOINTMENT = "2";

	/** other 其他 */
	public static final String DRAWBACK_REASON_OTHER = "3";
	private String orderDrawbackReason;// 退款原因

	/** 退款率 */
	public static final float ORDER_DRAWBACK_RATE = 0;

	private float orderDrawbackMoney;// 退款金额
	private String orderDrawbackDate;// 退款日期

	/** 已下单 order_create */
	public static final String ORDER_CREATE = "order_create";

	/** 未付款取消 */
	public static final String ORDER_NO_PAY_CANCEL = "order_no_pay_cancel";

	/** 已失效 */
	public static final String ORDER_INVALID = "order_invalid";

	/** 已支付 */
	public static final String ORDER_PAYMENT = "order_payment";

	/** 付款后 导游未同意前取消 */
	public static final String ORDER_NOT_AGREE_CANCEL = "order_not_agree_cancel";

	/** 已拒绝 */
	public static final String ORDER_DENY = "order_deny";

	/** 已接受 */
	public static final String ORDER_AGREE = "order_agree";

	/** 付款后， 导游同意后取消 */
	public static final String ORDER_AGREE_CANCEL = "order_agree_cancel";

	/** 订单完成 付款 */
	public static final String ORDER_SUCCESS = "order_success";

	/** 退款中 */
	public static final String ORDER_DRAWBACK = "order_drawback";

	/** 退款拒绝 ，失败 */
	public static final String ORDER_DRAWBACK_FAILURE = "order_drawback_failure";

	/** 退款完成 */
	public static final String ORDER_DRAWBACK_SUCCESS = "order_drawback_success";

	/** 支付失败 */
	public static final String ORDER_PAY_CANCEL = "order_pay_cancel";
	private String orderStatus;// 0 正在进行的 ,1 已经完成的,
	private String orderCreateDate;// 订单的生成日期

	private String userTel;
	private String userName;
	private String userAvatar;
	private String userIdentityCard;// 用户的身份证号码

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}

	public String getOrderUserId() {
		return orderUserId;
	}

	public void setOrderTourUserId(String orderTourUserId) {
		this.orderTourUserId = orderTourUserId;
	}

	public String getUserIdentityCard() {
		return userIdentityCard;
	}

	public void setUserIdentityCard(String userIdentityCard) {
		this.userIdentityCard = userIdentityCard;
	}

	public String getOrderTourUserId() {
		return orderTourUserId;
	}

	public void setOrderRoadlineId(int orderRoadlineId) {
		this.orderRoadlineId = orderRoadlineId;
	}

	public int getOrderRoadlineId() {
		return orderRoadlineId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderMark(String orderMark) {
		this.orderMark = orderMark;
	}

	public String getOrderMark() {
		return orderMark;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}

	public float getOrderPrice() {
		return orderPrice;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderCreateDate(String orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}

	public String getOrderCreateDate() {
		return orderCreateDate;
	}

	public String getOrderDrawbackReason() {
		return orderDrawbackReason;
	}

	public void setOrderDrawbackReason(String orderDrawbackReason) {
		this.orderDrawbackReason = orderDrawbackReason;
	}

	public float getOrderDrawbackMoney() {
		return orderDrawbackMoney;
	}

	public void setOrderDrawbackMoney(float orderDrawbackMoney) {
		this.orderDrawbackMoney = orderDrawbackMoney;
	}

	public String getOrderDrawbackDate() {
		return orderDrawbackDate;
	}

	public void setOrderDrawbackDate(String orderDrawbackDate) {
		this.orderDrawbackDate = orderDrawbackDate;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	/**
	 * 非持久化字段
	 */
	public int currentPage = 1;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
