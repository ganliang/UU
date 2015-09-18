package com.uugty.validate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uugty.validate.dao.IWithdrawCashDao;
import com.uugty.validate.domain.WithdrawCash;
import com.uugty.validate.service.IWithdrawCashService;
import com.uugty.validate.utils.Page;

@Service(IWithdrawCashService.SERVER_NAME)
public class WithdrawCashServiceImpl implements IWithdrawCashService {
	// 注入dao
	@Resource(name = IWithdrawCashDao.SERVER_NAME)
	private IWithdrawCashDao withDrawDao;

	@Override
	public int findAllWithdrawCount(WithdrawCash withdraw) {
		return withDrawDao.findAllWithdrawCount(withdraw);
	}

	@Override
	public List<WithdrawCash> findAllWithdraw(WithdrawCash withdraw, Page page) {
		return withDrawDao.findAllWithdraw(withdraw, page);
	}

	@Override
	public void updateWithDrawStatus(WithdrawCash withdraw) {
		withDrawDao.updateWithDrawStatus(withdraw);
	}

}
