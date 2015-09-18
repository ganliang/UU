package com.uugty.app.service;

import java.util.List;

import com.uugty.app.domain.TComment;
import com.uugty.app.domain.TOrder;
import com.uugty.app.domain.TOrderGratuity;
import com.uugty.app.domain.TOrderRecharge;
import com.uugty.app.domain.TOrderRecord;
import com.uugty.app.entity.OrderDetailEntity;

public interface IOrderService {
	/*
	 * 小费功能模块
	 */
	public int savePursePaymentGratuity(TOrderGratuity gratuity);

	public int saveWeChartPaymentGratuity(TOrderGratuity gratuity,
			TOrderRecharge recharge);

	public TOrderGratuity getGratuityById(TOrderGratuity gratuity);

	public void receiveGratuity(TOrderGratuity gratuity);

	public int getGratuityCountById(int gratuityId);

	public List<Object> getReceivedGratuityUserMessageById(int gratuityId);

	/*
	 * 订单功能模块
	 */
	public void updateOrderStatus(TOrder order);

	public void cancelOrder(TOrder order);

	public void CloseCharge(String outTradeNo);

	public void saveOrderRechargeAndRecord(TOrderRecharge recharge);

	public void orderCompleteByOrderId(int orderId);

	public OrderDetailEntity getOrderDetailMessage(int orderId, String userId);

	public void scheduleDrawbackSuccess(int orderId, String orderDrawbackSuccess);

	public void drawbackSuccess(int orderId, String orderStatus);

	public void orderTourDeny(TOrder order);

	public List<Object> getAllOrderByUserId(TOrder order);

	public void updateOrder(TOrder order);

	public TOrder getOrderById(int orderId);

	public void orderModify(TOrder orderById);

	public int saveOrder(TOrder order);

	/*
	 * 旅游支付
	 */
	public void saveTourPursePayment(TOrder orderById);

	public void saveTourWeChartPayment(TOrder orderById, TOrderRecharge recharge);

	/*
	 * 订单评价
	 */
	public TComment getOrderCommentByOrderId(int orderId);

	public int saveOrderComment(TComment comme);

	/*
	 * 账单
	 */
	public List<Object> getOrderRecordList(TOrderRecord record);

	public String wexinPayCall(String status, String outTradeNo);

	public void scheduleGratuityUnReceived(String outTradeNo);

	public float getOrderSevenDayTrade(String userId);

}
