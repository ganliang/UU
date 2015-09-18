package com.uugty.validate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uugty.validate.dao.IWithdrawCashDao;
import com.uugty.validate.domain.WithdrawCash;
import com.uugty.validate.utils.Page;

@Repository(IWithdrawCashDao.SERVER_NAME)
public class WithdrawCashDaoImpl extends BaseDaoImpl<WithdrawCash> implements
		IWithdrawCashDao {

	@Override
	public List<WithdrawCash> findAllWithdraw(WithdrawCash withdraw, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginIndex", page.getBeginIndex());
		map.put("pageSize", page.getPageSize());
		map.put("withdrawStatus", withdraw.getWithdrawStatus());
		return findAllByStatement("findAllWithdraw", map);
	}

	@Override
	public int findAllWithdrawCount(WithdrawCash withdraw) {
		return this.count("findAllWithdrawCount", withdraw);
	}

	@Override
	public void updateWithDrawStatus(WithdrawCash withdraw) {
		this.updateObject("updateWithDrawStatus", withdraw);
	}
}
