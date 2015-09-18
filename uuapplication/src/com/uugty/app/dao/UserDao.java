package com.uugty.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.uugty.app.domain.TOrderRecord;
import com.uugty.app.domain.TUserBank;
import com.uugty.app.domain.TWithdrawCash;

/**
 * @ClassName: UserDao
 * @Description:用户dao
 * @author ganliang
 * @date 2015年6月9日 下午6:06:53
 */
public class UserDao extends BaseDao {

	private static Logger log = Logger.getLogger(UserDao.class);

	public int saveWithDraw(TWithdrawCash withdraw) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;

		ResultSet rs = null;
		ResultSet rs2 = null;
		String sql = "";

		String bankCard = null;
		String bankCardType = null;

		int withDrawId = 0;

		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			// 根据银行id 来获取银行的数据
			sql = "SELECT bank_card,bank_card_type FROM T_USER_BANK WHERE bank_id=?";
			pst = conn.prepareStatement(sql);
			pst.setFloat(1, withdraw.getWithDrawBankId());
			log.info(sql + "参数:{" + withdraw.getWithDrawBankId() + "}");
			rs = pst.executeQuery();
			if (rs.next()) {
				bankCard = rs.getString("bank_card");
				bankCardType = rs.getString("bank_card_type");
			}
			rs.close();
			pst.close();

			// 保存提现记录
			sql = "INSERT INTO T_WITHDRAW_CASH(user_id,withdraw_type,withdraw_bank_card,withdraw_bank_card_type,withdraw_money,withdraw_status,out_trade_no,withdraw_date) VALUES(?,?,?,?,?,?,?,NOW())";
			pst2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst2.setString(1, withdraw.getUserId());
			pst2.setInt(2, 1);// 提现到银行卡
			pst2.setString(3, bankCard);
			pst2.setString(4, bankCardType);
			pst2.setFloat(5, withdraw.getWithdrawMoney());
			pst2.setInt(6, 1);// 正在提现
			pst2.setString(7, withdraw.getOutTradeNo());// 正在提现
			log.info(sql + "参数:{" + withdraw.getUserId() + ",1," + bankCard
					+ "," + bankCardType + "," + withdraw.getWithdrawMoney()
					+ ",1" + withdraw.getOutTradeNo() + "}");
			pst2.executeUpdate();
			rs2 = pst2.getGeneratedKeys();
			if (rs2.next()) {
				withDrawId = rs2.getInt(1);
			}
			rs.close();
			pst2.close();

			// 将提现用户的钱包输的钱 - 提现的钱数
			sql = "UPDATE T_USER SET user_purse=user_purse-? WHERE user_id=?";
			pst3 = conn.prepareStatement(sql);
			pst3.setFloat(1, withdraw.getWithdrawMoney());
			pst3.setString(2, withdraw.getUserId());
			log.info(sql + "参数:{" + withdraw.getWithdrawMoney() + ","
					+ withdraw.getUserId() + "}");
			pst3.executeUpdate();
			pst3.close();

			// 保存订单(提现) 记录
			sql = "INSERT INTO T_ORDER_RECORD(out_trade_no,user_id,record_type,record_status,record_trade_money,record_trade_date) VALUES(?,?,?,?,?,NOW())";
			pst4 = conn.prepareStatement(sql);
			pst4.setString(1, withdraw.getOutTradeNo());
			pst4.setString(2, withdraw.getUserId());
			pst4.setString(3, TOrderRecord.RECORD_TYPE_WIDTHDRAW);
			pst4.setString(4, TOrderRecord.RECORD_STATUS_RUNING);
			pst4.setFloat(5, withdraw.getWithdrawMoney());
			log.info(sql + "参数:{" + withdraw.getOutTradeNo() + ","
					+ withdraw.getUserId() + TOrderRecord.RECORD_TYPE_WIDTHDRAW
					+ TOrderRecord.RECORD_STATUS_RUNING
					+ withdraw.getWithdrawMoney() + "}");
			pst4.executeUpdate();
			pst4.close();

			conn.commit();// 事务关闭
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("提现回滚异常!", e);
				throw new RuntimeException("提现回滚异常!", e);
			}
			log.error("提现异常!", e);
			throw new RuntimeException("提现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
		return withDrawId;
	}

	public int saveUserBondBank(TUserBank userBank) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;

		ResultSet rs = null;
		String sql = "";
		int bankId = 0;
		conn = openConnection();
		try {
			conn.setAutoCommit(false);// 开启事务

			// 判断新添加的 是默认支付的银行卡
			if (userBank.getBankIsDefault() == 1) {
				sql = "UPDATE T_USER_BANK SET bank_is_default=0 WHERE user_id=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, userBank.getUserId());
				log.info(sql + "参数:{" + userBank.getUserId() + "}");
				pst.executeUpdate();
				pst.close();
			}
			sql = "INSERT INTO T_USER_BANK(user_id,bank_card,bank_card_type,bank_is_default,bank_owner,bank_bound_date) VALUES(?,?,?,?,?,NOW())";
			pst2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst2.setString(1, userBank.getUserId());
			pst2.setString(2, userBank.getBankCard());
			pst2.setString(3, userBank.getBankCardType());
			pst2.setInt(4, userBank.getBankIsDefault());
			pst2.setString(5, userBank.getBankOwner());
			log.info(sql + "参数:{" + userBank.getUserId() + ","
					+ userBank.getBankCard() + "," + userBank.getBankCardType()
					+ "," + userBank.getBankIsDefault() + ","
					+ userBank.getBankOwner() + "}");
			pst2.executeUpdate();
			rs = pst2.getGeneratedKeys();
			if (rs.next()) {
				bankId = rs.getInt(1);
			}
			rs.close();
			pst2.close();

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("绑定银行卡号回滚异常!", e);
				throw new RuntimeException("绑定银行卡号回滚异常!", e);
			}
			log.error("绑定银行卡号出现异常!", e);
			throw new RuntimeException("绑定银行卡号出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
		return bankId;
	}
}
