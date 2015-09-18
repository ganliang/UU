package com.uugty.validate.dao;

import java.util.List;

import com.uugty.validate.domain.Order;
import com.uugty.validate.utils.Page;

public interface IOrderDao {

	public static final String SERVER_NAME = "com.uugty.validate.dao.impl.OrderDaoImpl";

	public int findAllOrderCount(Order order);

	public List<Order> findAllOrder(Order order, Page page);
}
