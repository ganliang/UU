package com.uugty.validate.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uugty.validate.domain.WithdrawCash;
import com.uugty.validate.utils.Page;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
public interface IWithdrawCashService {

	public static final String SERVER_NAME = "com.uugty.validate.service.impl.WithdrawCashServiceImpl";

	public int findAllWithdrawCount(WithdrawCash withdraw);

	public List<WithdrawCash> findAllWithdraw(WithdrawCash withdraw, Page page);

	public void updateWithDrawStatus(WithdrawCash withdraw);

}
