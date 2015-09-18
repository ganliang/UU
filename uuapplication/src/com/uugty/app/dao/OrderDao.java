package com.uugty.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.domain.TComment;
import com.uugty.app.domain.TOrder;
import com.uugty.app.domain.TOrderGratuity;
import com.uugty.app.domain.TOrderRecharge;
import com.uugty.app.domain.TOrderRecord;
import com.uugty.app.entity.OrderListEntity;
import com.uugty.app.entity.SQLEntity;
import com.uugty.app.utils.SQLUtil;
import com.uugty.app.web.utils.GratuityUnRecivedThread;

/**
 * @ClassName: OrderDao
 * @Description: 订单的dao
 * @author ganliang
 * @date 2015年7月9日 下午7:11:47
 */
public class OrderDao extends BaseDao {

	private static final Logger log = Logger.getLogger(OrderDao.class);

	/**
	 * @Title: savePursePaymentGratuity
	 * @Description: 钱包支付小费
	 * @Description: 1.更新用户账户的余额
	 * @Description: 2.保存小费记录
	 * @Description: 3.发送者的账单记录
	 * @param @param gratuity
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	public int savePursePaymentGratuity(TOrderGratuity gratuity) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		ResultSet rs = null;
		String sql = "";
		int gratuityId = 0;
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			// 更新用户账户的余额
			sql = "UPDATE T_USER SET user_purse=user_purse-? WHERE user_id=?";
			pst = conn.prepareStatement(sql);
			pst.setFloat(1, gratuity.getGratuityTotalMoney());
			pst.setString(2, gratuity.getGratuitySenderUserId());
			log.info(sql + "参数:{" + gratuity.getGratuityTotalMoney() + ","
					+ gratuity.getGratuitySenderUserId() + "}");
			pst.execute();
			pst.close();

			// 钱包支付的时候保存小费
			SQLEntity insertSQL = SQLUtil.insertSQL(gratuity);
			sql = insertSQL.getParameter();
			pst2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			List<Object> list = insertSQL.getList();
			for (int i = 0; i < list.size(); i++) {
				pst2.setObject(i + 1, list.get(i));
			}
			super.log(sql, list);
			pst2.executeUpdate();
			rs = pst2.getGeneratedKeys();
			if (rs.next()) {
				gratuityId = rs.getInt(1);
			}
			pst2.close();
			rs.close();

			// 小费钱包发送订单记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst3 = conn.prepareStatement(sql);
			pst3.setString(1, gratuity.getOutTradeNo());
			pst3.setString(2, gratuity.getGratuitySenderUserId());
			pst3.setString(3, TOrderRecord.RECORD_TYPE_GRATUITY_PURSE_SEND);
			pst3.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst3.setFloat(5, gratuity.getGratuityTotalMoney());
			log.info(sql + "参数:{ " + gratuity.getOutTradeNo() + ","
					+ gratuity.getGratuitySenderUserId() + ","
					+ TOrderRecord.RECORD_TYPE_GRATUITY_PURSE_SEND + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + ","
					+ gratuity.getGratuityTotalMoney() + "}");
			pst3.execute();
			pst3.close();

			conn.commit();// 事务关闭
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("保存红包事务回滚异常!", e);
				throw new RuntimeException("保存红包事务回滚异常!", e);
			}
			log.error("保存红包异常!", e);
			throw new RuntimeException("保存红包异常!", e);
		} finally {
			close(null, null, null, conn);
		}
		return gratuityId;
	}

	/**
	 * @return
	 * @Title: saveWeChartPaymentGratuity
	 * @Description: 保存微信支付的小费
	 * @Description: 1.保存充值记录
	 * @Description: 2.保存小费记录
	 * @Description: 3.发送者的账单记录
	 * @param @param gratuity
	 * @param @param recharge
	 * @return void 返回类型
	 * @throws
	 */
	public int saveWeChartPaymentGratuity(TOrderGratuity gratuity,
			TOrderRecharge recharge) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		ResultSet rs = null;
		String sql = "";
		int gratutiyId = 0;

		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			// 保存充值记录
			sql = "INSERT INTO T_ORDER_RECHARGE(out_trade_no,recharge_user_id,recharge_money,recharge_status,recharge_date) VALUES(?,?,?,?,NOW())";
			pst = conn.prepareStatement(sql);
			pst.setString(1, recharge.getOutTradeNo());
			pst.setString(2, recharge.getRechargeUserId());
			pst.setFloat(3, recharge.getRechargeMoney());
			pst.setString(4, TOrderRecharge.RECHARGE_STATUS_RUNING);
			log.info(sql + "参数:{ " + recharge.getOutTradeNo() + ","
					+ recharge.getRechargeUserId() + ","
					+ recharge.getRechargeMoney() + ","
					+ TOrderRecharge.RECHARGE_STATUS_RUNING + "}");
			pst.execute();
			pst.close();

			// 保存小费记录
			SQLEntity insertSQL = SQLUtil.insertSQL(gratuity);
			sql = insertSQL.getParameter();
			pst2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			List<Object> list = insertSQL.getList();
			for (int i = 0; i < list.size(); i++) {
				pst2.setObject(i + 1, list.get(i));
			}
			super.log(sql, list);
			pst2.executeUpdate();
			rs = pst2.getGeneratedKeys();
			if (rs.next()) {
				gratutiyId = rs.getInt(1);
			}
			rs.close();
			pst2.close();

			// 小费微信支付订单记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst3 = conn.prepareStatement(sql);
			pst3.setString(1, gratuity.getOutTradeNo());
			pst3.setString(2, gratuity.getGratuitySenderUserId());
			pst3.setString(3, TOrderRecord.RECORD_TYPE_GRATUITY_WX_SEND);
			pst3.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst3.setFloat(5, gratuity.getGratuityTotalMoney());
			log.info(sql + "参数:{ " + gratuity.getOutTradeNo() + ","
					+ gratuity.getGratuitySenderUserId() + ","
					+ TOrderRecord.RECORD_TYPE_GRATUITY_WX_SEND + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + ","
					+ gratuity.getGratuityTotalMoney() + "}");
			pst3.execute();
			pst3.close();

			conn.commit();// 事务关闭
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("保存红包事务回滚异常!", e);
				throw new RuntimeException("保存红包事务回滚异常!", e);
			}
			log.error("保存红包异常!", e);
			throw new RuntimeException("保存红包异常!", e);
		} finally {
			close(null, null, null, conn);
		}
		return gratutiyId;
	}

	/*
	 * @Description: 接受红包
	 * 
	 * @Description: 1.接受者钱包+小费的钱
	 * 
	 * @Description: 2.更新小费订单记录
	 * 
	 * @Description: 3.保存小费接受者记录
	 */
	public void receiveGratuity(TOrderGratuity gratuity) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		String sql = "";
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			// 接受者钱包+小费的钱
			sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
			pst = conn.prepareStatement(sql);
			pst.setFloat(1, gratuity.getGratuityTotalMoney());
			pst.setString(2, gratuity.getGratuityReceiverUserId());
			log.info(sql + "参数:{" + gratuity.getGratuityTotalMoney() + ","
					+ gratuity.getGratuityReceiverUserId() + "}");
			pst.execute();
			pst.close();

			// 更新小费订单记录
			sql = "UPDATE T_ORDER_GRATUITY SET gratuity_status=?,gratuity_end_date=NOW()  WHERE gratuity_id=?";
			pst2 = conn.prepareStatement(sql);
			pst2.setString(1, gratuity.getGratuityStatus());
			pst2.setInt(2, gratuity.getGratuityId());
			log.info(sql + "参数:{" + gratuity.getGratuityStatus() + ","
					+ gratuity.getGratuityId() + "}");
			pst2.execute();
			pst2.close();

			// 保存小费接受者记录
			sql = "INSERT INTO T_ORDER_GRATUITY_RECEIVER(gratuity_id,receiver_user_id,receiver_date) VALUES(?,?,now())";
			pst3 = conn.prepareStatement(sql);
			pst3.setInt(1, gratuity.getGratuityId());
			pst3.setString(2, gratuity.getGratuityReceiverUserId());
			pst3.execute();
			pst3.close();

			// 接受者的账单记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst4 = conn.prepareStatement(sql);
			pst4.setString(1, gratuity.getOutTradeNo());
			pst4.setString(2, gratuity.getGratuityReceiverUserId());
			pst4.setString(3, TOrderRecord.RECORD_TYPE_GRATUITY_RECEIVE);
			pst4.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst4.setFloat(5, gratuity.getGratuityTotalMoney());
			log.info(sql + "参数:{" + gratuity.getOutTradeNo()
					+ gratuity.getGratuityReceiverUserId() + ","
					+ TOrderRecord.RECORD_TYPE_GRATUITY_RECEIVE + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + ","
					+ gratuity.getGratuityTotalMoney() + "}");
			pst4.execute();
			pst4.close();

			conn.commit();// 事务关闭
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("接收红包回滚异常!", e);
				throw new RuntimeException("接收红包回滚异常!", e);
			}
			log.error("接收红包异常!", e);
			throw new RuntimeException("接收红包异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	/*
	 * @Description: 旅游钱包支付
	 * 
	 * @Description: 1.游客的钱包-路线的费用
	 * 
	 * @Description: 2.更新订单状态
	 * 
	 * @Description: 3.保存订单记录
	 */
	public void saveTourPursePayment(TOrder order) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		String sql = "";
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务
			// 游客的钱包-路线的费用
			sql = "UPDATE T_USER SET user_purse=user_purse-? WHERE user_id=?";
			pst = conn.prepareStatement(sql);
			pst.setFloat(1, order.getOrderPrice());
			pst.setString(2, order.getOrderUserId());
			log.info(sql + "参数:{" + order.getOrderPrice() + ","
					+ order.getOrderUserId() + "}");
			pst.execute();
			pst.close();

			// 更新订单状态
			sql = "UPDATE T_ORDER SET order_status=? WHERE order_id=?";
			pst2 = conn.prepareStatement(sql);
			pst2.setString(1, TOrder.ORDER_PAYMENT);// 已支付
			pst2.setInt(2, order.getOrderId());
			log.info(sql + "参数:{order_payment," + order.getOrderId() + "}");
			pst2.execute();
			pst2.close();

			// 保存订单记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst3 = conn.prepareStatement(sql);
			pst3.setString(1, order.getOrderNo());
			pst3.setString(2, order.getOrderUserId());
			pst3.setString(3, TOrderRecord.RECORD_TYPE_ORDER_PURSE_SEND);
			pst3.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst3.setFloat(5, order.getOrderPrice());
			log.info(sql + "参数:{" + order.getOrderId() + ","
					+ order.getOrderUserId() + ","
					+ TOrderRecord.RECORD_TYPE_ORDER_PURSE_SEND + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + ","
					+ order.getOrderPrice() + "}");
			pst3.execute();
			pst3.close();

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("旅游订单钱包支付回滚异常!", e);
				throw new RuntimeException("旅游订单钱包支付回滚异常!", e);
			}
			log.error("旅游订单钱包支付异常!", e);
			throw new RuntimeException("旅游订单钱包支付异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	/*
	 * @Description: 旅游微信支付
	 * 
	 * @Description: 1.游客的钱包-路线的费用
	 * 
	 * @Description: 2.更新订单状态
	 * 
	 * @Description: 3.保存订单记录
	 */
	public void saveTourWeChartPayment(TOrder order, TOrderRecharge recharge) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		String sql = "";
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			// 保存订单记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getOrderNo());
			pst.setString(2, order.getOrderUserId());
			pst.setString(3, TOrderRecord.RECORD_TYPE_ORDER_WX_SEND);
			pst.setString(4, TOrderRecord.RECORD_STATUS_RUNING);
			pst.setFloat(5, order.getOrderPrice());
			log.info(sql + "参数:{" + order.getOrderNo() + ","
					+ order.getOrderUserId() + ","
					+ TOrderRecord.RECORD_TYPE_ORDER_WX_SEND + ","
					+ TOrderRecord.RECORD_STATUS_RUNING + ","
					+ order.getOrderPrice() + "}");
			pst.execute();
			pst.close();

			// 保存订单充值记录
			sql = "INSERT INTO T_ORDER_RECHARGE(recharge_user_id,recharge_money,recharge_status,out_trade_no,recharge_date) VALUES(?,?,?,?,NOW()) ";
			pst2 = conn.prepareStatement(sql);
			pst2.setString(1, recharge.getRechargeUserId());
			pst2.setFloat(2, recharge.getRechargeMoney());
			pst2.setString(3, recharge.getRechargeStatus());
			pst2.setString(4, recharge.getOutTradeNo());
			log.info(sql + "参数:{ " + recharge.getRechargeUserId() + ","
					+ recharge.getRechargeMoney() + ","
					+ recharge.getRechargeStatus() + ","
					+ recharge.getOutTradeNo() + "}");
			pst2.execute();
			pst2.close();

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("旅游订单微信支付回滚异常!", e);
				throw new RuntimeException("旅游订单微信支付回滚异常!", e);
			}
			log.error("旅游订单微信支付异常!", e);
			throw new RuntimeException("旅游订单微信支付异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	/**
	 * @Title: updateOrders
	 * @Description:批量更新订单的状态
	 * @param @param list
	 * @return void 返回类型
	 * @throws
	 */
	public void updateOrders(List<Object> list) {
		Connection conn = null;
		PreparedStatement pst = null;
		String sql = "UPDATE T_ORDER SET order_status=? WHERE order_id=?";
		try {
			conn = openConnection();
			pst = conn.prepareStatement(sql);
			for (Object object : list) {
				OrderListEntity order = (OrderListEntity) object;
				pst.setString(1, order.getOrderStatus());
				pst.setInt(2, order.getOrderId());
				pst.addBatch();
			}
			pst.executeBatch();
		} catch (SQLException e) {
			log.error("批量更新订单的状态异常!", e);
			throw new RuntimeException("批量更新订单的状态异常!", e);
		} finally {
			close(pst, null, null, conn);
		}
	}

	public void drawbackSuccess(int orderId, String orderStatus) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		PreparedStatement pst5 = null;
		PreparedStatement pst6 = null;
		String sql = "";
		ResultSet rs = null;
		String orderUserId = "";
		String orderNO = "";
		String orderTourUserId = "";
		float orderDrawbackMoney = 0;
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			// 获取订单数据
			sql = "SELECT order_user_id,order_no,order_tour_user_id,order_drawback_money FROM T_ORDER WHERE order_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, orderId);
			log.info(sql + "参数:{" + orderId + "}");
			rs = pst.executeQuery();
			if (rs.next()) {
				orderUserId = rs.getString("order_user_id");
				orderNO = rs.getString("order_no");
				orderTourUserId = rs.getString("order_tour_user_id");
				orderDrawbackMoney = rs.getFloat("order_drawback_money");
			}
			rs.close();
			pst.close();

			// 导游钱包-订单价格
			sql = "UPDATE T_USER SET user_purse=user_purse-? WHERE user_id=?";
			pst2 = conn.prepareStatement(sql);
			pst2.setFloat(1, orderDrawbackMoney);
			pst2.setString(2, orderTourUserId);
			log.info(sql + "参数:{" + orderDrawbackMoney + "," + orderTourUserId
					+ "}");
			pst2.execute();
			pst2.close();

			// 游客钱包 + 订单价格
			sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
			pst3 = conn.prepareStatement(sql);
			pst3.setFloat(1, orderDrawbackMoney);
			pst3.setString(2, orderUserId);
			log.info(sql + "参数:{" + orderDrawbackMoney + "," + orderUserId
					+ "}");
			pst3.execute();
			pst3.close();

			// 更新订单状态
			sql = "UPDATE T_ORDER SET order_status=? WHERE order_id=?";
			pst4 = conn.prepareStatement(sql);
			pst4.setString(1, orderStatus);
			pst4.setInt(2, orderId);
			log.info(sql + "参数:{" + orderStatus + "," + orderId + "}");
			pst4.execute();
			pst4.close();

			// 导游退款减益记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst5 = conn.prepareStatement(sql);
			pst5.setString(1, orderNO);
			pst5.setString(2, orderTourUserId);
			pst5.setString(3, TOrderRecord.RECORD_TYPE_DRAWBACK_OUTCOME);
			pst5.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst5.setFloat(5, orderDrawbackMoney);
			log.info(sql + "参数:{" + orderNO + "," + orderTourUserId + ","
					+ TOrderRecord.RECORD_TYPE_DRAWBACK_OUTCOME + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + orderDrawbackMoney
					+ "}");
			pst5.execute();
			pst5.close();

			// 游客退款增益记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst6 = conn.prepareStatement(sql);
			pst6.setString(1, orderNO);
			pst6.setString(2, orderUserId);
			pst6.setString(3, TOrderRecord.RECORD_TYPE_DRAWBACK_INCOME);
			pst6.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst6.setFloat(5, orderDrawbackMoney);
			log.info(sql + "参数:{" + orderNO + "," + orderUserId + ","
					+ TOrderRecord.RECORD_TYPE_DRAWBACK_INCOME + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + orderDrawbackMoney
					+ "}");
			pst6.execute();
			pst6.close();

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("退款回滚出现异常!", e);
				throw new RuntimeException("退款回滚出现异常!", e);
			}
			log.error("退款出现异常!", e);
			throw new RuntimeException("退款出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	public void scheduleDrawbackSuccess(int orderId, String orderDrawbackSuccess) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		PreparedStatement pst5 = null;
		PreparedStatement pst6 = null;
		String sql = "";
		ResultSet rs = null;
		String orderUserId = "";
		String orderNO = "";
		String orderTourUserId = "";
		float orderDrawbackMoney = 0;
		String orderStatus = "";
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务
			// 获取订单数据
			sql = "SELECT order_user_id,order_no,order_tour_user_id,order_price,order_status FROM T_ORDER WHERE order_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, orderId);
			log.info(sql + "参数:{" + orderId + "}");
			rs = pst.executeQuery();
			if (rs.next()) {
				orderUserId = rs.getString("order_user_id");
				orderNO = rs.getString("order_no");
				orderTourUserId = rs.getString("order_tour_user_id");
				orderDrawbackMoney = rs.getFloat("order_drawback_money");
				orderStatus = rs.getString("order_status");
			}
			pst.close();
			// 如果7天之后，订单状态不是退款中,那么结束定时退款
			if (!orderStatus.equals(TOrder.ORDER_DRAWBACK)) {
				return;
			}
			// 导游钱包-订单价格
			sql = "UPDATE T_USER SET user_purse=user_purse-? WHERE user_id=?";
			pst2 = conn.prepareStatement(sql);
			pst2.setFloat(1, orderDrawbackMoney);
			pst2.setString(2, orderTourUserId);
			log.info(sql + "参数:{" + orderDrawbackMoney + "," + orderTourUserId
					+ "}");
			pst2.execute();
			pst2.close();

			// 游客钱包 + 订单价格
			sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
			pst3 = conn.prepareStatement(sql);
			pst3.setFloat(1, orderDrawbackMoney);
			pst3.setString(2, orderUserId);
			log.info(sql + "参数:{" + orderDrawbackMoney + "," + orderUserId
					+ "}");
			pst3.execute();
			pst3.close();

			// 更新订单状态
			sql = "UPDATE T_ORDER SET order_status=? WHERE order_id=?";
			pst4 = conn.prepareStatement(sql);
			pst4.setString(1, orderDrawbackSuccess);
			pst4.setInt(2, orderId);
			log.info(sql + "参数:{" + orderDrawbackSuccess + "," + orderId + "}");
			pst4.execute();
			pst4.close();

			// 生成 游客退款记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst5 = conn.prepareStatement(sql);
			pst5.setString(1, orderNO);
			pst5.setString(2, orderUserId);
			pst5.setString(3, TOrderRecord.RECORD_TYPE_DRAWBACK_INCOME);
			pst5.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst5.setFloat(5, orderDrawbackMoney);
			log.info(sql + "参数:{" + orderNO + "," + orderUserId
					+ TOrderRecord.RECORD_TYPE_DRAWBACK_INCOME + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + ","
					+ orderDrawbackMoney + "}");
			pst5.execute();
			pst5.close();

			// 生成 导游退款记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst6 = conn.prepareStatement(sql);
			pst6.setString(1, orderNO);
			pst6.setString(2, orderTourUserId);
			pst6.setString(3, TOrderRecord.RECORD_TYPE_DRAWBACK_OUTCOME);
			pst6.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst6.setFloat(5, orderDrawbackMoney);
			log.info(sql + "参数:{" + orderNO + "," + orderUserId
					+ TOrderRecord.RECORD_TYPE_DRAWBACK_OUTCOME + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + ","
					+ orderDrawbackMoney + "}");
			pst6.execute();
			pst6.close();

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("退款回滚出现异常!", e);
				throw new RuntimeException("退款回滚出现异常!", e);
			}
			log.error("退款出现异常!", e);
			throw new RuntimeException("退款出现异常!", e);
		} finally {
			close(null, null, rs, conn);
		}
	}

	public void orderCompleteByOrderId(int orderId) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		ResultSet rs = null;
		String sql = "";
		conn = openConnection();

		float orderPrice = 0;
		String orderTourUserId = "";
		String orderStatus = "";
		try {
			conn.setAutoCommit(false);// 开启事务

			// 根据orderId来获取订单数据
			sql = "SELECT order_tour_user_id,order_price,order_status FROM T_ORDER WHERE order_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, orderId);
			log.info(sql + "参数:{" + orderId + "}");
			rs = pst.executeQuery();
			if (rs.next()) {
				orderTourUserId = rs.getString("order_tour_user_id");
				orderPrice = rs.getFloat("order_price");
				orderStatus = rs.getString("order_status");
			}
			rs.close();
			pst.close();

			// 如果订单状态是 未付款 忽略
			if (!TOrder.ORDER_AGREE.equals(orderStatus)) {
				return;
			}
			// 导游钱包 + 订单价格
			sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
			pst2 = conn.prepareStatement(sql);
			pst2.setFloat(1, orderPrice);
			pst2.setString(2, orderTourUserId);
			log.info(sql + "参数:{" + orderPrice + "," + orderTourUserId + "}");
			pst2.execute();
			pst2.close();

			// 修改订单的状态 为 订单完成
			sql = "UPDATE T_ORDER SET order_status=? WHERE order_id=?";
			pst3 = conn.prepareStatement(sql);
			pst3.setString(1, TOrder.ORDER_SUCCESS);// 订单完成
			pst3.setInt(2, orderId);
			log.info(sql + "参数:{order_success," + orderId + "}");
			pst3.execute();
			pst3.close();

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("订单完成回滚出现异常!", e);
				throw new RuntimeException("订单完成回滚出现异常!", e);
			}
			log.error("订单完成出现异常!", e);
			throw new RuntimeException("订单完成出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	public void cancelOrder(TOrder order) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		PreparedStatement pst5 = null;
		PreparedStatement pst6 = null;
		ResultSet rs = null;
		String sql = "";

		String orderUserId = null;
		String orderTourUserId = null;
		String orderNo = null;
		float orderPrice = 0;

		float tourFee = 0;
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务
			// 根据订单id，获取订单的详情
			sql = "SELECT order_user_id,order_tour_user_id,order_price,order_no FROM T_ORDER WHERE order_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, order.getOrderId());
			rs = pst.executeQuery();
			log.info(sql + "参数:{" + order.getOrderId() + "}");
			if (rs.next()) {
				orderUserId = rs.getString("order_user_id");
				orderTourUserId = rs.getString("order_tour_user_id");
				orderPrice = rs.getFloat("order_price");
				orderNo = rs.getString("order_no");
			}
			pst.close();
			rs.close();

			// 将支付的钱退还到游客钱包中去
			sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
			pst2 = conn.prepareStatement(sql);
			if (order.getOrderStatus().equals(TOrder.ORDER_AGREE_CANCEL)) {
				orderPrice = (orderPrice * (1 - TOrder.ORDER_DRAWBACK_RATE));
				tourFee = (orderPrice * TOrder.ORDER_DRAWBACK_RATE);
			}
			pst2.setFloat(1, orderPrice);
			pst2.setString(2, orderUserId);
			log.info(sql + "参数:{" + orderPrice + "," + orderUserId + "}");
			pst2.executeUpdate();
			pst2.close();

			// 如果导游同意后取消 则将退换的钱 TOrder.ORDER_DRAWBACK_RATE 退入到导游账户上去
			if (tourFee > 0) {
				sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
				pst3 = conn.prepareStatement(sql);
				pst3.setFloat(1, tourFee);
				pst3.setString(2, orderTourUserId);
				log.info(sql + "参数:{" + tourFee + "," + orderTourUserId + "}");
				pst3.executeUpdate();
				pst3.close();

				// 导游违约金记录
				sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,?,NOW())";
				pst4 = conn.prepareStatement(sql);
				pst4.setString(1, orderNo);
				pst4.setString(2, orderTourUserId);
				pst4.setString(3, TOrderRecord.RECORD_TYPE_PENALTY);
				pst4.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
				pst4.setFloat(5, tourFee);
				log.info(sql + "参数:{" + orderNo + "," + orderTourUserId + ","
						+ TOrderRecord.RECORD_TYPE_PENALTY + ","
						+ TOrderRecord.RECORD_STATUS_SUCCESS + "," + tourFee
						+ "}");
				pst4.execute();
				pst4.close();
			}
			// 更新订单状态
			sql = "UPDATE T_ORDER SET order_status=? WHERE order_id=?";
			pst5 = conn.prepareStatement(sql);
			pst5.setString(1, order.getOrderStatus());
			pst5.setInt(2, order.getOrderId());
			log.info(sql + "参数:{" + order.getOrderStatus() + ","
					+ order.getOrderId() + "}");
			pst5.executeUpdate();
			pst5.close();

			// 游客退款收益记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst6 = conn.prepareStatement(sql);
			pst6.setString(1, orderNo);
			pst6.setString(2, orderUserId);
			pst6.setString(3, TOrderRecord.RECORD_TYPE_DRAWBACK_INCOME);
			pst6.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst6.setFloat(5, orderPrice);
			log.info(sql + "参数:{" + orderNo + "," + orderUserId + ","
					+ TOrderRecord.RECORD_TYPE_DRAWBACK_INCOME + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + "," + +orderPrice
					+ "}");
			pst6.execute();
			pst6.close();

			// 事务提交
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("取消订单出现异常!", e);
				throw new RuntimeException("取消订单出现异常!", e);
			}
			log.error("取消订单出现异常!", e);
			throw new RuntimeException("取消订单出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	public int saveOrderComment(TComment comment) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sql = "";

		int commentId = 0;
		String orderUserId = null;
		String orderTourUser_id = null;

		int orderCommentId = 0;
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务
			// 根据订单id，获取订单的详情
			sql = "SELECT c.comment_id,o.order_user_id,o.order_tour_user_id FROM T_ORDER o LEFT JOIN T_COMMENT c ON(o.order_id=c.order_id) WHERE o.order_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, comment.getOrderId());
			rs = pst.executeQuery();
			log.info(sql + "参数:{" + comment.getOrderId() + "}");
			if (rs.next()) {
				commentId = rs.getInt("comment_id");
				orderUserId = rs.getString("order_user_id");
				orderTourUser_id = rs.getString("order_tour_user_id");
			}
			rs.close();
			pst.close();

			// 修改订单评价
			if (commentId > 0) {
				sql = "UPDATE T_COMMENT SET comment_content=?,service_index=?,fresh_index=?,ratio_index=?,total_index=?,comment_images=? WHERE comment_id=?";
				pst2 = conn.prepareStatement(sql);
				pst2.setString(1, comment.getCommentContent());
				pst2.setFloat(2, comment.getServiceIndex());
				pst2.setFloat(3, comment.getFreshIndex());
				pst2.setFloat(4, comment.getRatioIndex());
				pst2.setFloat(5, comment.getTotalIndex());
				pst2.setString(6, comment.getCommentImages());
				pst2.setInt(7, commentId);
				pst2.executeUpdate();
				log.info(sql + "参数:{" + comment.getCommentContent() + ","
						+ comment.getServiceIndex() + ","
						+ comment.getFreshIndex() + ","
						+ comment.getRatioIndex() + ","
						+ comment.getTotalIndex() + ","
						+ comment.getCommentImages() + "," + commentId + "}");
				orderCommentId = commentId;
				pst2.close();
			}
			// 添加订单评价
			else {
				sql = "INSERT INTO T_COMMENT(comment_user_id,commented_user_id,comment_type,comment_content,order_id,service_index,fresh_index,ratio_index,total_index,comment_images,comment_date) VALUES(?,?,?,?,?,?,?,?,?,?,NOW())";
				pst3 = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pst3.setString(1, orderUserId);
				pst3.setString(2, orderTourUser_id);
				pst3.setString(3, "2");// 订单评价
				pst3.setString(4, comment.getCommentContent());
				pst3.setInt(5, comment.getOrderId());
				pst3.setFloat(6, comment.getServiceIndex());
				pst3.setFloat(7, comment.getFreshIndex());
				pst3.setFloat(8, comment.getRatioIndex());
				pst3.setFloat(9, comment.getTotalIndex());
				pst3.setString(10, comment.getCommentImages());
				log.info(sql + "参数:{" + orderUserId + "," + orderTourUser_id
						+ ",2," + comment.getCommentContent() + ","
						+ comment.getOrderId() + ","
						+ comment.getServiceIndex() + ","
						+ comment.getFreshIndex() + ","
						+ comment.getRatioIndex() + ","
						+ comment.getTotalIndex() + ","
						+ comment.getCommentImages() + "}");
				pst3.executeUpdate();
				rs2 = pst3.getGeneratedKeys();
				if (rs2.next()) {
					orderCommentId = rs2.getInt(1);
				}
				rs2.close();
				pst3.close();
			}

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("保存订单评价出现异常!", e);
				throw new RuntimeException("保存订单评价出现异常!", e);
			}
			log.error("保存订单评价出现异常!", e);
			throw new RuntimeException("保存订单评价出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
		return orderCommentId;
	}

	public void orderTourDeny(TOrder order) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		ResultSet rs = null;
		String sql = "";

		float orderPrice = 0;
		String orderUserId = "";
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			// 根据订单id，获取订单的详情
			sql = "SELECT order_price,order_user_id FROM T_ORDER WHERE order_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, order.getOrderId());
			log.info(sql + "参数:{" + order.getOrderId() + "}");
			rs = pst.executeQuery();
			if (rs.next()) {
				orderPrice = rs.getFloat("order_price");
				orderUserId = rs.getString("order_user_id");
			}
			rs.close();
			pst.close();

			// 将钱打到游客账户上去
			sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
			pst2 = conn.prepareStatement(sql);
			pst2.setFloat(1, orderPrice);
			pst2.setString(2, orderUserId);
			log.info(sql + "参数:{" + orderPrice + "," + orderUserId + "}");
			pst2.executeUpdate();
			pst2.close();

			// 更新订单状态
			sql = "UPDATE T_ORDER SET order_status=? WHERE order_id=?";
			pst3 = conn.prepareStatement(sql);
			pst3.setString(1, order.getOrderStatus());
			pst3.setInt(2, order.getOrderId());
			log.info(sql + "参数:{" + order.getOrderStatus() + ","
					+ order.getOrderId() + "}");
			pst3.executeUpdate();
			pst3.close();

			// 生成退款的订单记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst4 = conn.prepareStatement(sql);
			pst4.setString(1, order.getOrderNo());
			pst4.setString(2, orderUserId);
			pst4.setString(3, TOrderRecord.RECORD_TYPE_DRAWBACK_INCOME);
			pst4.setString(4, TOrderRecord.RECORD_STATUS_SUCCESS);
			pst4.setFloat(5, orderPrice);
			log.info(sql + "参数:{" + order.getOrderId() + "," + orderUserId
					+ ",0," + TOrderRecord.RECORD_TYPE_DRAWBACK_INCOME + ","
					+ TOrderRecord.RECORD_STATUS_SUCCESS + "," + orderPrice
					+ "}");
			pst4.execute();
			pst4.close();

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("导游拒绝订单出现异常!", e);
				throw new RuntimeException("导游拒绝订单出现异常!", e);
			}
			log.error("导游拒绝订单出现异常!", e);
			throw new RuntimeException("导游拒绝订单出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	public String wexinPayCall(String status, String outTradeNo) {
		if (status == null || status.equals("") || status.equals("FAIL")) {
			status = TOrderRecord.RECORD_STATUS_CLOSE;
		} else if (status.equals("SUCCESS")) {
			status = TOrderRecord.RECORD_STATUS_SUCCESS;
		}

		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		PreparedStatement pst5 = null;
		PreparedStatement pst6 = null;
		ResultSet rs = null;
		String sql = "";

		String recordStatus = "";
		String recordType = "";
		float recordTradeMoney = 0;
		String userId = "";
		try {
			conn = openConnection();
			conn.setAutoCommit(false);// 开启事务
			// 根据订单流水号，获取订单记录
			sql = "SELECT record_status,record_type,record_trade_money,user_id FROM T_ORDER_RECORD WHERE out_trade_no=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, outTradeNo);
			log.info(sql + "参数:{" + outTradeNo + "}");
			rs = pst.executeQuery();
			if (rs.next()) {
				recordStatus = rs.getString("record_status");
				recordType = rs.getString("record_type");
				recordTradeMoney = rs.getFloat("record_trade_money");
				userId = rs.getString("user_id");
			}
			rs.close();
			pst.close();
			// 支付结束
			if (recordStatus.equals(TOrderRecord.RECORD_STATUS_SUCCESS)
					|| recordStatus.equals(TOrderRecord.RECORD_STATUS_CLOSE)) {
				log.info("微信支付已经结束,out_trade_no =" + outTradeNo);
				return "SUCCESS";
			}
			// 更新订单记录
			sql = "UPDATE T_ORDER_RECORD SET record_status=? WHERE out_trade_no=?";
			pst2 = conn.prepareStatement(sql);
			pst2.setString(1, status);
			pst2.setString(2, outTradeNo);
			log.info(sql + "参数:{" + status + "," + outTradeNo + "}");
			pst2.executeUpdate();
			pst2.close();

			// 更新充值订单记录
			sql = "UPDATE T_ORDER_RECHARGE SET recharge_status=? WHERE out_trade_no=?";
			pst3 = conn.prepareStatement(sql);
			pst3.setString(1, status);
			pst3.setString(2, outTradeNo);
			log.info(sql + "参数:{" + status + "," + outTradeNo + "}");
			pst3.executeUpdate();
			pst3.close();

			// 支付失败 删除小费记录
			if (TOrderRecord.RECORD_STATUS_CLOSE.equals(status)
					&& TOrderRecord.RECORD_TYPE_GRATUITY_WX_SEND
							.equals(recordType)) {
				sql = "DELETE FROM T_ORDER_GRATUITY  WHERE out_trade_no=?";
				pst4 = conn.prepareStatement(sql);
				pst4.setString(1, outTradeNo);
				log.info(sql + "参数:{" + outTradeNo + "}");
				pst4.executeUpdate();
				pst4.close();
			}
			// 支付成功 生成一个定时器
			if (TOrderRecord.RECORD_STATUS_SUCCESS.equals(status)
					&& TOrderRecord.RECORD_TYPE_GRATUITY_WX_SEND
							.equals(recordType)) {
				new GratuityUnRecivedThread(outTradeNo).start();
			}

			// 支付成功 修改路线订单状态
			if (TOrderRecord.RECORD_TYPE_ORDER_WX_SEND.equals(recordType)) {
				sql = "UPDATE T_ORDER SET order_status=? WHERE order_no=?";
				pst5 = conn.prepareStatement(sql);
				String order_status = "";
				switch (status) {
				// 已支付
				case "2":
					order_status = TOrder.ORDER_PAYMENT;
					break;
				// 已失效
				case "3":
					order_status = TOrder.ORDER_INVALID;
					break;
				}
				pst5.setString(1, order_status);
				pst5.setString(2, outTradeNo);
				log.info(sql + "参数:{" + order_status + "," + outTradeNo + "}");
				pst5.execute();
				pst5.close();
			}
			// 充值成功 将钱打到用户账号上去
			if (TOrderRecord.RECORD_STATUS_SUCCESS.equals(status)
					&& TOrderRecord.RECORD_TYPE_RECHARGE.equals(recordType)) {
				sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
				pst6 = conn.prepareStatement(sql);
				pst6.setFloat(1, recordTradeMoney);
				pst6.setString(2, userId);
				log.info(sql + "参数:{ " + recordTradeMoney + "," + userId + "}");
				pst6.executeUpdate();
				pst6.close();
			}
			conn.commit();
			return "SUCCESS";
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("微信回调回滚出现异常!", e);
			}
			log.error("微信回调出现异常!", e);
			return "FAIL";
		} finally {
			close(null, null, null, conn);
		}
	}

	public void CloseCharge(String outTradeNo) {

		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		PreparedStatement pst5 = null;
		ResultSet rs = null;
		String sql = "";

		String recordType = "";
		try {
			conn = openConnection();
			conn.setAutoCommit(false);// 开启事务

			// 根据订单流水号，获取订单记录
			sql = "SELECT record_type FROM T_ORDER_RECORD WHERE out_trade_no=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, outTradeNo);
			log.info(sql + "参数:{" + outTradeNo + "}");
			rs = pst.executeQuery();
			if (rs.next()) {
				recordType = rs.getString("record_type");
			}
			rs.close();
			pst.close();

			// 更新订单记录
			sql = "UPDATE T_ORDER_RECORD SET record_status=? WHERE out_trade_no=?";
			pst2 = conn.prepareStatement(sql);
			pst2.setString(1, TOrderRecord.RECORD_STATUS_CANCEL);
			pst2.setString(2, outTradeNo);
			log.info(sql + "参数:{" + TOrderRecord.RECORD_STATUS_CANCEL + ","
					+ outTradeNo + "}");
			pst2.executeUpdate();
			pst2.close();

			// 更新充值订单记录
			sql = "UPDATE T_ORDER_RECHARGE SET recharge_status=? WHERE out_trade_no=?";
			pst3 = conn.prepareStatement(sql);
			pst3.setString(1, TOrderRecord.RECORD_STATUS_CANCEL);
			pst3.setString(2, outTradeNo);
			log.info(sql + "参数:{" + TOrderRecord.RECORD_STATUS_CANCEL + ","
					+ outTradeNo + "}");
			pst3.executeUpdate();
			pst3.close();

			// 微信 小费 支付
			if (recordType.equals(TOrderRecord.RECORD_TYPE_GRATUITY_WX_SEND)) {
				sql = "DELETE FROM T_ORDER_GRATUITY  WHERE out_trade_no=?";
				pst4 = conn.prepareStatement(sql);
				pst4.setString(1, outTradeNo);
				log.info(sql + "参数:{" + outTradeNo + "}");
				pst4.executeUpdate();
				pst4.close();
			}

			// 旅游 微信 支付
			if (recordType.equals(TOrderRecord.RECORD_TYPE_ORDER_WX_SEND)) {
				sql = "UPDATE T_ORDER SET order_status=? WHERE order_no=?";
				pst5 = conn.prepareStatement(sql);
				pst5.setString(1, TOrder.ORDER_CREATE);
				pst5.setString(2, outTradeNo);
				log.info(sql + "参数:{" + TOrder.ORDER_CREATE + "," + outTradeNo
						+ "}");
				pst5.execute();
				pst5.close();
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("取消支付回滚出现异常!", e);
			}
			log.error("取消支付出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	public void saveOrderRechargeAndRecord(TOrderRecharge recharge) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		String sql = "";

		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务
			// 保存充值记录
			sql = "INSERT INTO T_ORDER_RECHARGE(recharge_user_id,recharge_money,recharge_status,out_trade_no,recharge_date) VALUES(?,?,?,?,NOW()) ";
			pst = conn.prepareStatement(sql);
			pst.setString(1, recharge.getRechargeUserId());
			pst.setFloat(2, recharge.getRechargeMoney());
			pst.setString(3, recharge.getRechargeStatus());
			pst.setString(4, recharge.getOutTradeNo());
			log.info(sql + "参数:{ " + recharge.getRechargeUserId() + ","
					+ recharge.getRechargeMoney() + ","
					+ recharge.getRechargeStatus() + ","
					+ recharge.getOutTradeNo() + "}");
			pst.execute();
			pst.close();

			// 保存订单充值记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst2 = conn.prepareStatement(sql);
			pst2.setString(1, recharge.getOutTradeNo());
			pst2.setString(2, recharge.getRechargeUserId());
			pst2.setString(3, TOrderRecord.RECORD_TYPE_RECHARGE);
			pst2.setString(4, TOrderRecord.RECORD_STATUS_RUNING);
			pst2.setFloat(5, recharge.getRechargeMoney());
			log.info(sql + "参数:{ " + recharge.getOutTradeNo() + ","
					+ recharge.getRechargeUserId() + ","
					+ TOrderRecord.RECORD_TYPE_RECHARGE + ","
					+ TOrderRecord.RECORD_STATUS_RUNING + ","
					+ recharge.getRechargeMoney() + "}");
			pst2.execute();
			pst2.close();

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("保存充值记录和订单记录出现异常!", e);
				throw new RuntimeException("保存充值记录和订单记录出现异常!", e);
			}
			log.error("保存充值记录和订单记录出现异常!", e);
			throw new RuntimeException("保存充值记录和订单记录出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	/**
	 * @Title: scheduleGratuityUnReceived
	 * @Description: 红包逾期未被领取
	 * @param @param gratuityId
	 * @return void 返回类型
	 * @throws
	 */
	public void scheduleGratuityUnReceived(String outTradeNo) {
		// 查看该红包是否被领取
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		ResultSet rs = null;
		String sql = "";

		String gratuityStatus = "";
		String gratuitySenderUserId = "";
		float gratuityTotalMoney = 0;

		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			sql = "SELECT g.gratuity_status,g.gratuity_sender_user_id,g.gratuity_total_money FROM T_ORDER_GRATUITY g WHERE g.out_trade_no=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, outTradeNo);
			log.info(sql + "参数:{ " + outTradeNo + "}");
			rs = pst.executeQuery();
			if (rs.next()) {
				gratuityStatus = rs.getString(1);
				gratuitySenderUserId = rs.getString(2);
				gratuityTotalMoney = rs.getFloat(3);
			}
			rs.close();
			pst.close();

			// 红包 对方未领取
			if (TOrderGratuity.GRATUITY_STATUS_RUNING.equals(gratuityStatus)) {
				// 返回红包的钱到发送者钱包中去
				sql = "UPDATE T_USER SET user_purse=user_purse+? WHERE user_id=?";
				pst2 = conn.prepareStatement(sql);
				pst2.setFloat(1, gratuityTotalMoney);
				pst2.setString(2, gratuitySenderUserId);
				log.info(sql + "参数:{ " + gratuityTotalMoney + ","
						+ gratuitySenderUserId + "}");
				pst2.executeUpdate();
				pst2.close();

				// 修改红包状态 为未领取状态
				sql = "UPDATE T_ORDER_GRATUITY SET gratuity_status=?,gratuity_end_date=NOW() WHERE out_trade_no=?";
				pst3 = conn.prepareStatement(sql);
				pst3.setString(1, TOrderGratuity.GRATUITY_STATUS_EXPIRE);
				pst3.setString(2, outTradeNo);
				log.info(sql + "参数:{ " + TOrderGratuity.GRATUITY_STATUS_EXPIRE
						+ outTradeNo + "}");
				pst3.executeUpdate();
				pst3.close();
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("清算逾期未领取订单出现异常!", e);
				throw new RuntimeException("清算逾期未领取订单出现异常", e);
			}
			log.error("清算逾期未领取订单出现异常!", e);
			throw new RuntimeException("清算逾期未领取订单出现异常", e);
		} finally {
			close(null, null, null, conn);
		}
	}
}
