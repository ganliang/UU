package com.uugty.validate.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uugty.validate.domain.Order;
import com.uugty.validate.utils.Page;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
public interface IOrderService {
	public static final String SERVER_NAME = "com.uugty.validate.service.impl.OrderServiceImpl";

	public int findAllOrderCount(Order order);

	public List<Order> findAllOrder(Order order, Page page);
}
