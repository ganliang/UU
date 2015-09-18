package com.uugty.validate.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uugty.validate.domain.Order;
import com.uugty.validate.service.IOrderService;
import com.uugty.validate.utils.Page;

@Controller("OrderController")
@RequestMapping("/order")
public class OrderController {

	@Resource(name = IOrderService.SERVER_NAME)
	private IOrderService orderService;

	/** 获取所有的订单 */
	@RequestMapping("/getAllOrder")
	public ModelAndView getAllOrder(Order order, Map<String, Object> model) {
		int totalSize = orderService.findAllOrderCount(order);
		Page page = new Page(totalSize, order.getCurrentPage());
		List<Order> orderList = orderService.findAllOrder(order, page);
		model.put("orderList", orderList);
		model.put("orderStatus", order.getOrderStatus());
		model.put("page", page);
		return new ModelAndView("order/showAllOrder", model);
	}
}
