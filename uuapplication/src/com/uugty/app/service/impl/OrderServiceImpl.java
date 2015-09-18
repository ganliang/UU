package com.uugty.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.dao.OrderDao;
import com.uugty.app.domain.TComment;
import com.uugty.app.domain.TOrder;
import com.uugty.app.domain.TOrderGratuity;
import com.uugty.app.domain.TOrderGratuityReceiver;
import com.uugty.app.domain.TOrderRecharge;
import com.uugty.app.domain.TOrderRecord;
import com.uugty.app.entity.OrderDetailEntity;
import com.uugty.app.entity.OrderListEntity;
import com.uugty.app.entity.OrderRecordEntity;
import com.uugty.app.entity.SQLEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.utils.DateUtil;
import com.uugty.app.utils.Page;
import com.uugty.app.utils.SQLUtil;

public class OrderServiceImpl implements IOrderService {
	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);
	private OrderDao orderDao = new OrderDao();

	/**
	 * 保存使用钱包来支付的小费
	 */
	@Override
	public int savePursePaymentGratuity(TOrderGratuity gratuity) {
		gratuity.setGratuityStartDate(new Date());
		gratuity.setGratuityStatus(TOrderGratuity.GRATUITY_STATUS_RUNING);// 发送中
		return orderDao.savePursePaymentGratuity(gratuity);
	}

	/**
	 * 保存使用微信来支付的小费
	 */
	@Override
	public int saveWeChartPaymentGratuity(TOrderGratuity gratuity,
			TOrderRecharge recharge) {
		gratuity.setGratuityStartDate(new Date());
		gratuity.setGratuityStatus(TOrderGratuity.GRATUITY_STATUS_RUNING);// 发送中
		return orderDao.saveWeChartPaymentGratuity(gratuity, recharge);
	}

	/**
	 * 根据小费id 来获取到小费的信息
	 */
	@Override
	public TOrderGratuity getGratuityById(TOrderGratuity gratuity) {
		String sql = "SELECT *FROM T_ORDER_GRATUITY WHERE gratuity_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(gratuity.getGratuityId());
		try {
			List<Object> query = orderDao
					.query(sql, args, TOrderGratuity.class);
			gratuity = query != null && query.size() > 0 ? (TOrderGratuity) query
					.get(0) : null;
		} catch (InstantiationException e) {
			log.error("根据小费id来获取小费信息出现异常", e);
			throw new RuntimeException("根据小费id来获取小费信息出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据小费id来获取小费信息出现异常", e);
			throw new RuntimeException("根据小费id来获取小费信息出现异常", e);
		} catch (SQLException e) {
			log.error("根据小费id来获取小费信息出现异常", e);
			throw new RuntimeException("根据小费id来获取小费信息出现异常", e);
		}
		return gratuity;
	}

	/**
	 * 接受红包
	 */
	@Override
	public void receiveGratuity(TOrderGratuity gratuity) {
		orderDao.receiveGratuity(gratuity);
	}

	@Override
	public int getGratuityCountById(int gratuityId) {
		String sql = "SELECT COUNT(receiver_id) FROM T_ORDER_GRATUITY_RECEIVER WHERE gratuity_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(gratuityId);
		int count = 0;
		try {
			count = orderDao.count(sql, args);
		} catch (SQLException e) {
			log.error("获取红包领取的数量出现异常", e);
			throw new RuntimeException("获取红包领取的数量出现异常", e);
		}
		return count;
	}

	@Override
	public List<Object> getReceivedGratuityUserMessageById(int gratuityId) {
		String sql = "SELECT r.* FROM T_ORDER_GRATUITY_RECEIVER r LEFT JOIN T_USER U ON(u.user_id=r.receiver_user_id) WHERE r.gratuity_id=? ORDER BY r.receiver_date ASC";
		List<Object> args = new ArrayList<Object>();
		args.add(gratuityId);
		List<Object> list = null;
		try {
			list = orderDao.query(sql, args, TOrderGratuityReceiver.class);
		} catch (Exception e) {
			log.error("获取红包接受用户的信息出现异常", e);
			throw new RuntimeException("获取红包接受用户的信息出现异常", e);
		}
		return list;
	}

	/**
	 * @Title: saveOrder
	 * @Description: 保存订单
	 * @param @param order
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	public int saveOrder(TOrder order) {
		SQLEntity insertSQL = SQLUtil.insertSQL(order);
		try {
			return orderDao.executeInsert(insertSQL.getParameter(),
					insertSQL.getList());
		} catch (Exception e) {
			log.error("保存订单信息出现异常", e);
			throw new RuntimeException("保存订单信息出现异常", e);
		}
	}

	/**
	 * @Title: getOrderById
	 * @Description: 根据订单id获取订单详细信息
	 * @param @param orderId
	 * @param @return
	 * @return OrderDetailEntity 返回类型
	 * @throws
	 */
	@Override
	public TOrder getOrderById(int orderId) {
		String sql = "SELECT *FROM T_ORDER WHERE order_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(orderId);
		TOrder order = null;
		try {
			List<Object> query = orderDao.query(sql, args, TOrder.class);
			order = query != null && query.size() > 0 ? (TOrder) query.get(0)
					: null;
		} catch (InstantiationException e) {
			log.error("根据订单id获取订单详细信息出现异常", e);
			throw new RuntimeException("根据订单id获取订单详细信息出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据订单id获取订单详细信息出现异常", e);
			throw new RuntimeException("根据订单id获取订单详细信息出现异常", e);
		} catch (SQLException e) {
			log.error("根据订单id获取订单详细信息出现异常", e);
			throw new RuntimeException("根据订单id获取订单详细信息出现异常", e);
		}
		return order;
	}

	/**
	 * @Title: getAllOrderByUserId
	 * @Description: 根据用户id来获取订单列表
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getAllOrderByUserId(TOrder myOrder) {
		String orderStatus = myOrder.getOrderStatus();
		String userId = myOrder.getOrderUserId();
		List<Object> args = new ArrayList<Object>();
		String sql = "";

		switch (myOrder.getUserStatus()) {
		// 游客身份登录
		case "1":
			switch (myOrder.getOrderStatus()) {
			case "0":// 正在进行的
				orderStatus = "'order_create','order_payment','order_agree','order_success','order_drawback'";
				break;
			case "1":// 已经完成的
				orderStatus = "'order_no_pay_cancel','order_invalid','order_not_agree_cancel','order_deny','order_agree_cancel','order_drawback_success','order_drawback_failure'";
				break;
			}
			sql = "SELECT u.user_id,u.user_avatar,u.user_realname,r.roadline_goal_area,r.roadline_days,o.order_id,o.order_create_date,o.order_time,o.order_status,o.order_price,(SELECT COUNT(e.comment_id) FROM T_COMMENT e WHERE e.order_id=o.order_id) isEval  FROM T_ORDER o LEFT JOIN T_USER u ON(u.user_id=o.order_tour_user_id) LEFT JOIN T_ROADLINE r ON(r.roadline_id=o.order_roadline_id) WHERE o.order_user_id=? AND o.order_status in("
					+ orderStatus + ") ORDER BY o.order_time DESC LIMIT ?,?";
			break;
		// 导游身份登录
		case "2":
			switch (myOrder.getOrderStatus()) {
			case "0":// 正在进行的
				orderStatus = "'order_payment','order_agree','order_success','order_drawback'";
				break;
			case "1":// 已经完成的
				orderStatus = "'order_not_agree_cancel','order_deny','order_agree_cancel','order_drawback_success','order_drawback_failure'";
				break;
			}
			sql = "SELECT u.user_id,u.user_avatar,u.user_realname,r.roadline_goal_area,r.roadline_days,o.order_id,o.order_create_date,o.order_time,o.order_status,o.order_price,(SELECT COUNT(e.comment_id) FROM T_COMMENT e WHERE e.order_id=o.order_id) isEval  FROM T_ORDER o LEFT JOIN T_USER u ON(u.user_id=o.order_user_id) LEFT JOIN T_ROADLINE r ON(r.roadline_id=o.order_roadline_id) WHERE o.order_tour_user_id=? AND o.order_status in("
					+ orderStatus + ") ORDER BY o.order_time DESC LIMIT ?,?";
			break;
		}
		args.add(userId);
		Page page = new Page(myOrder.getPageSize(), myOrder.getCurrentPage(), 0);
		args.add(page.getBeginIndex());
		args.add(page.getPageSize());

		List<Object> query = null;
		List<Object> list = null;
		try {
			query = orderDao.query(sql, args, OrderListEntity.class);
			list = new ArrayList<Object>();
			for (Object object : query) {
				OrderListEntity orderListEntity = (OrderListEntity) object;
				switch (orderListEntity.getOrderStatus()) {
				case TOrder.ORDER_CREATE:
					if (DateUtil.calculateMinutes(new Date(),
							orderListEntity.getOrderCreateDate()) > 30) {
						orderListEntity.setOrderStatus(TOrder.ORDER_INVALID);// 订单失效
					}
					break;
				case TOrder.ORDER_PAYMENT:
					if (DateUtil.calculateMinutes(new Date(),
							orderListEntity.getOrderCreateDate()) > 3 * 60) {
						orderListEntity
								.setOrderStatus(TOrder.ORDER_NOT_AGREE_CANCEL);// 导游未接受
																				// 拒绝
					}
					break;
				default:
					break;
				}
				orderListEntity.setOrderCreateDate(null);
				list.add(orderListEntity);
			}
			orderDao.updateOrders(list);
		} catch (InstantiationException e) {
			log.error("根据用户id来获取订单列表出现异常", e);
			throw new RuntimeException("根据用户id来获取订单列表出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据用户id来获取订单列表出现异常", e);
			throw new RuntimeException("根据用户id来获取订单列表出现异常", e);
		} catch (SQLException e) {
			log.error("根据用户id来获取订单列表出现异常", e);
			throw new RuntimeException("根据用户id来获取订单列表出现异常", e);
		}
		return list;
	}

	/**
	 * @Title: updateOrderStatus
	 * @Description: 修改订单状态
	 * @param @param order
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void updateOrderStatus(TOrder order) {
		String sql = "UPDATE T_ORDER SET order_status=? WHERE order_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(order.getOrderStatus());
		args.add(order.getOrderId());
		try {
			orderDao.update(sql, args);
		} catch (SQLException e) {
			log.error("更新订单状态出现异常", e);
			throw new RuntimeException("更新订单状态出现异常", e);
		}
	}

	/**
	 * @Title: saveTourPursePayment
	 * @Description: 旅游订单钱包支付
	 * @param @param orderById
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void saveTourPursePayment(TOrder orderById) {
		orderDao.saveTourPursePayment(orderById);
	}

	/**
	 * @Title: saveTourPursePayment
	 * @Description: 旅游小费钱包支付
	 * @param @param orderById
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void saveTourWeChartPayment(TOrder orderById, TOrderRecharge recharge) {
		orderDao.saveTourWeChartPayment(orderById, recharge);
	}

	/**
	 * @Title: drawbackSuccess
	 * @Description: 退款成功
	 * @param @param orderId
	 * @param @param orderStatus
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void drawbackSuccess(int orderId, String orderStatus) {
		orderDao.drawbackSuccess(orderId, orderStatus);
	}

	/**
	 * @Title: orderComplete
	 * @Description: 订单完成【修改订单状态，将旅游费用给导游】
	 * @param @param orderId
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void orderCompleteByOrderId(int orderId) {
		orderDao.orderCompleteByOrderId(orderId);
	}

	/**
	 * @param userId
	 * @Title: getOrderDetailMessageById
	 * @Description: 根据订单id，来获取订单详情
	 * @param @param orderId
	 * @param @return
	 * @return OrderDetailEntity 返回类型
	 * @throws
	 */
	@Override
	public OrderDetailEntity getOrderDetailMessage(int orderId, String userId) {
		String sql = "SELECT u.user_id,u.user_avatar,u.user_realname,u.user_sex,u.user_constellation,u.user_post,u.user_work,u.user_credit_score,r.roadline_goal_area,r.roadline_days,o.order_no,o.order_create_date,o.order_time,o.order_price,o.order_mark,o.order_status,o.order_drawback_reason,o.order_drawback_money,o.order_drawback_date  FROM T_ORDER o LEFT JOIN T_USER u ON(u.user_id=o.order_tour_user_id) LEFT JOIN T_ROADLINE r ON(r.roadline_id=o.order_roadline_id) WHERE o.order_id=? AND o.order_user_id=? "
				+ "   UNION "
				+ "   SELECT u.user_id,u.user_avatar,u.user_realname,u.user_sex,u.user_constellation,u.user_post,u.user_work,u.user_credit_score,r.roadline_goal_area,r.roadline_days,o.order_no,o.order_create_date,o.order_time,o.order_price,o.order_mark,o.order_status,o.order_drawback_reason,o.order_drawback_money,o.order_drawback_date  FROM T_ORDER o LEFT JOIN T_USER u ON(u.user_id=o.order_user_id) LEFT JOIN T_ROADLINE r ON(r.roadline_id=o.order_roadline_id) WHERE o.order_id=? AND o.order_tour_user_id=?";
		List<Object> args = new ArrayList<Object>();
		OrderDetailEntity orderDetail = null;
		args.add(orderId);
		args.add(userId);
		args.add(orderId);
		args.add(userId);
		try {
			List<Object> query = orderDao.query(sql, args,
					OrderDetailEntity.class);
			if (query != null && query.size() > 0) {
				orderDetail = (OrderDetailEntity) query.get(0);
			}
		} catch (InstantiationException e) {
			log.error("根据订单id，来获取订单详情出现异常", e);
			throw new RuntimeException("根据订单id，来获取订单详情出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据订单id，来获取订单详情出现异常", e);
			throw new RuntimeException("根据订单id，来获取订单详情出现异常", e);
		} catch (SQLException e) {
			log.error("根据订单id，来获取订单详情出现异常", e);
			throw new RuntimeException("根据订单id，来获取订单详情出现异常", e);
		}
		return orderDetail;
	}

	/**
	 * @Title: orderModify
	 * @Description: 订单编辑修改
	 * @param @param orderById
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void orderModify(TOrder order) {
		String sql = "UPDATE T_ORDER SET order_mark=?,order_time=? WHERE order_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(order.getOrderMark());
		args.add(order.getOrderTime());
		args.add(order.getOrderId());
		try {
			orderDao.update(sql, args);
		} catch (SQLException e) {
			log.error("更新订单状态出现异常", e);
			throw new RuntimeException("更新订单状态出现异常", e);
		}
	}

	/**
	 * @Title: cancelOrder
	 * @Description: 取消订单
	 * @param @param order
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void cancelOrder(TOrder order) {
		orderDao.cancelOrder(order);
	}

	/**
	 * @Title: saveEvaluation
	 * @Description: 保存评价
	 * @param @param evaluation
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	public int saveOrderComment(TComment comment) {
		return orderDao.saveOrderComment(comment);
	}

	/**
	 * @Title: getOrderEvaluationByOrderId
	 * @Description: 获取订单评价的详情
	 * @param @param orderId
	 * @param @return
	 * @return TOrderEvaluation 返回类型
	 * @throws
	 */
	@Override
	public TComment getOrderCommentByOrderId(int orderId) {
		String sql = "SELECT * FROM T_COMMENT WHERE order_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(orderId);
		List<Object> query = null;
		try {
			query = orderDao.query(sql, args, TComment.class);
		} catch (Exception e) {
			log.error("获取订单评价的详情出现异常", e);
			throw new RuntimeException("获取订单评价的详情出现异常", e);
		}
		return query != null && query.size() > 0 ? (TComment) query.get(0)
				: null;
	}

	/**
	 * @Title: updateOrder
	 * @Description: 更新订单
	 * @param @param order
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void updateOrder(TOrder order) {
		SQLEntity updateSQL = SQLUtil.updateSQL(order);
		try {
			String sql = "UPDATE T_ORDER SET " + updateSQL.getParameter()
					+ " WHERE ORDER_ID=?";
			List<Object> args = updateSQL.getList();
			args.add(order.getOrderId());
			orderDao.update(sql, args);
		} catch (SQLException e) {
			log.error("更新订单出现异常", e);
			throw new RuntimeException("更新订单出现异常", e);
		}
	}

	/**
	 * @Title: orderTourDeny
	 * @Description: 退款 ; 更新订单状态
	 * @param @param order
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void orderTourDeny(TOrder order) {
		orderDao.orderTourDeny(order);
	}

	/**
	 * @Title: scheduleDrawbackSuccess
	 * @Description: 启动一个定时器 如果七天之后 该订单还是申请退款中，则自动退款
	 * @param @param orderId
	 * @param @param orderDrawbackSuccess
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void scheduleDrawbackSuccess(int orderId, String orderDrawbackSuccess) {
		orderDao.scheduleDrawbackSuccess(orderId, orderDrawbackSuccess);
	}

	/**
	 * @Title: getOrderRecordList
	 * @Description: 获取用户的账单
	 * @param @param record
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getOrderRecordList(TOrderRecord record) {
		List<Object> args = new ArrayList<Object>();
		args.add(record.getUserId());

		String sql = "SELECT r.record_id,r.out_trade_no,r.record_type,r.record_status,r.record_trade_money,date_format(r.record_trade_date,'%Y-%m-%d') record_trade_date,date_format(r.record_trade_date,'%m') month,road.roadline_title FROM T_ORDER_RECORD r LEFT JOIN T_ORDER o ON(r.out_trade_no=o.order_no) LEFT JOIN T_ROADLINE road ON(road.roadline_id=o.order_roadline_id) WHERE r.user_id=? "
				+ " ORDER BY r.record_trade_date DESC limit ?,?";
		Page page = new Page(record.getPageSize(), record.getCurrentPage(), 0);
		args.add(page.getBeginIndex());
		args.add(page.getPageSize());
		List<Object> list = null;
		try {
			list = orderDao.query(sql, args, OrderRecordEntity.class);
		} catch (InstantiationException e) {
			log.error("获取账单出现异常", e);
			throw new RuntimeException("获取账单出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取账单出现异常", e);
			throw new RuntimeException("获取账单出现异常", e);
		} catch (SQLException e) {
			log.error("获取账单出现异常", e);
			throw new RuntimeException("获取账单出现异常", e);
		}
		return list;
	}

	/**
	 * @Title: saveOrderGratuityRecharge
	 * @Description: 小费支付
	 * @param @param recharge
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void saveOrderRechargeAndRecord(TOrderRecharge recharge) {
		orderDao.saveOrderRechargeAndRecord(recharge);
	}

	/**
	 * @Title: wexinPayBack
	 * @Description: 微信支付的结果 *
	 * @param @param status 支付的状态 2 成功,3 失败
	 * @param @param outTradeNo 订单编号
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	@Override
	public String wexinPayCall(String status, String outTradeNo) {
		return orderDao.wexinPayCall(status, outTradeNo);
	}

	/**
	 * @Title: CloseCharge
	 * @Description: 用户支付的时候 取消了支付
	 * @param @param outTradeNo
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void CloseCharge(String outTradeNo) {
		orderDao.CloseCharge(outTradeNo);
	}

	/**
	 * 
	 * @Title: scheduleGratuityUnReceived
	 * @Description: 一天之后 红包没有被领取 则返回红包到发送者账户
	 * @param @param gratuityId
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void scheduleGratuityUnReceived(String gratuityId) {
		orderDao.scheduleGratuityUnReceived(gratuityId);
	}

	/**
	 * 获取七天内的交易额度
	 */
	@Override
	public float getOrderSevenDayTrade(String userId) {
		String sql = "SELECT SUM(order_price) FROM T_ORDER WHERE order_tour_user_id=? AND order_status='order_success' AND DATEDIFF(order_create_date,NOW())<=7";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		float tradeMoney = 0;
		try {
			List<Object[]> query = orderDao.query(sql, args);
			if (query != null && query.size() > 0) {
				tradeMoney = Float.parseFloat(query.get(0)[0].toString());
			}
		} catch (SQLException e) {
			log.error("获取七天内的交易额度出现异常", e);
			throw new RuntimeException("获取七天内的交易额度出现异常", e);
		}
		return tradeMoney;
	}
}
