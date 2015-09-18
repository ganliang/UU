package com.uugty.validate.dao;

import java.util.List;

import com.uugty.validate.domain.WithdrawCash;
import com.uugty.validate.utils.Page;

public interface IWithdrawCashDao {

	public static final String SERVER_NAME = "com.uugty.validate.dao.impl.WithdrawCashDaoImpl";

	public int findAllWithdrawCount(WithdrawCash withdraw);

	public List<WithdrawCash> findAllWithdraw(WithdrawCash withdraw, Page page);

	public void updateWithDrawStatus(WithdrawCash withdraw);

}
