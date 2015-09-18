package com.uugty.validate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uugty.validate.dao.IOrderDao;
import com.uugty.validate.domain.Order;
import com.uugty.validate.utils.Page;

@Repository(IOrderDao.SERVER_NAME)
public class OrderDaoImpl extends BaseDaoImpl<Order> implements IOrderDao {

	@Override
	public List<Order> findAllOrder(Order order, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginIndex", page.getBeginIndex());
		map.put("pageSize", page.getPageSize());
		map.put("orderStatus", order.getOrderStatus());
		return findAllByStatement("findAllOrder", map);
	}

	@Override
	public int findAllOrderCount(Order order) {
		return this.count("findAllOrderCount", order);
	}
}
