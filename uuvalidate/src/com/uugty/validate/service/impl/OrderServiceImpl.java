package com.uugty.validate.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uugty.validate.dao.IOrderDao;
import com.uugty.validate.domain.Order;
import com.uugty.validate.service.IOrderService;
import com.uugty.validate.utils.Page;

@Service(IOrderService.SERVER_NAME)
public class OrderServiceImpl implements IOrderService {

	// 注入dao
	@Resource(name = IOrderDao.SERVER_NAME)
	private IOrderDao orderDao;

	@Override
	public int findAllOrderCount(Order order) {
		return orderDao.findAllOrderCount(order);
	}

	@Override
	public List<Order> findAllOrder(Order order, Page page) {
		return orderDao.findAllOrder(order, page);
	}
}
