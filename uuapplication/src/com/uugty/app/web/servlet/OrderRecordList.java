package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TOrderRecord;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.WebUtil;

/**
 * @ClassName: OrderRecordList
 * @Description: 订单记录集合
 * @author ganliang
 * @date 2015年7月22日 下午2:27:36
 */
@WebServlet(urlPatterns = { "/orderRecord.do" }, asyncSupported = false)
public class OrderRecordList extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 4282321404515878700L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TOrderRecord record = (TOrderRecord) BeanUtil.populate(request,
				TOrderRecord.class);
		IOrderService orderService = new OrderServiceImpl();

		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		record.setUserId(sessionUser.getUserId());

		// 获取账单列表
		List<Object> records = orderService.getOrderRecordList(record);
		ResponseEntity.printlns(response, records);
	}
}
